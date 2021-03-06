package org.sjon.only.parser;

import java.util.Map;
import java.util.Set;

public class ColumnMap extends ColumnGroup {
	
	private Map<String, Column> columnMap;
	
	public ColumnMap(Map<String, Column> columnMap) {
		this.columnMap = columnMap;
	}
	
	public Column getColumn(String name) {
		return columnMap.get(name);
	}
	
	public Set<String> getColumns() {
		return this.columnMap.keySet();
	}
	
}
