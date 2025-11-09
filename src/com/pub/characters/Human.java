package com.pub.characters;

public class Human {
    private static int humanCount = 0;
    
    private String prenom;
    private String surnom;
    private double porteMonnaie;
    private int popularite;
    
    // Statistiques de tournoi (communes à tous les humains)
    protected int matchsTournoiJoues;
    protected int matchsTournoiGagnes;
    protected int matchsTournoiPerdus;
    protected int pointsTournoi;
    
    public Human(String prenom, String surnom, double porteMonnaie, int popularite) {
        this.prenom = prenom;
        this.surnom = surnom;
        this.porteMonnaie = porteMonnaie;
        this.popularite = popularite;
        this.matchsTournoiJoues = 0;
        this.matchsTournoiGagnes = 0;
        this.matchsTournoiPerdus = 0;
        this.pointsTournoi = 0;
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
    
    // Méthodes pour les statistiques de tournoi
    public int getMatchsTournoiJoues() {
        return matchsTournoiJoues;
    }
    
    public int getMatchsTournoiGagnes() {
        return matchsTournoiGagnes;
    }
    
    public int getMatchsTournoiPerdus() {
        return matchsTournoiPerdus;
    }
    
    public int getPointsTournoi() {
        return pointsTournoi;
    }
    
    /**
     * Enregistre le résultat d'un match de tournoi pour ce joueur.
     * Met à jour les statistiques individuelles (matchs joués, victoires, défaites, points).
     * 
     * @param victoire true si le joueur a gagné le match, false sinon
     */
    public void enregistrerMatchTournoi(boolean victoire) {
        this.matchsTournoiJoues++;
        if (victoire) {
            this.matchsTournoiGagnes++;
            this.pointsTournoi += 3; // 3 points pour une victoire
        } else {
            this.matchsTournoiPerdus++;
        }
    }
}
