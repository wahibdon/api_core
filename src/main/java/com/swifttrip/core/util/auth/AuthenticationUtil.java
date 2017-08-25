package com.swifttrip.core.util.auth;

import com.swifttrip.core.util.FormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

/**
 * Utility class to manage checking authentication and storage of roles of a particular user
 */
public class AuthenticationUtil {
	private static Logger log = LoggerFactory.getLogger(AuthenticationUtil.class);

	/**
	 * Empty private constructor to mark this class as a utility.
	 */
	private AuthenticationUtil(){
		//Empty
	}
	/**
	 * Method to check if a valid access token is present in the request
	 * @param req HTTP request object.  Required by spark route methods
	 * @param res HTTP response object.  Required by spark route methods
	 */
	public static void checkAuthentication(Request req, Response res){
		if("OPTIONS".equalsIgnoreCase(req.requestMethod())){
			return;
		}
		try {
			final Token token = new Token(req.headers("authorization"));
			token.introspect();
		}catch(NullPointerException npe){
			log.info("Invalid or expired token was used to authenticate");
			log.error("this happened: ", npe);
			handleUnauthenticatedException();
		}
	}

	/**
	 * Throws a generic 401 stating an invalid token was provided if there was any problem either authenticating
	 * or authorizing.  The token is invalid for the operation attempted.
 	 */
	private static void handleUnauthenticatedException(){
		halt(401, FormatUtil.buildJsonMessage("invalid or expired token was used to authenticate").toString());
	}
}
