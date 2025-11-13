package com.pub.characters;

import com.pub.exceptions.BarException;
import com.pub.bar.Caisse;

/**
 * Représente le patron/la patronne du bar.
 * 
 * <p>Le patron gère les finances du bar, peut retirer de l'argent de la caisse,
 * payer les fournisseurs et exclure des clients indésirables.</p>
 */
public class Patron extends Human {
    
    public Patron(String prenom, String surnom, double porteMonnaie) throws BarException {
        super(prenom, surnom, porteMonnaie, 100);
        if (porteMonnaie < 500) {
            throw new BarException("Le patron doit avoir au moins 500 euros pour démarrer un bar!");
        }
    }
    
    /**
     * Paie un fournisseur pour une livraison.
     * Transfère l'argent du portefeuille du patron vers celui du fournisseur.
     * 
     * @param fournisseur Le fournisseur à payer
     * @param montant Le montant à payer
     */
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
    
    /**
     * Récupère de l'argent de la caisse du bar.
     * Transfère l'argent de la caisse vers le portefeuille du patron.
     * 
     * @param caisse La caisse du bar
     * @param montant Le montant à récupérer
     * @return true si le retrait a réussi, false sinon
     */
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
    public void sePresenter() {
        parler("Bonjour, je suis " + getPrenom() + " '" + getSurnom() + "', le propriétaire de cet établissement.");
    }
}
