package reflect;

import java.lang.reflect.Field;

public class PropertyUtil {

	public static void setProperty(Object object,String propertyName,Object value) {
		Class<? > class1 = object.getClass();
		try {
			Field field = class1.getDeclaredField(propertyName);
			field.setAccessible(true);
			field.set(object, value);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
