package com.swifttrip.core.util.auth;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.swifttrip.core.api.TokenData;
import com.swifttrip.core.util.ConsulUtil;
import com.swifttrip.core.util.CoreConfigurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * Created by Austin on 11-Apr-17.
 *
 * Class to handle token introspection with keycloak.
 */
public class Token {

	private static Logger LOGGER = LoggerFactory.getLogger(Token.class);

	private static final String keycloakClient = ConsulUtil.retrieveProperty(CoreConfigurationUtil.getConsulHost(), CoreConfigurationUtil.getServiceName() + "/keycloak-client-id");
	private static final String keycloakSecret = ConsulUtil.retrieveProperty(CoreConfigurationUtil.getConsulHost(), CoreConfigurationUtil.getServiceName() + "/keycloak-client-secret");
	private static final Encoder encoder = Base64.getEncoder();
	private static final String basicAuth = encoder.encodeToString((keycloakClient+":"+keycloakSecret).getBytes());

	final private String token;

	/**
	 * Constructor to set the user's access token on object creation.
	 *
 	 * @param token access token in standard JWT format
	 */

	public Token(String token){
		this.token = token;
	}

	/**
	 * Method that connects to KeyCloak's introspect endpoint.  Using this instead of a local JWT decoder gives the
	 * added benefit of offloading all checking of token's validity to KeyCloak
	 */
	public void introspect(){
		final Gson gson = new Gson();
		try {
			final HttpResponse<String> response = Unirest
				.post(ConsulUtil.retrieveProperty(CoreConfigurationUtil.getConsulHost(), CoreConfigurationUtil.getServiceName() + "/auth-url"))
				.header("authorization", "Basic " + basicAuth)
				.field("token", token)
				.asString();
			User.setTokenData(gson.fromJson(response.getBody(), TokenData.class));
		}catch(UnirestException ue){
			LOGGER.error("Unable to introspect token", ue);
		}
	}

}
