package com.mzglinicki.zoo;

import java.util.Scanner;

public class GameManager {
	
	private static GameManager gameManage = null;
	private GuiManager guiManager = GuiManager.getInstance();
	private AnimalsManager animalCreator = AnimalsManager.getInstance();
	
	private GameManager(){
	}
	
	public static GameManager getInstance(){
		if(gameManage == null){
			gameManage = new GameManager();
		}
		return gameManage;
	}

	public void selectMainManuOption() {
		
		Scanner userInput = new Scanner(System.in);
		String input;
		
		guiManager.printMainMenu();
		
		do {
			try {
				input = userInput.nextLine();
				int num = Integer.parseInt(input);
				
				for(MainManuOptions optiont : MainManuOptions.values()){
					if(num == optiont.ordinal()){
						optiont.doOption();
					}
				}

			} catch (NumberFormatException uncorrectFormat) {


			} catch (ArrayIndexOutOfBoundsException e) {
			}
		} while (true);
	}
	
	public void startNewGame(){
		
		boolean condition = true;

		animalCreator.loadExternalFiles();
		guiManager.printYearCounter();
		animalCreator.initialization();
		animalCreator.selectSpecies();

		do {
			animalCreator.selectSpecies();
			
			condition = !animalCreator.getMapOfSpieces().isEmpty() && animalCreator.getNumOfYear() < animalCreator.getLengthOfGama()
					&& animalCreator.getAnimalSatisfaction()>animalCreator.getMinimalSatisfaction();
			
		} while (condition);

		if (animalCreator.getMapOfSpieces().isEmpty()) {
			guiManager.printLoseInfo();
		} else {
			guiManager.printAnimationAfterWin();
		}
		selectMainManuOption();
	}

	public void loadLastGame() {
		System.out.println("jeszcze nei ma");
	}

	public void showRulesOfGame() {
		guiManager.printRules();
	}

	public void closeGame() {
		System.exit(0);		
	}

}
