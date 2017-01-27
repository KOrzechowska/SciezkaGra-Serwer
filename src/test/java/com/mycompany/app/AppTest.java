package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void TestApp(){
        SciezkaConfig sciezkaConfig = new SciezkaConfig();
        assertEquals("4", sciezkaConfig.dajStringZPlanszy("lifes"));

    }

}
