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
package com.li.mykotlinapp.test.core.selection;

import com.li.mykotlinapp.test.core.seproxy.SeReader;

/**
 * The SeResource class groups a AbstractMatchingSe and its associated SeReader
 */
public class SeResource<T extends AbstractMatchingSe> {
    private final SeReader seReader;
    private final T matchingSe;

    /**
     * Constructor
     * 
     * @param seReader the {@link SeReader} with which the SE is communicating
     * @param matchingSe the {@link AbstractMatchingSe} information structure
     */
    protected SeResource(SeReader seReader, T matchingSe) {
        this.seReader = seReader;
        this.matchingSe = matchingSe;
    }

    /**
     * @return the current {@link SeReader} for this SE
     */
    public SeReader getSeReader() {
        return seReader;
    }

    /**
     * @return the {@link AbstractMatchingSe}
     */
    public T getMatchingSe() {
        return matchingSe;
    }
}
