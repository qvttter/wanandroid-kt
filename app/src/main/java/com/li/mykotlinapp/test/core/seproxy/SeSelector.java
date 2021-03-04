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
package com.li.mykotlinapp.test.core.seproxy;

import java.util.*;
import java.util.regex.Pattern;
import com.li.mykotlinapp.test.core.seproxy.protocol.SeProtocol;
import com.li.mykotlinapp.test.core.util.ByteArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SeSelector class groups the information and methods used to select a particular secure
 * element
 */
public class SeSelector {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(SeSelector.class);

    private final SeProtocol seProtocol;
    private final AidSelector aidSelector;
    private final AtrFilter atrFilter;
    private final String extraInfo;

    /**
     * Static nested class to hold the data elements used to perform an AID based selection
     */
    public static class AidSelector {

        /**
         * FileOccurrence indicates how to carry out the file occurrence in accordance with
         * ISO7816-4
         * <p>
         * The getIsoBitMask method provides the bit mask to be used to set P2 in the select command
         * (ISO/IEC 7816-4.2)
         */
        public enum FileOccurrence {

            FIRST((byte) 0x00), LAST((byte) 0x01), NEXT((byte) 0x02), PREVIOUS((byte) 0x03);

            private final byte isoBitMask;

            FileOccurrence(byte isoBitMask) {
                this.isoBitMask = isoBitMask;
            }

            public byte getIsoBitMask() {
                return isoBitMask;
            }
        }

        /**
         * FileOccurrence indicates how to which template is expected in accordance with ISO7816-4
         * <p>
         * The getIsoBitMask method provides the bit mask to be used to set P2 in the select command
         * (ISO/IEC 7816-4.2)
         */
        public enum FileControlInformation {
            FCI(((byte) 0x00)), FCP(((byte) 0x04)), FMD(((byte) 0x08)), NO_RESPONSE(((byte) 0x0C));

            private final byte isoBitMask;

            FileControlInformation(byte isoBitMask) {
                this.isoBitMask = isoBitMask;
            }

            public byte getIsoBitMask() {
                return isoBitMask;
            }
        }

        public static class IsoAid {
            public static final int AID_MIN_LENGTH = 5;
            public static final int AID_MAX_LENGTH = 16;
            private final byte[] value;

            /**
             * Build an IsoAid and check length from a byte array
             * 
             * @param aid byte array containing the AID value
             * @throws IllegalArgumentException if the byte length array is not within the allowed
             *         range.
             */
            public IsoAid(byte[] aid) {
                if (aid.length < AID_MIN_LENGTH || aid.length > AID_MAX_LENGTH) {
                    value = null;
                    throw new IllegalArgumentException("Bad AID length: " + aid.length
                            + ". The AID length should be " + "between 5 and 15.");
                } else {
                    value = aid;
                }
            }


            /**
             * Build an IsoAid and check length from an hex string
             *
             * @param aid hex string containing the AID value
             * @throws IllegalArgumentException if the byte length array is not within the allowed
             *         range.
             */
            public IsoAid(String aid) {
                this(ByteArrayUtil.fromHex(aid));
            }

            /**
             * @return the AID value as a byte array
             */
            public byte[] getValue() {
                return value;
            }

            /**
             * Compares two IsoAid objects.
             * <p>
             * Tells if the current AID starts with the value contained in the provided AID
             * 
             * @param aid an other AID
             * @return true or false
             */
            public boolean startsWith(IsoAid aid) {
                if (this.value.length > aid.getValue().length) {
                    return false;
                }

                for (int i = 0; i < aid.getValue().length; i++) {
                    if (this.value[i] != aid.getValue()[i]) {
                        return false;
                    }
                }
                return true;
            }
        }

        private FileOccurrence fileOccurrence = FileOccurrence.FIRST;
        private FileControlInformation fileControlInformation = FileControlInformation.FCI;

        /**
         * - AID’s bytes of the SE application to select. In case the SE application is currently
         * not selected, a logical channel is established and the corresponding SE application is
         * selected by the SE reader, otherwise keep the current channel.
         *
         * - Could be missing when operating SE which don’t support the Select Application command
         * (as it is the case for SAM).
         */
        private IsoAid aidToSelect;

        /**
         * List of status codes in response to the select application command that should be
         * considered successful although they are different from 9000
         */
        private Set<Integer> successfulSelectionStatusCodes = new LinkedHashSet<Integer>();

        /**
         * AidSelector with additional select application successful status codes, file occurrence
         * and file control information.
         * <p>
         * The fileOccurrence parameter defines the selection options P2 of the SELECT command
         * message
         * <p>
         * The fileControlInformation parameter defines the expected command output template.
         * <p>
         * Refer to ISO7816-4.2 for detailed information about these parameters
         *
         * @param aidToSelect IsoAid
         * @param successfulSelectionStatusCodes list of successful status codes for the select
         *        application response
         * @param fileOccurrence the occurrence parameter (see ISO7816-4 definition)
         * @param fileControlInformation the file control information (see ISO7816-4 definition)
         */
        public AidSelector(IsoAid aidToSelect, Set<Integer> successfulSelectionStatusCodes,
                FileOccurrence fileOccurrence, FileControlInformation fileControlInformation) {
            this.aidToSelect = aidToSelect;
            this.successfulSelectionStatusCodes = successfulSelectionStatusCodes;
            this.fileOccurrence = fileOccurrence;
            this.fileControlInformation = fileControlInformation;
        }

        /**
         * AidSelector with additional select application successful status codes
         * <p>
         * The fileOccurrence field is set by default to FIRST
         * <p>
         * The fileControlInformation field is set by default to FCI
         *
         * @param aidToSelect IsoAid
         * @param successfulSelectionStatusCodes list of successful status codes for the select
         *        application response
         */
        public AidSelector(IsoAid aidToSelect, Set<Integer> successfulSelectionStatusCodes) {
            this(aidToSelect, successfulSelectionStatusCodes, FileOccurrence.FIRST,
                    FileControlInformation.FCI);
        }

        /**
         * Getter for the AID provided at construction time
         *
         * @return byte array containing the AID
         */
        public IsoAid getAidToSelect() {
            return aidToSelect;
        }

        /**
         * @return the file occurrence parameter
         */
        public FileOccurrence getFileOccurrence() {
            return fileOccurrence;
        }

        /**
         * @return the file control information parameter
         */
        public FileControlInformation getFileControlInformation() {
            return fileControlInformation;
        }

        /**
         * Gets the list of successful selection status codes
         *
         * @return the list of status codes
         */
        public Set<Integer> getSuccessfulSelectionStatusCodes() {
            return successfulSelectionStatusCodes;
        }


        /**
         * Print out the AID in hex
         *
         * @return a string
         */
        @Override
        public String toString() {
            return String.format("AID:%s, OCCURRENCE:%s",
                    aidToSelect == null ? "null" : ByteArrayUtil.toHex(aidToSelect.getValue()),
                    fileOccurrence);
        }
    }

    /**
     * Static nested class to hold the data elements used to perform an ATR based filtering
     */
    public static class AtrFilter {
        /**
         * Regular expression dedicated to handle SE logical channel opening based on ATR pattern
         */
        private String atrRegex;

        /**
         * Regular expression based filter
         *
         * @param atrRegex String hex regular expression
         */
        public AtrFilter(String atrRegex) {
            this.atrRegex = atrRegex;
        }

        /**
         * Setter for the regular expression provided at construction time
         *
         * @param atrRegex expression string
         */
        public void setAtrRegex(String atrRegex) {
            this.atrRegex = atrRegex;
        }

        /**
         * Getter for the regular expression provided at construction time
         *
         * @return Regular expression string
         */
        public String getAtrRegex() {
            return atrRegex;
        }

        /**
         * Tells if the provided ATR matches the registered regular expression
         *
         * If the registered regular expression is empty, the ATR is always matching.
         *
         * @param atr a buffer containing the ATR to be checked
         * @return a boolean true the ATR matches the current regex
         */
        public boolean atrMatches(byte[] atr) {
            boolean m;
            if (atrRegex.length() != 0) {
                Pattern p = Pattern.compile(atrRegex);
                String atrString = ByteArrayUtil.toHex(atr);
                m = p.matcher(atrString).matches();
            } else {
                m = true;
            }
            return m;
        }

        /**
         * Print out the ATR regex
         *
         * @return a string
         */
        @Override
        public String toString() {
            return String.format("ATR regex:%s", atrRegex.length() != 0 ? atrRegex : "empty");
        }
    }

    /**
     * Create a SeSelector to perform the SE selection
     * <p>
     * if seProtocol is null, all protocols will match and the selection process will continue
     *
     * <p>
     * if seProtocol is not null, the current SE protocol will checked and the selection process
     * will continue only if the protocol matches.
     *
     * <p>
     * if aidSelector is null, no 'select application' command is generated. In this case the SE
     * must have a default application selected. (e.g. SAM or Rev1 Calypso cards)
     * <p>
     * if aidSelector is not null, a 'select application' command is generated and performed.
     * Furthermore, the status code is checked against the list of successful status codes in the
     * {@link AidSelector} to determine if the SE matched or not the selection data.
     * <p>
     * if atrFilter is null, no check of the ATR is performed. All SE will match.
     * <p>
     * if atrFilter is not null, the ATR of the SE is compared with the regular expression provided
     * in the {@link AtrFilter} in order to determine if the SE match or not the expected ATR.
     *
     * @param seProtocol the SE communication protocol
     * @param atrFilter the ATR filter
     * @param aidSelector the AID selection data
     * @param extraInfo information string (to be printed in logs)
     */
    public SeSelector(SeProtocol seProtocol, AtrFilter atrFilter, AidSelector aidSelector,
            String extraInfo) {
        this.seProtocol = seProtocol;
        this.aidSelector = aidSelector;
        this.atrFilter = atrFilter;
        if (extraInfo != null) {
            this.extraInfo = extraInfo;
        } else {
            this.extraInfo = "";
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Selection data: AID = {}, ATRREGEX = {}, EXTRAINFO = {}",
                    this.aidSelector == null ? "null"
                            : ByteArrayUtil.toHex(this.aidSelector.getAidToSelect().getValue()),
                    this.atrFilter == null ? "null" : this.atrFilter.getAtrRegex(), extraInfo);
        }
    }

    /**
     * Getter
     *
     * @return the {@link SeProtocol} provided at construction time
     */
    public SeProtocol getSeProtocol() {
        return seProtocol;
    }

    /**
     * Getter
     * 
     * @return the {@link AtrFilter} provided at construction time
     */
    public AtrFilter getAtrFilter() {
        return atrFilter;
    }

    /**
     * Getter
     *
     * @return the {@link AidSelector} provided at construction time
     */
    public AidSelector getAidSelector() {
        return aidSelector;
    }

    /**
     * Gets the information string
     *
     * @return a string to be printed in logs
     */
    public final String getExtraInfo() {
        return extraInfo;
    }

    @Override
    public String toString() {
        return "SeSelector: AID_SELECTOR = "
                + (aidSelector == null ? "null" : aidSelector.toString()) + ", ATR_FILTER " + "= "
                + (atrFilter == null ? "null" : atrFilter.toString());
    }
}
