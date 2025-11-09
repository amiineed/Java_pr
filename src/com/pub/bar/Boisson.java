package com.pub.bar;

public class Boisson {
    private String nom;
    private double prixAchat;
    private double prixVente;
    
    public Boisson(String nom, double prixAchat, double prixVente) {
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public double getPrixAchat() {
        return prixAchat;
    }
    
    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }
    
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
