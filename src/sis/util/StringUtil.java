package sis.util;
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
}
