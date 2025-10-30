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

    public String getNom() { return nom; }
    public double getPrixAchat() { return prixAchat; }
    public double getPrixVente() { return prixVente; }

    @Override
    public String toString() {
        return nom + " (" + prixVente + "€)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boisson)) return false;
        Boisson b = (Boisson) o;
        return nom.equalsIgnoreCase(b.nom);
    }

    @Override
    public int hashCode() {
        return nom.toLowerCase().hashCode();
    }
}
