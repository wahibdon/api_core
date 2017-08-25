package com.swifttrip.core.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Austin on 11-Apr-17.
 *
 * Bean used to deserailze the only interesting bit of the JWT token returned with the introspect endpoint: the roles
 * assigned to a user
 */
public class TokenData {

	@SerializedName("realm_access")
	private RealmAccess realmAccess;

	@SerializedName("resource_access")
	private ResourceAccess resourceAccess;

	private String username;

	private String jti;

	private String exp;

	private String nbf;

	private String iat;

	private String iss;

	private String aud;

	private String sub;

	private String typ;

	private String azp;

	@SerializedName("auth_time")
	private String auth_time;

	@SerializedName("session_state")
	private String session_state;

	private String acr;

	@SerializedName("client_id")
	private String clientId;

	@SerializedName("client_session")
	private String clientSession;

	@SerializedName("allowed-origins")
	private List<String> allowedOrigins;

	private String name;
	@SerializedName("preferred_username")
	private String preferredUsername;
	@SerializedName("given_name")
	private String givenName;
	@SerializedName("family_name")
	private String familyName;
	private String email;

	private Boolean active;

	/**
	 * Default Generated Getter
	 */
	public RealmAccess getRealmAccess() {
		return realmAccess;
	}

	public String getUsername() {
		return username;
	}

	public ResourceAccess getResourceAccess() {
		return resourceAccess;
	}

	public String getJti() {
		return jti;
	}

	public String getExp() {
		return exp;
	}

	public String getNbf() {
		return nbf;
	}

	public String getIat() {
		return iat;
	}

	public String getIss() {
		return iss;
	}

	public String getAud() {
		return aud;
	}

	public String getSub() {
		return sub;
	}

	public String getTyp() {
		return typ;
	}

	public String getAzp() {
		return azp;
	}

	public String getAuth_time() {
		return auth_time;
	}

	public String getSession_state() {
		return session_state;
	}

	public String getAcr() {
		return acr;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSession() {
		return clientSession;
	}

	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}

	public String getName() {
		return name;
	}

	public String getPreferredUsername() {
		return preferredUsername;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getActive() {
		return active;
	}

}
