package com.mzglinicki.zoo;

/**
 * Created by mzglinicki.96 on 12.03.2016.
 */
public class Pterodactyl extends Animal {

    private static final int PTERODACTYL_SPEED = 12;
    private final int VALUE_OF_FOOD = 10;
    private final int MAX_INIT_WEIGHT_ADULT = 200;
    private final int MAX_INIT_WEIGHT_CHILD = 100;
    private final int MIN_WEIGHT = 40;
    private final int MAX_AGES = 200;
    private final int MAX_PRAGNANCY_TIME = 5;
    private final int BABY_AGE = 1;

    private int noOfyearsWithoutFood = 4;
    private int ageOfPragnancyStart;

    public Pterodactyl() {
        super(Species.PTERODACTYL, PTERODACTYL_SPEED, Constants.PTERODACTYL_SOUND, Food.RABBIT);
    }

    private Pterodactyl(int age) {
        super(Species.PTERODACTYL, PTERODACTYL_SPEED, Constants.PTERODACTYL_SOUND, Food.RABBIT);
        this.age = age;
    }

    @Override
    public Food getFood() {
        return Food.RABBIT;
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
        return PTERODACTYL_SPEED;
    }

    @Override
    public String getSound() {
        return Constants.PTERODACTYL_SOUND;
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
    public Animal updateAge() {
        age += 1;

        if (isPregnancy() && age - ageOfPragnancyStart == MAX_PRAGNANCY_TIME) {
            setPregnancy(false, age);
            return new Pterodactyl(BABY_AGE);
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

        if(weight> MIN_WEIGHT+satisfaction_pointer_1){
            return satisfaction_pointer_2;
        } else{
            return (weight * satisfaction_pointer_2) / (MIN_WEIGHT + satisfaction_pointer_1);
        }
    }
}
