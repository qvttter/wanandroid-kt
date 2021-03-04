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
 * Builder for the SAM Read Event Counter APDU command.
 */
public class SamReadEventCounterCmdBuild extends AbstractSamCommandBuilder {
    /** The command reference. */
    private static final CalypsoSamCommands command = CalypsoSamCommands.READ_EVENT_COUNTER;

    public static final int MAX_COUNTER_NUMB = 26;

    public static final int MAX_COUNTER_REC_NUMB = 3;

    public enum SamEventCounterOperationType {
        COUNTER_RECORD, SINGLE_COUNTER
    }

    public SamReadEventCounterCmdBuild(SamRevision revision,
            SamEventCounterOperationType operationType, int index) {

        super(command, null);
        if (revision != null) {
            this.defaultRevision = revision;
        }

        byte cla = this.defaultRevision.getClassByte();

        byte p2 = (byte) 0x00;

        switch (operationType) {
            case COUNTER_RECORD:

                if (index < 1 || index > MAX_COUNTER_REC_NUMB) {
                    throw new IllegalArgumentException(
                            "Record Number must be between 1 and " + MAX_COUNTER_REC_NUMB + ".");
                }

                p2 = (byte) (0xE0 + index);
                break;

            case SINGLE_COUNTER:

                if (index < 0 || index > MAX_COUNTER_NUMB) {
                    throw new IllegalArgumentException(
                            "Counter Number must be between 0 and " + MAX_COUNTER_NUMB + ".");
                }

                p2 = (byte) (0x81 + index);
                break;

            default:
                throw new IllegalStateException(
                        "Unsupported OperationType parameter " + operationType.toString());
        }

        request = setApduRequest(cla, command, (byte) 0x00, p2, null, (byte) 0x00);
    }

}
