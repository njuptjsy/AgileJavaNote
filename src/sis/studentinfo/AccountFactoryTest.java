package sis.studentinfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import sis.security.Permission;
import sis.security.PermissionException;
import junit.framework.TestCase;

public class AccountFactoryTest extends TestCase {
	private List<Method> updateMethods;
	private List<Method> readOnlyMethods;
	
	protected void setUp() throws Exception{
		updateMethods = new ArrayList<Method>();
		addUpdateMethod("setBankAba",String.class);
		addUpdateMethod("setBankAccountNumber", String.class);
		addUpdateMethod("setBankAccountType", Account.BankAccountType.class);
		addUpdateMethod("transferFromBank", BigDecimal.class);
		addUpdateMethod("credit", BigDecimal.class);
		
		readOnlyMethods = new ArrayList<Method>();//û�з���ֵ��
		addReadOnlyMethod("getBalance");
		addReadOnlyMethod("transactionAverage");
	}

	
	public void testUpdateAccess() throws Exception{
		Accountable account = AccountFactory.create(Permission.UPDATE);
		for(Method method: readOnlyMethods){
			verifyNoException(method, account);
		}
		for (Method method: updateMethods) {
			verifyNoException(method, account);
		}
	}
	
	public void testReadOnlyAccess() throws Exception{
		Accountable account = AccountFactory.create(Permission.READ_ONLY);
		for(Method method: updateMethods){
			verifyException(PermissionException.class, method, account);
		}
		for (Method method: readOnlyMethods) {
			verifyNoException(method, account);
		}
	}
	
	private void verifyException(Class exceptionType ,Method method,Object object)throws Exception{//�������һ��������ȷ���׳���SecurityException
		try {
			method.invoke(object, nullParmsFor(method));//Invokes the underlying method represented by this Method object, on the specified object in first parameter,
			//and specified formal parameter in second parameter.
			fail("expected expection");
		} catch (InvocationTargetException e) {
			assertEquals("expected exception", exceptionType, e.getCause().getClass());
		}		
	}
	
	private void verifyNoException(Method method,Object object)throws Exception{
		try {
			method.invoke(object, nullParmsFor(method));
		} catch (InvocationTargetException e) {
			assertFalse("unexpected permission exception", PermissionException.class == e.getCause().getClass());
		}
	}
	
	private Object[] nullParmsFor(Method method) {
		return new Object[method.getParameterTypes().length];//Returns an array of Class objects that represent the formal parameter types,than get the length of the array
	}

	private void addUpdateMethod(String name, Class parmClass) throws Exception{
		updateMethods.add(Accountable.class.getDeclaredMethod(name, parmClass));//��һ��������ʾͨ������õ��ķ��������֣��ڶ���������ʾ����������βε�����
	}
	
	private void addReadOnlyMethod(String name) throws Exception{
		Class[] noParms = new Class[] {};
		readOnlyMethods.add(Accountable.class.getDeclaredMethod(name, noParms));
	}
	
}
