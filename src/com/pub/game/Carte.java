package com.pub.game;



import java.util.Objects;

// Concept: Basic Class (Java1.2)
// Concept: Immutability for card properties (final)
public class Carte {
    private final CouleurCarte couleur;
    private final ValeurCarte valeur;

    public Carte(CouleurCarte couleur, ValeurCarte valeur) {
        // Concept: Use 'this' in constructor (Java1.2)
        this.couleur = couleur;
        this.valeur = valeur;
    }

    // Concept: Copy Constructor (Java1.2 - to prevent privacy leaks, though fields are final here)
    public Carte(Carte autreCarte) {
        this.couleur = autreCarte.couleur;
        this.valeur = autreCarte.valeur;
    }

    // Concept: Accessors (getters) (Java1.2)
    public CouleurCarte getCouleur() {
        return couleur;
    }

    public ValeurCarte getValeur() {
        return valeur;
    }

    public int getPoints(boolean isAtout) {
        return valeur.getPoints(isAtout);
    }

    // Basic comparison logic for Belote (can be expanded)
    public boolean estPlusForteQue(Carte autre, CouleurCarte atout) {
        if (this.couleur == autre.couleur) {
             boolean thisIsAtout = this.couleur == atout;
             if (thisIsAtout) {
                 return this.valeur.getOrdreForceAtout(this.valeur) > autre.valeur.getOrdreForceAtout(autre.valeur);
             } else {
                 return this.valeur.getOrdreForceNormal() > autre.valeur.getOrdreForceNormal();
             }
        } else {
            // Different suits
            if (this.couleur == atout) return true; // Current card is trump, other is not
            if (autre.couleur == atout) return false; // Other card is trump, current is not
            return false; // Neither is trump, and suits differ - first card played wins trick (handled in game logic)
        }
    }


    // Concept: toString override (Java1.2)
    @Override
    public String toString() {
        return valeur.toString() + " de " + couleur.toString();
    }

    // Concept: equals override (Java1.2)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return couleur == carte.couleur && valeur == carte.valeur;
    }

    // Concept: hashCode (must override if equals is overridden)
    @Override
    public int hashCode() {
        return Objects.hash(couleur, valeur);
    }
}
