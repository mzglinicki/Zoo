package com.mzglinicki.zoo;

public enum Species {
	TIGER(Constans.TIGRES) {
		@Override
		public Animal getAnimal() {
			return new Tiger();
		}
	},
	SNAKE(Constans.SNAKES) {
		@Override
		public Animal getAnimal() {
			return new Snake();
		}
	},
	GIRAFFE(Constans.GIRAFFE) {
		@Override
		public Animal getAnimal() {
			return new Giraffe();
		}
	};

	private String type;

	private Species(final String type) {
		this.type = type;
	}

	public abstract Animal getAnimal();

	public String getType() {
		return type;
	}
}
