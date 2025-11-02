package com.pub.bar;

public class Caisse {
    private double montantTotal;

    public Caisse(double montantInitial) {
        this.montantTotal = Math.max(0, montantInitial);
    }

    public void ajouter(double montant) {
        if (montant > 0) {
            montantTotal += montant;
        }
    }

    public void retirer(double montant) {
        if (montant > 0 && montant <= montantTotal) {
            montantTotal -= montant;
        }
    }

    public double getMontantTotal() {
        return montantTotal;
    }
}