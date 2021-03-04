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
package com.li.mykotlinapp.test.calypso.command.po.builder;


import com.li.mykotlinapp.test.calypso.command.PoClass;
import com.li.mykotlinapp.test.calypso.command.po.AbstractPoCommandBuilder;
import com.li.mykotlinapp.test.calypso.command.po.CalypsoPoCommands;
import com.li.mykotlinapp.test.calypso.command.po.PoModificationCommand;
import com.li.mykotlinapp.test.calypso.command.po.PoSendableInSession;
import com.li.mykotlinapp.test.calypso.command.po.parser.WriteRecordRespPars;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

// TODO: Auto-generated Javadoc

/**
 * The Class WriteRecordCmdBuild. This class provides the dedicated constructor to build the Write
 * Record APDU command.
 *
 */
public final class WriteRecordCmdBuild extends AbstractPoCommandBuilder<WriteRecordRespPars>
        implements PoSendableInSession, PoModificationCommand {

    /** The command. */
    private static final CalypsoPoCommands command = CalypsoPoCommands.WRITE_RECORD;

    /**
     * Instantiates a new WriteRecordCmdBuild.
     *
     * @param poClass indicates which CLA byte should be used for the Apdu
     * @param sfi the sfi to select
     * @param recordNumber the record number to write
     * @param newRecordData the new record data to write
     * @param extraInfo extra information included in the logs (can be null or empty)
     * @throws IllegalArgumentException - if record number is &lt; 1
     * @throws IllegalArgumentException - if the request is inconsistent
     */
    public WriteRecordCmdBuild(PoClass poClass, byte sfi, byte recordNumber, byte[] newRecordData,
            String extraInfo) throws IllegalArgumentException {
        super(command, null);
        if (recordNumber < 1) {
            throw new IllegalArgumentException("Bad record number (< 1)");
        }
        byte p2 = (sfi == 0) ? (byte) 0x04 : (byte) ((byte) (sfi * 8) + 4);

        this.request =
                setApduRequest(poClass.getValue(), command, recordNumber, p2, newRecordData, null);
        if (extraInfo != null) {
            this.addSubName(extraInfo);
        }
    }

    @Override
    public WriteRecordRespPars createResponseParser(ApduResponse apduResponse) {
        return new WriteRecordRespPars(apduResponse);
    }
}
