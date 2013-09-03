package in.async.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Demo {
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		processMap();
	}

	public static void processReflection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		Class<?> classObj = Class.forName(A.class.getName());
		Object instance = classObj.newInstance();
		Method method = classObj.getMethod("getA");
		method.invoke(instance);
		System.out.println("MORE::");
		method = classObj.getDeclaredMethod("getA", String.class, int.class);
		method.invoke(instance, "string", 1);
	}

	public static void processMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("raj", "raja");
		map.put("java", "adv ajva");

		System.out.println("Method::0");
		Iterator<String> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			String value = map.get(key);
			System.out.println("M0" + key + " :: " + value);
		}

		System.out.println("METHOD1::");
		for (String key : map.keySet()) {
			System.out.println(key + "::" + map.get(key));
		}

		System.out.println("Method::2");
		for (Map.Entry<String, String> obj : map.entrySet()) {
			System.out.println(obj.getKey() + "::" + obj.getValue());
		}
	}

	public static List<? extends A> processGenerics() {
		List<B> list = new ArrayList<B>();
		list.add(new B());
		return list;
	}
}

class A {
	public String getA() {
		System.out.println("With love from AA");
		return "With love from A";
	}

	void getA(String str, int i) {
		System.out.println("OVERLOAD::" + str + "::" + i);
	}
}

class B extends A {
	public String getB() {
		return "With love from C";
	}
}

class C extends A {
	public String getC() {
		return "With love from C";
	}
}