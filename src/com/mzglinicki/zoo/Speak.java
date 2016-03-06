package com.mzglinicki.zoo;

public enum Speak {

	SPEAK(Constans.SPEAK);

	private final String speak;

	private Speak(final String speak) {
		this.speak = speak;
	}

	public String getSpeak() {
		return speak;
	}

}
