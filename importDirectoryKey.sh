#!/usr/bin/env bash


openssl pkcs12 -export -in keys/transport.pem -inkey keys/transport.key -name "transport" \
 -out ./forgerock-openbanking-excercise-tpp/src/main/resources/keystore/keystore.p12 -password pass:changeit
