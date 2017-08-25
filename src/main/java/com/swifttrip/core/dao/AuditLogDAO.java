package com.swifttrip.core.dao;

import com.swifttrip.core.api.Audit;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;

import java.util.List;

/**
 * Created by Austin on 05-May-17.
 */
public interface AuditLogDAO {

	@SqlBatch("insert into audit values (:id, :databaseName, :tableName, :changeType, :recordId, :username, :fieldName, :previousValue, :newValue, :timeChanged)")
	void audit(@BindBean List<Audit> audit);
}
