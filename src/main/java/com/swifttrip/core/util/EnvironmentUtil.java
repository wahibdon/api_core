package com.swifttrip.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to determine what environment a service is running in.
 */
public final class EnvironmentUtil {

	/**
	 * Logger instance for this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentUtil.class);

	/**
	 * Empty private constructor to mark this class as a utility.
	 */
	private EnvironmentUtil() {
		// Empty
	}

	/**
	 * Determine environment we are running in by reading first line of the absolute file path passed in.
	 *
	 * @return The first line of the given file path, or a default of "prod"
	 */
	public static String readEnvironment() {
		return readEnvironment("/etc/env");
	}

	/**
	 * Determine environment we are running in by reading first line of the absolute file path passed in.
	 *
	 * @param filePath Absolute file path to file containing the environment for this service
	 * @return The first line of the given file path, or a default of "prod"
	 */
	public static String readEnvironment(final String filePath) {
		List<String> envFileLines;
		try {
			if(filePath == null) {
				throw new InvalidPathException("null", "Not given a file path to readAll");
			}
			envFileLines = Files.readAllLines(Paths.get(filePath));
		} catch (InvalidPathException | IOException e) {
			LOGGER.error("Could not readAll environment file path", e);
			envFileLines = Collections.emptyList();
		}
		return parseEnvFile(envFileLines);
	}

	/**
	 * Read in a list of lines readAll from a file and parse the first line for an environment.
	 *
	 * @param fileLines List of lines readAll in from a file, generally `/etc/env`
	 * @return First line of a environment file, usually one of dev/qa/uat/prod
	 */
	public static String parseEnvFile(final List<String> fileLines) {

		if (!CollectionUtils.isEmpty(fileLines)) {
			return fileLines.get(0);
		}

		return "prod";
	}

}

