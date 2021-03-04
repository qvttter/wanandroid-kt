/********************************************************************************
 * Copyright (c) 2018 Calypso Networks Association https://www.calypsonet-asso.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information regarding copyright
 * ownership.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package com.li.mykotlinapp.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.li.mykotlinapp.test.core.seproxy.SeSelector;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleApplicationSelectionException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleChannelControlException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleIOReaderException;
import com.li.mykotlinapp.test.core.seproxy.message.*;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.AbstractLocalReader;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.SmartSelectionReader;
import com.li.mykotlinapp.test.core.seproxy.protocol.SeCommonProtocols;
import com.li.mykotlinapp.test.core.seproxy.protocol.SeProtocol;
import com.li.mykotlinapp.test.core.seproxy.protocol.TransmissionMode;
import com.li.mykotlinapp.test.core.util.ByteArrayUtil;
import org.simalliance.openmobileapi.Channel;
import org.simalliance.openmobileapi.Reader;
import org.simalliance.openmobileapi.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.util.Log;

/**
 * Implementation of the {@link AndroidOmapiReader} based on the {@link AbstractLocalReader}
 */
final class AndroidOmapiReaderImpl extends AbstractLocalReader implements AndroidOmapiReader, SmartSelectionReader {

    private static final Logger logger =
            LoggerFactory.getLogger(AndroidOmapiReaderImpl.class);

    private static final String TAG = AndroidOmapiReaderImpl.class.getSimpleName();

    private Reader omapiReader;
    private Session session = null;
    private Channel openChannel = null;
    private Map<String, String> parameters = new HashMap<String, String>();

    protected AndroidOmapiReaderImpl(String pluginName, Reader omapiReader, String readerName) {
        super(pluginName, readerName);
        this.omapiReader = omapiReader;
    }


    @Override
    public Map<String, String> getParameters() {
        Log.w(TAG, "No parameters are supported by AndroidOmapiReaderImpl");
        return parameters;
    }

    @Override
    public void setParameter(String key, String value) {
        Log.w(TAG, "No parameters are supported by AndroidOmapiReaderImpl");
        parameters.put(key, value);
    }

    /**
     * The transmission mode is always CONTACTS in an OMAPI reader
     *
     * @return the current transmission mode
     */
    @Override
    public TransmissionMode getTransmissionMode() {
        return TransmissionMode.CONTACTS;
    }

    /**
     * Check if a SE is present in this reader. see {@link Reader#isSecureElementPresent()}
     * @return True if the SE is present, false otherwise
     */
    @Override
    protected boolean checkSePresence() {
        return omapiReader.isSecureElementPresent();
    }

    /**
     * Get the SE Answer To Reset
     * @return a byte array containing the ATR or null if no session was available
     */
    @Override
    protected byte[] getATR() {
        if(session != null) {
            Log.i(TAG, "Retrieving ATR from session...");
            return session.getATR();
        }
        else {
            return null;
        }
    }

    /**
     * Open a logical channel by selecting the application
     * @param aidSelector the selection parameters
     * @return a ApduResponse built from the FCI data resulting from the application selection
     * @throws KeypleIOReaderException
     * @throws KeypleChannelControlException
     * @throws KeypleApplicationSelectionException
     */
    public ApduResponse openChannelForAid(SeSelector.AidSelector aidSelector) throws KeypleIOReaderException, KeypleChannelControlException, KeypleApplicationSelectionException {
        if(aidSelector.getAidToSelect() == null) {
            try {
                openChannel = session.openBasicChannel(null);
            } catch (IOException e) {
                e.printStackTrace();
                throw new KeypleIOReaderException("IOException while opening basic channel.");
            } catch (SecurityException e) {
                throw new KeypleChannelControlException("Error while opening basic channel, SE_SELECTOR = " +  aidSelector.toString(), e.getCause());
            }

            if (openChannel == null) {
                throw new KeypleIOReaderException("Failed to open a basic channel.");
            }
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("[{}] openLogicalChannel => Select Application with AID = {}",
                        this.getName(), ByteArrayUtil.toHex(aidSelector.getAidToSelect().getValue()));
            }
            try {
                openChannel = session.openLogicalChannel(aidSelector.getAidToSelect().getValue(),
                        (byte)(aidSelector.getFileOccurrence().getIsoBitMask()
                                | aidSelector.getFileControlInformation().getIsoBitMask()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new KeypleIOReaderException("IOException while opening logical channel.");
            } catch (NoSuchElementException e) {
                throw new KeypleApplicationSelectionException(
                        "Error while selecting application : " + ByteArrayUtil.toHex(aidSelector.getAidToSelect().getValue()), e);
            } catch (SecurityException e) {
                throw new KeypleChannelControlException("Error while opening logical channel, aid :" + ByteArrayUtil.toHex(aidSelector.getAidToSelect().getValue()), e.getCause());
            }

            if (openChannel == null) {
                throw new KeypleIOReaderException("Failed to open a logical channel.");
            }
        }
        /* get the FCI and build an ApduResponse */
        return new ApduResponse(openChannel.getSelectResponse(), aidSelector.getSuccessfulSelectionStatusCodes());
    }

    @Override
    public boolean isPhysicalChannelOpen() {
        if(session != null && !session.isClosed()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void openPhysicalChannel() throws KeypleChannelControlException {
        try {
            session = omapiReader.openSession();
        } catch (IOException e) {
            e.printStackTrace();
            throw new KeypleChannelControlException("IOException while opening physical channel.");
        }
    }

    /**
     * Close session see org.simalliance.openmobileapi.Session#close()
     */
    @Override
    protected void closePhysicalChannel() {
        // close physical channel if exists
        if (openChannel != null) {
            openChannel.getSession().close();
            openChannel = null;
        }
    }

    /**
     * Transmit an APDU command (as per ISO/IEC 7816) to the SE see org.simalliance.openmobileapi.Channel#transmit(byte[])
     * 
     * @param apduIn byte buffer containing the ingoing data
     * @return apduOut response
     * @throws KeypleIOReaderException if error while sending or receiving bytes
     */
    @Override
    protected byte[] transmitApdu(byte[] apduIn) throws KeypleIOReaderException {
        // Initialization
        Log.d(TAG, "Data Length to be sent to tag : " + apduIn.length);
        Log.d(TAG, "Data in : " + ByteArrayUtil.toHex(apduIn));
        byte[] data = apduIn;
        byte[] dataOut = new byte[0];
        try {
            dataOut = openChannel.transmit(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new KeypleIOReaderException("Error while transmitting APDU", e);
        }
        byte[] out = dataOut;
        Log.d(TAG, "Data out : " + ByteArrayUtil.toHex(out));
        return out;
    }

    /**
     * Check that protocolFlag is PROTOCOL_ISO7816_3
     * @param protocolFlag
     * @return true if match PROTOCOL_ISO7816_3
     */
    @Override
    protected boolean protocolFlagMatches(SeProtocol protocolFlag) {
        return protocolFlag.equals(SeCommonProtocols.PROTOCOL_ISO7816_3);
    }
}
