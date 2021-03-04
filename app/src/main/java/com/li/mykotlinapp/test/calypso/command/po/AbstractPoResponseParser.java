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
package com.li.mykotlinapp.test.calypso.command.po;

import com.li.mykotlinapp.test.core.command.AbstractApduResponseParser;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

public abstract class AbstractPoResponseParser extends AbstractApduResponseParser {
    /**
     * the generic abstract constructor to build a parser of the APDU response.
     *
     * @param response response to parse
     */
    public AbstractPoResponseParser(ApduResponse response) {
        super(response);
    }
}
