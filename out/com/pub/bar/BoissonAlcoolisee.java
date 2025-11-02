package com.pub.bar;

public class BoissonAlcoolisee extends Boisson {
    private int degreAlcool;

    public BoissonAlcoolisee(String nom, double prixAchat, double prixVente, int degreAlcool) {
        super(nom, prixAchat, prixVente);
        this.degreAlcool = degreAlcool;
    }

    public int getDegreAlcool() { return degreAlcool; }

    @Override
    public String toString() {
        return super.toString() + " (" + degreAlcool + "°)";
    }
}