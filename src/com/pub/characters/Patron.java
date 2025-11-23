package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.bar.Caisse;
import com.pub.exceptions.BarException;
import com.pub.exceptions.NotEnoughMoneyException;
import com.pub.exceptions.OutOfStockException;


public class Patron extends Human {
    
    public Patron(String prenom, String surnom, double porteMonnaie) throws BarException {
        // "Bonjour." -> "Hello."
        super(prenom, surnom, porteMonnaie, 100, "Hello.");
        if (porteMonnaie < 500) {
            // Exception message translated
            throw new BarException("The owner must have at least 500 euros to start a bar!");
        }
    }
    

    public void payerFournisseur(Fournisseur fournisseur, double montant) {
        if (fournisseur == null || montant <= 0) {
            // "Paiement invalide!" -> "Invalid payment!"
            parler("Invalid payment!");
            return;
        }
        
        if (payer(montant)) {
            fournisseur.recevoirArgent(montant);
            // "J'ai payé..." -> "I paid..."
            parler("I paid " + montant + " euros to " + fournisseur.getPrenom() + " (" + fournisseur.getEntreprise() + ").");
            // Supplier response translated
            fournisseur.parler("Thanks for the payment! See you for the next delivery.");
        } else {
            // "Je n'ai pas assez d'argent..." -> "I don't have enough money..."
            parler("I don't have enough money to pay " + fournisseur.getPrenom() + "!");
        }
    }
 
    public boolean recupererArgentCaisse(Caisse caisse, double montant) {
        if (caisse == null || montant <= 0) {
            // "Opération invalide!" -> "Invalid operation!"
            parler("Invalid operation!");
            return false;
        }
        
        if (caisse.retirerMontant(montant)) {
            recevoirArgent(montant);
            // "J'ai récupéré..." -> "I collected..."
            parler("I collected " + montant + " euros from the register. My wallet: " + 
                   String.format("%.2f", getPorteMonnaie()) + " euros.");
            return true;
        } else {
            // "Pas assez d'argent..." -> "Not enough money..."
            parler("Not enough money in the register to withdraw " + montant + " euros!");
            return false;
        }
    }
    
    @Override
    public void offrirVerre(Human receveur, Boisson boisson, Barman barman) {
        if (receveur == null || boisson == null || barman == null) {
            // "Impossible d'offrir..." -> "Impossible to offer..."
            parler("Impossible to offer a drink at the moment.");
            return;
        }
        // "Je t'offre un..." -> "I'm treating you to a..."
        this.parler("I'm treating you to a " + boisson.getNom() + ", " + receveur.getPrenom() + "!");
        try {
            barman.servirBoisson(boisson);
            receveur.boire(boisson);
            // "Santé !" -> "Cheers!"
            parler("Cheers!");
        } catch (OutOfStockException e) {
            // "Oh, zut." -> "Oh, shoot."
            parler("Oh, shoot. " + e.getMessage());
        }
    }
    
    @Override
    public void sePresenter() {
        // Intro translated
        parler("Hello, I am " + getPrenom() + " '" + getSurnom() + "', the owner of this establishment.");
    }
}