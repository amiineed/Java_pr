package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.game.JoueurBelote;

public class Serveur extends Human implements JoueurBelote {
    private int tailleBiceps;
    
    public Serveur(String prenom, String surnom, double porteMonnaie, int tailleBiceps) {
        // "Et voilà !" -> "Here you go!"
        super(prenom, surnom, porteMonnaie, 30, "Here you go!");
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
            // "Je n'ai rien à boire." -> "I have nothing to drink."
            parler("I have nothing to drink.");
            return;
        }
        
        if (!boisson.getNom().equalsIgnoreCase("Water")) {
            // "Désolé..." -> "Sorry..."
            parler("Sorry, I only drink water during service.");
        } else {
            super.boire(boisson);
        }
    }
    
    @Override
    public void sePresenter() {
        // Intro translated
        parler("Hello, I am " + getPrenom() + " '" + getSurnom() + "', a server with " + tailleBiceps + "cm biceps!");
    }
}