package com.mzglinicki.zoo;

public enum MainManuOptions {

	NEW_GAME(Constans.NEW_GAME) {
		@Override
		public void doOption() {
			gameManager.startNewGame();
		}
	},
	LOAD_LAST_GAME(Constans.LOAD_LAST_GAME) {
		@Override
		public void doOption() {
			gameManager.loadLastGame();
		}
	},
	RULES(Constans.RULES) {
		@Override
		public void doOption() {
			gameManager.showRulesOfGame();
		}
	},
	CLOSE(Constans.CLOSE) {
		@Override
		public void doOption() {
			gameManager.closeGame();
		}
	};

	private static GameManager gameManager = GameManager.getInstance();
	private String mainManuOption;

	private MainManuOptions(String mainManuOption) {
		this.mainManuOption = mainManuOption;
	}

	public String getMainManuOption() {
		return mainManuOption;
	}

	public abstract void doOption();

}
