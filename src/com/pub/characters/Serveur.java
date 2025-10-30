package com.pub.characters;

public class Serveur extends Human {

    private int biceps;

    public Serveur(String prenom, String surnom, double argent, int biceps) {
        super(prenom, surnom, argent, 10, "Voici votre commande");
        this.biceps = biceps;
    }

    public int getBiceps() {
        return biceps;
    }

    @Override
    public void sePresenter() {
        parler("Je suis " + getPrenom() + ", votre serveur. Admirez mes biceps de " + biceps + " cm!");
    }
}
