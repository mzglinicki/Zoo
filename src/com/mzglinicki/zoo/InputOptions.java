package com.mzglinicki.zoo;

public enum InputOptions {
	LIST(Constans.LIST),
	RULES(Constans.RULES);
	

	private final String list;

	private InputOptions(final String list) {
		this.list = list;
	}
	
	public String getValue() {
		return list;
	}
}
