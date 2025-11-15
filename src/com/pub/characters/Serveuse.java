package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.game.JoueurBelote;

public class Serveuse extends Human implements JoueurBelote {
    private int niveauCharme;
    
    public Serveuse(String prenom, String surnom, double porteMonnaie, int niveauCharme) {
        super(prenom, surnom, porteMonnaie, 40, "Et voilà !");
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
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', serveuse avec un charme de niveau " + niveauCharme + "!");
    }
}
