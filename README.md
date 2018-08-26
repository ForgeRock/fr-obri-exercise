<!--
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2018 ForgeRock AS.
-->
# Open Banking Reference Implementation Sample

Code sample for helping TPPs and ASPSP using the ForgeRock Open Banking reference implementation platform.

## Licence
License:	CDDLv1.0 \
License URL	: http://forgerock.org/cddlv1-0/


## How to install the app


### Downloading the project code and loading it with intellij

You need to get the code locally for the git project repo. For this, you need to

- Fork the project
- Clone your fork (if it's your first development with GIT, we recommend putting all the git repo in ~/Development/GIT)
- Download the latest intellij version (ultimate)
- Open the project with intellij (you will need to open it as an existing maven project)

### Setup the host files

You will need to create some new hostnames for the application.

```$xslt
127.0.0.1		tpp.example.com
```

### Setup the google chrome driver

Our functional tests use Selenium, which requires a google chrome driver to run.
This would need to be installed in your OS system first.
Follow the instruction here:
https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver