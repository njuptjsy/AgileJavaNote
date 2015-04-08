package sis.studentinfo;

import java.math.BigDecimal;

import junit.framework.TestCase;

public class AccoutTest extends TestCase{
	public void testTransactions(){
		Account account = new Account();
		account.credit(new BigDecimal("0.10"));
		account.credit(new BigDecimal("11.00"));
		assertEquals(new BigDecimal("11.10"), account.getBalance());
		
		assertEquals(new BigDecimal("11.1"), account.getBalance());
	}
	
	public void testTransactionAverage(){
		Account account = new Account();
		account.credit(new BigDecimal("0.10"));
		account.credit(new BigDecimal("11.00"));
		account.credit(new BigDecimal("2.99"));
		assertEquals(new BigDecimal("4.70"), account.transactionAverage());
	}
}
