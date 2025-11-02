package com.pub.characters;

public class Human {
    private static int humanCount = 0;
    
    private String prenom;
    private String surnom;
    private double porteMonnaie;
    private int popularite;
    
    public Human(String prenom, String surnom, double porteMonnaie, int popularite) {
        this.prenom = prenom;
        this.surnom = surnom;
        this.porteMonnaie = porteMonnaie;
        this.popularite = popularite;
        humanCount++;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getSurnom() {
        return surnom;
    }
    
    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }
    
    public double getPorteMonnaie() {
        return porteMonnaie;
    }
    
    public void setPorteMonnaie(double porteMonnaie) {
        this.porteMonnaie = porteMonnaie;
    }
    
    public int getPopularite() {
        return popularite;
    }
    
    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }
    
    public static int getHumanCount() {
        return humanCount;
    }
    
    public void parler(String message) {
        System.out.println(prenom + ": " + message);
    }
    
    public void sePresenter() {
        parler("Hi! I am " + prenom + " called '" + surnom + "'.");
    }
    
    public boolean payer(double montant) {
        if (porteMonnaie >= montant) {
            porteMonnaie -= montant;
            return true;
        }
        return false;
    }
    
    public void recevoirArgent(double montant) {
        if (montant > 0) {
            porteMonnaie += montant;
        }
    }
}
