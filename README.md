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

### Download and set up keys
1. Log in to [Open Banking Reference Implementation]()
1. Go to [Software Statements](https://directory.ob.forgerock.financial/software-statement)
1. Click `See More` on your Software Statement you've previously created
1. Go to the `Transport/Signing/Encryption keys` tab and scroll to the bottom
1. For your transport key click on `Actions` download `Public certificate (.pem)` and `Private certificate (.key)`
1. Move them from your downloads folder to the root of this directory
1. Run `importDirectoryKey.sh`

Note. You'll need to unregister your TPP to run the tests if it's already registered.

### Setup the google chrome driver

Our functional tests use Selenium, which requires a google chrome driver to run.
This would need to be installed in your OS system first.
Follow the instruction here:
https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver

## Docs of the app

The app generated its own doc when you compile, in the folder target/generated-docs.
For conveniency, we also host it under https://qcastel.github.io/

