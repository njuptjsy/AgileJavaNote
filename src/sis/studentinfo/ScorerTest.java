package sis.studentinfo;

import junit.framework.TestCase;

public class ScorerTest extends TestCase{
	public void testCaptureScore(){
		Scorer scorer = new Scorer();
		assertEquals(75, scorer.score("75"));
	}
	
	public void testBadScoreEntered(){
		Scorer scorer = new Scorer();
		try {
			scorer.score("abc");
			fail("exception NumberFormatException on bad input");
		} catch (NumberFormatException success) {

		}
	}
	
	public void testIsVaild(){
		Scorer scorer = new Scorer();
		assertTrue(scorer.isValid("75"));
		assertFalse(scorer.isValid("bd"));
	}
}
