package com.mzglinicki.zoo;

public class Giraffe extends Animal {

	private static final int GIRAFFE_SPEED = 12;
	private final int VALUE_OF_FOOD = 10;
	private final int MAX_INIT_WEIGHT_ADULT = 100;
	private final int MAX_INIT_WEIGHT_CHILD = 60;
	private final int MIN_WEIGHT = 20;
	private final int MAX_AGES = 100;
	private final int MAX_PRAGNANCY_TIME = 4;
	private final int BABY_AGE = 1;

	private int noOfyearsWithoutFood = 1;
	private int ageOfPragnancyStart;

	public Giraffe() {
		super(Species.GIRAFFE, GIRAFFE_SPEED, Constans.GIRAFFE_SOUND, Food.LEAVES);
	}
	
	private Giraffe(int age) {
		super(Species.GIRAFFE, GIRAFFE_SPEED, Constans.GIRAFFE_SOUND, Food.LEAVES);
		this.age = age;
	}

	@Override
	public int weightOfNewAnimal() {

		if (this.age <= 10) {
			return generator.nextInt((MAX_INIT_WEIGHT_CHILD - MIN_WEIGHT) + 1) + MIN_WEIGHT;
		}
		return generator.nextInt((MAX_INIT_WEIGHT_ADULT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
	}

	@Override
	public int getSpeed() {
		return GIRAFFE_SPEED;
	}

	@Override
	public String getSound() {
		return Constans.GIRAFFE_SOUND;
	}

	@Override
	public Food getFood() {
		return Food.LEAVES;
	}

	@Override
	public void getInfo() {
		GuiManager.getInstance().printIntoAboutSpecies(MAX_PRAGNANCY_TIME, MIN_WEIGHT, MAX_AGES, getFood().getType());
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
	public int getMinWeight() {
		return MIN_WEIGHT;
	}

	@Override
	public Giraffe updateAge() {
		age += 1;

		if (isPregnancy() && age - ageOfPragnancyStart == MAX_PRAGNANCY_TIME) {
			setPregnancy(false, age);
			return new Giraffe(BABY_AGE);
		}
		return null;
	}

	@Override
	public void setPregnancy(boolean pregnancy, int age) {
		this.pregnancy = pregnancy;
		ageOfPragnancyStart = age;
	}
}
