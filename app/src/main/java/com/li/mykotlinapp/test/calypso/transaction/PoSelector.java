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
package com.li.mykotlinapp.test.calypso.transaction;

import java.util.HashSet;
import java.util.Set;
import com.li.mykotlinapp.test.core.seproxy.SeSelector;
import com.li.mykotlinapp.test.core.seproxy.protocol.SeProtocol;

/**
 * The {@link PoSelector} class extends {@link SeSelector} to handle specific PO features such as
 * the additional successful status codes list (in response to a select application command)
 */
public final class PoSelector extends SeSelector {
    /**
     * Indicates if an invalidated PO should be selected or not.
     * <p>
     * The acceptance of an invalid PO is determined with the additional successful status codes
     * specified in the {@link AidSelector}
     */
    public enum InvalidatedPo {
        REJECT, ACCEPT
    }

    /**
     * Create a PoSelector to perform the PO selection. See {@link SeSelector}
     *
     * @param seProtocol the SE communication protocol
     * @param poAtrFilter the ATR filter
     * @param poAidSelector the AID selection data
     * @param extraInfo information string (to be printed in logs)
     */
    public PoSelector(SeProtocol seProtocol, PoAtrFilter poAtrFilter, PoAidSelector poAidSelector,
            String extraInfo) {
        super(seProtocol, poAtrFilter, poAidSelector, extraInfo);
    }

    /**
     * PoAidSelector embedding the Calypo PO additional successful codes list
     */
    public static class PoAidSelector extends AidSelector {

        private final static Set<Integer> successfulSelectionStatusCodes = new HashSet<Integer>() {
            {
                add(0x6283);
            }
        };;

        /**
         * Create a {@link PoAidSelector} to select a Calypso PO with an AID through a select
         * application command.
         * 
         * @param aidToSelect the application identifier
         * @param invalidatedPo an enum value to indicate if an invalidated PO should be accepted or
         *        not
         * @param fileOccurrence the ISO7816-4 file occurrence parameter (see
         *        {@link FileOccurrence})
         * @param fileControlInformation the ISO7816-4 file control information parameter (see
         *        {@link FileControlInformation})
         */
        public PoAidSelector(IsoAid aidToSelect, InvalidatedPo invalidatedPo,
                FileOccurrence fileOccurrence, FileControlInformation fileControlInformation) {
            super(aidToSelect,
                    invalidatedPo == InvalidatedPo.ACCEPT ? successfulSelectionStatusCodes : null,
                    fileOccurrence, fileControlInformation);
        }

        /**
         * Simplified constructor with default values for the FileOccurrence and
         * FileControlInformation (see {@link AidSelector})
         * 
         * @param aidToSelect the application identifier
         * @param invalidatedPo an enum value to indicate if an invalidated PO should be accepted or
         *        not
         */
        public PoAidSelector(IsoAid aidToSelect, InvalidatedPo invalidatedPo) {
            super(aidToSelect,
                    invalidatedPo == InvalidatedPo.ACCEPT ? successfulSelectionStatusCodes : null);
        }
    }

    /**
     * PoAtrFilter to perform a PO selection based on its ATR
     * <p>
     * Could be completed to handle Calypso specific ATR filtering process.
     */
    public static class PoAtrFilter extends AtrFilter {

        /**
         * Regular expression based filter
         *
         * @param atrRegex String hex regular expression
         */
        public PoAtrFilter(String atrRegex) {
            super(atrRegex);
        }
    }
}
