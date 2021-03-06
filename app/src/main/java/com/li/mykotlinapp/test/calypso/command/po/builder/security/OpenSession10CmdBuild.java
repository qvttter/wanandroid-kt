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
package com.li.mykotlinapp.test.calypso.command.po.builder.security;


import com.li.mykotlinapp.test.calypso.command.PoClass;
import com.li.mykotlinapp.test.calypso.command.po.CalypsoPoCommands;
import com.li.mykotlinapp.test.calypso.command.po.PoRevision;
import com.li.mykotlinapp.test.calypso.command.po.parser.security.OpenSession10RespPars;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

public final class OpenSession10CmdBuild
        extends AbstractOpenSessionCmdBuild<OpenSession10RespPars> {
    /**
     * Instantiates a new AbstractOpenSessionCmdBuild.
     *
     * @param keyIndex the key index
     * @param samChallenge the sam challenge returned by the SAM Get Challenge APDU command
     * @param sfiToSelect the sfi to select
     * @param recordNumberToRead the record number to read
     * @param extraInfo extra information included in the logs (can be null or empty)
     * @throws IllegalArgumentException - if key index is 0 (rev 1.0)
     * @throws IllegalArgumentException - if the request is inconsistent
     */
    public OpenSession10CmdBuild(byte keyIndex, byte[] samChallenge, byte sfiToSelect,
            byte recordNumberToRead, String extraInfo) throws IllegalArgumentException {
        super(PoRevision.REV1_0);

        if (keyIndex == 0x00) {
            throw new IllegalArgumentException("Key index can't be null for rev 1.0!");
        }

        byte p1 = (byte) ((recordNumberToRead * 8) + keyIndex);
        byte p2 = (byte) (sfiToSelect * 8);
        /*
         * case 4: this command contains incoming and outgoing data. We define le = 0, the actual
         * length will be processed by the lower layers.
         */
        byte le = 0;

        this.request = setApduRequest(PoClass.LEGACY.getValue(),
                CalypsoPoCommands.getOpenSessionForRev(PoRevision.REV1_0), p1, p2, samChallenge,
                le);
        if (extraInfo != null) {
            this.addSubName(extraInfo);
        }
    }

    @Override
    public OpenSession10RespPars createResponseParser(ApduResponse apduResponse) {
        return new OpenSession10RespPars(apduResponse);
    }
}
