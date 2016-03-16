package com.mzglinicki.zoo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class GuiManager {

    private static GuiManager instance = null;
    private AnimalsManager animalManager = AnimalsManager.getInstance();
    private final int AMOUNT_OF_DASHES = 34;
    private final int LENGTH_OF_ANIMATION = 10;
    private final int LENGTH_OF_ENDING_ANIMATION = 40;
    private final int VELOCITY_OF_ANIMATION = 25;
    private final int BUY_ANIMAL = 100;
    private final int ONE_ANIMAL = 1;
    private final String NEW_LINE = "\n";
    private final String COLON = ": ";
    private final char SPACE = ' ';
    private final char VERTICAL_LINE = '|';
    private final char DASH = '-';
    private final char PLUS = '+';
    private final char TAB = '\t';
    public final char PERCENT = '%';

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

        String day = String.format( dataFormat, date );
        String time = String.format( timeFormat, date );

        System.out.printf( displayData, day, time );
        createDash();
        System.out.printf( displayWelcome, VERTICAL_LINE, Constants.WELCOME_TEXT, VERTICAL_LINE );
        createDash();
    }

    public void printYearCounter() {
        String printYearFormat = "\n\n%s%-5d%s%d%s\n";
        System.out.printf( printYearFormat, Constants.NUMBER_OF_YEAR, animalManager.getNumOfYear(), Constants.ANIMAL_SATISFACTION,
                animalManager.getAnimalSatisfaction(), PERCENT );
    }

    public void createDash() {

        System.out.print( NEW_LINE + VERTICAL_LINE );
        for (int i = 0; i < AMOUNT_OF_DASHES; i++) {
            System.out.print( DASH );
        }
        System.out.print( VERTICAL_LINE );
    }

    public void printMainMenu() {

        String optionsTextFormatter = "\n\n %s\n";
        String optionFormatter = " %-2d%s\n";

        StringBuffer text = new StringBuffer();

        System.out.printf( optionsTextFormatter, Constants.CHOOSE_OPTION );
        System.out.printf( optionFormatter, MainMenuOptions.NEW_GAME.ordinal(), Constants.NEW_GAME );
        System.out.printf( optionFormatter, MainMenuOptions.LOAD_LAST_GAME.ordinal(), Constants.LOAD_LAST_GAME );
        System.out.printf( optionFormatter, MainMenuOptions.RULES.ordinal(), Constants.RULES );
        System.out.printf( optionFormatter, MainMenuOptions.CREDITS.ordinal(), Constants.CREDITS );
        System.out.printf( optionFormatter, MainMenuOptions.CLOSE.ordinal(), Constants.CLOSE );

    }

    public void printAnimationAfterWin() {

        String charsPart1 = "|/-\\";
        String charsPart2 = "-\\|";

        int id = 0;
        for (int i = 0; i < LENGTH_OF_ENDING_ANIMATION; i++) {

            System.out.print( charsPart1.charAt( id ) );
            try {
                Thread.sleep( VELOCITY_OF_ANIMATION );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            id = ++id % charsPart1.length();
        }
        System.out.println( charsPart2 + NEW_LINE + Constants.CONGRATULATION );
    }

    public void printInfoAnimalsAmount() {

        StringBuffer text = new StringBuffer();

        int counter = animalManager.getAmountOfAnimal();

        if (counter > ONE_ANIMAL) {

            System.out.println( text.append( Constants.AMOUNT_OF_ANIMALS_TEXT ).append( counter ).append( Constants.AMOUNT_OF_ANIMALS_PART2 ) );

        } else {
            System.out.println( NEW_LINE + Constants.ONE_ANIMAL_TEXT );
        }
    }

    public void printAmountOdAnimals() {
        System.out.println( NEW_LINE + Constants.AVAILABLE_SPECIES );
    }

    public void printListOfAvailableSpecies() {

        StringBuffer text = new StringBuffer();

        for (Species animal : Species.values()) {
            text.append( animal.ordinal() ).append( SPACE ).append( animal.getType() ).append( NEW_LINE );
        }
        System.out.println( text );
    }

    public void printListOfSpeciesWithAmount() {

        Map<Species, List<Animal>> map = animalManager.getMapOfSpieces();
        StringBuffer text = new StringBuffer();

        Stream.of( Species.values() ).forEach( species -> text.append( species.ordinal() ).append( SPACE ).append( species.getType() ).append( COLON )
                .append( (map.get( species ) != null ? map.get( species ).size() : 0) ).append( NEW_LINE ) );

//        for (Species species : Species.values()) {
//            text.append( species.ordinal() ).append( SPACE ).append( species.getType() ).append( COLON )
//                    .append( (map.get( species ) != null ? map.get( species ).size() : 0) ).append( NEW_LINE );
//        }
        System.out.print( text );
    }

    public void printOtherOptions() {

        StringBuffer text = new StringBuffer();
        System.out.println( text.append( NEW_LINE + Constants.OTHER_OPTION )
                .append( NEW_LINE + TAB + Constants.SHOW_LIST_OF_ANIMALS )
                .append( NEW_LINE + TAB + Constants.SHOW_SPECIES_INFO )
                .append( NEW_LINE + TAB + Constants.SAVE_TEXT )
                .append( NEW_LINE + TAB + Constants.PLAY_MAIN_PANEL ) );
    }

    public void printPlayActivities() {

        StringBuffer text = new StringBuffer();
        System.out.println( text.append( NEW_LINE + Constants.OTHER_OPTION )
                .append( NEW_LINE + TAB + Constants.SPEAK )
                .append( Constants.PRESS_ENTER ) );
    }

    public void printListOfAnimals() {

        Map<Species, List<Animal>> mapOfAnimals = animalManager.getMapOfSpieces();
        String animalListFormatter = " %-4d%-13s%-6d %-8d %s\n";
        String headlineFormatter = "%-4s %-12s %-6s %-8s %s\n";

        mapOfAnimals
                .values()
                .stream()
                .filter( list -> !list.isEmpty() )
                .forEach( list -> {
                    System.out.println( list.get( 0 ).getSpecies().getType() + COLON );
                    System.out.printf( headlineFormatter, Constants.NUMBER, Constants.NAME, Constants.AGE, Constants.WEIGHT, Constants.SEX );
                    list.forEach( animal ->
                            System.out.printf( animalListFormatter, list.indexOf( animal ) + 1, animal.getName(), animal.getAge(),
                                    animal.getWeight(), animal.getSex() ) );
                    System.out.println();
                } );
        printListOfSpeciesWithAmount();
        getNumOfSpeciesOrOtherOption();
        printOtherOptions();
    }

    public void getNumOfSpeciesOrOtherOption() {
        System.out.println( Constants.GIVE_NUM_OF_SPEC );
    }

    public void printFood() {
        StringBuffer text = new StringBuffer();

        System.out.println( NEW_LINE + Constants.TYPE_OF_FOOD );

        for (Food food : Food.values()) {
            text.append( food.ordinal() ).append( SPACE ).append( food.getType() ).append( NEW_LINE );
        }
        System.out.print( text );
    }

    public void printActivities() {

        StringBuffer text = new StringBuffer();

        System.out.println( NEW_LINE + Constants.SELECT_ACTIVITY + NEW_LINE );

        for (Activities activities : Activities.values()) {
            text.append( activities.ordinal() ).append( SPACE ).append( activities.getActivity() ).append( NEW_LINE );
        }
        System.out.print( text );
    }

    public void animationAfterAction() {

        StringBuffer text = new StringBuffer();
        for (int i = 0; i < LENGTH_OF_ANIMATION; i++) {
            System.out.print( text.append( PLUS ) );
            try {
                Thread.sleep( VELOCITY_OF_ANIMATION );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printSelectionText() {
        System.out.println( Constants.INIT_SELECT_SPECIES );
    }

    public void printInfoInitAmount() {
        System.out.println( Constants.INITIAL_AMOUNT_OF_ANIMALS_TEXT );
    }

    public void printLoseInfo() {

        System.out.print( Constants.LOSE_INFO );
    }

    public void printInfoAboutPregnancy(Animal animal, Animal dad) {

        StringBuffer text = new StringBuffer();
        System.out.println( text.append( animal.getName() ).append( Constants.IN_PRAGNANCY ).append( dad.getName() ) );
    }

    public void printIncorrectDataFormat() {
        System.out.println( Constants.INCORRECT_DATA_FARMAT );
    }

    public void printUnavailableSpeciesInfo() {
        System.out.println( Constants.UNAVAILABLE_SPECIES );
    }

    public void printUnknownSpecies() {
        System.out.println( Constants.UNKNOWN_SPECIES );
    }

    public void printIncorrectType() {
        System.err.println( Constants.INCORRECT_TYPE );
    }

    public void printLackOfCages() {
        System.err.println( Constants.LACK_OF_CAGES );
    }

    public void printInfoForInit() {
        printAmountOdAnimals();
        printListOfAvailableSpecies();
    }

    public void printInfoAboutSpecies(Animal animal) {
        animal.getInfo();
    }

    public void showSummary(int babiesCounter, int corpseCounter, int boughtAnimal) {

        animationAfterAction();
        System.out.println();
        printYearCounter();
        System.out.println();
        printInfoAnimalsAmount();
        System.out.println( Constants.IN_LAST_MONTH );
        System.out.println( Constants.BORN + babiesCounter + Constants.ANIMALS );
        System.out.println( Constants.DIED + corpseCounter );
        if (boughtAnimal == BUY_ANIMAL) {
            System.out.println( Constants.BOUGHT );
        }
        System.out.println();
    }

    public void printHeadlineAfterLoad() {
        printYearCounter();
        System.out.println();
        printInfoAnimalsAmount();
    }

    public void printTooLowNumberOfAnimal() {
        System.out.println( Constants.TOO_LOW_NUMBER );
    }

    public void printIntoAboutSpecies(int length, int minWeight, int maxAge, String type) {

        StringBuffer text = new StringBuffer();

        System.out.println( text.append( Constants.LENGTH_OF_PREGNANCY ).append( length ).append( NEW_LINE )
                .append( Constants.MINIMAL_WEIGHT ).append( minWeight ).append( NEW_LINE ).append( Constants.MAX_AGE )
                .append( maxAge ).append( NEW_LINE ).append( Constants.FAVORITE_FOOD ).append( type ) );
    }

    public void printRules() {

        System.out.println( Constants.RULES_TEXT_PART_1 + animalManager.getLengthOfGama() + Constants.RULES_TEXT_PART_2 );
    }

    public void printSavedInfo() {
        System.out.println( Constants.DATA_ARE_SAVED );
    }

    public void printSavedInfoToXML() {
        System.out.println( Constants.DATA_ARE_SAVED_TO_XML );
    }

    public void printSavedInfoToJson() {
        System.out.println( Constants.DATA_ARE_SAVED_TO_JSON );
    }

    public void printTooHeightNumberOfAnimal() {
        System.out.println( Constants.TOO_HEIGHT_NUMBER );
    }

    public void showCredits() {
        System.out.println( Constants.CREDITS_TEXT );
        System.out.println( Constants.AUTHOR );
    }
}
