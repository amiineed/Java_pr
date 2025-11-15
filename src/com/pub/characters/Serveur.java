package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.game.JoueurBelote;

public class Serveur extends Human implements JoueurBelote {
    private int tailleBiceps;
    
    public Serveur(String prenom, String surnom, double porteMonnaie, int tailleBiceps) {
        super(prenom, surnom, porteMonnaie, 30, "Et voilà !");
        this.tailleBiceps = tailleBiceps;
    }
    
    public int getTailleBiceps() {
        return tailleBiceps;
    }
    
    public void setTailleBiceps(int tailleBiceps) {
        this.tailleBiceps = tailleBiceps;
    }
    
    @Override
    public void boire(Boisson boisson) {
        if (boisson == null) {
            parler("Je n'ai rien à boire.");
            return;
        }
        
        if (!boisson.getNom().equalsIgnoreCase("Water")) {
            parler("Désolé, je ne bois que de l'eau pendant le service.");
        } else {
            super.boire(boisson);
        }
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', serveur avec des biceps de " + tailleBiceps + "cm!");
    }
}
