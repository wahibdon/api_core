package com.swifttrip.core.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Bean for holding zone information pulled from the access token.  This is used for json serialization with GSON
 */
public class RealmAccess {

	@SerializedName("roles")
	private List<String> permissions;

	/**
	 * Default Generated Getter/Setter
	 */
	public List<String> getPermissions() {
		return permissions;
	}
}
