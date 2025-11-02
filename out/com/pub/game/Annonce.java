package com.pub.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une annonce à la belote.
 * Une annonce peut être une suite (tierce, cinquante, cent), un carré, ou belote-rebelote.
 * 
 * Concept: Basic Class (Java1.2)
 */
public class Annonce {
    private final TypeAnnonce type;
    private final List<Carte> cartes;
    
    /**
     * Constructeur d'une annonce.
     * 
     * @param type Le type d'annonce
     * @param cartes Les cartes constituant l'annonce
     */
    public Annonce(TypeAnnonce type, List<Carte> cartes) {
        this.type = type;
        this.cartes = new ArrayList<>(cartes);
    }
    
    /**
     * Retourne le type de l'annonce.
     * 
     * @return Le type d'annonce
     */
    public TypeAnnonce getType() {
        return type;
    }
    
    /**
     * Retourne les cartes de l'annonce.
     * 
     * @return La liste des cartes
     */
    public List<Carte> getCartes() {
        return cartes;
    }
    
    /**
     * Retourne les points de l'annonce.
     * 
     * @return Les points de l'annonce
     */
    public int getPoints() {
        return type.getPoints();
    }
    
    @Override
    public String toString() {
        return type.toString() + " : " + cartes;
    }
}
