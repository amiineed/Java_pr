package com.pub.characters;

import com.pub.exceptions.BarException;

public class Patron extends Human {
    
    public Patron(String prenom, String surnom, double porteMonnaie) throws BarException {
        super(prenom, surnom, porteMonnaie, 100);
        if (porteMonnaie < 500) {
            throw new BarException("Le patron doit avoir au moins 500 euros pour démarrer un bar!");
        }
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', le propriétaire de cet établissement.");
    }
}
