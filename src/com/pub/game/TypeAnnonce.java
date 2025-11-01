package com.pub.game;

/**
 * Énumération des types d'annonces possibles à la belote.
 * 
 * Concept: Enum (Java1.3)
 */
public enum TypeAnnonce {
    /**
     * Tierce: 3 cartes consécutives de la même couleur (20 points)
     */
    TIERCE("Tierce", 20),
    
    /**
     * Cinquante: 4 cartes consécutives de la même couleur (50 points)
     */
    CINQUANTE("Cinquante", 50),
    
    /**
     * Cent: 5 cartes consécutives de la même couleur (100 points)
     */
    CENT("Cent", 100),
    
    /**
     * Carré de 9: 4 cartes identiques de valeur 9 (150 points)
     */
    CARRE_NEUF("Carré de 9", 150),
    
    /**
     * Carré de Valets: 4 cartes identiques de valeur Valet (200 points)
     */
    CARRE_VALET("Carré de Valet", 200),
    
    /**
     * Carré standard: 4 cartes identiques (As, 10, Roi, Dame) (100 points)
     */
    CARRE_STANDARD("Carré", 100),
    
    /**
     * Belote-Rebelote: Roi et Dame d'atout (20 points)
     */
    BELOTE_REBELOTE("Belote-Rebelote", 20);
    
    private final String nom;
    private final int points;
    
    /**
     * Constructeur de l'énumération.
     * 
     * @param nom Le nom de l'annonce
     * @param points Les points associés à l'annonce
     */
    TypeAnnonce(String nom, int points) {
        this.nom = nom;
        this.points = points;
    }
    
    /**
     * Retourne le nom de l'annonce.
     * 
     * @return Le nom de l'annonce
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Retourne les points de l'annonce.
     * 
     * @return Les points de l'annonce
     */
    public int getPoints() {
        return points;
    }
    
    @Override
    public String toString() {
        return nom + " (" + points + " points)";
    }
}
