package com.swifttrip.core.api;

/**
 * Created by Austin on 04-Apr-17.
 *
 * Base bean that holds all common database fields
 */
public class AbstractRecord {
	private String id;

	/**
	 * Default Generated Getter/Setter
	 */
	public AbstractRecord(){
		//Empty
	}

	/**
	 * Default Generated Getter/Setter
	 */
	public void setId(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
