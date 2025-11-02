package com.pub.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Représente la feuille de score du tournoi.
 * Gère le classement des équipes et l'affichage des résultats.
 * 
 * Concept: Basic Class (Java1.2)
 * Concept: Collection management (Java1.3)
 */
public class FeuilleDeScore {
    private List<EquipeTournoi> equipes;
    private List<Match> matchsJoues;
    
    /**
     * Constructeur de la feuille de score.
     */
    public FeuilleDeScore() {
        this.equipes = new ArrayList<>();
        this.matchsJoues = new ArrayList<>();
    }
    
    /**
     * Ajoute une équipe à la feuille de score.
     * 
     * @param equipe L'équipe à ajouter
     */
    public void ajouterEquipe(EquipeTournoi equipe) {
        if (!equipes.contains(equipe)) {
            equipes.add(equipe);
        }
    }
    
    /**
     * Ajoute un match joué.
     * 
     * @param match Le match à ajouter
     */
    public void ajouterMatch(Match match) {
        matchsJoues.add(match);
    }
    
    /**
     * Retourne la liste des équipes.
     * 
     * @return La liste des équipes
     */
    public List<EquipeTournoi> getEquipes() {
        return equipes;
    }
    
    /**
     * Retourne la liste des matchs joués.
     * 
     * @return La liste des matchs
     */
    public List<Match> getMatchsJoues() {
        return matchsJoues;
    }
    
    /**
     * Retourne le classement trié par points (puis par différence de manches).
     * 
     * @return La liste des équipes triées
     */
    public List<EquipeTournoi> getClassement() {
        List<EquipeTournoi> classement = new ArrayList<>(equipes);
        
        // Trier par points décroissants, puis par différence de manches
        Collections.sort(classement, new Comparator<EquipeTournoi>() {
            @Override
            public int compare(EquipeTournoi e1, EquipeTournoi e2) {
                // D'abord par points de tournoi
                int comparePoints = Integer.compare(e2.getPointsTournoi(), e1.getPointsTournoi());
                if (comparePoints != 0) {
                    return comparePoints;
                }
                
                // Ensuite par différence de manches
                int diff1 = e1.getManchesGagnees() - e1.getManchesPerdues();
                int diff2 = e2.getManchesGagnees() - e2.getManchesPerdues();
                int compareDiff = Integer.compare(diff2, diff1);
                if (compareDiff != 0) {
                    return compareDiff;
                }
                
                // Enfin par nombre de victoires
                return Integer.compare(e2.getPartiesGagnees(), e1.getPartiesGagnees());
            }
        });
        
        return classement;
    }
    
    /**
     * Affiche le classement complet du tournoi.
     */
    public void afficherClassement() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           CLASSEMENT DU TOURNOI DE BELOTE                ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ Pos | Équipe              | Pts | V | D | Manches        ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        
        List<EquipeTournoi> classement = getClassement();
        for (int i = 0; i < classement.size(); i++) {
            EquipeTournoi equipe = classement.get(i);
            String nom = String.format("%-20s", 
                equipe.getNomEquipe().length() > 20 ? 
                equipe.getNomEquipe().substring(0, 20) : equipe.getNomEquipe());
            
            System.out.println(String.format("║ %3d | %s | %3d | %d | %d | %2d-%2d (%+3d)  ║",
                i + 1,
                nom,
                equipe.getPointsTournoi(),
                equipe.getPartiesGagnees(),
                equipe.getPartiesPerdues(),
                equipe.getManchesGagnees(),
                equipe.getManchesPerdues(),
                equipe.getManchesGagnees() - equipe.getManchesPerdues()
            ));
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Affiche l'historique des matchs.
     */
    public void afficherHistorique() {
        System.out.println("\n=== HISTORIQUE DES MATCHS ===");
        if (matchsJoues.isEmpty()) {
            System.out.println("Aucun match joué pour le moment.");
        } else {
            for (Match match : matchsJoues) {
                System.out.println(match);
            }
        }
    }
    
    /**
     * Retourne l'équipe gagnante du tournoi (première du classement).
     * 
     * @return L'équipe gagnante, ou null si aucune équipe
     */
    public EquipeTournoi getEquipeGagnante() {
        List<EquipeTournoi> classement = getClassement();
        return classement.isEmpty() ? null : classement.get(0);
    }
    
    /**
     * Affiche un résumé compact de la feuille de score.
     */
    public void afficherResume() {
        System.out.println("\n--- Résumé du Tournoi ---");
        System.out.println("Nombre d'équipes: " + equipes.size());
        System.out.println("Matchs joués: " + matchsJoues.size());
        
        EquipeTournoi gagnant = getEquipeGagnante();
        if (gagnant != null) {
            System.out.println("En tête: " + gagnant.getNomEquipe() + 
                             " avec " + gagnant.getPointsTournoi() + " points");
        }
    }
}
