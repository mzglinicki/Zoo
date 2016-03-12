package com.mzglinicki.zoo;

public enum GameInputOptions {
	LIST(Constants.LIST),
	MAIN_PANEL(Constants.MAIN_PANEL),
	SAVE(Constants.SAVE),
	IO_DATA_SER(Constants.DATA_SER),
	DATA_XML(Constants.DATA_XML_TXT),
	DATA_JSON(Constants.DATA_JSON_TXT);

	private final String option;

	private GameInputOptions(final String option) {
		this.option = option;
	}

	public String getValue() {
		return option;
	}
}
