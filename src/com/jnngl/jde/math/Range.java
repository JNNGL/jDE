package com.jnngl.jde.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Range<T1> {
	
	private List<Integer> minimal = new ArrayList<Integer>();
	private List<Integer> maximal = new ArrayList<Integer>();
	
	private HashMap<String, T1> values = new HashMap<String, T1>();
	
	public Range<T1> put(int min, int max, T1 value) {
		minimal.add(min);
		maximal.add(max);
		values.put(min+":"+max, value);
		return this;
	}
	
	public Range<T1> clear() {
		minimal = new ArrayList<Integer>();
		maximal = new ArrayList<Integer>();
		
		values = new HashMap<String, T1>();
		return this;
	}
	
	public Range<T1> remove(int index) {
		if(index>(minimal.size()&maximal.size()&values.size()))
			return this;
		
		int min = minimal.get(index);
		int max = maximal.get(index);
		
		minimal.remove(index);
		maximal.remove(index);
		values.remove(min+":"+max);
		return this;
	}
	
	public Range<T1> remove(int min, int max) {
		minimal.remove((Object) min);
		maximal.remove((Object) max);
		values.remove(min+":"+max);
		return this;
	}
	
	public T1 getValue(int n) {
		for(int i = 0; i < minimal.size(); i++) {
			int min = minimal.get(i);
			int max = maximal.get(i);
			if(n>=min&&n<=max&&values.containsKey(min+":"+max))
				return values.get(min+":"+max);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Range [minimal=" + minimal + ", maximal=" + maximal + ", values=" + values + "]";
	}
}
