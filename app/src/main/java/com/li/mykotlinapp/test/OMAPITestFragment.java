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
package com.li.mykotlinapp.test;

import java.util.SortedSet;

import com.li.mykotlinapp.test.calypso.command.po.parser.ReadDataStructure;
import com.li.mykotlinapp.test.calypso.command.po.parser.ReadRecordsRespPars;
import com.li.mykotlinapp.test.calypso.transaction.CalypsoPo;
import com.li.mykotlinapp.test.calypso.transaction.PoResource;
import com.li.mykotlinapp.test.calypso.transaction.PoSelectionRequest;
import com.li.mykotlinapp.test.calypso.transaction.PoSelector;
import com.li.mykotlinapp.test.calypso.transaction.PoTransaction;
import com.li.mykotlinapp.test.core.selection.MatchingSelection;
import com.li.mykotlinapp.test.core.selection.SeSelection;
import com.li.mykotlinapp.test.core.selection.SelectionsResult;
import com.li.mykotlinapp.test.core.seproxy.ChannelControl;
import com.li.mykotlinapp.test.core.seproxy.MultiSeRequestProcessing;
import com.li.mykotlinapp.test.core.seproxy.SeProxyService;
import com.li.mykotlinapp.test.core.seproxy.SeReader;
import com.li.mykotlinapp.test.core.seproxy.SeSelector;
import com.li.mykotlinapp.test.core.seproxy.exception.KeyplePluginInstantiationException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeyplePluginNotFoundException;
import com.li.mykotlinapp.test.core.seproxy.exception.KeypleReaderException;
import com.li.mykotlinapp.test.core.seproxy.protocol.SeCommonProtocols;
import com.li.mykotlinapp.test.core.util.ByteArrayUtil;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.li.mykotlinapp.R;

/**
 * View for OMAPI Tests
 */
public class OMAPITestFragment extends Fragment {


    private static final String TAG = OMAPITestFragment.class.getSimpleName();

    private TextView mText;

    public static OMAPITestFragment newInstance() {
        return new OMAPITestFragment();
    }

    /**
     * Initialize SEProxy with Keyple Android OMAPI Plugin
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize SEProxy with Android Plugin
        Log.d(TAG, "Initialize SEProxy with Android OMAPI Plugin ");

        /* Get the instance of the SeProxyService (Singleton pattern) */
        SeProxyService seProxyService = SeProxyService.getInstance();

        /* register Omapi Plugin to the SeProxyService */
        try {
            seProxyService.registerPlugin(new AndroidOmapiPluginFactory());
        } catch (KeyplePluginInstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize UI for this view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_omapi_test, container, false);
        mText = (TextView) view.findViewById(R.id.text);
        return view;
    }


    /**
     * Run a basic set of commands to connected Keyple readers
     */
    @Override
    public void onResume() {
        super.onResume();

        try {
            SortedSet<SeReader> readers = SeProxyService.getInstance()
                    .getPlugin(AndroidOmapiPlugin.PLUGIN_NAME).getReaders();

            if (readers == null || readers.size() < 1) {
                mText.append("\nNo readers found in OMAPI Keyple Plugin");
                mText.append("\nTry to reload..");
            } else {
                for (SeReader aReader : readers) {
                    Log.d(TAG, "Launching tests for reader : " + aReader.getName());
                    runHoplinkSimpleRead(aReader);
                }

            }

        } catch (KeyplePluginNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Run Hoplink Simple read command
     */
    private void runHoplinkSimpleRead(SeReader reader) {
        Log.d(TAG, "Running HopLink Simple Read Tests");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mText.append("\nLaunching tests for reader : " + reader.getName());

                try {


                    String poAid = "A000000291A000000191";
                    byte SFIHoplinkEFT2Environment = (byte) 0x14;
                    byte SFIHoplinkEFT2Usage = (byte) 0x1A;

                    String t2UsageRecord1_dataFill =
                            "0102030405060708090A0B0C0D0E0F10" + "1112131415161718191A1B1C1D1E1F20"
                                    + "2122232425262728292A2B2C2D2E2F30";

                    /*
                     * Prepare a Calypso PO selection
                     */
                    SeSelection seSelection = new SeSelection(MultiSeRequestProcessing.FIRST_MATCH,
                            ChannelControl.KEEP_OPEN);

                    /*
                     * Setting of an AID based selection of a Calypso REV3 PO
                     *
                     * Select the first application matching the selection AID whatever the SE
                     * communication protocol keep the logical channel open after the selection
                     */

                    /*
                     * Calypso selection: configures a PoSelectionRequest with all the desired
                     * attributes to make the selection and read additional information afterwards
                     */
                    PoSelectionRequest poSelectionRequest = new PoSelectionRequest(
                            new PoSelector(SeCommonProtocols.PROTOCOL_ISO14443_4, null,
                                    new PoSelector.PoAidSelector(
                                            new SeSelector.AidSelector.IsoAid(poAid),
                                            PoSelector.InvalidatedPo.REJECT),
                                    "AID: " + poAid));


                    mText.append("\n");
                    mText.append("Selecting application : " + poAid);
                    mText.append("\n");

                    /*
                     * Prepare the reading order and keep the associated parser for later use once
                     * the selection has been made.
                     */
                    int readEnvironmentParserIndex = poSelectionRequest.prepareReadRecordsCmd(
                            SFIHoplinkEFT2Environment, ReadDataStructure.SINGLE_RECORD_DATA,
                            (byte) 1, String.format("Hoplink EF T2Environment (SFI=%02X)",
                                    SFIHoplinkEFT2Environment));

                    int readUsageParserIndex = poSelectionRequest.prepareReadRecordsCmd(
                            SFIHoplinkEFT2Environment, ReadDataStructure.SINGLE_RECORD_DATA,
                            (byte) 1, String.format("Hoplink EF T2Usage (SFI=%02X)",
                                    SFIHoplinkEFT2Environment));

                    /*
                     * Add the selection case to the current selection (we could have added other
                     * cases here)
                     *
                     * Ignore the returned index since we have only one selection here.
                     */
                    seSelection.prepareSelection(poSelectionRequest);

                    /*
                     * Actual PO communication: operate through a single request the Calypso PO
                     * selection and the file read
                     */

                    SelectionsResult selectionsResult = null;
                    try {
                        selectionsResult = seSelection.processExplicitSelection(reader);
                    } catch (KeypleReaderException e) {
                        e.printStackTrace();
                    }

                    if (selectionsResult.hasActiveSelection()) {
                        MatchingSelection matchingSelection = selectionsResult.getActiveSelection();

                        CalypsoPo calypsoPo = (CalypsoPo) matchingSelection.getMatchingSe();
                        mText.append("The selection of the PO has succeeded.\n");

                        ReadRecordsRespPars readEnvironmentParser =
                                (ReadRecordsRespPars) matchingSelection
                                        .getResponseParser(readEnvironmentParserIndex);

                        /*
                         * Retrieve the data read from the parser updated during the selection
                         * process (Environment)
                         */
                        byte environmentAndHolder[] = (readEnvironmentParser.getRecords()).get(1);

                        /* Log the result */
                        mText.append("Environment file data: "
                                + ByteArrayUtil.toHex(environmentAndHolder) + "\n");

                        ReadRecordsRespPars readT2UsageParser =
                                (ReadRecordsRespPars) matchingSelection
                                        .getResponseParser(readUsageParserIndex);

                        /*
                         * Retrieve the data read from the parser updated during the selection
                         * process (Usage)
                         */
                        byte t2Usage[] = (readT2UsageParser.getRecords()).get(1);

                        /* Log the result */
                        mText.append("T2 Usage data: " + ByteArrayUtil.toHex(t2Usage) + "\n");

                        /* Go on with the reading of the first record of the EventLog file */
                        mText.append(
                                "==================================================================================");
                        mText.append(
                                "= Update T2 Usage.                                                               =");
                        mText.append(
                                "==================================================================================");

                        PoTransaction poTransaction =
                                new PoTransaction(new PoResource(reader, calypsoPo));

                        /*
                         * Prepare the update order and keep the associated parser for later use
                         * once the transaction has been processed.
                         */

                        int updateT2UsageParserIndex =
                                poTransaction.prepareUpdateRecordCmd(SFIHoplinkEFT2Usage,
                                        (byte) 0x01, ByteArrayUtil.fromHex(t2UsageRecord1_dataFill),
                                        String.format("Update T2 USage (SFI=%02X, recnbr=%d))",
                                                SFIHoplinkEFT2Usage, 1));

                        /*
                         * Actual PO communication: send the prepared read order, then close the
                         * channel with the PO
                         */
                        if (poTransaction.processPoCommands(ChannelControl.CLOSE_AFTER)) {
                            mText.append("The update of the T2 Usage file has succeeded.");

                        }
                        mText.append("=====================================\n");
                        mText.append("= End of the Calypso PO processing. =\n");
                        mText.append("=====================================\n");
                    }
                } catch (final KeypleReaderException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            e.printStackTrace();
                            mText.append("\n ---- \n");
                            mText.append("IOReader Exception : " + e.getMessage());

                        }
                    });
                }
            }
        });
    }


    /**
     * Revocation of the Activity from @{@link SeReader} list of observers
     */
    @Override
    public void onDestroy() {
        super.onDestroy();


        /*
         * // destroy AndroidNFC fragment if needed FragmentManager fm = getFragmentManager();
         * Fragment f = fm.findFragmentByTag(TAG_OMAPI_ANDROID_FRAGMENT); if (f != null) {
         * fm.beginTransaction().remove(f).commit(); }
         */

    }


}
