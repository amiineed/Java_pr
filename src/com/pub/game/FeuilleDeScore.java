package com.pub.game;

import java.util.*;

public class FeuilleDeScore {
    private List<Equipe> equipes;
    private List<String> resultats;
    
    public FeuilleDeScore() {
        this.equipes = new ArrayList<>();
        this.resultats = new ArrayList<>();
    }
    
    public void ajouterEquipe(Equipe equipe) {
        if (!equipes.contains(equipe)) {
            equipes.add(equipe);
        }
    }
    
    public void ajouterResultat(String resultat) {
        resultats.add(resultat);
    }
    
    public List<Equipe> getEquipes() {
        return new ArrayList<>(equipes);
    }
    
    public void afficherClassement() {
        System.out.println("\n=== CLASSEMENT DU TOURNOI ===");
        if (equipes.isEmpty()) {
            System.out.println("Aucune équipe inscrite.");
            return;
        }
        
        equipes.sort((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()));
        
        System.out.printf("%-3s %-20s %-8s %-8s %-8s %-8s%n", 
                         "Pos", "Équipe", "Points", "Joués", "Gagnés", "Perdus");
        System.out.println("--------------------------------------------------------");
        
        int rangActuel = 1;
        for (int i = 0; i < equipes.size(); i++) {
            Equipe equipe = equipes.get(i);
            
            if (i > 0 && equipe.getPoints() < equipes.get(i - 1).getPoints()) {
                rangActuel++;
            }
            
            System.out.printf("%-3d %-20s %-8d %-8d %-8d %-8d%n", 
                             rangActuel, 
                             equipe.getNom(), 
                             equipe.getPoints(), 
                             equipe.getMatchsJoues(), 
                             equipe.getMatchsGagnes(), 
                             equipe.getMatchsPerdus());
        }
        System.out.println();
    }
    
    public void afficherResultats() {
        System.out.println("\n=== RÉSULTATS DES MATCHS ===");
        if (resultats.isEmpty()) {
            System.out.println("Aucun match joué.");
            return;
        }
        
        for (String resultat : resultats) {
            System.out.println(resultat);
        }
        System.out.println();
    }
}
