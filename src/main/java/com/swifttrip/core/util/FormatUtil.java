package com.swifttrip.core.util;

import com.google.gson.JsonObject;

/**
 * Utility class to format strings according to various criteria.
 */
public final class FormatUtil {

	/**
	 * Empty private constructor to mark this class as a utility.
	 */
	private FormatUtil() {
		// Empty
	}

	/**
	 * Format some message input text as a full JSON object string.
	 *
	 * @param message The simple message to place into a JSON wrapper
	 * @return Input wrapped in a JSON format
	 */
	public static JsonObject buildJsonMessage(final String message) {
		return buildJsonMessage("message", message);
	}

	/**
	 * Format some simple message text as a full JSON object string.
	 *
	 * @param messageKey Denotation for message being placed in JSON
	 * @param message    The simple message to place into a JSON wrapper
	 * @return Input wrapped in a JSON format
	 */
	public static JsonObject buildJsonMessage(final String messageKey, final String message) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(messageKey, message);
		return jsonObject;
	}

}
