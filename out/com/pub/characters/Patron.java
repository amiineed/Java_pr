package com.pub.characters;

import com.pub.exceptions.BarException;

public class Patron extends Human {

    public Patron(String prenom, String surnom, double argent) throws BarException {
        super(prenom, surnom, argent, 100, "Bienvenue!");
    }

    @Override
    public void sePresenter() {
        parler("Je suis " + getPrenom() + ", le patron de cet établissement.");
    }
}
