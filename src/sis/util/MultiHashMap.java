package sis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.PUBLIC_MEMBER;

public class MultiHashMap<K,V> {//一个hashMap保存日期对象和对应的list列表
	public Map<K, List<V>> map = new HashMap<K,List<V>>();
	
	
	public int size(){
		return map.size();
	}
	
	public void put(K key, V value){
		List<V> values = map.get(key);
		if (values == null) {
			values = new ArrayList<V>();
			map.put(key, values);
		}
		values.add(value);
	}
	
	public List<V> get(K key){
		return map.get(key);
	}
	
	public interface Filter<T>{
		boolean apply(T item);
	}
	
	public static <K,V> void filter (final MultiHashMap<K, ? super V> target, final MultiHashMap<K, V> source, final Filter<? super V> filter){
		for (K key: source.keys()) {
			final List<V> values = source.get(key);
			for (V value : values) {
				if (filter.apply(value)) {
					target.put(key, value);
				}
			}
		}
	}
	
	private Set<K> keys(){
		return map.keySet();
	}
}
