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
package com.li.mykotlinapp.test.core.seproxy.event;

import java.util.Set;
import com.li.mykotlinapp.test.core.seproxy.ChannelControl;
import com.li.mykotlinapp.test.core.seproxy.MultiSeRequestProcessing;
import com.li.mykotlinapp.test.core.seproxy.message.SeRequest;

/**
 * The abstract class defining the default selection request to be processed when an SE is inserted
 * in an observable reader.
 * <p>
 * The default selection is defined by:
 * <ul>
 * <li>a set of requests corresponding to one or more selection cases
 * <li>a {@link MultiSeRequestProcessing} indicator specifying whether all planned selections are to
 * be executed or whether to stop at the first one that is successful
 * <li>an indicator to control the physical channel to stipulate whether it should be closed or left
 * open at the end of the selection process
 * </ul>
 */

public abstract class AbstractDefaultSelectionsRequest {
    /**
     * @return the selection request set
     */
    protected abstract Set<SeRequest> getSelectionSeRequestSet();

    /**
     * @return the multi SE request mode
     */
    protected abstract MultiSeRequestProcessing getMultiSeRequestProcessing();

    /**
     * @return the channel control
     */
    protected abstract ChannelControl getChannelControl();
}
