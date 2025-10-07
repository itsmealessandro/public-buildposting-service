#!/bin/sh
#ls -la 
mvn clean package spring-boot:repackage
java -jar target/camundaApi-0.0.1-SNAPSHOT.jar