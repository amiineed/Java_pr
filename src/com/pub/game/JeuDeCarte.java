package com.pub.game;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Concept: Basic Class, managing a collection (Java1.2, Java1.3)
public class JeuDeCarte {
    // Concept: Use ArrayList (Java1.3)
    private List<Carte> cartes;

    // Concept: Constructor (Java1.2)
    public JeuDeCarte(boolean jeuDe32) {
        // Concept: Use 'this' (Java1.2)
        this.cartes = new ArrayList<>();
        initialiserJeu(jeuDe32);
    }

    private void initialiserJeu(boolean jeuDe32) {
        this.cartes.clear();
        // Concept: Enhanced for loop (Java1.1 style, though applies to collections)
        for (CouleurCarte couleur : CouleurCarte.values()) {
            for (ValeurCarte valeur : ValeurCarte.values()) {
                if (!jeuDe32 || valeur.ordinal() >= ValeurCarte.SEPT.ordinal()) {
                    // Concept: Adding to ArrayList (Java1.3)
                    this.cartes.add(new Carte(couleur, valeur));
                }
            }
        }
    }

    public void melanger() {
        Collections.shuffle(this.cartes);
        System.out.println("Le jeu de cartes a été mélangé.");
    }

    public void remettreEnOrdre(boolean jeuDe32) {
        initialiserJeu(jeuDe32); // Easiest way to reset
        System.out.println("Le jeu de cartes a été remis en ordre.");
    }

     // Simple cut - splits deck near middle
     public void couper() {
         if (cartes.size() < 2) return;
         int cutPoint = cartes.size() / 2 + (int)(Math.random() * 5) - 2; // Add some randomness near middle
         if (cutPoint <= 0) cutPoint = 1;
         if (cutPoint >= cartes.size()) cutPoint = cartes.size() - 1;

         List<Carte> topPart = new ArrayList<>(cartes.subList(cutPoint, cartes.size()));
         List<Carte> bottomPart = new ArrayList<>(cartes.subList(0, cutPoint));

         cartes.clear();
         cartes.addAll(topPart);
         cartes.addAll(bottomPart);
         System.out.println("Le jeu a été coupé.");
     }

    public Carte tirerCarte() {
        if (cartes.isEmpty()) {
            return null; // Or throw exception
        }
        // Concept: Removing from ArrayList (Java1.3)
        return cartes.remove(cartes.size() - 1); // Draw from the "top" (end of list)
    }

    public int nombreDeCartesRestantes() {
         // Concept: ArrayList size (Java1.3)
        return cartes.size();
    }

    // Concept: toString override (Java1.2)
    @Override
    public String toString() {
        return "JeuDeCarte{" +
                "cartes=" + cartes +
                ", restantes=" + cartes.size() +
                '}';
    }

    // Concept: equals override (Java1.2)
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         JeuDeCarte that = (JeuDeCarte) o;
         return Objects.equals(cartes, that.cartes);
     }

     // Concept: hashCode override (Java1.2)
     @Override
     public int hashCode() {
         return Objects.hash(cartes);
     }

    // --- Static Utility Method ---
    // Concept: Static Method (Java1.2)
    public static String getNomJeu(boolean jeuDe32) {
        return jeuDe32 ? "Jeu de 32 cartes" : "Jeu de 52 cartes";
    }
}