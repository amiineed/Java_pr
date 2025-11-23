package com.pub.bar;

/**
 * Represents the bar's cash register for managing financial transactions.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Caisse {
    private double montantTotal;
   
    /**
     * Constructs a new cash register with an initial amount.
     * * @param montantInitial the initial amount in the cash register
     */
    public Caisse(double montantInitial) {
        this.montantTotal = montantInitial;
    }
    
    /**
     * Returns the total amount in the cash register.
     * * @return the total amount
     */
    public double getMontantTotal() {
        return montantTotal;
    }
    
    /**
     * Adds an amount to the cash register.
     * * @param montant the amount to add (must be positive)
     */
    public void ajouterMontant(double montant) {
        if (montant > 0) {
            this.montantTotal += montant;
        }
    }
    
    /**
     * Withdraws an amount from the cash register.
     * * @param montant the amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    public boolean retirerMontant(double montant) {
        if (montant > 0 && montant <= this.montantTotal) {
            this.montantTotal -= montant;
            return true;
        }
        return false;
    }

    /**
     * Sets the total amount of the cash register.
     * * @param montant the new total amount
     */
    public void setMontantTotal(double montant) {
        this.montantTotal = montant;
    }
    
    @Override
    public String toString() {
        // TRANSLATED: "Caisse" -> "Cash Register"
        return "Cash Register: " + montantTotal + " euros";
    }
}