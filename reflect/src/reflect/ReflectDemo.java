package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectDemo {
	
	//获取class
	public static void demo01() {
		// 1.
		try {
			Class<?> perClass1 = Class.forName("reflect.Person");
			System.out.println(perClass1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 2.
		Class<?> perClass2 = Person.class;
		System.out.println(perClass2);
		
		// 3.
		Person person = new Person();
		Class<?> perClass3 = person.getClass();
		System.out.println(perClass3);
	}
	
	//获取方法
	public static void demo02() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//public
		Method[] methods = perClass.getMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
		// 获取所有方法(只有本类中的)
		System.out.println("------------------------");
		Method[] declaredMethods = perClass.getDeclaredMethods();
		for (Method method : declaredMethods) {
			System.out.println(method);
		}
	}
	
	//获取interface接口
	public static void demo03() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<?>[] interfaces = perClass.getInterfaces();
		for (Class<?> class1 : interfaces) {
			System.out.println(class1);
		}
	}
	
	//获取父类
	public static void demo04() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<?> superclass = perClass.getSuperclass();
		System.out.println(superclass);
	}
	
	//构造方法
	public static void demo05() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//public
		Constructor<?>[] constructors = perClass.getConstructors();
		for (Constructor<?> constructor : constructors) {
			System.out.println(constructor);
		}
		//获取说有构造方法
		System.out.println("====================");
		Constructor<?>[] declaredConstructors = perClass.getDeclaredConstructors();
		for (Constructor<?> constructor : declaredConstructors) {
			System.out.println(constructor);
		}
	}
	
	//属性
	public static void demo06() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//public
		Field[] fields = perClass.getFields();
		for (Field field : fields) {
			System.out.println(field);
		}
		
		//获取所有的属性
		System.out.println("====================");
		Field[] declaredFields = perClass.getDeclaredFields();
		for (Field field : declaredFields) {
			System.out.println(field);
		}
	}
	
	//获取当前类的对象
	public static void demo07()  {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object newInstance = null;
		try {
			newInstance = perClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Person person = (Person)newInstance;
		person.interfaceMethod();
	}

	public static void main(String[] args) {
//		demo01();
//		demo02();
//		demo03();
//		demo04();
//		demo05();
//		demo06();
		demo07();
	}
}
