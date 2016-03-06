package com.mzglinicki.zoo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GuiManager {

	private static GuiManager instance = null;
	private AnimalsManager animalManager = AnimalsManager.getInstance();
	private final int AMOUNT_OF_DASHES = 34;
	private final int LENGTH_OF_ANIMATION = 10;
	private final int LENGTH_OF_ENDING_ANIMATION = 40;
	private final int VELOCITY_OF_ANIMATION = 25;
	private final String NEW_LINE = "\n";
	private final String COLON = ": ";
	private final char SPACE = ' ';
	private final char VERTICAL_LINE = '|';
	private final char DASH = '-';
	private final char PLUS = '+';

	private GuiManager() {
	}

	public static GuiManager getInstance() {
		if (instance == null) {
			instance = new GuiManager();
		}
		return instance;
	}

	public void printWelcomeGui() {

		Date date = new Date();
		String dataFormat = "%tA %<te %<tB";
		String timeFormat = "%tT";
		String displayData = "\n%20s %s";
		String displayWelcome = "\n%-10s %-23s %s";

		String day = String.format(dataFormat, date);
		String time = String.format(timeFormat, date);

		System.out.printf(displayData, day, time);
		createDash();
		System.out.printf(displayWelcome, VERTICAL_LINE, Constans.WELCOME_TEXT, VERTICAL_LINE);
		createDash();
	}

	public void printYearCounter() {
		System.out.println(NEW_LINE + Constans.NUMBER_OF_YEAR + animalManager.changeNumOfYear());
	}

	public void createDash() {

		System.out.print(NEW_LINE + VERTICAL_LINE);
		for (int i = 0; i < AMOUNT_OF_DASHES; i++) {
			System.out.print(DASH);
		}
		System.out.print(VERTICAL_LINE);
	}

	public void printAnimationAfterWin() {
		
		String charsPart1 = "|/-\\";
		String charsPart2 = "-\\|";
		
		int id = 0;
		for (int i = 0; i < LENGTH_OF_ENDING_ANIMATION; i++) {
			
			System.out.print(charsPart1.charAt(id));
			try {
				Thread.sleep(VELOCITY_OF_ANIMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			id = ++id % charsPart1.length();
		}
		System.out.println(charsPart2 + NEW_LINE + Constans.CONGRATULATION);
	}

	public void printInfoAnimalsAmount() {

		StringBuffer text = new StringBuffer();
		int counter = 0;

		for (List<Animal> list : animalManager.getMapOfSpieces().values()) {
			counter += list.size();
		}

		if (counter > 1) {

			System.out.println(text.append(Constans.AMOUNT_OF_ANIMALS_TEXT).append(counter).append(Constans.AMOUNT_OF_ANIMALS_PART2));

		} else {
			System.out.println(NEW_LINE + Constans.ONE_ANIMAL_TEXT);
		}
	}

	public void printAmountOdAnimals() {
		System.out.println(NEW_LINE + Constans.AVAILABLE_SPECIES);
	}

	public void printListOfAvailableSpecies() {

		StringBuffer text = new StringBuffer();

		for (Species animal : Species.values()) {
			System.out.println(text.append(animal.ordinal()).append(SPACE).append(animal.getType()));
			text.delete(0, 10);
		}
	}

	public void printListOfSpeciesWithAmount() {

		Map<Species, List<Animal>> map = animalManager.getMapOfSpieces();
		StringBuffer text = new StringBuffer();

		for (Species species : Species.values()) {
			System.out.println(text.append(species.ordinal()).append(SPACE).append(species.getType()).append(COLON)
					.append((map.get(species) != null ? map.get(species).size() : 0)));
			text.delete(0, 100);
		}
	}

	public void printOtherOptions() {

		System.out.println(NEW_LINE + Constans.OTHER_OPTION);
		System.out.println(Constans.SHOW_LIST_OF_ANIMALS);
		System.out.println(Constans.HOW_TO_PLAY);
	}

	public void printListOfAnimals() {

		String animalListFormater = " %-4d%-13s%-6d %-8d %s\n";
		String headlineFormater = "%-4s %-12s %-6s %-8s %s\n";

		for (List<Animal> list : animalManager.getMapOfSpieces().values()) {

			boolean condition = true;
			for (Animal animal : list) {
				if (condition) {
					System.out.println(animal.getSpecies().getType() + COLON);
					System.out.printf(headlineFormater, Constans.NUMBER, Constans.NAME, Constans.AGE, Constans.WEIGHT, Constans.SEX);
					condition = false;
				}
				System.out.printf(animalListFormater, list.indexOf(animal) + 1, animal.getName(), animal.getAge(), animal.getWeight(),
						animal.getSex());
			}
			System.out.println();
		}
		printListOfSpeciesWithAmount();
		getNumOfSpeciesOrOtherOption();
		printOtherOptions();
	}

	public void getNumOfSpeciesOrOtherOption() {
		System.out.println(Constans.GIVE_NUM_OF_SPEC);
	}

	public void printFood() {
		StringBuffer text = new StringBuffer();

		System.out.println(NEW_LINE + Constans.TYPE_OF_FOOD);

		for (Food food : Food.values()) {
			System.out.println(text.append(food.ordinal()).append(SPACE).append(food.getType()));
			text.delete(0, 15);
		}
	}

	public void printActivities() {

		StringBuffer text = new StringBuffer();

		System.out.println(NEW_LINE + Constans.SELECT_ACTIVITY + NEW_LINE);

		for (Activities activities : Activities.values()) {
			System.out.println(text.append(activities.ordinal()).append(SPACE).append(activities.getActivity()));
			text.delete(0, 60);
		}
	}

	public void animationAfterAction() {

		StringBuffer text = new StringBuffer();
		for (int i = 0; i < LENGTH_OF_ANIMATION; i++) {
			System.out.print(text.append(PLUS));
			try {
				Thread.sleep(VELOCITY_OF_ANIMATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void printSelectionText() {
		System.out.println(Constans.INIT_SELECT_SPECIES);
	}

	public void printInfoInitAmount() {
		System.out.println(Constans.INITIAL_AMOUNT_OF_ANIMALS_TEXT);
	}

	public void printLoseInfo() {

		System.out.print(Constans.LOSE_INFO);
	}

	public void printInfoAboutPregrency(Animal animal) {

		System.out.println(animal.getName() + Constans.IN_PRAGNANCY);
	}

	public void printIncorrectDataFormat() {
		System.err.println(Constans.INCORRECT_DATA_FARMAT);
	}

	public void printUnknownSpecies() {
		System.out.println(Constans.UNKNOWN_SPECIES);
	}

	public void printIncorrectType() {
		System.err.println(Constans.INCORRECT_TYPE);
	}

	public void printLackOfCages() {
		System.err.println(Constans.LACK_OF_CAGES);
	}

	public void printInfoForInit() {
		printAmountOdAnimals();
		printListOfAvailableSpecies();
	}

	public void printInfoAboutSpecies(Animal animal) {
		animal.getInfo();
	}

	public void showSummary(int babiesCounter, int corpseCounter) {

		animationAfterAction();
		System.out.println();
		printYearCounter();
		System.out.println();
		printInfoAnimalsAmount();
		System.out.println(Constans.IN_LAST_MONTH);
		System.out.println(Constans.BORN + babiesCounter + Constans.ANIMALS);
		System.out.println(Constans.DIED + corpseCounter);
		System.out.println();
	}

	public void printTooLowNumberOfAnimal() {
		System.out.println(Constans.TOO_LOW_NUMBER);
	}

	public void printIntoAboutSpecies(int length, int minWeight, int maxAge, String type) {
		System.out.println(Constans.LENGTH_OF_PREGNANCY + length);
		System.out.println(Constans.MINIMAL_WEIGHT + minWeight);
		System.out.println(Constans.MAX_AGE + maxAge);
		System.out.println(Constans.FAVORITE_FOOD + type);

	}
}
