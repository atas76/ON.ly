package org.sjon.only.parser;

import java.util.ArrayList;
import java.util.List;

public class ColumnList extends ColumnGroup {
	
	private List<Object> columnValues = new ArrayList<Object>();
	
	public ColumnList(List<Object> columnValues) {
		this.columnValues = columnValues;
	}
	
	public Object getValue(int index) {
		return this.columnValues.get(index);
	}
	
	public List<Object> getValues() {
		return this.columnValues;
	}
	
}
