#!/bin/bash

mvn clean install

java -classpath target/classes:{M2RePo}/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:{M2RePo}/org/slf4j/slf4j-api/1.7.29/slf4j-api-1.7.29.jar:{M2RePo}/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:{M2RePo}/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar com.neodem.rays.Rays
