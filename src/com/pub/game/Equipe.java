package com.pub.game;

import com.pub.characters.Client;

public class Equipe {
    private String nom;
    private Client joueur1;
    private Client joueur2;
    private int points;
    private int matchsJoues;
    private int matchsGagnes;
    private int matchsPerdus;
    
    public Equipe(String nom, Client joueur1, Client joueur2) {
        this.nom = nom;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.points = 0;
        this.matchsJoues = 0;
        this.matchsGagnes = 0;
        this.matchsPerdus = 0;
    }
    
    public String getNom() {
        return nom;
    }
    
    public Client getJoueur1() {
        return joueur1;
    }
    
    public Client getJoueur2() {
        return joueur2;
    }
    
    public int getPoints() {
        return points;
    }
    
    public int getMatchsJoues() {
        return matchsJoues;
    }
    
    public int getMatchsGagnes() {
        return matchsGagnes;
    }
    
    public int getMatchsPerdus() {
        return matchsPerdus;
    }
    
    public void ajouterPoints(int points) {
        this.points += points;
    }
    
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
