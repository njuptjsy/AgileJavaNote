package sis.studentinfo;

import junit.framework.TestCase;

public class Performance extends TestCase{
	private int[] tests;//��Ҫ�����ַ�ʽprivate int tests[];��������
	public void setNumOfTests(int numberOfTests) {
		tests = new int[numberOfTests];//����ſ�ʼΪ��������ڴ棬�ƶ���������ͺ�������Ԫ�صĸ���
	}
	
	public void set(int testNumber , int score) {
		tests[testNumber] = score;
	}
	
	public int get(int testNumber) {
		return tests[testNumber];
	}
	
	public double average(){
		double total = 0.0;
		for (int sorce: tests) {
			total += sorce;
		}
		return total / tests.length;//����֮��������length������length()����Ϊ���鲻�Ƕ���û�в��ܽ�����Ϣ��length��java�е����ⷽ��
	}

	public void setScores(int... tests) {//������ββ��ÿɱ�������壬���Դ�����һ��������
		//tests = new int[] {score1,score2,score3,score4};//�ȼ���tests = new int[] {score1,score2,score3,score4,}java���Զ��������һ������
		this.tests = tests;
	}
	
	
}
