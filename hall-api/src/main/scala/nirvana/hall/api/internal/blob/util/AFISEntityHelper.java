package nirvana.hall.api.internal.blob.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nirvana.hall.api.internal.blob.error.AFISError;
import javax.persistence.Column;
import javax.persistence.Table;

public class AFISEntityHelper {

	public static <T> T toEntity(Class<T> cla, Map<String, Object> maps) {
		try {
			Object obj = cla.newInstance();

			Map columns = getColumns(cla);

			if (AFISHelper.isEmpty(columns))
				return (T) obj;

			for (Entry it : maps.entrySet()) {
				Field field = (Field) columns.get(it.getKey());
				if (field == null) {
					continue;
				}
				String fieldName = field.getName();

				String methodName = "set"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Method method = cla.getDeclaredMethod(methodName,
							new Class[] { field.getType() });

					Object fieldValue = AFISDataTypeHelper.convert(field.getType(), it.getValue());
					method.invoke(obj, new Object[] { fieldValue });
				} catch (NoSuchMethodException e) {
					String str = String.format("%s 没有方法 %s\n", new Object[] {
							cla.getName(), methodName });
					System.out.println(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AFISError(e);
		}
	}

	public static Map<String, Object> fromEntity(Object obj) {
		Class cla = obj.getClass();

		Map<String, Field> fields = getColumns(cla);

		if (AFISHelper.isEmpty(fields)) {
			return null;
		}

		Map<String, Object> maps = new HashMap<String, Object>();

		for (Entry<String, Field> it : fields.entrySet()) {
			Field field = (Field) it.getValue();

			String fieldName = field.getName();

			Method method = getMethod(cla, fieldName);

			if (method == null) {
				continue;
			}
			try {
				Object fieldValue = method.invoke(obj, new Object[0]);

				if (fieldValue == null)
					continue;
				maps.put((String) it.getKey(), fieldValue);
			} catch (Exception e) {
				System.out.printf("excute method %s.%s failed.\n",
						new Object[] { cla.getName(), method.getName() });
			}
		}

		return maps;
	}

	public static Map<String, Object> entityToMap(Object obj) {
		Class cla = obj.getClass();

		Map<String, Field> fields = getColumns(cla);

		if (AFISHelper.isEmpty(fields)) {
			return null;
		}

		Map<String, Object> maps = new HashMap<String, Object>();

		for (Entry<String, Field> it : fields.entrySet()) {
			Field field = (Field) it.getValue();

			String fieldName = field.getName();

			Method method = getMethod(cla, fieldName);

			if (method == null) {
				continue;
			}
			try {
				Object fieldValue = method.invoke(obj, new Object[0]);

				if (fieldValue == null)
					continue;
				maps.put(fieldName, fieldValue);
			} catch (Exception e) {
				System.out.printf("excute method %s.%s failed.\n",
						new Object[] { fieldName, method.getName() });
			}
		}

		return maps;
	}

	public static List<Map<String, Object>> entityListToMap(Object obj) {
		List mapList = new ArrayList();
		if ((obj instanceof List)) {
			List list = (List) obj;
			for (int i = 0; i < list.size(); i++) {
				mapList.add(entityToMap(list.get(i)));
			}
		} else {
			mapList.add(entityToMap(obj));
		}
		return mapList;
	}

	private static Map<String, Field> getColumns(Class<?> cla) {
		Map maps = new HashMap();

		Field[] fields = cla.getDeclaredFields();

		for (Field field : fields) {
			Column column = (Column) field.getAnnotation(Column.class);
			if (column != null) {
				maps.put(column.name(), field);
			}
		}
		return maps;
	}

	private static Method getMethod(Class<?> cla, String fieldName) {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		try {
			return cla.getDeclaredMethod(methodName, new Class[0]);
		} catch (Exception e) {
			if (!fieldName.substring(0, 2).equals("is")) {
				System.out.printf("Method:%s is not exist in %s\n",
						new Object[] { methodName, cla.getName() });
				return null;
			}
		}
		methodName = fieldName;
		try {
			return cla.getDeclaredMethod(methodName, new Class[0]);
		} catch (Exception e) {
			System.out.printf("Method:%s is not exist in %s\n", new Object[] {
					methodName, cla.getName() });
		}
		return null;
	}

	public static String getRelatedTableName(Class<?> cla) {
		return ((Table) cla.getAnnotation(Table.class)).name();
	}

}