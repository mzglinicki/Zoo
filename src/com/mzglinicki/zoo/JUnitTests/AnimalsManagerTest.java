package com.mzglinicki.zoo.JUnitTests;

import com.mzglinicki.zoo.Animal;
import com.mzglinicki.zoo.AnimalsManager;
import com.mzglinicki.zoo.GuiManager;
import com.mzglinicki.zoo.Species;
import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by mzglinicki.96 on 12.03.2016.
 */
public class AnimalsManagerTest {

    AnimalsManager animalsManager = AnimalsManager.getInstance();
    GuiManager guiManager = GuiManager.getInstance();

    @Before
    public void beforeTests(){

    }

    @org.junit.Test
    public void testDefaultDelimiter() throws Exception {

    }

    @org.junit.Test
    public void testInitialization() throws Exception {

        Map<Species, List<Animal>> mapOfSpieces = animalsManager.getMapOfSpieces();
        List<Animal> animals = mapOfSpieces.get( 0 );
        if (animals == null) {
            animals = new ArrayList<>();
            mapOfSpieces.put( Species.GIRAFFE, animals );
        }
        Assert.assertNotNull(  animalsManager.initialization() );
    }

    @org.junit.Test
    public void testGetInitAmount() throws Exception {

    }

    @org.junit.Test
    public void testSelectSpecies() throws Exception {

    }

    @org.junit.Test
    public void testBuyAnimal() throws Exception {

    }

    @org.junit.Test
    public void testUpdate() throws Exception {

    }
}