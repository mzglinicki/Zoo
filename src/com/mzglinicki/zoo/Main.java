package com.mzglinicki.zoo;

public class Main {

	public static void main(String[] args) {

		final int LENGTH_OF_GAME = 20;

		GuiManager guiManager = GuiManager.getInstance();

		guiManager.printWelcomeGui();
		AnimalsManager animalCreator = AnimalsManager.getInstance();

		animalCreator.loadExternalFiles();

		guiManager.printYearCounter();
		animalCreator.initialization();
		animalCreator.selectSpecies();

		do {
			animalCreator.selectSpecies();

		} while (!animalCreator.getMapOfSpieces().isEmpty() && animalCreator.getNumOfYear() < LENGTH_OF_GAME);

		if (animalCreator.getMapOfSpieces().isEmpty()) {
			guiManager.printLoseInfo();
		} else {
			guiManager.printAnimationAfterWin();
		}

	}
}
