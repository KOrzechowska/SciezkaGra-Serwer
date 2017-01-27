package com.mycompany.app;

import java.io.Serializable;

public class HighScore implements Serializable {
    final private Integer Score;
    final private String Nick;

    public HighScore(Integer score, String nick)

    {
        Score = score;
        Nick = nick;
    }

    public Integer getScore() {
        return Score;
    }


    public String getNick() {
        return Nick;
    }
}
