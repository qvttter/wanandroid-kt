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

import java.util.Set;
import com.li.mykotlinapp.test.core.seproxy.ChannelControl;
import com.li.mykotlinapp.test.core.seproxy.MultiSeRequestProcessing;
import com.li.mykotlinapp.test.core.seproxy.event.AbstractDefaultSelectionsRequest;
import com.li.mykotlinapp.test.core.seproxy.event.ObservableReader;

/**
 * Class containing the Set of {@link SeRequest} used to make a default selection at the
 * {@link ObservableReader} level.
 */
public final class DefaultSelectionsRequest extends AbstractDefaultSelectionsRequest {

    private Set<SeRequest> selectionSeRequestSet;

    private MultiSeRequestProcessing multiSeRequestProcessing;

    private ChannelControl channelControl;

    public DefaultSelectionsRequest(Set<SeRequest> selectionSeRequestSet,
            MultiSeRequestProcessing multiSeRequestProcessing, ChannelControl channelControl) {
        this.selectionSeRequestSet = selectionSeRequestSet;
        this.multiSeRequestProcessing = multiSeRequestProcessing;
        this.channelControl = channelControl;
    }

    public DefaultSelectionsRequest(Set<SeRequest> selectionSeRequestSet) {
        this(selectionSeRequestSet, MultiSeRequestProcessing.FIRST_MATCH, ChannelControl.KEEP_OPEN);
    }

    @Override
    public MultiSeRequestProcessing getMultiSeRequestProcessing() {
        return multiSeRequestProcessing;
    }

    @Override
    public ChannelControl getChannelControl() {
        return channelControl;
    }

    @Override
    public Set<SeRequest> getSelectionSeRequestSet() {
        return selectionSeRequestSet;
    }
}
