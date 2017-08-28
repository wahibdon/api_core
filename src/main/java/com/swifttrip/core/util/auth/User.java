package com.swifttrip.core.util.auth;

import com.swifttrip.core.api.TokenData;

/**
 * Created by Austin on 05-May-17.
 */
public class User {

	private static TokenData tokenData;

	private User() {
		//EMPTY
	}

	public static TokenData getTokenData() {
		return tokenData;
	}

	public static void setTokenData(TokenData tokenData) {
		User.tokenData = tokenData;
	}
}
