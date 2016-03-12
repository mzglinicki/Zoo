package com.mzglinicki.zoo;

public enum MainMenuOptions {

    NEW_GAME( Constants.NEW_GAME ) {
        @Override
        public void doOption() {
            gameManager.startNewGame();
        }
    },
    LOAD_LAST_GAME( Constants.LOAD_LAST_GAME ) {
        @Override
        public void doOption() {
            gameManager.loadLastGame();
        }
    },
    RULES( Constants.RULES ) {
        @Override
        public void doOption() {
            gameManager.showRulesOfGame();
        }
    },
    CREDITS( Constants.CREDITS ) {
        @Override
        public void doOption() {
            guiManager.showCredits();
        }
    },
    CLOSE( Constants.CLOSE ) {
        @Override
        public void doOption() {
            gameManager.closeGame();
        }
    };

    private static GameManager gameManager = GameManager.getInstance();
    private static GuiManager guiManager = GuiManager.getInstance();
    private String mainMenuOption;

    private MainMenuOptions(String mainMenuOption) {
        this.mainMenuOption = mainMenuOption;
    }

    public String getMainMenuOption() {
        return mainMenuOption;
    }

    public abstract void doOption();

}
