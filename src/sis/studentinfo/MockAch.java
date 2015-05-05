package sis.studentinfo;

import java.util.Date;

import junit.framework.Assert;

import com.jimbob.ach.*;

public class MockAch implements Ach{

	@Override
	public AchResponse issueDebit(AchCredentials credentials,AchTransactionData data) {
		return null;
	}

	@Override
	public AchResponse markTransactionAsNSF(AchCredentials credentials,
			AchTransactionData data, String traceCode) {//this method is called stub
		return null;
	}

	@Override
	public AchResponse refundTranscation(AchCredentials credentials,
			AchTransactionData data, String traceCode) {
		return null;
	}

	@Override
	public AchResponse issueCredit(AchCredentials credentials,
			AchTransactionData data) {
		return null;
	}

	@Override
	public AchResponse voidSameDayTransaction(AchCredentials credentials,
			AchTransactionData data, String traceCode) {
		return null;
	}

	@Override
	public AchResponse queryTransactionStatus(AchCredentials credentials,
			AchTransactionData data, String traceCode) {
		return null;
	}

}
