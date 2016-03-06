package com.mzglinicki.zoo;

public enum Food {
	FISH(Constans.FISH), MEAN(Constans.MEAT), LEAVES(Constans.LEAVES), MAUSE(Constans.MOUSE), RABBIT(Constans.RABBIT);

	private String type;

	private Food(final String food) {
		this.type = food;
	}

	public String getType() {
		return type;
	}
}
