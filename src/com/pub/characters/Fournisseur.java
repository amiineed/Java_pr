package com.pub.characters;


public class Fournisseur extends Human {
    private String entreprise;
    

    public Fournisseur(String prenom, String surnom, double porteMonnaie, int popularite, String entreprise) {
        super(prenom, surnom, porteMonnaie, popularite, "Livraison !");
        this.entreprise = entreprise;
    }
    

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
    

    public void livrerBoisson(String boisson, int quantite) {
        parler("Voici votre livraison : " + quantite + " unités de " + boisson + ".");
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour ! Je suis " + getPrenom() + ", fournisseur pour " + entreprise + ".");
        parler("Je livre les meilleures boissons pour votre établissement !");
    }
}
