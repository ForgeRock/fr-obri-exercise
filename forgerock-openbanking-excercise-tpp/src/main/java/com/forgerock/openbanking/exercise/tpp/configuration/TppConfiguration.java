/*
 * The contents of this file are subject to the terms of the Common Development and
 *  Distribution License (the License). You may not use this file except in compliance with the
 *  License.
 *
 *  You can obtain a copy of the License at https://forgerock.org/license/CDDLv1.0.html. See the License for the
 *  specific language governing permission and limitations under the License.
 *
 *  When distributing Covered Software, include this CDDL Header Notice in each file and include
 *  the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 *  Header, with the fields enclosed by brackets [] replaced by your own identifying
 *  information: "Portions copyright [year] [name of copyright owner]".
 *
 *  Copyright 2018 ForgeRock AS.
 *
 */
package com.forgerock.openbanking.exercise.tpp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Configuration
public class TppConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TppConfiguration.class);

    @Value("${tpp.aisp.redirect_uri}")
    public String aispRedirectUri;

    @Value("${tpp.pisp.redirect_uri}")
    public String pispRedirectUri;

    @Value("${tpp.openbanking.version}")
    public String version;

    @Value("${tpp.jwkms}")
    private String jwkms;
    @Value("${tpp.directory}")
    private String directory;

    private String ssaContent = null;

    public String getAispRedirectUri() {
        return aispRedirectUri;
    }

    public String getPispRedirectUri() {
        return pispRedirectUri;
    }

    public String getVersion() {
        return version;
    }

    public String getJwkms() {
        return jwkms;
    }

    public String getDirectory() {
        return directory;
    }
}
