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

import com.li.mykotlinapp.test.core.seproxy.event.ObservableReader;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleIOReaderException;

public interface SmartInsertionReader extends ObservableReader {
    /**
     * Waits for a SE. Returns true if a SE is detected before the end of the provided timeout.
     * <p>
     * This method must be implemented by the plugin's reader class when it implements the
     * {@link SmartInsertionReader} interface.
     * <p>
     * Returns false if no SE is detected.
     *
     * @return presence status
     * @throws KeypleIOReaderException in the event of a communication failure with the reader
     *         (disconnection)
     */
    boolean waitForCardPresent() throws KeypleIOReaderException;


    /**
     * Interrupts the waiting of a SE
     */
    void stopWaitForCard();
}
