package org.sjon.only.parser;

import java.util.Map;

public class Column implements Map.Entry<String, Object> {
	
	private String key;
	private Object value;
	
	public Column(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public Object setValue(Object value) {
		this.value = value;
		return null;
	}
}
