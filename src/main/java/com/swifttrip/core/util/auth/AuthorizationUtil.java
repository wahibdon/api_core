package com.swifttrip.core.util.auth;

import com.swifttrip.core.api.User;
import com.swifttrip.core.dao.ConfigEndpointPermissionsDAO;
import com.swifttrip.core.util.CoreConfigurationUtil;
import com.swifttrip.core.util.DatabaseUtil;
import com.swifttrip.core.util.FormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static spark.Spark.halt;

/**
 * Created by Austin on 18-Apr-17.
 */
public class AuthorizationUtil {

	private static Class clazz = AuthorizationUtil.class;
	private static String className = clazz.getSimpleName();
	private static final Logger LOGGER = LoggerFactory.getLogger(clazz);

	private AuthorizationUtil(){
		//Empty
	}

	public static void checkAuthorization(Request req, Response res){
		if("OPTIONS".equalsIgnoreCase(req.requestMethod())){
			return;
		}
		List<String> dbPermissions = getPermissionsEndpointFromDb(req);
		checkPermissions(dbPermissions);
	}

	private static List<String> getPermissionsEndpointFromDb(Request req){
		final String endpoint = req.pathInfo().split("/")[2];
		final ConfigEndpointPermissionsDAO configEndpointPermissionsDAO = new DatabaseUtil(CoreConfigurationUtil.getDbHost(),CoreConfigurationUtil.getDbUser(),CoreConfigurationUtil.getDbPassword()).getDbi().onDemand(ConfigEndpointPermissionsDAO.class);
		final List<String> basePermissions = configEndpointPermissionsDAO.getPermissions(endpoint, req.requestMethod());
		final List<String> dbPermissions = new ArrayList<>();
		for(String permission : basePermissions){
			if(req.pathInfo().split("/").length >=4)
				dbPermissions.add(permission + ":" + req.pathInfo().split("/")[3]);
			dbPermissions.add(permission + ":all");
		}
		return dbPermissions;
	}

	/**
	 * Method to handle compare the userPermissions derived from the database with the userPermissions set on the users'
	 * accounts in keycloak.
	 *
	 * @param permissions List of strings holding userPermissions for the specific resource from database
	 */
	private static void checkPermissions(List<String> permissions){
		if(Collections.disjoint(User.getTokenData().getRealmAccess().getPermissions(), permissions)){
			handleUnauthorizedException();
		}
	}

	private static void handleUnauthorizedException(){
		halt(403, FormatUtil.buildJsonMessage("FORBIDDEN").toString());
	}

}
