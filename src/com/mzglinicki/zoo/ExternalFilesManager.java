package com.mzglinicki.zoo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class ExternalFilesManager {

	private GuiManager guiManager = GuiManager.getInstance();
	private AnimalsManager animalCreator = AnimalsManager.getInstance();
	
	private final String DELIMITER = ",";
	
	private Map<Sex, List<String>> mapOfNames = new HashMap<>();
	
	private int numOfYear = 0;
	private int animalsSatisfaction = 0;
	
	private static ExternalFilesManager fileManager = null;

	private ExternalFilesManager() {
	}

	public Map<Sex, List<String>> getMapOfNames() {
		return mapOfNames;
	}

	public static ExternalFilesManager getInstance() {
		if (fileManager == null) {
			fileManager = new ExternalFilesManager();
		}
		return fileManager;
	}

	public void writeDataToFile(Map<Species, List<Animal>> mapOfSpieces, String fileToWrite, int year, int satisfaction) {

		List<Integer> data = new ArrayList<Integer>();

		data.add(year);
		data.add(satisfaction);

		try {

			FileOutputStream fileStream = new FileOutputStream(fileToWrite);

			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			objectStream.writeObject(data);

			objectStream.writeObject(mapOfSpieces);

			objectStream.close();

			fileStream.close();

			guiManager.printSavedInfo();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Map<Species, List<Animal>> readData(String fileName) {

		List<Integer> data = new ArrayList<Integer>();

		Map<Species, List<Animal>> loadHashMap = null;

		try {

			FileInputStream fileInput = new FileInputStream(fileName);

			ObjectInputStream objectStream = new ObjectInputStream(fileInput);

			try {

				data = (List<Integer>) objectStream.readObject();

				loadHashMap = (HashMap<Species, List<Animal>>) objectStream.readObject();

				
				fileInput.close();

				numOfYear = data.get(0);
				animalsSatisfaction = data.get(1);

				return loadHashMap;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			objectStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> readNames(String fileName) {

		List<String> listOfNames = new ArrayList<String>();

		try {
			BufferedReader namesForMale = new BufferedReader(new FileReader(fileName));

			String line = namesForMale.readLine();

			while (line != null) {
				String[] namesLine = line.split(DELIMITER);
				for (String name : namesLine) {
					listOfNames.add(name.trim());
				}
				line = namesForMale.readLine();
			}
			namesForMale.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listOfNames;
	}
	
	public void loadNamas() {
		
		ExternalFilesManager fileManager = ExternalFilesManager.getInstance();
		
		mapOfNames.put(Sex.FEMALE, fileManager.readNames(Sex.FEMALE.getNames()));
		mapOfNames.put(Sex.MALE, fileManager.readNames(Sex.MALE.getNames()));
	}

	public void writeDataToXML(Map<Species, List<Animal>> mapOfSpieces, String fileToWrite){
		
		XStream xstream = new XStream(new StaxDriver());
		String xml = xstream.toXML(mapOfSpieces);
		
		try {

			FileOutputStream fileStream = new FileOutputStream(fileToWrite);

			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			objectStream.writeObject(xml);

			objectStream.close();

			fileStream.close();

			guiManager.printSavedInfoToXML();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeDataToJson(Map<Species, List<Animal>> mapOfSpieces, String fileToWrite){
		
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		String xml = xstream.toXML(mapOfSpieces);
		
		try {

			FileOutputStream fileStream = new FileOutputStream(fileToWrite);

			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			objectStream.writeObject(xml);

			objectStream.close();

			fileStream.close();

			guiManager.printSavedInfoToJson();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}