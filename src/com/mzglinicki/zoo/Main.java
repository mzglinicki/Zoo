package com.mzglinicki.zoo;

public class Main {

	public static void main(String[] args) {

		GuiManager guiManager = GuiManager.getInstance();
		GameManager gameManager = GameManager.getInstance();
		
		guiManager.printWelcomeGui();
		gameManager.selectMainMenuOption();

	}
}
