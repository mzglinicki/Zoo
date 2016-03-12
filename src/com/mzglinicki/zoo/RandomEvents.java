package com.mzglinicki.zoo;

import java.util.Random;

public class RandomEvents {

	private Random generator = new Random();
	private GuiManager guiManager = GuiManager.getInstance();
	private final int PROB_OF_PREGNANCY = 3;
	private final int PREGNANCY = 2;
	private final int PROB_OF_DEATH = 100;
	private final int DEATH = 1;

	public void pregnancy(Animal animal) {

		AnimalsManager animalsManager = AnimalsManager.getInstance();
		int randomer = generator.nextInt(animalsManager.getMapOfSpieces().get(animal.getSpecies()).size());

		int secondAnimalIndex = randomer;
		Animal secondAnimal = animalsManager.getMapOfSpieces().get(animal.getSpecies()).get(secondAnimalIndex);

		if (!secondAnimal.getSex().equals(animal.getSex()) && (generator.nextInt(PROB_OF_PREGNANCY) == PREGNANCY) && !animal.isPregnancy()
				&& !secondAnimal.isPregnancy()) {

			if (animal.getSex().equals(Sex.FEMALE.getSexToString())) {
				animal.setPregnancy(true, animal.getAge());
				guiManager.printInfoAboutPregrency(animal, secondAnimal);
			} else {
				secondAnimal.setPregnancy(true, animal.getAge());
				guiManager.printInfoAboutPregrency(secondAnimal, animal);
			}
		}
	}

	public boolean death() {

		return (generator.nextInt(PROB_OF_DEATH) == DEATH);
	}
}
