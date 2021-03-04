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
package com.li.mykotlinapp.test.core.seproxy.plugin;

import java.util.List;
import java.util.Set;
import com.li.mykotlinapp.test.core.seproxy.AbstractSeProxyComponent;
import com.li.mykotlinapp.test.core.seproxy.ChannelControl;
import com.li.mykotlinapp.test.core.seproxy.MultiSeRequestProcessing;
import com.li.mykotlinapp.test.core.seproxy.SeReader;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleChannelControlException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleIOReaderException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleReaderException;
import com.li.mykotlinapp.test.core.seproxy.message.ProxyReader;
import com.li.mykotlinapp.test.core.seproxy.message.SeRequest;
import com.li.mykotlinapp.test.core.seproxy.message.SeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract definition of an observable reader.
 * <ul>
 * <li>High level logging and benchmarking of Set of SeRequest and SeRequest transmission</li>
 * <li>Name-based comparison of ProxyReader (required for SortedSet&lt;ProxyReader&gt;)</li>
 * <li>Plugin naming management</li>
 * </ul>
 */

public abstract class AbstractReader extends AbstractSeProxyComponent implements ProxyReader {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(AbstractReader.class);

    /** Timestamp recorder */
    private long before;

    /** Contains the name of the plugin */
    private final String pluginName;

    /**
     * This flag is used with transmit or transmitSet
     * <p>
     * It will be used by the notifySeProcessed method (AbstractObservableLocalReader) to determine
     * if a request to close the physical channel has been already made and therefore to switch
     * directly to the removal sequence for the observed readers.<br>
     * TODO find a better way to manage this need
     */
    @Deprecated // will change in a later version
    protected boolean forceClosing = true;

    /**
     * Reader constructor
     * <p>
     * Initialize the time measurement
     *
     * @param pluginName the name of the plugin that instantiated the reader
     * @param name the name of the reader
     */
    protected AbstractReader(String pluginName, String name) {
        super(name);
        this.pluginName = pluginName;
        this.before = System.nanoTime(); /*
                                          * provides an initial value for measuring the
                                          * inter-exchange time. The first measurement gives the
                                          * time elapsed since the plugin was loaded.
                                          */
    }

    /**
     * Gets the name of plugin provided in the constructor.
     * <p>
     * The method will be used particularly for logging purposes. The plugin name is also part of
     * the ReaderEvent and PluginEvent objects.
     *
     * @return the plugin name String
     */
    public final String getPluginName() {
        return pluginName;
    }

    /**
     * Compare the name of the current SeReader to the name of the SeReader provided in argument
     *
     * @param seReader a SeReader object
     * @return 0 if the names match (The method is needed for the SortedSet lists)
     */
    @Override
    public final int compareTo(SeReader seReader) {
        return this.getName().compareTo(seReader.getName());
    }

    /**
     * Execute the transmission of a list of {@link SeRequest} and returns a list of
     * {@link SeResponse}
     * <p>
     * The {@link MultiSeRequestProcessing} parameter indicates whether all requests are to be sent
     * regardless of their result (PROCESS_ALL) or whether the process should stop at the first
     * request whose result is a success (FIRST_MATCH).
     * <p>
     * The {@link ChannelControl} parameter specifies whether the physical channel should be closed
     * (CLOSE_AFTER) or not (KEEP_OPEN) after all requests have been transmitted.
     * <p>
     * The global execution time (inter-exchange and communication) and the Set of SeRequest content
     * is logged (DEBUG level).
     * <p>
     * As the method is final, it cannot be extended.
     *
     * @param requestSet the request set
     * @param multiSeRequestProcessing the multi SE request processing mode
     * @param channelControl the channel control indicator
     * @return the response set
     * @throws KeypleReaderException if a reader error occurs
     */
    @Override
    public final List<SeResponse> transmitSet(Set<SeRequest> requestSet,
            MultiSeRequestProcessing multiSeRequestProcessing, ChannelControl channelControl)
            throws KeypleReaderException {
        if (requestSet == null) {
            throw new IllegalArgumentException("seRequestSet must not be null");
        }

        /* sets the forceClosing flag */
        forceClosing = channelControl == ChannelControl.KEEP_OPEN;

        List<SeResponse> responseSet;

        if (logger.isDebugEnabled()) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - this.before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SEREQUESTSET = {}, elapsed {} ms.", this.getName(),
                    requestSet, elapsedMs);
        }

        try {
            responseSet = processSeRequestSet(requestSet, multiSeRequestProcessing, channelControl);
        } catch (KeypleChannelControlException ex) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - this.before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SEREQUESTSET channel failure. elapsed {}",
                    this.getName(), elapsedMs);
            /* Throw an exception with the responses collected so far. */
            throw ex;
        } catch (KeypleIOReaderException ex) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - this.before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SEREQUESTSET IO failure. elapsed {}", this.getName(),
                    elapsedMs);
            /* Throw an exception with the responses collected so far. */
            throw ex;
        }

        if (logger.isDebugEnabled()) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SERESPONSESET = {}, elapsed {} ms.", this.getName(),
                    responseSet, elapsedMs);
        }

        return responseSet;
    }

    /**
     * Simplified version of transmitSet for standard use.
     *
     * @param requestSet the request set
     * @return the response set
     * @throws KeypleReaderException if a reader error occurs
     */
    @Override
    public final List<SeResponse> transmitSet(Set<SeRequest> requestSet)
            throws KeypleReaderException {
        return transmitSet(requestSet, MultiSeRequestProcessing.FIRST_MATCH,
                ChannelControl.KEEP_OPEN);
    }

    /**
     * Abstract method implemented by the AbstractLocalReader and VirtualReader classes.
     * <p>
     * This method is handled by transmitSet.
     *
     * @param requestSet the Set of {@link SeRequest} to be processed
     * @param multiSeRequestProcessing the multi se processing mode
     * @param channelControl indicates if the channel has to be closed at the end of the processing
     * @return the List of {@link SeResponse} (responses to the Set of {@link SeRequest})
     * @throws KeypleReaderException if reader error occurs
     */
    protected abstract List<SeResponse> processSeRequestSet(Set<SeRequest> requestSet,
            MultiSeRequestProcessing multiSeRequestProcessing, ChannelControl channelControl)
            throws KeypleReaderException;

    /**
     * Execute the transmission of a {@link SeRequest} and returns a {@link SeResponse}
     * <p>
     * The individual execution time (inter-exchange and communication) and the {@link SeRequest}
     * content is logged (DEBUG level).
     * <p>
     * As the method is final, it cannot be extended.
     *
     * @param seRequest the request to be transmitted
     * @param channelControl indicates if the channel has to be closed at the end of the processing
     * @return the received response
     * @throws KeypleReaderException if a reader error occurs
     */
    @Override
    public final SeResponse transmit(SeRequest seRequest, ChannelControl channelControl)
            throws KeypleReaderException {
        if (seRequest == null) {
            throw new IllegalArgumentException("seRequest must not be null");
        }

        /* sets the forceClosing flag */
        forceClosing = channelControl == ChannelControl.KEEP_OPEN;

        SeResponse seResponse;

        if (logger.isDebugEnabled()) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - this.before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SEREQUEST = {}, elapsed {} ms.", this.getName(),
                    seRequest, elapsedMs);
        }

        try {
            seResponse = processSeRequest(seRequest, channelControl);
        } catch (KeypleChannelControlException ex) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - this.before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SEREQUEST channel failure. elapsed {}", this.getName(),
                    elapsedMs);
            /* Throw an exception with the responses collected so far (ex.getSeResponse()). */
            throw ex;
        } catch (KeypleIOReaderException ex) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - this.before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SEREQUEST IO failure. elapsed {}", this.getName(),
                    elapsedMs);
            /* Throw an exception with the responses collected so far (ex.getSeResponse()). */
            throw ex;
        }

        if (logger.isDebugEnabled()) {
            long timeStamp = System.nanoTime();
            double elapsedMs = (double) ((timeStamp - before) / 100000) / 10;
            this.before = timeStamp;
            logger.debug("[{}] transmit => SERESPONSE = {}, elapsed {} ms.", this.getName(),
                    seResponse, elapsedMs);
        }

        return seResponse;
    }

    /**
     * Simplified version of transmit for standard use.
     *
     * @param seRequest the request to be transmitted
     * @return the received response
     * @throws KeypleReaderException if a reader error occurs
     */
    @Override
    public final SeResponse transmit(SeRequest seRequest) throws KeypleReaderException {
        return transmit(seRequest, ChannelControl.KEEP_OPEN);
    }

    /**
     * Abstract method implemented by the AbstractLocalReader and VirtualReader classes.
     * <p>
     * This method is handled by transmit.
     *
     * @param seRequest the {@link SeRequest} to be processed
     * @param channelControl a flag indicating if the channel has to be closed after the processing
     *        of the {@link SeRequest}
     * @return the {@link SeResponse} (responses to the {@link SeRequest})
     * @throws KeypleReaderException if reader error occurs
     */
    protected abstract SeResponse processSeRequest(SeRequest seRequest,
            ChannelControl channelControl) throws KeypleReaderException;

}
