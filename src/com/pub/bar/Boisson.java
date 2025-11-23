package com.pub.bar;

/**
 * Represents a drink available in the bar.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Boisson {
    private String nom;
    private double prixAchat;
    private double prixVente;
    
    /**
     * Constructs a new drink.
     * * @param nom the name of the drink
     * @param prixAchat the purchase price of the drink
     * @param prixVente the selling price of the drink
     */
    public Boisson(String nom, double prixAchat, double prixVente) {
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }
    
    /**
     * Returns the name of the drink.
     * * @return the name of the drink
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Sets the name of the drink.
     * * @param nom the new name of the drink
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Returns the purchase price of the drink.
     * * @return the purchase price
     */
    public double getPrixAchat() {
        return prixAchat;
    }
    
    /**
     * Sets the purchase price of the drink.
     * * @param prixAchat the new purchase price
     */
    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }
    
    /**
     * Returns the selling price of the drink.
     * * @return the selling price
     */
    public double getPrixVente() {
        return prixVente;
    }
    
    /**
     * Sets the selling price of the drink.
     * * @param prixVente the new selling price
     */
    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }
    
    @Override
    public String toString() {
        // "euros" works for both languages, so this line is safe.
        return nom + " (" + prixVente + " euros)";
    }
    
    /**
     * Compares this drink with another object.
     * * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Boisson boisson = (Boisson) obj;
        return nom.equals(boisson.nom);
    }
    
    /**
     * Returns the hash code of the drink.
     * * @return the hash code
     */
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}