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


import com.li.mykotlinapp.test.core.command.AbstractApduResponseParser;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

/**
 * PO Get challenge response parser. See specs: Calypso / page 108 / 9.54 - Get challenge
 */
public final class PoGetChallengeRespPars extends AbstractApduResponseParser {

    /**
     * Instantiates a new PoGetChallengeRespPars.
     *
     * @param response the response from PO Get Challenge APDU Command
     */
    public PoGetChallengeRespPars(ApduResponse response) {
        super(response);
    }

    public byte[] getPoChallenge() {
        return getApduResponse().getDataOut();
    }
}
