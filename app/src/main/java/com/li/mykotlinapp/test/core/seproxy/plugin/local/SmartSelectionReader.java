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
package com.li.mykotlinapp.test.core.seproxy.plugin.local;

import com.li.mykotlinapp.test.core.seproxy.SeReader;
import com.li.mykotlinapp.test.core.seproxy.SeSelector;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleApplicationSelectionException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleChannelControlException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleIOReaderException;
import com.li.mykotlinapp.test.core.seproxy.message.ApduResponse;

/**
 * Interface implemented by readers able to handle natively the SE selection process (e.g. Android
 * OMAPI readers).
 */
public interface SmartSelectionReader extends SeReader {

    /**
     * Opens a logical channel for the provided AID
     * 
     * @param aidSelector the selection data
     * @return an ApduResponse containing the SE answer to selection
     * @throws KeypleIOReaderException if a communication error occurs
     * @throws KeypleChannelControlException if channel control error occurs
     * @throws KeypleApplicationSelectionException if selection error occurs
     */
    ApduResponse openChannelForAid(SeSelector.AidSelector aidSelector)
            throws KeypleIOReaderException, KeypleChannelControlException,
            KeypleApplicationSelectionException;
}
