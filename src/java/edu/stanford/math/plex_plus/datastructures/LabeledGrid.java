package edu.stanford.math.plex_plus.datastructures;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import edu.stanford.math.plex_plus.utility.ExceptionUtility;

/**
 * TODO: Reimplement - I don't like this
 * 
 * @author Andrew Tausz
 *
 * @param <T> row label class
 * @param <U> column label class
 * @param <V> entry class
 */
public class LabeledGrid<T extends Comparable<T>, U extends Comparable<U>, V> {
	protected SortedMap<GenericOrderedPair<T, U>, V> map = new TreeMap<GenericOrderedPair<T, U>, V>();
	
	public V getEntry(T rowLabel, U columnLabel) {
		ExceptionUtility.verifyNonNull(rowLabel);
		ExceptionUtility.verifyNonNull(columnLabel);
		return this.map.get(new GenericOrderedPair<T, U>(rowLabel, columnLabel));
	}
	
	public void setEntry(T rowLabel, U columnLabel, V value) {
		ExceptionUtility.verifyNonNull(rowLabel);
		ExceptionUtility.verifyNonNull(columnLabel);
		this.map.put(new GenericOrderedPair<T, U>(rowLabel, columnLabel), value);
	}
	
	public List<T> getRowLabelList() {
		List<T> list = new ArrayList<T>();
		Set<Map.Entry<GenericOrderedPair<T, U>, V> > entrySet = map.entrySet();
		for (Map.Entry<GenericOrderedPair<T, U>, V> entry : entrySet) {
			list.add(entry.getKey().getFirst());
		}
		
		return list;
	}
	
	public List<U> getColumnLabelSet() {
		List<U> list = new ArrayList<U>();
		Set<Map.Entry<GenericOrderedPair<T, U>, V> > entrySet = map.entrySet();
		for (Map.Entry<GenericOrderedPair<T, U>, V> entry : entrySet) {
			list.add(entry.getKey().getSecond());
		}
		
		return list;
	}
	
	public V[][] getDenseMatrixForm() {
		SortedMap<T, Integer> rowLabelIndexMap = new TreeMap<T, Integer>();
		SortedMap<U, Integer> columnLabelIndexMap = new TreeMap<U, Integer>();
		
		List<T> rowLabelList = this.getRowLabelList();
		List<U> columnLabelList = this.getColumnLabelSet();
		
		for (int i = 0; i < rowLabelList.size(); i++) {
			rowLabelIndexMap.put(rowLabelList.get(i), i);
		}
		
		for (int i = 0; i < columnLabelList.size(); i++) {
			columnLabelIndexMap.put(columnLabelList.get(i), i);
		}
		
		
		Set<Map.Entry<GenericOrderedPair<T, U>, V> > entrySet = map.entrySet();
		int row = 0;
		int column = 0;
		V[][] matrix = null;
		if (!entrySet.isEmpty()) {
			matrix = (V[][]) Array.newInstance(entrySet.iterator().next().getValue().getClass(), new int[]{rowLabelList.size(), columnLabelList.size()});
		}
		for (Map.Entry<GenericOrderedPair<T, U>, V> entry : entrySet) {
			row = rowLabelIndexMap.get(entry.getKey().getFirst());
			column = columnLabelIndexMap.get(entry.getKey().getSecond());
			matrix[row][column] = entry.getValue();
		}
		
		return matrix;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Set<Map.Entry<GenericOrderedPair<T, U>, V> > entrySet = map.entrySet();
		for (Map.Entry<GenericOrderedPair<T, U>, V> entry : entrySet) {
			builder.append(entry.getKey().toString());
			builder.append(": ");
			builder.append(entry.getValue().toString());
			builder.append("\n");
		}
		return builder.toString();
	}
}