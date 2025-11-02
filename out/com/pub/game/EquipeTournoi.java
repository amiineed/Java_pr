package com.pub.game;

import com.pub.characters.Client;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une équipe de tournoi composée de 2 clients.
 * Gère les inscriptions, les scores de tournoi et les statistiques.
 * 
 * Concept: Basic Class (Java1.2)
 */
public class EquipeTournoi {
    private final String nomEquipe;
    private final Client joueur1;
    private final Client joueur2;
    private int pointsTournoi; // Points de classement (3 pour victoire, 1 pour égalité, 0 pour défaite)
    private int partiesGagnees;
    private int partiesPerdues;
    private int manchesGagnees;
    private int manchesPerdues;
    private List<Match> historique;
    
    /**
     * Constructeur d'une équipe de tournoi.
     * 
     * @param nomEquipe Le nom de l'équipe
     * @param joueur1 Le premier joueur (Client)
     * @param joueur2 Le deuxième joueur (Client)
     */
    public EquipeTournoi(String nomEquipe, Client joueur1, Client joueur2) {
        this.nomEquipe = nomEquipe;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.pointsTournoi = 0;
        this.partiesGagnees = 0;
        this.partiesPerdues = 0;
        this.manchesGagnees = 0;
        this.manchesPerdues = 0;
        this.historique = new ArrayList<>();
    }
    
    /**
     * Retourne le nom de l'équipe.
     * 
     * @return Le nom de l'équipe
     */
    public String getNomEquipe() {
        return nomEquipe;
    }
    
    /**
     * Retourne le premier joueur.
     * 
     * @return Le premier joueur
     */
    public Client getJoueur1() {
        return joueur1;
    }
    
    /**
     * Retourne le deuxième joueur.
     * 
     * @return Le deuxième joueur
     */
    public Client getJoueur2() {
        return joueur2;
    }
    
    /**
     * Retourne les points de tournoi (classement).
     * 
     * @return Les points de tournoi
     */
    public int getPointsTournoi() {
        return pointsTournoi;
    }
    
    /**
     * Ajoute des points de tournoi.
     * 
     * @param points Les points à ajouter
     */
    public void ajouterPointsTournoi(int points) {
        this.pointsTournoi += points;
    }
    
    /**
     * Retourne le nombre de parties gagnées.
     * 
     * @return Le nombre de parties gagnées
     */
    public int getPartiesGagnees() {
        return partiesGagnees;
    }
    
    /**
     * Retourne le nombre de parties perdues.
     * 
     * @return Le nombre de parties perdues
     */
    public int getPartiesPerdues() {
        return partiesPerdues;
    }
    
    /**
     * Retourne le nombre de manches gagnées.
     * 
     * @return Le nombre de manches gagnées
     */
    public int getManchesGagnees() {
        return manchesGagnees;
    }
    
    /**
     * Retourne le nombre de manches perdues.
     * 
     * @return Le nombre de manches perdues
     */
    public int getManchesPerdues() {
        return manchesPerdues;
    }
    
    /**
     * Enregistre une victoire.
     */
    public void enregistrerVictoire() {
        this.partiesGagnees++;
        this.pointsTournoi += 3; // 3 points pour une victoire
    }
    
    /**
     * Enregistre une défaite.
     */
    public void enregistrerDefaite() {
        this.partiesPerdues++;
        // 0 points pour une défaite
    }
    
    /**
     * Enregistre une égalité.
     */
    public void enregistrerEgalite() {
        this.pointsTournoi += 1; // 1 point pour une égalité
    }
    
    /**
     * Ajoute des manches gagnées.
     * 
     * @param nb Le nombre de manches gagnées
     */
    public void ajouterManchesGagnees(int nb) {
        this.manchesGagnees += nb;
    }
    
    /**
     * Ajoute des manches perdues.
     * 
     * @param nb Le nombre de manches perdues
     */
    public void ajouterManchesPerdues(int nb) {
        this.manchesPerdues += nb;
    }
    
    /**
     * Retourne l'historique des matchs.
     * 
     * @return La liste des matchs
     */
    public List<Match> getHistorique() {
        return historique;
    }
    
    /**
     * Ajoute un match à l'historique.
     * 
     * @param match Le match à ajouter
     */
    public void ajouterMatch(Match match) {
        this.historique.add(match);
    }
    
    /**
     * Vérifie si un client fait partie de cette équipe.
     * 
     * @param client Le client à vérifier
     * @return true si le client est dans l'équipe
     */
    public boolean contientJoueur(Client client) {
        return joueur1.equals(client) || joueur2.equals(client);
    }
    
    @Override
    public String toString() {
        return nomEquipe + " (" + joueur1.getPrenom() + " & " + joueur2.getPrenom() + 
               ") - " + pointsTournoi + " pts";
    }
    
    /**
     * Retourne une représentation détaillée de l'équipe.
     * 
     * @return Les statistiques détaillées
     */
    public String toStringDetaille() {
        return String.format("%s: %d pts (V:%d D:%d | Manches: %d-%d)", 
            nomEquipe, pointsTournoi, partiesGagnees, partiesPerdues,
            manchesGagnees, manchesPerdues);
    }
}
