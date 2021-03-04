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
import com.li.mykotlinapp.test.calypso.command.po.PoSendableInSession;
import com.li.mykotlinapp.test.calypso.command.po.parser.ReadDataStructure;
import com.li.mykotlinapp.test.calypso.command.po.parser.ReadRecordsRespPars;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

/**
 * The Class ReadRecordsCmdBuild. This class provides the dedicated constructor to build the Read
 * Records APDU command.
 */
public final class ReadRecordsCmdBuild extends AbstractPoCommandBuilder<ReadRecordsRespPars>
        implements PoSendableInSession {

    /** The command. */
    private static final CalypsoPoCommands command = CalypsoPoCommands.READ_RECORDS;

    private final byte firstRecordNumber;
    private final ReadDataStructure readDataStructure;

    /**
     * Instantiates a new read records cmd build.
     *
     * @param poClass indicates which CLA byte should be used for the Apdu
     * @param sfi the sfi top select
     * @param readDataStructure file structure type (used to create the parser)
     * @param firstRecordNumber the record number to read (or first record to read in case of
     *        several records)
     * @param readJustOneRecord the read just one record
     * @param expectedLength the expected length of the record(s)
     * @param extraInfo extra information included in the logs (can be null or empty)
     * @throws IllegalArgumentException - if record number &lt; 1
     * @throws IllegalArgumentException - if the request is inconsistent
     */
    public ReadRecordsCmdBuild(PoClass poClass, byte sfi, ReadDataStructure readDataStructure,
            byte firstRecordNumber, boolean readJustOneRecord, byte expectedLength,
            String extraInfo) throws IllegalArgumentException {
        super(command, null);

        if (firstRecordNumber < 1) {
            throw new IllegalArgumentException("Bad record number (< 1)");
        }

        this.firstRecordNumber = firstRecordNumber;
        this.readDataStructure = readDataStructure;

        byte p2 = (sfi == (byte) 0x00) ? (byte) 0x05 : (byte) ((byte) (sfi * 8) + 5);
        if (readJustOneRecord) {
            p2 = (byte) (p2 - (byte) 0x01);
        }
        this.request = setApduRequest(poClass.getValue(), command, firstRecordNumber, p2, null,
                expectedLength);
        if (extraInfo != null) {
            this.addSubName(extraInfo);
        }
    }

    /**
     * Instantiates a new read records cmd build without specifying the expected length. This
     * constructor is allowed only in contactless mode.
     *
     * @param poClass indicates which CLA byte should be used for the Apdu
     * @param readDataStructure file structure type
     * @param sfi the sfi top select
     * @param firstRecordNumber the record number to read (or first record to read in case of
     *        several records)
     * @param readJustOneRecord the read just one record
     * @param extraInfo extra information included in the logs (can be null or empty)
     * @throws IllegalArgumentException - if record number &lt; 1
     * @throws IllegalArgumentException - if the request is inconsistent
     */
    public ReadRecordsCmdBuild(PoClass poClass, byte sfi, ReadDataStructure readDataStructure,
            byte firstRecordNumber, boolean readJustOneRecord, String extraInfo)
            throws IllegalArgumentException {
        this(poClass, sfi, readDataStructure, firstRecordNumber, readJustOneRecord, (byte) 0x00,
                extraInfo);
    }

    @Override
    public ReadRecordsRespPars createResponseParser(ApduResponse apduResponse) {
        return new ReadRecordsRespPars(apduResponse, readDataStructure, firstRecordNumber);
    }
}
