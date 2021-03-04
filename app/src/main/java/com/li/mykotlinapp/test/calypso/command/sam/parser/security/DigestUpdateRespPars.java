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
package com.li.mykotlinapp.test.calypso.command.sam.parser.security;



import com.li.mykotlinapp.test.calypso.command.sam.AbstractSamResponseParser;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

/**
 * Digest update response parser. See specs: Calypso / page 54 / 7.4.2 - Session MAC computation
 */
public class DigestUpdateRespPars extends AbstractSamResponseParser {
    /**
     * Instantiates a new DigestUpdateRespPars.
     *
     * @param response the response
     */
    public DigestUpdateRespPars(ApduResponse response) {
        super(response);
    }
}
