package sis.util;

import java.util.List;

public class ListUtil {
	public static<T> void pad(List<T> list, T object,int count){
		for (int i = 0; i < count; i++) {
			list.add(object);
		}
	}
}
