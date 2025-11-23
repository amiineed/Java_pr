package com.pub.bar;

/**
 * Represents an alcoholic beverage with an alcohol degree.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class BoissonAlcoolisee extends Boisson {
    private int degreAlcool;
    
    /**
     * Constructs a new alcoholic beverage.
     * * @param nom the name of the drink
     * @param prixAchat the purchase price of the drink
     * @param prixVente the selling price of the drink
     * @param degreAlcool the alcohol degree of the drink
     */
    public BoissonAlcoolisee(String nom, double prixAchat, double prixVente, int degreAlcool) {
        super(nom, prixAchat, prixVente);
        this.degreAlcool = degreAlcool;
    }
    
    /**
     * Returns the alcohol degree of the drink.
     * * @return the alcohol degree
     */
    public int getDegreAlcool() {
        return degreAlcool;
    }
    
    /**
     * Sets the alcohol degree of the drink.
     * * @param degreAlcool the new alcohol degree
     */
    public void setDegreAlcool(int degreAlcool) {
        this.degreAlcool = degreAlcool;
    }
    
    @Override
    public String toString() {
        // "euros" and "°" are standard, so this output is safe for English speakers.
        return getNom() + " (" + getPrixVente() + " euros, " + degreAlcool + "°)";
    }
}