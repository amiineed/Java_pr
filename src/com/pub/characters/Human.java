package com.pub.characters;

/**
 * Classe abstraite représentant un être humain dans le système du bar.
 * Cette classe sert de base pour tous les personnages (Client, Serveur, Serveuse, Barman, Patron, Fournisseur).
 * 
 * <p>Un Human possède des attributs de base (nom, argent, popularité) et des statistiques
 * de tournoi communes à tous les personnages pouvant participer à des jeux.</p>
 */
public abstract class Human {
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
    
    /**
     * Constructeur de base pour tous les humains.
     * 
     * @param prenom Le prénom du personnage
     * @param surnom Le surnom du personnage
     * @param porteMonnaie Le montant d'argent initial
     * @param popularite Le niveau de popularité initial
     */
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
    
    /**
     * Permet au personnage de parler (afficher un message).
     * 
     * @param message Le message à afficher
     */
    public void parler(String message) {
        System.out.println(prenom + ": " + message);
    }
    
    /**
     * Méthode abstraite pour se présenter.
     * Chaque sous-classe doit implémenter sa propre manière de se présenter.
     */
    public abstract void sePresenter();
    
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
