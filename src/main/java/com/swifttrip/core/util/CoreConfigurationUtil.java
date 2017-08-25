package com.swifttrip.core.util;

import org.slf4j.LoggerFactory;

public class CoreConfigurationUtil {

	private static String ENV = EnvironmentUtil.readEnvironment();
	/**
	 * Name of the service that will be used in the Nginx template.
	 */
	private static String SERVICE_NAME;

	/**
	 * Major version of the service that will be used in the Nginx template.
	 */
	private static String SERVICE_VERSION;

	/**
	 * String to prepend to every route.
	 */
	private static String ROUTE_PREFIX;
	/**
	 * Where our Consul API can be found.
	 */
	private static String CONSUL_HOST;

	/**
	 * String containing the Database host for the envrionment
	 */
	private static String DB_HOST;

	/**
	 * String containing the Database password for the envrionment
	 */
	private static String DB_PASSWORD;

	/**
	 * String containing the Database user
	 */
	private static String DB_USER;

	/**
	 * Service ID instance for this class.
	 */
	private static String CONSUL_ID;

	/**
	 * Empty private constructor to mark this class as a utility.
	 */
	private CoreConfigurationUtil() {
		// Empty
	}

	public static String getENV() {
		return ENV;
	}

	public static String getServiceName() {
		return SERVICE_NAME;
	}

	public static String getServiceVersion() {
		return SERVICE_VERSION;
	}

	public static String getRoutePrefix() {
		return ROUTE_PREFIX;
	}

	public static String getConsulHost() {
		return CONSUL_HOST;
	}

	public static String getDbHost() {
		return DB_HOST;
	}

	public static String getDbPassword() {
		return DB_PASSWORD;
	}

	public static String getDbUser() {
		return DB_USER;
	}

	public static String getConsulId() {
		return CONSUL_ID;
	}

	public static void setServiceName(String serviceName) {
		SERVICE_NAME = serviceName;
	}

	public static void setServiceVersion(String serviceVersion) {
		SERVICE_VERSION = serviceVersion;
		setRoutePrefix('/'+serviceVersion);
	}

	private static void setRoutePrefix(String routePrefix) {
		ROUTE_PREFIX = routePrefix;
	}

	public static void setConsulHost(String consulHost) {
		CONSUL_HOST = consulHost;
	}

	public static void setDbHost(String dbHost) {
		DB_HOST = dbHost;
	}

	public static void setDbPassword(String dbPassword) {
		DB_PASSWORD = dbPassword;
	}

	public static void setDbUser(String dbUser) {
		DB_USER = dbUser;
	}

	public static void setConsulId(String consulId) {
		CONSUL_ID = consulId;
	}
}
