package com.pub.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une équipe de belote (2 joueurs).
 * 
 * Concept: Basic Class (Java1.2)
 * Concept: Gestion de collections (Java1.3)
 */
public class Equipe {
    private final String nom;
    private List<Joueur> joueurs;
    private int scorePartie; // Score pour la partie en cours
    private int scoreTotal;  // Score total du jeu
    private List<Annonce> annonces; // Annonces de l'équipe
    
    /**
     * Constructeur de l'équipe.
     * 
     * @param nom Le nom de l'équipe
     */
    public Equipe(String nom) {
        this.nom = nom;
        this.joueurs = new ArrayList<>();
        this.scorePartie = 0;
        this.scoreTotal = 0;
        this.annonces = new ArrayList<>();
    }
    
    /**
     * Ajoute un joueur à l'équipe.
     * 
     * @param joueur Le joueur à ajouter
     */
    public void ajouterJoueur(Joueur joueur) {
        if (joueurs.size() < 2) {
            joueurs.add(joueur);
            joueur.setEquipe(this);
        }
    }
    
    /**
     * Retourne le nom de l'équipe.
     * 
     * @return Le nom de l'équipe
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Retourne la liste des joueurs.
     * 
     * @return La liste des joueurs
     */
    public List<Joueur> getJoueurs() {
        return joueurs;
    }
    
    /**
     * Retourne le score de la partie en cours.
     * 
     * @return Le score de la partie
     */
    public int getScorePartie() {
        return scorePartie;
    }
    
    /**
     * Définit le score de la partie en cours.
     * 
     * @param scorePartie Le nouveau score de la partie
     */
    public void setScorePartie(int scorePartie) {
        this.scorePartie = scorePartie;
    }
    
    /**
     * Ajoute des points au score de la partie.
     * 
     * @param points Les points à ajouter
     */
    public void ajouterPointsPartie(int points) {
        this.scorePartie += points;
    }
    
    /**
     * Retourne le score total du jeu.
     * 
     * @return Le score total
     */
    public int getScoreTotal() {
        return scoreTotal;
    }
    
    /**
     * Définit le score total du jeu.
     * 
     * @param scoreTotal Le nouveau score total
     */
    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
    
    /**
     * Ajoute des points au score total.
     * 
     * @param points Les points à ajouter
     */
    public void ajouterPointsTotal(int points) {
        this.scoreTotal += points;
    }
    
    /**
     * Retourne les annonces de l'équipe.
     * 
     * @return La liste des annonces
     */
    public List<Annonce> getAnnonces() {
        return annonces;
    }
    
    /**
     * Ajoute une annonce à l'équipe.
     * 
     * @param annonce L'annonce à ajouter
     */
    public void ajouterAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
    }
    
    /**
     * Vide les annonces de l'équipe.
     */
    public void viderAnnonces() {
        this.annonces.clear();
    }
    
    /**
     * Réinitialise le score de la partie.
     */
    public void reinitialiserScorePartie() {
        this.scorePartie = 0;
        this.viderAnnonces();
    }
    
    /**
     * Calcule le total des points des annonces.
     * 
     * @return Le total des points d'annonces
     */
    public int getTotalPointsAnnonces() {
        int total = 0;
        for (Annonce annonce : annonces) {
            total += annonce.getPoints();
        }
        return total;
    }
    
    @Override
    public String toString() {
        return "Equipe{" +
                "nom='" + nom + '\'' +
                ", scoreTotal=" + scoreTotal +
                '}';
    }
}
