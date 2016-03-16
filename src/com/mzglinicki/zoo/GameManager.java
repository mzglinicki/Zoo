package com.mzglinicki.zoo;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameManager {

	private static GameManager gameManage = null;
	private GuiManager guiManager = GuiManager.getInstance();
	private AnimalsManager animalCreator = AnimalsManager.getInstance();
	
	private GameManager() {
	}

	public static GameManager getInstance() {
		if (gameManage == null) {
			gameManage = new GameManager();
		}
		return gameManage;
	}

	public void selectMainMenuOption() {

		Scanner userInput = new Scanner(System.in);
		String input;
		int num;

		guiManager.printMainMenu();

		do {
			try {
				input = userInput.nextLine();
				num = Integer.parseInt(input);

				for (MainMenuOptions options : MainMenuOptions.values()) {
					if (num == options.ordinal()) {
						options.doOption();
					}
				}
			} catch (NumberFormatException uncorrectFormat) {
				guiManager.printIncorrectDataFormat();
			}
		} while (true);
	}

	public void startNewGame() {
		
		ExternalFilesManager.getInstance().loadNames();
		guiManager.printYearCounter();
		animalCreator.initialization();
		animalCreator.selectSpecies();

		playRegularGame();
	}

	public void loadLastGame() {
		ExternalFilesManager fileManager = ExternalFilesManager.getInstance();

		Map<Species, List<Animal>> loadHashMap = fileManager.readData(GameInputOptions.IO_DATA_SER.getValue());

		animalCreator.setMapOfSpecies(loadHashMap);

		guiManager.printHeadlineAfterLoad();
		animalCreator.selectSpecies();
		
		playRegularGame();
	}

	public void showRulesOfGame() {
		guiManager.printRules();
	}

	public void closeGame() {
		System.exit(0);
	}

	public void playRegularGame() {
		boolean condition;

		do {
			animalCreator.selectSpecies();

			condition = !animalCreator.getMapOfSpieces().isEmpty() && animalCreator.getNumOfYear() < animalCreator.getLengthOfGama()
					&& animalCreator.getAnimalSatisfaction() > animalCreator.getMinimalSatisfaction();

		} while (condition);

		if (animalCreator.getMapOfSpieces().isEmpty()) {
			guiManager.printLoseInfo();
		} else {
			guiManager.printAnimationAfterWin();
		}
		selectMainMenuOption();
	}


}
