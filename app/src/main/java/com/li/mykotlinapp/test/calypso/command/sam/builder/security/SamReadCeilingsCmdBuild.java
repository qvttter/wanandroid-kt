/********************************************************************************
 * Copyright (c) 2019 Calypso Networks Association https://www.calypsonet-asso.org/
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
 * Builder for the SAM Read Ceilings APDU command.
 */
public class SamReadCeilingsCmdBuild extends AbstractSamCommandBuilder {
    /** The command reference. */
    private static final CalypsoSamCommands command = CalypsoSamCommands.READ_CEILINGS;

    public static final int MAX_CEILING_NUMB = 26;

    public static final int MAX_CEILING_REC_NUMB = 3;

    public enum CeilingsOperationType {
        CEILING_RECORD, SINGLE_CEILING
    }

    public SamReadCeilingsCmdBuild(SamRevision revision, CeilingsOperationType operationType,
            int index) {

        super(command, null);
        if (revision != null) {
            this.defaultRevision = revision;
        }

        byte cla = this.defaultRevision.getClassByte();

        byte p1 = (byte) 0x00;
        byte p2;

        switch (operationType) {
            case CEILING_RECORD:

                if (index < 1 || index > MAX_CEILING_REC_NUMB) {
                    throw new IllegalArgumentException(
                            "Record Number must be between 1 and " + MAX_CEILING_REC_NUMB + ".");
                }

                p2 = (byte) (0xB0 + index);
                break;

            case SINGLE_CEILING:

                if (index < 0 || index > MAX_CEILING_NUMB) {
                    throw new IllegalArgumentException(
                            "Counter Number must be between 0 and " + MAX_CEILING_NUMB + ".");
                }

                p1 = (byte) index;
                p2 = (byte) (0xB8);
                break;

            default:
                throw new IllegalStateException(
                        "Unsupported OperationType parameter " + operationType.toString());
        }

        request = setApduRequest(cla, command, p1, p2, null, (byte) 0x00);
    }

}
