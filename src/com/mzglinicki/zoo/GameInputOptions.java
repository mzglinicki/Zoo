package com.mzglinicki.zoo;

public enum GameInputOptions {
	LIST(Constans.LIST), 
	MAIN_PANEL(Constans.MAIN_PANEL),
	SAVE(Constans.SAVE);

	private final String option;

	private GameInputOptions(final String option) {
		this.option = option;
	}

	public String getValue() {
		return option;
	}
}
