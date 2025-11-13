package com.pub.game;

import com.pub.characters.Human;

/**
 * Représente une équipe de deux joueurs participant à un tournoi.
 * 
 * <p>Une équipe possède un nom et est composée de deux joueurs (Human).
 * Elle accumule des points selon les résultats de ses matchs (3 points par victoire)
 * et garde un historique de ses performances (matchs joués, gagnés, perdus).</p>
 */
public class Equipe {
    private String nom;
    private Human joueur1;
    private Human joueur2;
    private int points;
    private int matchsJoues;
    private int matchsGagnes;
    private int matchsPerdus;
    
    /**
     * Construit une nouvelle équipe avec deux joueurs.
     * 
     * @param nom Le nom de l'équipe
     * @param joueur1 Le premier joueur de l'équipe
     * @param joueur2 Le deuxième joueur de l'équipe
     */
    public Equipe(String nom, Human joueur1, Human joueur2) {
        this.nom = nom;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.points = 0;
        this.matchsJoues = 0;
        this.matchsGagnes = 0;
        this.matchsPerdus = 0;
    }
    
    /**
     * Récupère le nom de l'équipe.
     * 
     * @return Le nom de l'équipe
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Récupère le premier joueur de l'équipe.
     * 
     * @return Le premier joueur
     */
    public Human getJoueur1() {
        return joueur1;
    }
    
    /**
     * Récupère le deuxième joueur de l'équipe.
     * 
     * @return Le deuxième joueur
     */
    public Human getJoueur2() {
        return joueur2;
    }
    
    /**
     * Récupère le nombre total de points de l'équipe.
     * 
     * @return Le nombre de points
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Récupère le nombre de matchs joués par l'équipe.
     * 
     * @return Le nombre de matchs joués
     */
    public int getMatchsJoues() {
        return matchsJoues;
    }
    
    /**
     * Récupère le nombre de matchs gagnés par l'équipe.
     * 
     * @return Le nombre de matchs gagnés
     */
    public int getMatchsGagnes() {
        return matchsGagnes;
    }
    
    /**
     * Récupère le nombre de matchs perdus par l'équipe.
     * 
     * @return Le nombre de matchs perdus
     */
    public int getMatchsPerdus() {
        return matchsPerdus;
    }
    
    /**
     * Ajoute des points au total de l'équipe.
     * 
     * @param points Le nombre de points à ajouter
     */
    public void ajouterPoints(int points) {
        this.points += points;
    }
    
    /**
     * Enregistre le résultat d'un match pour cette équipe.
     * Incrémente les compteurs appropriés et ajoute 3 points en cas de victoire.
     * 
     * @param gagne true si l'équipe a gagné le match, false sinon
     */
    public void enregistrerMatch(boolean gagne) {
        this.matchsJoues++;
        if (gagne) {
            this.matchsGagnes++;
            this.points += 3; // 3 points pour une victoire
        } else {
            this.matchsPerdus++;
        }
    }
    
    @Override
    public String toString() {
        return nom + " (" + joueur1.getPrenom() + " & " + joueur2.getPrenom() + ") - " + points + " pts";
    }
}
