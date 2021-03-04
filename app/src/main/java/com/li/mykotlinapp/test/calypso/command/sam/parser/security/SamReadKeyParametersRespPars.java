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
 * SAM read key parameters.
 */
public class SamReadKeyParametersRespPars extends AbstractSamResponseParser {
    /**
     * Instantiates a new SamReadKeyParametersRespPars.
     *
     * @param response of the SamReadKeyParametersRespPars
     */
    public SamReadKeyParametersRespPars(ApduResponse response) {
        super(response);
    }

    /**
     * Gets the key parameters.
     *
     * @return the key parameters
     */
    public byte[] getKeyParameters() {
        return isSuccessful() ? response.getDataOut() : null;
    }
}
