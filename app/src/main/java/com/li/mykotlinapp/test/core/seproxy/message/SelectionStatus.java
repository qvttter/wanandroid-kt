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
package com.li.mykotlinapp.test.core.seproxy.message;

import java.util.Arrays;

public class SelectionStatus {
    private final AnswerToReset atr;
    private final ApduResponse fci;
    private final boolean isMatching;

    public SelectionStatus(AnswerToReset atr, ApduResponse fci, boolean isMatching) {
        if (atr == null && fci == null) {
            throw new IllegalArgumentException("Atr and Fci can't be null at the same time.");
        }
        this.atr = atr;
        this.fci = fci;
        this.isMatching = isMatching;
    }

    public AnswerToReset getAtr() {
        return atr;
    }

    public ApduResponse getFci() {
        return fci;
    }

    public boolean hasMatched() {
        return isMatching;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SelectionStatus)) {
            return false;
        }
        SelectionStatus selectionStatus = (SelectionStatus) o;
        return selectionStatus.getAtr() == null ? this.atr == null
                : selectionStatus.getAtr().equals(this.atr) && selectionStatus.getFci() == null
                        ? this.fci == null
                        : selectionStatus.getFci().equals(this.fci)
                                && selectionStatus.hasMatched() == isMatching;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 19 * hash + (isMatching ? 0 : 1);
        hash = 31 * hash + (atr == null ? 0 : Arrays.hashCode(atr.getBytes()));
        hash = 7 * hash + (fci == null ? 0 : Arrays.hashCode(fci.getBytes()));
        return hash;
    }
}
