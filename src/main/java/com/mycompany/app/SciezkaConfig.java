package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mrgrinch on 22.01.17.
 */
public class SciezkaConfig {
    public static String dajStringaZPlanszy(String plansza, String levelNumber) {
        PropertiesReader propertiesReader = new PropertiesReader("properties.xml");
        String planszaStream = propertiesReader.getPropertyValue("plansza", Integer.parseInt(levelNumber));
        return planszaStream;
    }

    public static int dajIntZProperties(String tag, String difficultyLvl){
        PropertiesReader propertiesReader = new PropertiesReader("properties.xml");
        int predkosc = propertiesReader.getPropertyValueInt2(tag, Integer.parseInt(difficultyLvl));
        return predkosc;
    }
    public static String dajStringZPlanszy(String zycia) {
        PropertiesReader propertiesReader = new PropertiesReader("properties.xml");
        int nrOfLifes = propertiesReader.getPropertyValueInt(zycia);
        return String.valueOf(nrOfLifes);
    }

    public static void saveScore(String name, String score) {
        HighScores highScores = new HighScores();
        highScores.addToHighScoreList(name , Integer.parseInt(score));
        try {
            highScores.saveHighScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HighScore> getHighScores() {
        HighScores highScores = new HighScores();
        try {
            highScores.loadHighScores();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       return highScores.getHighScoreList();

    }
}
