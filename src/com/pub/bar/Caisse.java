package com.pub.bar;


public class Caisse {
    private double montantTotal;
   
    public Caisse(double montantInitial) {
        this.montantTotal = montantInitial;
    }
    

    public double getMontantTotal() {
        return montantTotal;
    }
    

    public void ajouterMontant(double montant) {
        if (montant > 0) {
            this.montantTotal += montant;
        }
    }
    

    public boolean retirerMontant(double montant) {
        if (montant > 0 && montant <= this.montantTotal) {
            this.montantTotal -= montant;
            return true;
        }
        return false;
    }

    public void setMontantTotal(double montant) {
        this.montantTotal = montant;
    }
    
    @Override
    public String toString() {
        return "Caisse: " + montantTotal + " euros";
    }
}
