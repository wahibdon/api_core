#!/usr/bin/env bash

# Setup -----------------------------------------------------------------------

all:
	# Print the contents of this makefile
	cat makefile

# Maven -----------------------------------------------------------------------

clean:
	# Delete the `target` directory
	mvn clean

compile:
	# Compile Java files and build a folder that looks like an unzipped WAR
	mvn compile

package:
	mvn package -B -Dbuildid=$$CI_BUILD_ID

deploy:
	mvn deploy:deploy-file -DgroupId=com.swifttrip -DartifactId=api-core -Dversion=$$CI_PIPELINE_ID -DgeneratePom=true -Dpackaging=jar -DrepositoryId=nexus -Durl=http://build7.swifttrip.com:8081/nexus/content/repositories/thirdparty -Dfile=target/api-core-$$CI_PIPELINE_ID.jar

test:
	# Run every file in the test directory like `Test*.java`
	mvn test

integration-test:
	# Run every file in the test directory like `ITest*.java`
	mvn integration-test
