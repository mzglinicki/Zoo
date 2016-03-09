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

		animalCreator.loadExternalFiles();
		guiManager.printYearCounter();
		animalCreator.initialization();
		animalCreator.selectSpecies();

		playRegularGame();
	}

	public void loadLastGame() {

		Map<Species, List<Animal>> loadHashMap = readData(GameInputOptions.WRITE_DATA_SER.getValue());
		readData(GameInputOptions.WRITE_DATA_SER.getValue());

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

	public void writeDataToFile(Map<Species, List<Animal>> mapOfSpieces, String fileToWrite, int year, int satisfaction) {

		ArrayList<Integer> data = new ArrayList<Integer>();

		data.add(year);
		data.add(satisfaction);

		try {

			FileOutputStream fileStream = new FileOutputStream(fileToWrite);

			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			objectStream.writeObject(data);

			objectStream.writeObject(mapOfSpieces);

			objectStream.close();

			fileStream.close();

			guiManager.printSavedInfo();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Species, List<Animal>> readData(String fileName) {

		ArrayList<Integer> data = new ArrayList<Integer>();

		Map<Species, List<Animal>> loadHashMap = null;

		try {

			FileInputStream fileInput = new FileInputStream(fileName);

			ObjectInputStream objectStream = new ObjectInputStream(fileInput);

			try {

				data = (ArrayList<Integer>) objectStream.readObject();

				loadHashMap = (HashMap<Species, List<Animal>>) objectStream.readObject();

				objectStream.close();
				fileInput.close();

				numOfYear = data.get(0);
				animalsSatisfaction = data.get(1);

				return loadHashMap;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
