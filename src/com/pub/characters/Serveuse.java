package com.pub.characters;

public class Serveuse extends Human {

    private int charme;

    public Serveuse(String prenom, String surnom, double argent, int charme) {
        super(prenom, surnom, argent, 10, "Et voilà pour vous!");
        this.charme = charme;
    }

    public int getCharme() {
        return charme;
    }

    @Override
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + ", votre serveuse. Mon charme est de " + charme + "!");
    }
}
