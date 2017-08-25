package com.swifttrip.core.util;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Utility class to manage database interactions.
 */

public final class DatabaseUtil {

	/**
	 * Logger instance for this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);

	/**
	 * Database connection pool source for opening/active connections.
	 */
	private BasicDataSource basicDataSource = new BasicDataSource();

	/**
	 * Database interaction convenience object builder.
	 */
	private DBI dbi = new DBI(basicDataSource);
	private String host;
	private String username;
	private String password;

	/**
	 * Empty private constructor to mark this class as a utility.
	 * @param host     Set a new host for db connections.
	 * @param username Set a new username for db connections,
	 * @param password Set a new password for db connections.
	 */
	public DatabaseUtil(
			final String host,
			final String username,
			final String password) {
		this.host = host;
		this.username = username;
		this.password = password;
		connect();
	}

	/**
	 * Connect to a MySQL instance.
	 *
	 */
	private void connect(
	) {

		basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setValidationQuery("SELECT 1");
		basicDataSource.setTestWhileIdle(true);
		basicDataSource.setTestOnBorrow(true);
		basicDataSource.setUrl(host);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		dbi = new DBI(basicDataSource);
	}

	/**
	 * Disconnect the database connection source.
	 */
	public void disconnect() {

		if (basicDataSource == null) {
			return;
		}

		try {
			basicDataSource.close();
		} catch (final SQLException e) {
			LOGGER.error("Could not close database source.", e);
		}

		AbandonedConnectionCleanupThread.checkedShutdown();
	}

	/**
	 * @return Automatically generated class accessor or modifier.
	 */
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

	/**
	 * @return Automatically generated class accessor or modifier.
	 */
	public DBI getDbi() {
		return dbi;
	}
}
