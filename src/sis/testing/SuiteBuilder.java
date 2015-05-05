package sis.testing;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import junit.runner.ClassPathTestCollector;
import junit.runner.TestCollector;
import junit.framework.*;

public class SuiteBuilder {

	public List<String> gatherTestClassNames(){
		TestCollector collector = new ClassPathTestCollector(){
			@Override
			public boolean isTestClass(String classFileName){
				if (!super.isTestClass(classFileName)) {
					return false;
				}
				String className = classNameFromFile(classFileName);
				Class klass = createClass(className);
				return TestCase.class.isAssignableFrom(klass) && isConcrete(klass);//Determines if the class or interface represented by 
				//this Class object is either the same as, or is a superclass or superinterface of, 
				//the class or interface represented by the specified Class parameter
			}
		};
		return Collections.list(collector.collectTests());
	}

	protected Class createClass(String name) {
		try{
			return Class.forName(name);//Returns the Class object associated with the class or interface with the given string name
		}
		catch(ClassNotFoundException e){
			return null;
		}
	}

	public TestSuite suite() {
		TestSuite suite= new TestSuite();
		for (String test:gatherTestClassNames()) {
			if (createClass(test)!=null) {
				suite.addTestSuite(createClass(test));
			}
		}
		return suite;
	}

	private boolean isConcrete(Class klass) {
		if (klass.isInterface()) {//是否是一个接口
			return false;
		}
		int modifiers = klass.getModifiers();//Returns the Java language modifiers for this class or interface, encoded in an integer.
		return !Modifier.isAbstract(modifiers);
	}
}
