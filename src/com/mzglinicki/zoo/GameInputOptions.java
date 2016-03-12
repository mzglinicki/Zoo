package com.mzglinicki.zoo;

public enum GameInputOptions {
	LIST(Constans.LIST), 
	MAIN_PANEL(Constans.MAIN_PANEL),
	SAVE(Constans.SAVE),
	IO_DATA_SER(Constans.DATA_SER),
	DATA_XML(Constans.DATA_XML_TXT),
	DATA_JSON(Constans.DATA_JSON_TXT);

	private final String option;

	private GameInputOptions(final String option) {
		this.option = option;
	}

	public String getValue() {
		return option;
	}
}
