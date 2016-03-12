package com.mzglinicki.zoo;

/**
 * Created by mzglinicki.96 on 12.03.2016.
 */
public class SugarGlider extends Animal {

    private static final int SUGAR_GLIDER_SPEED = 10;
    private final int VALUE_OF_FOOD = 10;
    private final int MAX_INIT_WEIGHT_ADULT = 20;
    private final int MAX_INIT_WEIGHT_CHILD = 5;
    private final int MIN_WEIGHT = 1;
    private final int MAX_AGES = 80;
    private final int MAX_PRAGNANCY_TIME = 1;
    private final int BABY_AGE = 1;

    private int noOfyearsWithoutFood = 4;
    private int ageOfPragnancyStart;

    public SugarGlider() {
        super(Species.SUGAR_GLIDER, SUGAR_GLIDER_SPEED, Constants.SUGAR_GLIDER_SOUND, Food.ORANGE);
    }

    private SugarGlider(int age) {
        super(Species.SUGAR_GLIDER, SUGAR_GLIDER_SPEED, Constants.SUGAR_GLIDER_SOUND, Food.ORANGE);
        this.age = age;
    }

    @Override
    public Food getFood() {
        return Food.ORANGE;
    }

    @Override
    public int weightOfNewAnimal() {
        if (this.age <= 4) {
            return generator.nextInt((MAX_INIT_WEIGHT_CHILD - MIN_WEIGHT) + 1) + MIN_WEIGHT;
        }
        return generator.nextInt((MAX_INIT_WEIGHT_ADULT - MIN_WEIGHT) + 1) + MIN_WEIGHT;
    }

    @Override
    public int getSpeed() {
        return SUGAR_GLIDER_SPEED;
    }

    @Override
    public String getSound() {
        return Constants.SUGAR_GLIDER_SOUND;
    }

    @Override
    public void getInfo() {
        GuiManager.getInstance().printIntoAboutSpecies(MAX_PRAGNANCY_TIME, MIN_WEIGHT, MAX_AGES, getFood().getType());
    }

    @Override
    public void setWeightAfterForage(Food type) {
        if (type == getFood()) {
            this.weight = this.getWeight() + VALUE_OF_FOOD;
        }
    }

    @Override
    public int decreaseWeightWithoutFood(boolean areWeEating) {
        if (!areWeEating) {
            noOfyearsWithoutFood++;
        }
        return this.weight = areWeEating ? this.weight : (this.weight - 2 * noOfyearsWithoutFood / 4);
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
    public Animal updateAge() {
        age += 1;

        if (isPregnancy() && age - ageOfPragnancyStart == MAX_PRAGNANCY_TIME) {
            setPregnancy(false, age);
            return new SugarGlider(BABY_AGE);
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
        int satisfaction_pointer_1 = 5;
        int satisfaction_pointer_2 = 100;

        if(weight> MIN_WEIGHT+satisfaction_pointer_1){
            return satisfaction_pointer_2;
        } else{
            return (weight * satisfaction_pointer_2) / (MIN_WEIGHT + satisfaction_pointer_1);
        }
    }
}
