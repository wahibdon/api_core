package com.swifttrip.core.util;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * Utility class for Consul operations.
 * All the big Java Consul libraries tend to have memory leaks and require huge dependencies.
 */

public final class ConsulUtil {

	/**
	 * Logger instance for this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsulUtil.class);

	/**
	 * Where our Consul API can be found.
	 */
	private static String CONSUL_HOST;

	/**
	 * Service ID instance for this class.
	 */
	private static String CONSUL_ID;

	/**
	 * Empty private constructor to mark this class as a utility.
	 */
	public ConsulUtil() {
		//EMPTY
	}

	public static void init(){
		CONSUL_HOST = CoreConfigurationUtil.getConsulHost();
		CONSUL_ID = CoreConfigurationUtil.getConsulId();
	}

	/**
	 * Register this instance of the API from Consul.
	 */
	public static void register() {

		final JSONObject serviceJson = new JSONObject();
		serviceJson.put("ID", CONSUL_ID);
		serviceJson.put("Name", CoreConfigurationUtil.getServiceName());
		serviceJson.put("Tags", Collections.singletonList(CoreConfigurationUtil.getServiceVersion()));

		final JSONObject checkJson = new JSONObject();
		checkJson.put("TTL", "2m");
		checkJson.put("Status", "passing");
		serviceJson.put("Check", checkJson);

		try {
			Unirest.put(CONSUL_HOST + "/agent/service/register").body(serviceJson).asJson();
		} catch (final UnirestException e) {
			LOGGER.error("Failed to register service with Consul", e);
		}
	}

	/**
	 * Tell Consul that this service is still alive and well.
	 */
	public static void checkin() {
		try {
			Unirest.get(CONSUL_HOST + "/agent/check/pass/service:{id}").routeParam("id", CONSUL_ID).asJson();
		} catch (final UnirestException e) {
			LOGGER.error("Failed to pass service TTL check with Consul", e);
		}
	}

	/**
	 * Deregister this instance of the API from Consul.
	 */
	public static void deregister() {
		try {
			Unirest.put(CONSUL_HOST + "/agent/service/deregister/{id}").routeParam("id", CONSUL_ID).asJson();
		} catch (final UnirestException e) {
			LOGGER.error("Failed to deregister service with Consul", e);
		}
	}

	/**
	 * Get a string value from Consul's KV store.
	 *
	 * @param path Where the desired KV exists, which will have "config/api" prepended.
	 * @return Raw string value for desired key, or null.
	 */
	public static String retrieveProperty(final String host, final String path) {

		try {
			return Unirest.get(host + "/kv/config/api/{path}?raw=true")
						   .routeParam("path", path)
						   .asString()
						   .getBody();
		} catch (final UnirestException e) {
			LOGGER.error("Failed to retrieve KV from Consul", e);
		}

		return null;
	}
}
