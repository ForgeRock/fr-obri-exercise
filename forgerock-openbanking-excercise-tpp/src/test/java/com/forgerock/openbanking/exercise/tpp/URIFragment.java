package com.forgerock.openbanking.exercise.tpp;

import com.forgerock.openbanking.exercise.tpp.ui.SeleniumConfig;

import java.net.URI;
import java.net.URISyntaxException;

public class URIFragment {

    public static String get(SeleniumConfig config) throws InterruptedException, URISyntaxException {
        for (int i = 0; i < 30; i++) {
            URI uri = new URI(config.getDriver().getCurrentUrl());
            if (uri.getFragment() != null) {
                return uri.getFragment();
            }
            Thread.sleep(500);
        }
        throw new IllegalStateException("Cannot get URI with fragment");
    }
}
