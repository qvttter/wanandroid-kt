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
package com.li.mykotlinapp.test.core.seproxy.event;

import com.li.mykotlinapp.test.core.seproxy.message.DefaultSelectionsResponse;

/**
 * ReaderEvent used to notify changes at reader level
 */
public final class ReaderEvent {
    /**
     * The name of the plugin handling the reader that produced the event
     */
    private final String pluginName;

    /**
     * The name of the reader that produced the event
     */
    private final String readerName;

    /**
     * The response to the selection request Note: although the object is instantiated externally,
     * we use DefaultSelectionsResponse here to keep ReaderEvent serializable
     */
    private final DefaultSelectionsResponse defaultResponseSet;

    /**
     * The different types of reader events, reflecting the status of the reader regarding the
     * presence of the card
     */
    public enum EventType {
        /**
         * An timeout error occurred.
         */
        TIMEOUT_ERROR("SE Reader timeout Error"),

        /**
         * A SE has been inserted.
         */
        SE_INSERTED("SE insertion"),

        /**
         * A SE has been inserted and the default requests process has been successfully operated.
         */
        SE_MATCHED("SE matched"),

        /**
         * The SE has been removed and is no longer able to communicate with the reader
         */
        SE_REMOVED("SE removed");

        /** The event name. */
        private String name;

        EventType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * The type of event
     */
    private final EventType eventType;

    /**
     * ReaderEvent constructor for simple insertion notification mode
     *
     * @param pluginName the name of the current plugin
     * @param readerName the name of the current reader
     * @param eventType the type of event
     * @param defaultSelectionsResponse the response to the default AbstractDefaultSelectionsRequest
     *        (may be null)
     */
    public ReaderEvent(String pluginName, String readerName, EventType eventType,
            AbstractDefaultSelectionsResponse defaultSelectionsResponse) {
        this.pluginName = pluginName;
        this.readerName = readerName;
        this.eventType = eventType;
        this.defaultResponseSet = (DefaultSelectionsResponse) defaultSelectionsResponse;
    }


    public String getPluginName() {
        return pluginName;
    }

    public String getReaderName() {
        return readerName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public AbstractDefaultSelectionsResponse getDefaultSelectionsResponse() {
        return defaultResponseSet;
    }
}
