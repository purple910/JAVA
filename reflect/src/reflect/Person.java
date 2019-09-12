package reflect;

public class Person implements MyInterface , MyInterface2{

	private int id;
	private String name;
	private int age;
	
	public String pwdString;
	
	private Person(String pwdString) {
		this.pwdString=pwdString;
	}
	
	public Person() {
		
	}
	public Person(int id) {
		this.id = id;
	}
	public Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public String getPwdString() {
		return pwdString;
	}
	public void setPwdString(String pwdString) {
		this.pwdString = pwdString;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
		
	public static void staticMethod() {
		System.out.println("static  method ....");
	}
	
	private void privateMethod() {
		System.out.println("private method....");
	}
	
	private void privateMethod2(String aString) {
		System.out.println(aString);
	}
	
	@Override
	public void interfaceMethod() {
		// TODO Auto-generated method stub
		System.out.println("interface  method....");
	}
	@Override
	public void interfaceMethod2() {
		// TODO Auto-generated method stub
		System.out.println("interface method2......");
	}
	
	

}
