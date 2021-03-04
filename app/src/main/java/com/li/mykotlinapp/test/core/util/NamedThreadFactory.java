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
package com.li.mykotlinapp.test.core.util;

import java.util.concurrent.ThreadFactory;

/**
 * factory that allows to create thread with custom name
 */
public class NamedThreadFactory implements ThreadFactory {
    String name;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    public Thread newThread(Runnable r) {
        return new Thread(r, name);
    }
}
