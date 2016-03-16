package com.mzglinicki.zoo;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {


		GuiManager guiManager = GuiManager.getInstance();
		GameManager gameManager = GameManager.getInstance();

		guiManager.printWelcomeGui();
		gameManager.selectMainMenuOption();

	}
}
