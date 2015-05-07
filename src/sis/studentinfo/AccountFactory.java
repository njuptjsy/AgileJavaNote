package sis.studentinfo;

import java.lang.reflect.Proxy;

import sis.security.Permission;
import sis.security.SecureProxy;

public class AccountFactory {

	public static Accountable create(Permission permission) {
		switch (permission) {
		case UPDATE:
			return new Account();//������account����
		case READ_ONLY:
			return createSecuredAccount();//��̬�Ĵ������
		}
		return null;
	}

	private static Accountable createSecuredAccount() {
		SecureProxy secureAccount = new SecureProxy(new Account(),"credit","setBankAba","setBankAccountNumber","setBankAccountType","transferFromBank");
		return (Accountable)Proxy.newProxyInstance(//returns an instance of a proxy class for the specified interfaces that dispatches method invocations to the specified invocation handler
				Accountable.class.getClassLoader(),//����ӿڵ��������the class loader to define the proxy class
				new Class[]{Accountable.class},//�ӿ��۵����飬java���ں�̨ʵ����Щ�ӿ�the list of interfaces for the proxy class to implement
				secureAccount);//the invocation handler to dispatch method invocations to
	}

}
