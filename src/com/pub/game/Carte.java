package com.pub.game;

public class Carte {
    private final Couleur couleur;
    private final ValeurCarte valeur;
    
    public Carte(Couleur c, ValeurCarte v) {
        this.couleur = c;
        this.valeur = v;
    }
    
    public Couleur getCouleur() {
        return couleur;
    }
    
    public ValeurCarte getValeur() {
        return valeur;
    }
    
    
    public int getPoints(Couleur atout) {
        boolean estAtout = (this.couleur == atout);
        return valeur.getPoints(estAtout);
    }
    

    public boolean bat(Carte autreCarte, Couleur atout) {
        if (this.couleur == atout && autreCarte.getCouleur() != atout) {
            return true;
        }
        
        if (this.couleur != atout && autreCarte.getCouleur() == atout) {
            return false;
        }
        
        if (this.couleur == atout && autreCarte.getCouleur() == atout) {
            return valeur.getOrdreForceAtout(valeur) > autreCarte.getValeur().getOrdreForceAtout(autreCarte.getValeur());
        }
        
        if (this.couleur == autreCarte.getCouleur()) {
            return valeur.getOrdreForceNormal() > autreCarte.getValeur().getOrdreForceNormal();
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return valeur.toString() + " de " + couleur;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Carte carte = (Carte) obj;
        return couleur == carte.couleur && valeur == carte.valeur;
    }
    
    @Override
    public int hashCode() {
        return couleur.hashCode() * 31 + valeur.hashCode();
    }
}
