package com.mzglinicki.zoo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AnimalsManager {

	private final String DELIMITER = ",";
	private final int MAX_AMOUNT_OF_ANIMALS = 400;
	private final int MIN_AMOUNT_OF_ANIMALS = 1;
	private static AnimalsManager animalsManager = null;
	private static GuiManager guiManager = GuiManager.getInstance();

	private Scanner userInput = new Scanner(System.in);
	private Random generator = new Random();
	private Map<Species, List<Animal>> mapOfSpieces = new HashMap<>();

	private List<String> maleNames = new ArrayList<String>();
	private List<String> femaleNames = new ArrayList<String>();
	private int numOfYear = 0;

	public static AnimalsManager getInstance() {
		if (animalsManager == null) {
			animalsManager = new AnimalsManager();
		}
		return animalsManager;
	}

	public Map<Species, List<Animal>> getMapOfSpieces() {
		return mapOfSpieces;
	}

	public int changeNumOfYear() {
		return numOfYear++;
	}
	public int getNumOfYear(){
		return numOfYear;
	}

	public List<String> readFile(String fileName) {

		List<String> listOfNames = new ArrayList<String>();

		try {
			BufferedReader namesForMale = new BufferedReader(new FileReader(fileName));

			String line = namesForMale.readLine();

			while (line != null) {
				String[] namesLine = line.split(DELIMITER);
				for (String name : namesLine) {
					listOfNames.add(name.trim());
				}
				line = namesForMale.readLine();
			}
			namesForMale.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listOfNames;
	}

	public void loadExternalFiles() {

		femaleNames = readFile(Sex.FEMALE.getNames());
		maleNames = readFile(Sex.MALE.getNames());
	}

	public String setName(String sex) {

		return sex.equals(Sex.FEMALE.getSexToString()) ? femaleNames.get(generator.nextInt(femaleNames.size())) : maleNames.get(generator
				.nextInt(maleNames.size()));
	}

	public Map<Species, List<Animal>> initialization() {

		guiManager.printInfoForInit();

		List<Species> listOfSpecies = getInitSpecies();

		int amountOfAnimal = getInitAmount();

		for (int i = 0; i < amountOfAnimal; i++) {
			Species species = listOfSpecies.get(generator.nextInt(listOfSpecies.size()));

			List<Animal> animals = mapOfSpieces.get(species);
			if (animals == null) {
				animals = new ArrayList<Animal>();
				mapOfSpieces.put(species, animals);
			}
			animals.add(species.getAnimal());
			Collections.sort(animals);
		}
		guiManager.printInfoAnimalsAmount();
		return mapOfSpieces;
	}

	private List<Species> getInitSpecies() {

		List<Species> listOfSpecies = new ArrayList<Species>();

		do {
			guiManager.printSelectionText();
			try {
				String userInputTab[] = userInput.nextLine().split(DELIMITER);

				for (String speciesId : userInputTab) {
					listOfSpecies.add(Species.values()[Integer.parseInt(speciesId)]);
				}
				return listOfSpecies;
			} catch (NumberFormatException e) {
				guiManager.printIncorrectDataFormat();
			} catch (ArrayIndexOutOfBoundsException e) {
				guiManager.printIncorrectType();
			}
		} while (true);
	}

	public int getInitAmount() {
		do {
			guiManager.printInfoInitAmount();
			try {
				int amountOfAnimal = Integer.parseInt(userInput.nextLine());

				if (amountOfAnimal > MAX_AMOUNT_OF_ANIMALS) {
					guiManager.printLackOfCages();
				} else if (amountOfAnimal < MIN_AMOUNT_OF_ANIMALS) {
					guiManager.printTooLowNumberOfAnimal();
				} else {
					return amountOfAnimal;
				}
			} catch (NumberFormatException e) {
				guiManager.printIncorrectDataFormat();
			}
		} while (true);
	}

	public void selectSpecies() {

		String input = null;
		boolean condition = true;
		guiManager.printListOfSpeciesWithAmount();
		guiManager.getNumOfSpeciesOrOtherOption();
		guiManager.printOtherOptions();

		do {
			try {
				input = userInput.nextLine();
				int num = Integer.parseInt(input);

				availableActivities(returnKeyToSpeciesList(num));
				condition = false;
			} catch (NumberFormatException uncorrectFormat) {
				if (input.equals(com.mzglinicki.zoo.List.LIST.getList())) {
					guiManager.printListOfAnimals();
				} else if (input.equals(Speak.SPEAK.getSpeak())) {

				} else {
					getInfoAboutSpecies(input);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				guiManager.printTooLowNumberOfAnimal();
			}
		} while (condition);
	}

	public void availableActivities(Species keyToSpeciesList) {

		GuiManager.getInstance().printActivities();
		int input = Integer.parseInt(userInput.nextLine());

		try {
			for (Activities activity : Activities.values()) {
				if (input == activity.ordinal()) {
					activity.selectActivity(keyToSpeciesList);
				}
			}
		} catch (NumberFormatException e) {
			guiManager.printIncorrectDataFormat();
		}
	}

	public void forage(Animal animal, int userInput) {

		animal.setWeightAftrForage(Food.values()[userInput]);
		animal.decreaseWeightWithoutFood(true);
	}

	public int getUserInputForForage() {
		GuiManager.getInstance().printFood();
		String numOfFood = userInput.nextLine();
		return Integer.parseInt(numOfFood);
	}

	public void walk(Animal animal) {

	}

	public void play(Animal animal) {

		RandomEvents event = new RandomEvents();

		event.pregnancy(animal);
	}

	public void buyAnimal(Species species) {

		List<Animal> animals = mapOfSpieces.get(species);
		if (animals == null) {
			animals = new ArrayList<Animal>();
			mapOfSpieces.put(species, animals);
		}
		animals.add(species.getAnimal());
		Collections.sort(animals);
	}

	public boolean checkDeath(Animal animal) {

		RandomEvents event = new RandomEvents();

		return animal.deathOfOldAge() || animal.starvation() || event.death();
	}

	public Species returnKeyToSpeciesList(int num) throws ArrayIndexOutOfBoundsException {

		if (mapOfSpieces.containsKey(Species.values()[num])) {
			return Species.values()[num];
		}
		throw new NumberFormatException();
	}

	public boolean getInfoAboutSpecies(String input) {

		for (Species species : Species.values()) {
			if (input.toLowerCase().equals(species.getType().toLowerCase())) {
				try {
					guiManager.printInfoAboutSpecies(mapOfSpieces.get(species).get(0));
					guiManager.getNumOfSpeciesOrOtherOption();
					return true;
				} catch (NullPointerException e) {
					guiManager.printUnknownSpecies();
					return true;
				}
			}
		}
		guiManager.printIncorrectDataFormat();
		guiManager.getNumOfSpeciesOrOtherOption();
		return true;
	}

	public void update(Activities activity, Species keyToSpeciesList, int userInput) {

		int babiesCounter = 0;
		int corpseCounter = 0;

		for (List<Animal> listOfAnimal : mapOfSpieces.values()) {

			for (int i = listOfAnimal.size() - 1; i >= 0; i--) {

				Animal animal = listOfAnimal.get(i);

				if (keyToSpeciesList == animal.getSpecies()) {
					makeActivityOnAnimal(activity, animal, userInput);
				} else {
					animal.decreaseWeightWithoutFood(false);
				}

				Animal newBaby = animal.updateAge();
				if (newBaby != null) {
					listOfAnimal.add(newBaby);
					babiesCounter++;
				}

				if (checkDeath(animal)) {
					listOfAnimal.remove(i);
					corpseCounter++;
				}
			}
		}
		guiManager.showSummary(babiesCounter, corpseCounter);
	}

	private void makeActivityOnAnimal(Activities activity, Animal animal, int userInput) {
		switch (activity) {
		case EAT:
			forage(animal, userInput);
			break;
		case WALK:
			walk(animal);
			break;
		case LEISURE:
			play(animal);
			break;
		case BUY_ANIMAL:
			buyAnimal(animal.getSpecies());
		}
	}
}