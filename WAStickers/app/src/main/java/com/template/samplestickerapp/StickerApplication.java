/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.template.samplestickerapp;
import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp

public class StickerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
