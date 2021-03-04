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

import java.util.ArrayList;
import java.util.List;
import com.li.mykotlinapp.test.core.command.AbstractApduResponseParser;
import com.li.mykotlinapp.test.core.seproxy.SeSelector;
import com.li.mykotlinapp.test.core.seproxy.message.ApduRequest;
import com.li.mykotlinapp.test.core.seproxy.message.SeRequest;
import com.li.mykotlinapp.test.core.seproxy.message.SeResponse;

/**
 * The AbstractSeSelectionRequest class combines a SeSelector with additional helper methods useful
 * to the selection process done in {@link SeSelection}.
 * <p>
 * This class may also be extended to add particular features specific to a SE family.
 */
public abstract class AbstractSeSelectionRequest {
    protected final SeSelector seSelector;

    /** optional apdu requests list to be executed following the selection process */
    private final List<ApduRequest> seSelectionApduRequestList = new ArrayList<ApduRequest>();

    public AbstractSeSelectionRequest(SeSelector seSelector) {
        this.seSelector = seSelector;
    }

    /**
     * Returns a selection SeRequest built from the information provided in the constructor and
     * possibly completed with the seSelectionApduRequestList
     *
     * @return the selection SeRequest
     */
    final SeRequest getSelectionRequest() {
        return new SeRequest(seSelector, seSelectionApduRequestList);
    }

    public SeSelector getSeSelector() {
        return seSelector;
    }

    /**
     * Add an additional {@link ApduRequest} to be executed after the selection process if it
     * succeeds.
     * <p>
     * If more than one {@link ApduRequest} is added, all will be executed in the order in which
     * they were added.
     *
     * @param apduRequest an {@link ApduRequest}
     */
    protected final void addApduRequest(ApduRequest apduRequest) {
        seSelectionApduRequestList.add(apduRequest);
    }

    /**
     * Return the parser corresponding to the command whose index is provided.
     *
     * @param seResponse the received SeResponse containing the commands raw responses
     * @param commandIndex the command index
     * @return a parser of the type matching the command
     */
    public AbstractApduResponseParser getCommandParser(SeResponse seResponse, int commandIndex) {
        /* not yet implemented in keyple-core */
        // TODO add a generic command parser
        throw new IllegalStateException("No parsers available for this request.");
    }

    /**
     * Virtual parse method
     * 
     * @param seResponse the SE response received
     * @return a {@link AbstractMatchingSe}
     */
    protected abstract AbstractMatchingSe parse(SeResponse seResponse);
}
