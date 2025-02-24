package com.brunoferre.gestioninventario.logica;

public class GenerateNumber {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String TicketNumber() {
        return getRandomNumber(1000, 10000) + "-"
                + getRandomNumber(1000, 10000) + "-"
                + getRandomNumber(1000, 10000) + "-"
                + getRandomNumber(1000, 10000);
    }
}
