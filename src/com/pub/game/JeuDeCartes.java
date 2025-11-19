package com.pub.game;

import java.util.*;

public class JeuDeCartes {
    private List<Carte> cartes;
    
    public JeuDeCartes() {
        this.cartes = new ArrayList<>();
        initialiser();
    }
    
    private void initialiser() {
        for (Couleur couleur : Couleur.values()) {
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
    
    
    public void melanger() {
        Collections.shuffle(cartes);
    }
    
   
    public Carte tirerCarte() {
        if (cartes.isEmpty()) {
            return null;
        }
        return cartes.remove(0);
    }
    
    
    public void couper() {
        if (cartes.size() < 2) {
            return;
        }
        
        int positionCoupe = cartes.size() / 2 + (new Random().nextInt(cartes.size() / 4) - cartes.size() / 8);
        
        List<Carte> dessus = new ArrayList<>(cartes.subList(0, positionCoupe));
        List<Carte> dessous = new ArrayList<>(cartes.subList(positionCoupe, cartes.size()));
        
        cartes.clear();
        cartes.addAll(dessous);
        cartes.addAll(dessus);
    }
    
    
    public int nombreCartesRestantes() {
        return cartes.size();
    }
   
    public boolean estVide() {
        return cartes.isEmpty();
    }
}
