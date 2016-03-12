package com.mzglinicki.zoo;

public enum Activities {

	EAT(Constants.FEED) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {

			animalsManager.update(this, keyToSpeciesList, AnimalsManager.getInstance().getUserInputForForage());
		}
	},
	WALK(Constants.GO_FOR_WALK) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {
			animalsManager.playForSpecies(keyToSpeciesList);
			animalsManager.update(this, keyToSpeciesList, -1);
		}
	},
	LEISURE(Constants.PLAY) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {

			animalsManager.update(this, keyToSpeciesList, -1);
		}
	},
	BUY_ANIMAL(Constants.BUY_ANIMAL) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {
			int buyAnimal = 100;			//:)
			animalsManager.buyAnimal(keyToSpeciesList);
			animalsManager.update(this, keyToSpeciesList, buyAnimal);
		}
	};

	private static AnimalsManager animalsManager = AnimalsManager.getInstance();
	private String activity;

	private Activities(final String activity) {
		this.activity = activity;
	}

	public String getActivity() {
		return activity;
	}

	public abstract void selectActivity(Species keyToSpeciesList);
}
