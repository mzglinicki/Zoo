package com.mzglinicki.zoo;

public enum Sound {

	SPEAK(Constants.GIVE_SOUND);

	private final String speak;

	private Sound(final String speak) {
		this.speak = speak;
	}

	public String getSpeak() {
		return speak;
	}

}
