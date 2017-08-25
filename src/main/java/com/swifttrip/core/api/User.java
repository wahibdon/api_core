package com.swifttrip.core.api;

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
