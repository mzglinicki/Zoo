package com.mzglinicki.zoo;

public class Tiger extends Animal {

	private static final int TIGER_SPEED = 12;
	private final int VALUE_OF_FOOD = 10;
	private final int MAX_INIT_WEIGHT_ADULT = 100;
	private final int MAX_INIT_WEIGHT_CHILD = 30;
	private final int MIN_WEIGHT = 10;
	private final int MAX_AGES = 80;
	private final int MAX_PRAGNANCY_TIME = 3;
	private final int BABY_AGE = 1;

	private int noOfyearsWithoutFood = 1;
	private int ageOfPragnancyStart;

	public Tiger() {
		super(Species.TIGER, TIGER_SPEED, Constans.TIGER_SOUND, Food.RABBIT);
	}

	private Tiger(int age) {
		super(Species.TIGER, TIGER_SPEED, Constans.TIGER_SOUND, Food.RABBIT);
		this.age = age;
	}

	public int weightOfNewAnimal() {
		if (this.age <= 8) {
			return generator.nextInt((MAX_INIT_WEIGHT_CHILD - MIN_WEIGHT) + 1) + MIN_WEIGHT;
		}
		return generator.nextInt((MAX_INIT_WEIGHT_ADULT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
	}

	@Override
	public int getSpeed() {
		return TIGER_SPEED;
	}

	@Override
	public String getSound() {
		return Constans.TIGER_SOUND;
	}

	@Override
	public Food getFood() {
		return Food.MEAN;
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
		return this.weight = areWeEating ? this.weight : (this.weight - 2 * noOfyearsWithoutFood / 2);
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
	public Tiger updateAge() {

		age += 1;

		if (isPregnancy() && age - ageOfPragnancyStart == MAX_PRAGNANCY_TIME) {
			setPregnancy(false, age);
			return new Tiger(BABY_AGE);
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
		int satisfaction_pointer_1 = 20;
		int satisfaction_pointer_2 = 100;

		if (weight > MIN_WEIGHT + satisfaction_pointer_1) {
			return satisfaction_pointer_2;
		} else {
			return (weight * satisfaction_pointer_2) / (MIN_WEIGHT + satisfaction_pointer_1);
		}
	}
}
