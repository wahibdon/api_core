package com.swifttrip.core.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Austin on 21-Apr-17.
 */

public class FilterUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtil.class);

	/**
	 * Accepts an object and JSON object.  The json object defines what information is desired.
	 * This method nulls out fields in obj based on the JSON filter.  The null fields will not be returned in the
	 * serialized object that is returned to the requesting front end.
	 * <p>
	 * JSON filter is in format lie {fieldName: true, fieldName2: true, ..., {subObjFieldName: treu}, ...}
	 * <p>
	 * method is recursive for all sub-objects.
	 *
	 * @param obj
	 * @param filter
	 */
	public static void keepJsonFields(Object obj, JsonObject filter){
		if(filter == null)
			return;
		List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());
		List<String> newFilter = new ArrayList<>();
		for (Map.Entry<String, JsonElement> entry : filter.entrySet()) {
			try {
				try {
					newFilter.add(entry.getKey());
					entry.getValue().getAsString();
				}catch(UnsupportedOperationException uoe) {
					Field field = obj.getClass().getDeclaredField(entry.getKey());
					field.setAccessible(true);
					try {
						Object newObj = field.get(obj);
						keepJsonFields(newObj, entry.getValue().getAsJsonObject());
					}catch(IllegalAccessException iae){
						LOGGER.error("unable to access field: {}", field.getName());
					}
				}
			}catch(NoSuchFieldException iae){
				LOGGER.error(" field - {} - not found", entry.getKey());
			}
		}
		for(Field field : fields){
			try{
				field.setAccessible(true);
				if (!newFilter.contains(field.getName())){
					field.set(obj, null);
				}
			}catch (IllegalAccessException iae){
				LOGGER.error("unable to set field: {}", field.getName());
			}
		}
	}
}
