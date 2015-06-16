package sis.util;

import java.util.List;

/**测试一个字串在给的字符串出现了几次*/
public class StringUtil {
	static public int occurrences(String string, String subString){//获得匹配到的次数
		int occurrences = 0;
		int length = subString.length();
		final boolean ignoreCase = true;
		for (int i = 0; i < string.length() - subString.length() + 1; i++) {
			if (string.regionMatches(ignoreCase, i, subString, 0, length)) {//比较string对象和subString的字串是否一样，第一参数表示忽略大小写，
				//第二个参数表示从string的第几个字符开始比较，第三个参数表示要比较教的subString,第五个是要比较字串的长度
				occurrences ++;
			}
		}
		return occurrences;
	}
	
	public static String concatenate(List<?> list){
		StringBuffer builder = new StringBuffer();
		for (Object element: list) {
			builder.append(String.format("%s%n", element));
		}
		return builder.toString();
	}
	
	public static String concatenateNumbers(List<? extends Number> list, int decimalPlaces){//不论列表中是Number的那个子类对象，都可以将其连接并打印
		String decimalFormat = "%." + decimalPlaces + "f";//规定精读是小数点后decimalPlaces位
		StringBuffer builder = new StringBuffer();
		for (Number element:list) {
			double value = element.doubleValue();//Returns the value of the specified number as a double
			builder.append(String.format(decimalFormat + "%n", value));
		}
		return builder.toString();
	}
}
