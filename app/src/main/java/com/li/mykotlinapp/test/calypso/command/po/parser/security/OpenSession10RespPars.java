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
package com.li.mykotlinapp.test.calypso.command.po.parser.security;

import java.util.Arrays;
import com.li.mykotlinapp.test.calypso.command.po.PoRevision;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

public final class OpenSession10RespPars extends AbstractOpenSessionRespPars {

    public OpenSession10RespPars(ApduResponse response) {
        super(response, PoRevision.REV1_0);
    }

    @Override
    SecureSession toSecureSession(byte[] apduResponseData) {
        return createSecureSession(apduResponseData);
    }

    public static SecureSession createSecureSession(byte[] apduResponseData) {
        boolean previousSessionRatified = true;

        /**
         * In rev 1.0 mode, the response to the Open Secure Session command is as follows:
         * <p>
         * <code>CC CC CC CC [RR RR] [NN..NN]</code>
         * <p>
         * Where:
         * <ul>
         * <li><code>CC CC CC CC</code> = PO challenge</li>
         * <li><code>RR RR</code> = ratification bytes (may be absent)</li>
         * <li><code>NN..NN</code> = record data (29 bytes)</li>
         * </ul>
         * Legal length values are:
         * <ul>
         * <li>4: ratified, 4-byte challenge, no data</li>
         * <li>33: ratified, 4-byte challenge, 29 bytes of data</li>
         * <li>6: not ratified (2 ratification bytes), 4-byte challenge, no data</li>
         * <li>35 not ratified (2 ratification bytes), 4-byte challenge, 29 bytes of data</li>
         * </ul>
         */
        switch (apduResponseData.length) {
            case 4:
            case 33:
                previousSessionRatified = true;
                break;
            case 6:
            case 35:
                previousSessionRatified = false;
                break;
            default:
                throw new IllegalStateException(
                        "Bad response length to Open Secure Session: " + apduResponseData.length);
        }

        /* KVC doesn't exist and is set to null for this type of PO */
        return new SecureSession(Arrays.copyOfRange(apduResponseData, 1, 4),
                Arrays.copyOfRange(apduResponseData, 4, 5), previousSessionRatified, false, null,
                null, apduResponseData);
    }
}
