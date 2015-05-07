package sis.security;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class SecureProxy implements InvocationHandler{
	private List<String> secureMethods;
	private Object target;
	
	public SecureProxy(Object target, String... secureMethods) {
		this.target = target;
		this.secureMethods = Arrays.asList(secureMethods);//把一个可变参数字符串对象转换成字符串队列
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		try {
			if (isSecure(method)) {
				throw new PermissionException();
			}
			return method.invoke(target, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private boolean isSecure(Method method) {
		return secureMethods.contains(method.getName());
	}
}
