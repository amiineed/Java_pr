package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.bar.Caisse;
import com.pub.exceptions.BarException;
import com.pub.exceptions.NotEnoughMoneyException;
import com.pub.exceptions.OutOfStockException;


public class Patron extends Human {
    
    public Patron(String prenom, String surnom, double porteMonnaie) throws BarException {
        super(prenom, surnom, porteMonnaie, 100, "Bonjour.");
        if (porteMonnaie < 500) {
            throw new BarException("Le patron doit avoir au moins 500 euros pour démarrer un bar!");
        }
    }
    

    public void payerFournisseur(Fournisseur fournisseur, double montant) {
        if (fournisseur == null || montant <= 0) {
            parler("Paiement invalide!");
            return;
        }
        
        if (payer(montant)) {
            fournisseur.recevoirArgent(montant);
            parler("J'ai payé " + montant + " euros à " + fournisseur.getPrenom() + " (" + fournisseur.getEntreprise() + ").");
            fournisseur.parler("Merci pour le paiement ! À bientôt pour la prochaine livraison.");
        } else {
            parler("Je n'ai pas assez d'argent pour payer " + fournisseur.getPrenom() + "!");
        }
    }
 
    public boolean recupererArgentCaisse(Caisse caisse, double montant) {
        if (caisse == null || montant <= 0) {
            parler("Opération invalide!");
            return false;
        }
        
        if (caisse.retirerMontant(montant)) {
            recevoirArgent(montant);
            parler("J'ai récupéré " + montant + " euros de la caisse. Mon portefeuille: " + 
                   String.format("%.2f", getPorteMonnaie()) + " euros.");
            return true;
        } else {
            parler("Pas assez d'argent dans la caisse pour retirer " + montant + " euros!");
            return false;
        }
    }
    
    @Override
    public void offrirVerre(Human receveur, Boisson boisson, Barman barman) {
        if (receveur == null || boisson == null || barman == null) {
            parler("Impossible d'offrir un verre pour le moment.");
            return;
        }
        this.parler("Je t'offre un " + boisson.getNom() + ", " + receveur.getPrenom() + " !");
        try {
            barman.servirBoisson(boisson);
            receveur.boire(boisson);
            parler("Santé !");
        } catch (OutOfStockException e) {
            parler("Oh, zut. " + e.getMessage());
        }
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', le propriétaire de cet établissement.");
    }
}
