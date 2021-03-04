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
package com.li.mykotlinapp.test.core.seproxy.message;

import java.util.List;
import com.li.mykotlinapp.test.core.seproxy.event.AbstractDefaultSelectionsResponse;
import com.li.mykotlinapp.test.core.seproxy.event.ObservableReader;

/**
 * Class containing the List of {@link SeResponse} used from a default selection made at the
 * {@link ObservableReader} level.
 */
public final class DefaultSelectionsResponse extends AbstractDefaultSelectionsResponse {
    /** The List of {@link SeResponse} */
    private final List<SeResponse> selectionSeResponseSet;

    public DefaultSelectionsResponse(List<SeResponse> selectionSeResponseSet) {
        this.selectionSeResponseSet = selectionSeResponseSet;
    }

    @Override
    public List<SeResponse> getSelectionSeResponseSet() {
        return selectionSeResponseSet;
    }
}
