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
package com.li.mykotlinapp.test.calypso.command.sam.builder.security;


import com.li.mykotlinapp.test.calypso.command.sam.AbstractSamCommandBuilder;
import com.li.mykotlinapp.test.calypso.command.sam.CalypsoSamCommands;
import com.li.mykotlinapp.test.calypso.command.sam.SamRevision;

/**
 * Builder for the SAM Digest Update APDU command. This command have to be sent twice for each
 * command executed during a session. First time for the command sent and second time for the answer
 * received
 */
public class DigestUpdateCmdBuild extends AbstractSamCommandBuilder {

    /** The command reference. */

    private static final CalypsoSamCommands command = CalypsoSamCommands.DIGEST_UPDATE;

    /**
     * Instantiates a new DigestUpdateCmdBuild.
     *
     * @param revision of the SAM
     * @param encryptedSession the encrypted session
     * @param digestData all bytes from command sent by the PO or response from the command
     * @throws IllegalArgumentException - if the digest data is null or has a length &gt; 255
     * @throws IllegalArgumentException - if the request is inconsistent
     */
    public DigestUpdateCmdBuild(SamRevision revision, boolean encryptedSession, byte[] digestData)
            throws IllegalArgumentException {
        super(command, null);
        if (revision != null) {
            this.defaultRevision = revision;
        }
        byte cla = this.defaultRevision.getClassByte();
        byte p1 = (byte) 0x00;
        byte p2 = (byte) 0x00;
        if (encryptedSession) {
            p2 = (byte) 0x80;
        }

        if (digestData != null && digestData.length > 255) {
            throw new IllegalArgumentException("Digest data null or too long!");
        }

        // CalypsoRequest calypsoRequest = new CalypsoRequest(cla, command, p1, p2, digestData);
        request = setApduRequest(cla, command, p1, p2, digestData, null);
    }
}
