package com.swifttrip.core.util.audit;

import com.swifttrip.core.api.AbstractRecord;
import com.swifttrip.core.api.Audit;
import com.swifttrip.core.dao.AuditLogDAO;
import com.swifttrip.core.dao.BaseDAO;
import com.swifttrip.core.util.CoreConfigurationUtil;
import com.swifttrip.core.util.DatabaseUtil;
import com.swifttrip.core.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditLogUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogUtil.class);

	public static <T extends AbstractRecord, U extends BaseDAO> boolean audit(Class<U> dao, T newInfo, String typeOfChange) throws IllegalAccessException, NoSuchFieldException, CloneNotSupportedException{
		DatabaseUtil databaseUtil = new DatabaseUtil(CoreConfigurationUtil.getDbHost(),CoreConfigurationUtil.getDbUser(),CoreConfigurationUtil.getDbPassword());
		boolean success = false;
		AuditLogDAO auditLogDAO = databaseUtil.getDbi().onDemand(AuditLogDAO.class);
		U u = databaseUtil.getDbi().onDemand(dao);
		Audit audit = new Audit();
		audit.setDatabaseName((String) dao.getDeclaredField("databaseName").get(u));
		audit.setTableName((String) dao.getDeclaredField("tableName").get(u));
		audit.setRecordId(newInfo.getId());
		audit.setChangeType(typeOfChange);
		audit.setUsername(User.getTokenData().getUsername());
		switch (typeOfChange){
			case "update":
				T record = u.readOne(newInfo.getId());
				Field[] fields = newInfo.getClass().getDeclaredFields();
				List<Audit> audits = new ArrayList<>();
				for (Field field : fields) {
					Audit holder = audit.clone();
					field.setAccessible(true);
					if (field.isAnnotationPresent(IgnoreInAudit.class))
						continue;
					Object recordVal = field.get(record);
					Object newInfoVal = field.get(newInfo);
					if( recordVal.toString().equals(newInfoVal.toString()))
						continue;
					holder.setFieldName(field.getName());
					holder.setPreviousValue(String.valueOf(recordVal));
					holder.setNewValue(String.valueOf(newInfoVal));
					audits.add(holder);
				}
				auditLogDAO.audit(audits);
				success = true;
				break;
			case "create":
				auditLogDAO.audit(Collections.singletonList(audit));
				success = true;
				break;
			case "delete":
			default:
				break;
		}
		return success;
	}
}
