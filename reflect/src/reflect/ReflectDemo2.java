package reflect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

/**
* 
* @Project reflect
* @Package reflect.ReflectDemo2
* @ClassName ReflectDemo2
* @Description TODO
* @date 2019年9月12日 上午10:35:03
* @author fate
*/
public class ReflectDemo2 {


	//获取实例，操作实例
	public static void demo01() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Person per = (Person)perClass.newInstance();
			per.setAge(18);
			per.setId(11111);
			per.setName("Tom");
			per.setPwdString("123456");
			System.out.println(per.getAge()+","+per.getName());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//操作属性
	public static void demo02() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Person person = (Person)perClass.newInstance();
			Field field = perClass.getDeclaredField("pwdString");
			field.set(person, "111");
			System.out.println(person.getPwdString());
			
			Field field2 = perClass.getDeclaredField("id");
			field2.setAccessible(true);	// 打开访问权限
			field2.set(person, 1);
			System.out.println(person.getId());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//操作方法
	public static void demo03() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Person person = (Person)perClass.newInstance();
			
			Method method = perClass.getDeclaredMethod("privateMethod", null);
			method.setAccessible(true);
			method.invoke(person, null);
			
			Method method2 = perClass.getDeclaredMethod("privateMethod2", String.class);
			method2.setAccessible(true);
			method2.invoke(person, "Sjkhk");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//操作构造方法
	public static void demo04() {
		Class<?> perClass = null;
		try {
			perClass = Class.forName("reflect.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Constructor<?> constructor = perClass.getConstructor(int.class,String.class,int.class);
			Person per = (Person)constructor.newInstance(18,"Tom",222);
			System.out.println(per.getId());
			
			Constructor<?> constructor2 = perClass.getDeclaredConstructor(String.class);
			constructor2.setAccessible(true);
			Person per1 = (Person)constructor2.newInstance("aaaaaaaaaaaa");
			System.out.println(per1.getPwdString());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//动态加载
	public static void demo05() {
		Class<?> perClass = null;
		
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("class.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String classname = properties.getProperty("classname");
		String methodname = properties.getProperty("methodname");
		
		try {
			perClass = Class.forName(classname);
			Method method = perClass.getMethod(methodname);
			method.invoke(perClass.newInstance());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//可以越过泛型检查
	public static void demo06() {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(111);
		list.add(2222);
		
		Class<? > listClass = list.getClass();
		try {
			Method method = listClass.getMethod("add",Object.class);
			method.invoke(listClass.newInstance(), "aaaaa");// 这里相当于重新创建一个ArraryList
			method.invoke(list, "zs....");//这个相当于在原有的ArraryList中添加
			System.out.println(list);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//万能的set方法
	public static void demo07() {
		Person person = new Person();
		PropertyUtil.setProperty(person, "age", 11);
		System.err.println(person.getAge());
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
