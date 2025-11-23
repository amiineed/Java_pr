package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.game.JoueurBelote;

public class Serveuse extends Human implements JoueurBelote {
    private int niveauCharme;
    
    public Serveuse(String prenom, String surnom, double porteMonnaie, int niveauCharme) {
        // "Et voilà !" -> "Here you go!"
        super(prenom, surnom, porteMonnaie, 40, "Here you go!");
        this.niveauCharme = niveauCharme;
    }
    
    public int getNiveauCharme() {
        return niveauCharme;
    }
    
    public void setNiveauCharme(int niveauCharme) {
        this.niveauCharme = niveauCharme;
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
        parler("Hello, I am " + getPrenom() + " '" + getSurnom() + "', a waitress with a charm level of " + niveauCharme + "!");
    }
}