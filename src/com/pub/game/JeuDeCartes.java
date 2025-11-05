package com.pub.game;

import java.util.*;

public class JeuDeCartes {
    private List<Carte> cartes;
    
    public JeuDeCartes() {
        this.cartes = new ArrayList<>();
        initialiser();
    }
    
    /**
     * Initialise le jeu avec les 32 cartes de la Belote
     */
    private void initialiser() {
        // Pour chaque couleur
        for (Couleur couleur : Couleur.values()) {
            // Ajouter les 8 valeurs (7, 8, 9, 10, Valet, Dame, Roi, As)
            cartes.add(new Carte(couleur, ValeurCarte.SEPT));
            cartes.add(new Carte(couleur, ValeurCarte.HUIT));
            cartes.add(new Carte(couleur, ValeurCarte.NEUF));
            cartes.add(new Carte(couleur, ValeurCarte.DIX));
            cartes.add(new Carte(couleur, ValeurCarte.VALET));
            cartes.add(new Carte(couleur, ValeurCarte.DAME));
            cartes.add(new Carte(couleur, ValeurCarte.ROI));
            cartes.add(new Carte(couleur, ValeurCarte.AS));
        }
    }
    
    /**
     * Mélange le jeu de cartes
     */
    public void melanger() {
        Collections.shuffle(cartes);
    }
    
    /**
     * Tire une carte du dessus du paquet
     * @return La carte tirée, ou null si le paquet est vide
     */
    public Carte tirerCarte() {
        if (cartes.isEmpty()) {
            return null;
        }
        return cartes.remove(0);
    }
    
    /**
     * Coupe le jeu de cartes (déplace des cartes du dessus vers le dessous)
     */
    public void couper() {
        if (cartes.size() < 2) {
            return;
        }
        
        // Couper au milieu (ou proche du milieu)
        int positionCoupe = cartes.size() / 2 + (new Random().nextInt(cartes.size() / 4) - cartes.size() / 8);
        
        List<Carte> dessus = new ArrayList<>(cartes.subList(0, positionCoupe));
        List<Carte> dessous = new ArrayList<>(cartes.subList(positionCoupe, cartes.size()));
        
        cartes.clear();
        cartes.addAll(dessous);
        cartes.addAll(dessus);
    }
    
    /**
     * Retourne le nombre de cartes restantes
     */
    public int nombreCartesRestantes() {
        return cartes.size();
    }
    
    /**
     * Vérifie si le jeu est vide
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }
}
