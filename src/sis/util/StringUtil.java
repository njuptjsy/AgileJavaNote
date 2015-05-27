package sis.util;
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
}
