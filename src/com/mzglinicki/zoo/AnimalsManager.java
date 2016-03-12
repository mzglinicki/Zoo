package com.mzglinicki.zoo;

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
    private final int LENGTH_OF_GAME = 20;
    private final int MIN_SATISFACTION = 30;
    private static AnimalsManager animalsManager = null;
    private static GuiManager guiManager = GuiManager.getInstance();
    private static GameManager gameManager = GameManager.getInstance();
    private static ExternalFilesManager filesManager = ExternalFilesManager.getInstance();

    private Scanner userInput = new Scanner( System.in );
    private Random generator = new Random();
    private Map<Species, List<Animal>> mapOfSpieces = new HashMap<>();

    private int numOfYear = 0;
    private int animalSatisfaction;

    public static AnimalsManager getInstance() {
        if (animalsManager == null) {
            animalsManager = new AnimalsManager();
        }
        return animalsManager;
    }

    public Map<Species, List<Animal>> getMapOfSpieces() {
        return mapOfSpieces;
    }

    public void setMapOfSpieces(Map<Species, List<Animal>> loadHashMap) {
        mapOfSpieces = loadHashMap;
    }

    public int getNumOfYear() {
        return numOfYear;
    }

    public void setNumOfYear(int loadYear) {
        numOfYear = loadYear;
    }

    public int getLengthOfGama() {
        return LENGTH_OF_GAME;
    }

    public int getMinimalSatisfaction() {
        return MIN_SATISFACTION;
    }

    public String setName(String sex) {

        ExternalFilesManager fileManager = ExternalFilesManager.getInstance();

        return sex.equals( Sex.FEMALE.getSexToString() ) ? fileManager.getMapOfNames().get( Sex.FEMALE )
                .get( generator.nextInt( fileManager.getMapOfNames().get( Sex.FEMALE ).size() ) ) : fileManager.getMapOfNames().get( Sex.MALE )
                .get( generator.nextInt( fileManager.getMapOfNames().get( Sex.MALE ).size() ) );
    }

    public Map<Species, List<Animal>> initialization() {

        guiManager.printInfoForInit();

        List<Species> listOfSpecies = getInitSpecies();

        int amountOfAnimal = getInitAmount();

        for (int i = 0; i < amountOfAnimal; i++) {
            Species species = listOfSpecies.get( generator.nextInt( listOfSpecies.size() ) );

            List<Animal> animals = mapOfSpieces.get( species );
            if (animals == null) {
                animals = new ArrayList<>();
                mapOfSpieces.put( species, animals );
            }
            animals.add( species.getAnimal() );
            Collections.sort( animals );
        }
        guiManager.printInfoAnimalsAmount();
        return mapOfSpieces;
    }

    private List<Species> getInitSpecies() {

        List<Species> listOfSpecies = new ArrayList<Species>();

        do {
            guiManager.printSelectionText();
            try {
                String userInputTab[] = userInput.nextLine().split( DELIMITER );

                for (String speciesId : userInputTab) {
                    listOfSpecies.add( Species.values()[Integer.parseInt( speciesId )] );
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
                int amountOfAnimal = Integer.parseInt( userInput.nextLine() );

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
        int num = 0;
        guiManager.printListOfSpeciesWithAmount();
        guiManager.getNumOfSpeciesOrOtherOption();
        guiManager.printOtherOptions();

        do {
            try {
                input = userInput.nextLine();
                num = Integer.parseInt( input );

                availableActivities( returnKeyToSpeciesList( num ) );
                condition = false;
            } catch (NumberFormatException uncorrectFormat) {

                followOptionalChoice( input );

            } catch (ArrayIndexOutOfBoundsException e) {
                if (num < 0) {
                    guiManager.printTooLowNumberOfAnimal();
                } else {
                    guiManager.printTooHeightNumberOfAnimal();
                }
            }
        } while (condition);
    }

    private void followOptionalChoice(String input) {

        if (input.equals( GameInputOptions.LIST.getValue() )) {
            guiManager.printListOfAnimals();
        } else if (input.equals( GameInputOptions.MAIN_PANEL.getValue() )) {
            gameManager.selectMainMenuOption();
        } else if (input.equals( GameInputOptions.SAVE.getValue() )) {
            ExternalFilesManager.getInstance().writeDataToFile( mapOfSpieces, GameInputOptions.IO_DATA_SER.getValue(), getNumOfYear(),
                    getAnimalSatisfaction() );
            ExternalFilesManager.getInstance().writeDataToXML( mapOfSpieces, GameInputOptions.DATA_XML.getValue() );
            ExternalFilesManager.getInstance().writeDataToJson( mapOfSpieces, GameInputOptions.DATA_JSON.getValue() );
        } else {
            getInfoAboutSpecies( input );
        }
    }

    public void availableActivities(Species keyToSpeciesList) {

        GuiManager.getInstance().printActivities();
        int input = Integer.parseInt( userInput.nextLine() );

        try {
            for (Activities activity : Activities.values()) {
                if (input == activity.ordinal()) {
                    activity.selectActivity( keyToSpeciesList );
                }
            }
        } catch (NumberFormatException e) {
            guiManager.printUnavailableSpeciesInfo();
        }
    }

    public void forage(Animal animal, int userInput) {

        animal.setWeightAfterForage( Food.values()[userInput] );
        animal.decreaseWeightWithoutFood( true );
    }

    public int getUserInputForForage() {
        GuiManager.getInstance().printFood();
        String numOfFood = userInput.nextLine();
        return Integer.parseInt( numOfFood );
    }

    public void playForAnimal(Animal animal) {

        RandomEvents event = new RandomEvents();

        event.pregnancy( animal );
    }

    public void playForSpecies(Species species) {

        guiManager.printPlayActivities();
        boolean condition = true;
        do {
            String input = userInput.nextLine();
            if (input.equals( Sound.SPEAK.getSpeak() )) {
                filesManager.getSoundForAnimal( getMapOfSpieces().get( species ).get( 0 ).getSound() );
            } else if (input.isEmpty()) {
                condition = false;
            }
        } while (condition);
    }

    public void buyAnimal(Species species) {

        List<Animal> animals = mapOfSpieces.get( species );
        if (animals == null) {
            animals = new ArrayList<>();
            mapOfSpieces.put( species, animals );
        }
        animals.add( species.getAnimal() );
        Collections.sort( animals );
    }

    public boolean checkDeath(Animal animal) {

        RandomEvents event = new RandomEvents();

        return animal.deathOfOldAge() || animal.starvation() || event.death();
    }

    public Species returnKeyToSpeciesList(int num) throws ArrayIndexOutOfBoundsException {

        if (mapOfSpieces.containsKey( Species.values()[num] )) {
            return Species.values()[num];
        }
        throw new NumberFormatException();
    }

    public boolean getInfoAboutSpecies(String input) {

        for (Species species : Species.values()) {
            if (input.toLowerCase().equals( species.getType().toLowerCase() )) {
                try {
                    guiManager.printInfoAboutSpecies( mapOfSpieces.get( species ).get( 0 ) );
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

        int satisfaction = 0;
        int babiesCounter = 0;
        int corpseCounter = 0;

        for (List<Animal> listOfAnimal : mapOfSpieces.values()) {

            for (int i = listOfAnimal.size() - 1; i >= 0; i--) {

                Animal animal = listOfAnimal.get( i );

                if (keyToSpeciesList == animal.getSpecies()) {
                    makeActivityOnAnimal( activity, animal, userInput );
                } else {
                    animal.decreaseWeightWithoutFood( false );
                }

                Animal newBaby = animal.updateAge();
                if (newBaby != null) {
                    listOfAnimal.add( newBaby );
                    babiesCounter++;
                }

                if (checkDeath( animal )) {
                    listOfAnimal.remove( i );
                    corpseCounter++;
                } else {
                    satisfaction += animal.getAnimalSatisfaction();
                }
            }
        }
        satisfaction /= (getAmountOfAnimal());
        setAnimalSatisfaction( satisfaction );
        numOfYear++;
        guiManager.showSummary( babiesCounter, corpseCounter, userInput );
    }

    private void makeActivityOnAnimal(Activities activity, Animal animal, int userInput) {
        switch (activity) {
            case EAT:
                forage( animal, userInput );
                break;
            case WALK:
                break;
            case LEISURE:
                playForAnimal( animal );
                break;
            case BUY_ANIMAL:
                break;
        }
    }

    public int getAnimalSatisfaction() {
        return animalSatisfaction;
    }

    public void setAnimalSatisfaction(int loadAnimalSatisfaction) {
        animalSatisfaction = loadAnimalSatisfaction;
    }

    public int getAmountOfAnimal() {

        int counter = 0;

        for (List<Animal> list : mapOfSpieces.values()) {
            counter += list.size();
        }
        return counter;
    }
}
