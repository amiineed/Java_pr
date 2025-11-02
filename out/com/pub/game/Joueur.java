package com.pub.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un joueur de belote.
 * Cette classe gère la main du joueur et les actions de base.
 * 
 * Concept: Basic Class (Java1.2)
 * Concept: Encapsulation avec getters/setters
 */
public class Joueur {
    private final String nom;
    private List<Carte> main;
    private Equipe equipe;
    
    /**
     * Constructeur du joueur.
     * 
     * @param nom Le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.equipe = null;
    }
    
    /**
     * Retourne le nom du joueur.
     * 
     * @return Le nom du joueur
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Retourne la main du joueur.
     * 
     * @return La liste des cartes en main
     */
    public List<Carte> getMain() {
        return main;
    }
    
    /**
     * Retourne l'équipe du joueur.
     * 
     * @return L'équipe du joueur
     */
    public Equipe getEquipe() {
        return equipe;
    }
    
    /**
     * Définit l'équipe du joueur.
     * 
     * @param equipe L'équipe à assigner
     */
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
    
    /**
     * Ajoute une carte à la main du joueur.
     * 
     * @param carte La carte à ajouter
     */
    public void recevoirCarte(Carte carte) {
        this.main.add(carte);
    }
    
    /**
     * Joue une carte de la main du joueur.
     * 
     * @param carte La carte à jouer
     * @return true si la carte a été jouée, false sinon
     */
    public boolean jouerCarte(Carte carte) {
        return this.main.remove(carte);
    }
    
    /**
     * Vide la main du joueur.
     */
    public void viderMain() {
        this.main.clear();
    }
    
    /**
     * Vérifie si le joueur possède une carte de la couleur demandée.
     * 
     * @param couleur La couleur recherchée
     * @return true si le joueur a au moins une carte de cette couleur
     */
    public boolean possedeCouleur(CouleurCarte couleur) {
        for (Carte carte : main) {
            if (carte.getCouleur() == couleur) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Vérifie si le joueur possède un atout.
     * 
     * @param atout La couleur d'atout
     * @return true si le joueur a au moins un atout
     */
    public boolean possedeAtout(CouleurCarte atout) {
        return possedeCouleur(atout);
    }
    
    /**
     * Retourne la plus forte carte d'atout dans la main du joueur.
     * 
     * @param atout La couleur d'atout
     * @return La plus forte carte d'atout, ou null si le joueur n'a pas d'atout
     */
    public Carte getPlusFortAtout(CouleurCarte atout) {
        Carte plusForte = null;
        for (Carte carte : main) {
            if (carte.getCouleur() == atout) {
                if (plusForte == null || carte.estPlusForteQue(plusForte, atout)) {
                    plusForte = carte;
                }
            }
        }
        return plusForte;
    }
    
    /**
     * Affiche la main du joueur.
     */
    public void afficherMain() {
        System.out.println("Main de " + nom + ":");
        for (int i = 0; i < main.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + main.get(i));
        }
    }
    
    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom + '\'' +
                ", cartes=" + main.size() +
                '}';
    }
}
