package com.arman.joker;

import java.util.Random;

public class Joks {

    private final String[] jokes = {
            "Two bytes meet.  The first byte asks, “Are you ill?”\n" +
                    "The second byte replies, “No, just feeling a bit off.”",
            "How many programmers does it take to change a light bulb?\n" +
                    "None – It’s a hardware problem",
            "There are only 10 kinds of people in this world: those who know binary and those who don’t.",
            "Programming is 10% science, 20% ingenuity, and 70% getting the ingenuity to work with the science.",
            "Debugging: Removing the needles from the haystack."
    };
    public String getJoke(){
        Random rand = new Random();
        int value = rand.nextInt(jokes.length - 1);
        return jokes[value];
    }
}