package com.swifttrip.core.api;

import java.time.LocalDateTime;

/**
 * Created by Austin on 10-May-17.
 */
public class Audit implements Cloneable{

	private Integer id;
	private String databaseName;
	private String tableName;
	private String recordId;
	private String changeType;
	private String username;
	private LocalDateTime timeChanged;
	private String fieldName;
	private String previousValue;
	private String newValue;

	public Audit() {
		//Empty
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(final String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(final String recordId) {
		this.recordId = recordId;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(final String changeType) {
		this.changeType = changeType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public LocalDateTime getTimeChanged() {
		return timeChanged;
	}

	public void setTimeChanged(final LocalDateTime timeChanged) {
		this.timeChanged = timeChanged;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(final String previousValue) {
		this.previousValue = previousValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(final String newValue) {
		this.newValue = newValue;
	}

	public Audit clone() throws CloneNotSupportedException{
		return (Audit) super.clone();
	}
}
