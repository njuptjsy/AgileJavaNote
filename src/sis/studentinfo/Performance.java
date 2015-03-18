package sis.studentinfo;

import junit.framework.TestCase;

public class Performance extends TestCase{
	private int[] tests;//不要用这种方式private int tests[];定义数组
	public void setNumOfTests(int numberOfTests) {
		tests = new int[numberOfTests];//这里才开始为数组分配内存，制定数组的类型和数组中元素的个数
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
		return total / tests.length;//这里之所以是用length而不是length()是因为数组不是对象，没有不能接受消息，length是java中的特殊方法
	}

	public void setScores(int... tests) {//这里的形参采用可变参数定义，可以传入人一个整型数
		//tests = new int[] {score1,score2,score3,score4};//等价与tests = new int[] {score1,score2,score3,score4,}java会自动忽略最后一个逗号
		this.tests = tests;
	}
	
	
}
