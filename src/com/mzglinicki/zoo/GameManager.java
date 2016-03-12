package com.mzglinicki.zoo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameManager {

	private static GameManager gameManage = null;
	private GuiManager guiManager = GuiManager.getInstance();
	private AnimalsManager animalCreator = AnimalsManager.getInstance();
	
	private int numOfYear = 0;
	private int animalsSatisfaction = 0;

	private GameManager() {
	}

	public static GameManager getInstance() {
		if (gameManage == null) {
			gameManage = new GameManager();
		}
		return gameManage;
	}

	public void selectMainManuOption() {

		Scanner userInput = new Scanner(System.in);
		String input;
		int num = 0;

		guiManager.printMainMenu();

		do {
			try {
				input = userInput.nextLine();
				num = Integer.parseInt(input);

				for (MainManuOptions optiont : MainManuOptions.values()) {
					if (num == optiont.ordinal()) {
						optiont.doOption();
					}
				}
			} catch (NumberFormatException uncorrectFormat) {
				guiManager.printIncorrectDataFormat();
			}
		} while (true);
	}

	public void startNewGame() {
		
		ExternalFilesManager.getInstance().loadNamas();
		guiManager.printYearCounter();
		animalCreator.initialization();
		animalCreator.selectSpecies();

		playRegularGame();
	}

	public void loadLastGame() {
		ExternalFilesManager fileManager = ExternalFilesManager.getInstance();

		Map<Species, List<Animal>> loadHashMap = fileManager.readData(GameInputOptions.IO_DATA_SER.getValue());
		fileManager.readData(GameInputOptions.IO_DATA_SER.getValue());

		animalCreator.setNumOfYear(numOfYear);
		animalCreator.setAnimalSatisfaction(animalsSatisfaction);
		animalCreator.setMapOfSpieces(loadHashMap);

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
		boolean condition = true;

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
		selectMainManuOption();
	}

}
