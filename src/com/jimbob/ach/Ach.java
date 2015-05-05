package com.jimbob.ach;

public interface Ach{
	public AchResponse issueDebit(AchCredentials credentials, AchTransactionData data);
	
	public AchResponse markTransactionAsNSF(AchCredentials credentials, AchTransactionData data, String traceCode);
	
	public AchResponse refundTranscation(AchCredentials  credentials,AchTransactionData data, String traceCode);
	
	public AchResponse issueCredit(AchCredentials credentials, AchTransactionData data);
	
	public AchResponse voidSameDayTransaction(AchCredentials credentials, AchTransactionData data, String traceCode);
	
	public AchResponse queryTransactionStatus(AchCredentials credentials, AchTransactionData data, String traceCode);
}
