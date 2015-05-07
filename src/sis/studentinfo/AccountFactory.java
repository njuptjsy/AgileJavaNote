package sis.studentinfo;

import java.lang.reflect.Proxy;

import sis.security.Permission;
import sis.security.SecureProxy;

public class AccountFactory {

	public static Accountable create(Permission permission) {
		switch (permission) {
		case UPDATE:
			return new Account();//真正的account对象
		case READ_ONLY:
			return createSecuredAccount();//动态的代理对象
		}
		return null;
	}

	private static Accountable createSecuredAccount() {
		SecureProxy secureAccount = new SecureProxy(new Account(),"credit","setBankAba","setBankAccountNumber","setBankAccountType","transferFromBank");
		return (Accountable)Proxy.newProxyInstance(//returns an instance of a proxy class for the specified interfaces that dispatches method invocations to the specified invocation handler
				Accountable.class.getClassLoader(),//这个接口的类加载器the class loader to define the proxy class
				new Class[]{Accountable.class},//接口累的数组，java会在后台实现这些接口the list of interfaces for the proxy class to implement
				secureAccount);//the invocation handler to dispatch method invocations to
	}

}
