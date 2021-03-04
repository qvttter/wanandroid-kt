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
package com.li.mykotlinapp.test.core.seproxy.plugin.local.state;

import java.util.concurrent.ExecutorService;
import com.li.mykotlinapp.test.core.seproxy.event.ObservableReader;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.AbstractObservableLocalReader;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.AbstractObservableState;
import com.li.mykotlinapp.test.core.seproxy.plugin.local.MonitoringJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wait for Se Processing State
 * <p>
 * The state during which the SE is being processed by the application.
 * <ul>
 * <li>Upon SE_PROCESSED event, the machine changes state for WAIT_FOR_SE_REMOVAL or
 * WAIT_FOR_SE_DETECTION according to the {@link ObservableReader.PollingMode} setting.
 * <li>Upon SE_REMOVED event, the machine changes state for WAIT_FOR_SE_INSERTION or
 * WAIT_FOR_SE_DETECTION according to the {@link ObservableReader.PollingMode} setting.
 * <li>Upon STOP_DETECT event, the machine changes state for WAIT_FOR_SE_DETECTION.
 * </ul>
 */
public class WaitForSeProcessing extends AbstractObservableState {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(WaitForSeProcessing.class);

    public WaitForSeProcessing(AbstractObservableLocalReader reader) {
        super(MonitoringState.WAIT_FOR_SE_PROCESSING, reader);
    }

    public WaitForSeProcessing(AbstractObservableLocalReader reader, MonitoringJob monitoringJob,
            ExecutorService executorService) {
        super(MonitoringState.WAIT_FOR_SE_PROCESSING, reader, monitoringJob, executorService);
    }

    @Override
    public void onEvent(AbstractObservableLocalReader.InternalEvent event) {
        logger.trace("[{}] onEvent => Event {} received in currentState {}", reader.getName(),
                event, state);

        /*
         * Process InternalEvent
         */
        switch (event) {
            case SE_PROCESSED:
                if (this.reader.getPollingMode() == ObservableReader.PollingMode.REPEATING) {
                    switchState(MonitoringState.WAIT_FOR_SE_REMOVAL);
                } else {
                    // We close the channels now and notify the application of
                    // the SE_REMOVED event.
                    this.reader.processSeRemoved();
                    switchState(MonitoringState.WAIT_FOR_START_DETECTION);
                }
                break;

            case SE_REMOVED:
                // the SE has been removed, we close all channels and return to
                // the currentState of waiting
                // for insertion
                // We notify the application of the SE_REMOVED event.
                reader.processSeRemoved();
                if (reader.getPollingMode() == ObservableReader.PollingMode.REPEATING) {
                    switchState(MonitoringState.WAIT_FOR_SE_INSERTION);
                } else {
                    switchState(MonitoringState.WAIT_FOR_START_DETECTION);
                }
                break;


            case STOP_DETECT:
                reader.processSeRemoved();
                switchState(MonitoringState.WAIT_FOR_START_DETECTION);
                break;

            default:
                logger.warn("[{}] Ignore =>  Event {} received in currentState {}",
                        reader.getName(), event, state);
                break;
        }
    }
}
