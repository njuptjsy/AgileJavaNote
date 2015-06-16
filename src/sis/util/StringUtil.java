package sis.util;

import java.util.List;

/**����һ���ִ��ڸ����ַ��������˼���*/
public class StringUtil {
	static public int occurrences(String string, String subString){//���ƥ�䵽�Ĵ���
		int occurrences = 0;
		int length = subString.length();
		final boolean ignoreCase = true;
		for (int i = 0; i < string.length() - subString.length() + 1; i++) {
			if (string.regionMatches(ignoreCase, i, subString, 0, length)) {//�Ƚ�string�����subString���ִ��Ƿ�һ������һ������ʾ���Դ�Сд��
				//�ڶ���������ʾ��string�ĵڼ����ַ���ʼ�Ƚϣ�������������ʾҪ�ȽϽ̵�subString,�������Ҫ�Ƚ��ִ��ĳ���
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
	
	public static String concatenateNumbers(List<? extends Number> list, int decimalPlaces){//�����б�����Number���Ǹ�������󣬶����Խ������Ӳ���ӡ
		String decimalFormat = "%." + decimalPlaces + "f";//�涨������С�����decimalPlacesλ
		StringBuffer builder = new StringBuffer();
		for (Number element:list) {
			double value = element.doubleValue();//Returns the value of the specified number as a double
			builder.append(String.format(decimalFormat + "%n", value));
		}
		return builder.toString();
	}
}
