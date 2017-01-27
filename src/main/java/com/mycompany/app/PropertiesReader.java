package com.mycompany.app;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * klasa czytajaca z pliku konfiguracyjnego
 */
public class PropertiesReader {
    /**interfejs reprezentujący dokument xml*/
    Document doc;

    /**
     * konstruktor klasy
     * @param Filename nazwa pliku xml znajdujacego się w Resource
     */
    public PropertiesReader(String Filename) {
        try {
            //Get file from resources folder
            ClassLoader classLoader = PropertiesReader.class.getClassLoader();
            InputStream in = getClass().getResourceAsStream("/"+Filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(in);

            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * funkcja pobierajaca wartośc z pliku konfiguracyjnego
     * @param propertyName nazwa wartości którą chcemy pobrać czyli tag z xml
     * @return zwraca string z tą wartością
     */
    public String getPropertyValue(String propertyName) {

        NodeList nList = doc.getElementsByTagName(propertyName);
        String output = (nList.item(0) != null) ? nList.item(0).getTextContent() : null;
        return output;
    }

    /**
     * funkcja wczytujaca plansze gry
     * @param propertyName nazwa wartości którą chcemy pobrać czyli tag z xml
     * @param id numer planszy i jednocześnie poziomu
     * @return zwraca stringa
     */
    public String getPropertyValue(String propertyName, int id) {

        NodeList nList = doc.getElementsByTagName(propertyName);
        String output = null;
        //chodzenie po wszystkich planszach
        for(int x=0,size= nList.getLength(); x<size; x++) {
            if(Integer.valueOf(nList.item(x).getAttributes().getNamedItem("id").getNodeValue()) == id)
                output = nList.item(x).getTextContent();
        }
        return output;
    }

    /**
     * funkcja zwracająca z pliku konfuguracyjnego wartości typu int
     * @param propertyName nazwa wartości którą chcemy pobrać czyli tag z xml
     * @return wartość odpytywanego pola
     */
    public int getPropertyValueInt(String propertyName) {
        String returnValue = getPropertyValue(propertyName);
        return Integer.valueOf(returnValue);
    }
    public int getPropertyValueInt2(String propertyName, int id) {
        NodeList nList = doc.getElementsByTagName(propertyName);
        String output = null;
        //chodzenie po wszystkich planszach
        for(int x=0,size= nList.getLength(); x<size; x++) {
            if(Integer.valueOf(nList.item(x).getAttributes().getNamedItem("id").getNodeValue()) == id)
                output = nList.item(x).getTextContent();
        }
        return Integer.valueOf(output);
    }
}
