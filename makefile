#!/usr/bin/env bash

# Setup -----------------------------------------------------------------------

# Where Tomcat is installed and where the calling script is located
TOMCAT = /opt/tomcat
CATALINA = $(TOMCAT)/bin/catalina.sh

# The hostname should match the Tomcat context for development
HOST=$(shell hostname)

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
	mvn war:exploded

test:
	# Run every file in the test directory like `Test*.java`
	mvn test

integration-test:
	# Run every file in the test directory like `ITest*.java`
	mvn integration-test

war: clean compile
	# Create a deployable WAR file
	mvn package

# Tomcat targets --------------------------------------------------------------

sync:
	# Place build output from the compile target into Tomcat
	rsync -av --delete target/$(HOST)/ $(TOMCAT)/webapps/$(HOST)

run:
	# Place build output from the compile target into Tomcat
	$(CATALINA) run

run-background:
	$(CATALINA) start

debug:
	$(CATALINA) jpda run

debug-background:
	$(CATALINA) jpda start

stop-background:
	$(CATALINA) stop
