package com.pub.game;

// Concept: Enum (Java1.3)
public enum CouleurCarte {
    PIQUE("Pique"),
    CARREAU("Carreau"),
    TREFLE("Trèfle"),
    COEUR("Coeur");

    private final String nom;

    CouleurCarte(String nom) {
        this.nom = nom;
    }

    // Concept: toString override (Java1.2)
    @Override
    public String toString() {
        return nom;
    }
}
