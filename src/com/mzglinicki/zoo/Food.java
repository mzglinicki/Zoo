package com.mzglinicki.zoo;

public enum Food {
	FISH(Constants.FISH), MEAN(Constants.MEAT), LEAVES(Constants.LEAVES), MAUSE(Constants.MOUSE), RABBIT(Constants.RABBIT), ORANGE(Constants.ORANGE);

	private String type;

	private Food(final String food) {
		this.type = food;
	}

	public String getType() {
		return type;
	}
}
