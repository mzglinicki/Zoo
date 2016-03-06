package com.mzglinicki.zoo;

public enum Sex {

	MALE(Constans.NAMES_FOR_MALE, Constans.SEX_MALE), 
	FEMALE(Constans.NAMES_FOR_FEMALE, Constans.SEX_FEMALE);

	private String names;
	private String sex;

	private Sex(final String fileName, final String sex) {
		this.names = fileName;
		this.sex = sex;
	}

	public String getNames() {
		return names;
	}

	public String getSexToString() {
		return sex;
	}
}
