package com.pub.bar;

/**
 * Représente une boisson disponible dans le bar.
 * 
 * <p>Une boisson possède un nom, un prix d'achat (coût pour le bar)
 * et un prix de vente (ce que paie le client). La différence entre
 * les deux représente la marge bénéficiaire du bar.</p>
 */
public class Boisson {
    private String nom;
    private double prixAchat;
    private double prixVente;
    
    /**
     * Construit une nouvelle boisson avec ses prix.
     * 
     * @param nom Le nom de la boisson
     * @param prixAchat Le prix d'achat (coût pour le bar)
     * @param prixVente Le prix de vente (prix payé par le client)
     */
    public Boisson(String nom, double prixAchat, double prixVente) {
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }
    
    /**
     * Récupère le nom de la boisson.
     * 
     * @return Le nom de la boisson
     */
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Récupère le prix d'achat de la boisson (coût pour le bar).
     * 
     * @return Le prix d'achat en euros
     */
    public double getPrixAchat() {
        return prixAchat;
    }
    
    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }
    
    /**
     * Récupère le prix de vente de la boisson (prix payé par le client).
     * 
     * @return Le prix de vente en euros
     */
    public double getPrixVente() {
        return prixVente;
    }
    
    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }
    
    @Override
    public String toString() {
        return nom + " (" + prixVente + " euros)";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Boisson boisson = (Boisson) obj;
        return nom.equals(boisson.nom);
    }
    
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
