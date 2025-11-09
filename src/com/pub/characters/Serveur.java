package com.pub.characters;

import com.pub.game.JoueurBelote;

public class Serveur extends Human implements JoueurBelote {
    private int tailleBiceps;
    
    public Serveur(String prenom, String surnom, double porteMonnaie, int tailleBiceps) {
        super(prenom, surnom, porteMonnaie, 30);
        this.tailleBiceps = tailleBiceps;
    }
    
    public int getTailleBiceps() {
        return tailleBiceps;
    }
    
    public void setTailleBiceps(int tailleBiceps) {
        this.tailleBiceps = tailleBiceps;
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', serveur avec des biceps de " + tailleBiceps + "cm!");
    }
}
