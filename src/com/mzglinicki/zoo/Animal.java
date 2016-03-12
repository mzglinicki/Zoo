package com.mzglinicki.zoo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Animal implements Comparable<Animal>, Serializable {

	private static final long serialVersionUID = 8350452777352202050L;
	
	private final int MAX_INIT_AGE = 30;
	protected Random generator = new Random();
	protected static Map<Species, List<Animal>> mapOfbabies = new HashMap<>();

	private String sound;
	protected String sex;
	private int speed;
	private int id;
	protected int age;
	protected int weight;
	protected Food food;
	protected String name;
	protected boolean pregnancy;
	private Species spieces;

	public Animal(final Species spieces, final int speed, final String sound, final Food food) {
		this.spieces = spieces;
		this.speed = speed;
		this.sound = sound;
		this.food = food;
		this.pregnancy = false;
		this.sex = setInitialtSex();
		this.age = getInitialAgeGenerator();
		this.weight = weightOfNewAnimal();
		this.name = AnimalsManager.getInstance().setName(this.sex);
	}

	public abstract Food getFood();

	public abstract int weightOfNewAnimal();

	public abstract int getSpeed();

	public abstract String getSound();

	public abstract void getInfo();

	public abstract void setWeightAftrForage(Food typeOfFood);

	public abstract int decreaseWeightWithoutFood(boolean areWeEating);

	public abstract boolean deathOfOldAge();

	public abstract boolean starvation();

	public abstract int getMinWeight();

	public abstract Animal updateAge();

	public abstract void setPregnancy(boolean pregnancy, int age);
	
	public abstract int getAnimalSatisfaction();

	public boolean isPregnancy() {
		return pregnancy;
	}

	public Species getSpecies() {
		return spieces;
	}

	public int getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	public int getWeight() {
		return weight;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getInitialAgeGenerator() {
		Random generator = new Random();
		return generator.nextInt(MAX_INIT_AGE) + 1;
	}

	public String setInitialtSex() {
		Random generator = new Random();
		return generator.nextBoolean() ? Sex.FEMALE.getSexToString() : Sex.MALE.getSexToString();
	}

	public Map<Species, List<Animal>> getMapOfbabies() {
		return mapOfbabies;
	}

	@Override
	public int compareTo(Animal animal) {
		return getAge() - animal.getAge();
	}
}
