package com.pub.characters;

/**
 * Représente un fournisseur de boissons pour le bar.
 * 
 * <p>Le fournisseur livre des boissons au bar et reçoit des paiements
 * de la patronne pour ses livraisons. Il maintient son propre stock
 * et effectue des livraisons sur commande du barman.</p>
 */
public class Fournisseur extends Human {
    private String entreprise;
    
    /**
     * Construit un nouveau fournisseur.
     * 
     * @param prenom Le prénom du fournisseur
     * @param surnom Le surnom du fournisseur
     * @param porteMonnaie Le montant d'argent initial
     * @param popularite Le niveau de popularité initial
     * @param entreprise Le nom de l'entreprise du fournisseur
     */
    public Fournisseur(String prenom, String surnom, double porteMonnaie, int popularite, String entreprise) {
        super(prenom, surnom, porteMonnaie, popularite, "Livraison !");
        this.entreprise = entreprise;
    }
    
    /**
     * Récupère le nom de l'entreprise du fournisseur.
     * 
     * @return Le nom de l'entreprise
     */
    public String getEntreprise() {
        return entreprise;
    }
    
    /**
     * Définit le nom de l'entreprise du fournisseur.
     * 
     * @param entreprise Le nouveau nom de l'entreprise
     */
    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }
    
    /**
     * Effectue une livraison de boissons au bar.
     * 
     * @param boisson La boisson à livrer
     * @param quantite La quantité à livrer
     */
    public void livrerBoisson(String boisson, int quantite) {
        parler("Voici votre livraison : " + quantite + " unités de " + boisson + ".");
    }
    
    @Override
    public void sePresenter() {
        parler("Bonjour ! Je suis " + getPrenom() + ", fournisseur pour " + entreprise + ".");
        parler("Je livre les meilleures boissons pour votre établissement !");
    }
}
