package com.mzglinicki.zoo;

public class Main {

	public static void main(String[] args) {

		GuiManager guiManager = GuiManager.getInstance();

		guiManager.printWelcomeGui();
		AnimalsManager animalCreator = AnimalsManager.getInstance();

		animalCreator.loadExternalFiles();

		guiManager.printYearCounter();
		animalCreator.initialization();
		animalCreator.selectSpecies();

		do {
			animalCreator.selectSpecies();

		} while (!animalCreator.getMapOfSpieces().isEmpty() && animalCreator.getNumOfYear() < animalCreator.getLengthOfGama());

		if (animalCreator.getMapOfSpieces().isEmpty()) {
			guiManager.printLoseInfo();
		} else {
			guiManager.printAnimationAfterWin();
		}

	}
}
