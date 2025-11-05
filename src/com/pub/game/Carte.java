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
    
    /**
     * Retourne les points de cette carte en fonction de si c'est un atout ou non
     * @param atout La couleur d'atout actuelle
     * @return Le nombre de points de la carte
     */
    public int getPoints(Couleur atout) {
        boolean estAtout = (this.couleur == atout);
        return valeur.getPoints(estAtout);
    }
    
    /**
     * Compare la force de cette carte avec une autre dans un contexte de pli
     * @param autreCarte La carte à comparer
     * @param atout La couleur d'atout
     * @return true si cette carte bat l'autre carte
     */
    public boolean bat(Carte autreCarte, Couleur atout) {
        // Si cette carte est atout et l'autre non, cette carte gagne
        if (this.couleur == atout && autreCarte.getCouleur() != atout) {
            return true;
        }
        
        // Si l'autre carte est atout et cette carte non, cette carte perd
        if (this.couleur != atout && autreCarte.getCouleur() == atout) {
            return false;
        }
        
        // Si les deux sont atout, comparer selon l'ordre atout
        if (this.couleur == atout && autreCarte.getCouleur() == atout) {
            return valeur.getOrdreForceAtout(valeur) > autreCarte.getValeur().getOrdreForceAtout(autreCarte.getValeur());
        }
        
        // Si les deux sont de la même couleur (mais pas atout), comparer selon l'ordre normal
        if (this.couleur == autreCarte.getCouleur()) {
            return valeur.getOrdreForceNormal() > autreCarte.getValeur().getOrdreForceNormal();
        }
        
        // Si les couleurs sont différentes et aucune n'est atout, aucune ne peut battre l'autre
        // (dans la belote, on ne peut pas couper si on a de la couleur demandée)
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
