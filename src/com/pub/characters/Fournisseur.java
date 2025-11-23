package com.pub.characters;

/**
 * Represents a supplier who delivers drinks to the bar.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Fournisseur extends Human {
    private String entreprise;
    
    /**
     * Constructs a new supplier.
     * * @param prenom the first name of the supplier
     * @param surnom the nickname of the supplier
     * @param porteMonnaie the amount of money the supplier has
     * @param popularite the popularity level of the supplier
     * @param entreprise the name of the supplier's company
     */
    public Fournisseur(String prenom, String surnom, double porteMonnaie, int popularite, String entreprise) {
        // TRANSLATED: "Livraison !" -> "Delivery!"
        super(prenom, surnom, porteMonnaie, popularite, "Delivery!");
        this.entreprise = entreprise;
    }
    
    /**
     * Returns the name of the supplier's company.
     * * @return the company name
     */
    public String getEntreprise() {
        return entreprise;
    }

    /**
     * Sets the name of the supplier's company.
     * * @param entreprise the new company name
     */
    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
    
    /**
     * Delivers a certain quantity of a drink.
     * * @param boisson the name of the drink to deliver
     * @param quantite the quantity to deliver
     */
    public void livrerBoisson(String boisson, int quantite) {
        // TRANSLATED DIALOGUE
        parler("Here is your delivery: " + quantite + " units of " + boisson + ".");
    }
    
    @Override
    public void sePresenter() {
        // TRANSLATED DIALOGUE
        parler("Hello! I am " + getPrenom() + ", supplier for " + entreprise + ".");
        parler("I deliver the best drinks for your establishment!");
    }
}