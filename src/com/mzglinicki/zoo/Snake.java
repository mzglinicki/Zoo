package com.mzglinicki.zoo;


public class Snake extends Animal implements Swimming {

	private static final int SNAKE_SPEED = 6;
	private final int VALUE_OF_FOOD = 10;
	private final int MAX_INIT_WEIGHT_ADULT = 70;
	private final int MAX_INIT_WEIGHT_CHILD = 20;
	private final int MAX_AGES = 50;
	private final int BABY_AGE = 1;
	private final int MAX_PRAGNANCY_TIME = 2;
	private final int MIN_WEIGHT = 2;
	private final int SATISFACTION_POINTER_1 = 20;
	private final int SATISFACTION_POINTER_2 = 100;

	private int depthOfswimming = 3;
	private int noOfyearsWithoutFood = 1;
	private int ageOfPragnancyStart;

	public Snake() {
		super(Species.SNAKE, SNAKE_SPEED, Constans.SNAKE_SOUND, Food.MAUSE);
	}

	private Snake(int age) {
		super(Species.SNAKE, SNAKE_SPEED, Constans.SNAKE_SOUND, Food.MAUSE);
		this.age = age;
	}

	public int getMinWeight() {
		return MIN_WEIGHT;
	}

	@Override
	public int weightOfNewAnimal() {

		if (this.age <= 10) {
			return generator.nextInt((MAX_INIT_WEIGHT_CHILD - MIN_WEIGHT) + 1) + MIN_WEIGHT;
		}
		return generator.nextInt((MAX_INIT_WEIGHT_ADULT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
	}

	@Override
	public int depthOfswimming() {
		return depthOfswimming;
	}

	@Override
	public String getSound() {
		return Constans.SNAKE_SOUND;
	}

	@Override
	public Food getFood() {
		return Food.MAUSE;
	}

	@Override
	public void getInfo() {

		GuiManager.getInstance().printIntoAboutSpecies(MAX_PRAGNANCY_TIME, MIN_WEIGHT, MAX_AGES, getFood().getType());
	}

	@Override
	public int getSpeed() {
		return 0;
	}

	@Override
	public void setWeightAftrForage(Food type) {

		if (type == getFood()) {
			this.weight = this.getWeight() + VALUE_OF_FOOD;
		}
	}

	@Override
	public int decreaseWeightWithoutFood(boolean areWeEating) {

		if (!areWeEating) {
			noOfyearsWithoutFood++;
		}
		return this.weight = areWeEating ? this.weight : (this.weight - 2 * noOfyearsWithoutFood/2);
	}

	@Override
	public boolean deathOfOldAge() {

		return (age >= MAX_AGES);
	}

	@Override
	public boolean starvation() {
		return (weight <= MIN_WEIGHT);
	}

	@Override
	public Snake updateAge() {

		age += 1;

		if (isPregnancy() && age - ageOfPragnancyStart == MAX_PRAGNANCY_TIME) {
			setPregnancy(false, age);
			return new Snake(BABY_AGE);
		}
		return null;
	}

	@Override
	public void setPregnancy(boolean pregnancy, int age) {
		this.pregnancy = pregnancy;
		ageOfPragnancyStart = age;
	}

	@Override
	public int getAnimalSatisfaction() {
		return weight*SATISFACTION_POINTER_2/(MIN_WEIGHT+SATISFACTION_POINTER_1);
	}
}
