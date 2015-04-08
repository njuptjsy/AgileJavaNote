package sis.util;

import java.util.Random;

import junit.framework.TestCase;

public class PasswordGeneratorTest extends TestCase{
	public void testGeneratePassword(){
		PasswordGenerator generator = new PasswordGenerator();
		generator.setRandom(new MockRandom('A'));
		assertEquals("ABCDEFGH", generator.generatePassword());
		generator.setRandom(new MockRandom('C'));
		assertEquals("CDEFGHIJ", generator.generatePassword());
	}
	
	class MockRandom extends Random{
		private int i;
		MockRandom(char startCharValue) {
			i = startCharValue - PasswordGenerator.LOW_END_PASSWORD_CHAR; 
		}
		
		@Override
		protected int next(int bits){
			return i++;
		}
	}
}
