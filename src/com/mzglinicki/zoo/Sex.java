package com.mzglinicki.zoo;

public enum Sex {

	MALE(Constants.NAMES_FOR_MALE, Constants.SEX_MALE),
	FEMALE(Constants.NAMES_FOR_FEMALE, Constants.SEX_FEMALE);

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
