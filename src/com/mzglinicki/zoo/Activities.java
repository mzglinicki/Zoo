package com.mzglinicki.zoo;

public enum Activities {
	EAT(Constans.FEED) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {

			AnimalsManager.getInstance().update(this, keyToSpeciesList, AnimalsManager.getInstance().getUserInputForForage());
		}
	},
	WALK(Constans.GO_FOR_WALK) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {
			AnimalsManager.getInstance().update(this, keyToSpeciesList, -1);
		}
	},
	LEISURE(Constans.PLAY) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {
			AnimalsManager.getInstance().update(this, keyToSpeciesList, -1);
		}
	},
	BUY_ANIMAL(Constans.BUY_ANIMAL) {
		@Override
		public void selectActivity(Species keyToSpeciesList) {
			AnimalsManager.getInstance().update(this, keyToSpeciesList, -1);
		}
	};

	private String activity;

	private Activities(final String activity) {
		this.activity = activity;
	}

	public String getActivity() {
		return activity;
	}
	public abstract void selectActivity(Species keyToSpeciesList);
}
