package com.mzglinicki.zoo;

public enum List {
	LIST(Constans.LIST);

	private final String list;

	private List(final String list) {
		this.list = list;
	}

	public String getList() {
		return list;
	}
}
