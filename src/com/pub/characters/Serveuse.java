package com.pub.characters;

import com.pub.game.JoueurBelote;

public class Serveuse extends Human implements JoueurBelote {
    private int niveauCharme;
    
    public Serveuse(String prenom, String surnom, double porteMonnaie, int niveauCharme) {
        super(prenom, surnom, porteMonnaie, 40);
        this.niveauCharme = niveauCharme;
    }
    
    public int getNiveauCharme() {
        return niveauCharme;
    }
    
    public void setNiveauCharme(int niveauCharme) {
        this.niveauCharme = niveauCharme;
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', serveuse avec un charme de niveau " + niveauCharme + "!");
    }
}
