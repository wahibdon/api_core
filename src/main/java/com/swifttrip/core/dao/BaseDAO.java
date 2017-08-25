package com.swifttrip.core.dao;

import com.swifttrip.core.api.AbstractRecord;

import java.util.List;

/**
 * Created by Austin on 09-May-17.
 */
public interface BaseDAO {
	<U extends AbstractRecord> List<U> readAll();

	<U extends AbstractRecord> U readOne(String id);

	Integer create(Object obj);

	Integer update(Object obj);

	String tableName = null;

	String databaseName = null;
}
