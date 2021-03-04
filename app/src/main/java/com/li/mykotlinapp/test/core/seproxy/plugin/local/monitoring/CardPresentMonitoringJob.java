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
package com.li.mykotlinapp.test.core.seproxy.plugin.local.monitoring;

import java.util.concurrent.atomic.AtomicBoolean;
import com.li.mykotlinapp.test.core.seproxy.SeReader;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleIOReaderException;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.AbstractObservableLocalReader;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.AbstractObservableState;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.MonitoringJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This monitoring job polls the {@link SeReader#isSePresent()} method to detect
 * SE_INSERTED/SE_REMOVED
 */
public class CardPresentMonitoringJob implements MonitoringJob {

    private static final Logger logger = LoggerFactory.getLogger(CardPresentMonitoringJob.class);

    private final long waitTimeout;
    private final boolean monitorInsertion;
    private final SeReader reader;
    final private AtomicBoolean loop = new AtomicBoolean();

    /**
     * Build a monitoring job to detect the card insertion
     * 
     * @param reader : reader that will be polled with the method isSePresent()
     * @param waitTimeout : wait time during two hit of the polling
     * @param monitorInsertion : if true, polls for SE_INSERTED, else SE_REMOVED
     */
    public CardPresentMonitoringJob(SeReader reader, long waitTimeout, boolean monitorInsertion) {
        this.waitTimeout = waitTimeout;
        this.reader = reader;
        this.monitorInsertion = monitorInsertion;
    }

    @Override
    public Runnable getMonitoringJob(final AbstractObservableState state) {
        return new Runnable() {
            long retries = 0;

            @Override
            public void run() {
                logger.debug("[{}] Polling from isSePresent", reader.getName());

                // re-init loop value to true
                loop.set(true);
                while (loop.get()) {
                    try {
                        // polls for SE_INSERTED
                        if (monitorInsertion && reader.isSePresent()) {
                            logger.debug("[{}] The SE is present ", reader.getName());
                            loop.set(false);
                            state.onEvent(AbstractObservableLocalReader.InternalEvent.SE_INSERTED);
                            return;
                        }
                        // polls for SE_REMOVED
                        if (!monitorInsertion && !reader.isSePresent()) {
                            logger.debug("[{}] The SE is not present ", reader.getName());
                            loop.set(false);
                            state.onEvent(AbstractObservableLocalReader.InternalEvent.SE_REMOVED);
                            return;
                        }

                    } catch (KeypleIOReaderException e) {
                        loop.set(false);
                        // what do do here
                    }
                    retries++;

                    if (logger.isTraceEnabled()) {
                        logger.trace("[{}] isSePresent polling retries : {}", reader.getName(),
                                retries);
                    }
                    try {
                        // wait a bit
                        Thread.sleep(waitTimeout);
                    } catch (InterruptedException ignored) {
                        // Restore interrupted state...      
                        Thread.currentThread().interrupt();
                        loop.set(false);
                    }
                }
                logger.trace("[{}] Looping has been stopped", reader.getName());

            }
        };
    }

    @Override
    public void stop() {
        logger.debug("[{}] Stop polling ", reader.getName());
        loop.set(false);
    }

}
