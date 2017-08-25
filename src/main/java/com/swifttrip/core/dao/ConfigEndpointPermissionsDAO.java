package com.swifttrip.core.dao;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.util.List;

/**
 * Created by Austin on 13-Apr-17.
 *
 * DAO interface for queries to the database
 */
public interface ConfigEndpointPermissionsDAO {
	/**
	 * get permissions for a particular resource
	 * @param endpoint endpoint name access is being looked up for
	 * @param method method used to hit endpoint
	 * @return list of permissions
	 */
	@SqlQuery("select permission from dashboard.config_endpoint_permissions where endpoint = :endpoint and method = :method")
	List<String> getPermissions(@Bind("endpoint") String endpoint, @Bind("method") String method);
}
