package com.pub.game;

/**
 * Représente un match entre deux équipes de tournoi.
 * Un match se compose de plusieurs manches (parties) jusqu'à ce qu'une équipe atteigne 1010 points.
 * 
 * Concept: Basic Class (Java1.2)
 */
public class Match {
    private final EquipeTournoi equipe1;
    private final EquipeTournoi equipe2;
    private int manchesEquipe1; // Nombre de manches gagnées par l'équipe 1
    private int manchesEquipe2; // Nombre de manches gagnées par l'équipe 2
    private EquipeTournoi vainqueur;
    private boolean termine;
    private int numeroMatch;
    
    /**
     * Constructeur d'un match.
     * 
     * @param equipe1 La première équipe
     * @param equipe2 La deuxième équipe
     * @param numeroMatch Le numéro du match dans le tournoi
     */
    public Match(EquipeTournoi equipe1, EquipeTournoi equipe2, int numeroMatch) {
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.manchesEquipe1 = 0;
        this.manchesEquipe2 = 0;
        this.vainqueur = null;
        this.termine = false;
        this.numeroMatch = numeroMatch;
    }
    
    /**
     * Retourne la première équipe.
     * 
     * @return La première équipe
     */
    public EquipeTournoi getEquipe1() {
        return equipe1;
    }
    
    /**
     * Retourne la deuxième équipe.
     * 
     * @return La deuxième équipe
     */
    public EquipeTournoi getEquipe2() {
        return equipe2;
    }
    
    /**
     * Retourne le nombre de manches gagnées par l'équipe 1.
     * 
     * @return Le nombre de manches
     */
    public int getManchesEquipe1() {
        return manchesEquipe1;
    }
    
    /**
     * Retourne le nombre de manches gagnées par l'équipe 2.
     * 
     * @return Le nombre de manches
     */
    public int getManchesEquipe2() {
        return manchesEquipe2;
    }
    
    /**
     * Retourne le vainqueur du match.
     * 
     * @return L'équipe vainqueur, ou null si le match n'est pas terminé
     */
    public EquipeTournoi getVainqueur() {
        return vainqueur;
    }
    
    /**
     * Retourne le numéro du match.
     * 
     * @return Le numéro du match
     */
    public int getNumeroMatch() {
        return numeroMatch;
    }
    
    /**
     * Vérifie si le match est terminé.
     * 
     * @return true si le match est terminé
     */
    public boolean isTermine() {
        return termine;
    }
    
    /**
     * Enregistre la victoire d'une manche pour une équipe.
     * 
     * @param equipe L'équipe qui a gagné la manche
     */
    public void enregistrerVictoireManche(EquipeTournoi equipe) {
        if (equipe.equals(equipe1)) {
            manchesEquipe1++;
        } else if (equipe.equals(equipe2)) {
            manchesEquipe2++;
        }
    }
    
    /**
     * Termine le match et détermine le vainqueur.
     * Applique les règles: si 2-0, le score devient 1-1 et chaque équipe marque 1 point.
     * Sinon, le vainqueur marque 3 points et le perdant 0.
     */
    public void terminerMatch() {
        this.termine = true;
        
        // Déterminer le vainqueur basé sur les manches
        if (manchesEquipe1 > manchesEquipe2) {
            vainqueur = equipe1;
        } else if (manchesEquipe2 > manchesEquipe1) {
            vainqueur = equipe2;
        }
        
        // Appliquer les règles de scoring du tournoi
        if (manchesEquipe1 == 2 && manchesEquipe2 == 0) {
            // Cas spécial: 2-0 devient 1-1, chaque équipe marque 1 point
            manchesEquipe1 = 1;
            manchesEquipe2 = 1;
            equipe1.enregistrerEgalite();
            equipe2.enregistrerEgalite();
            vainqueur = null; // Égalité
        } else if (manchesEquipe2 == 2 && manchesEquipe1 == 0) {
            // Cas spécial: 0-2 devient 1-1, chaque équipe marque 1 point
            manchesEquipe1 = 1;
            manchesEquipe2 = 1;
            equipe1.enregistrerEgalite();
            equipe2.enregistrerEgalite();
            vainqueur = null; // Égalité
        } else {
            // Cas normal: le vainqueur marque 3 points
            if (vainqueur == equipe1) {
                equipe1.enregistrerVictoire();
                equipe2.enregistrerDefaite();
            } else {
                equipe2.enregistrerVictoire();
                equipe1.enregistrerDefaite();
            }
        }
        
        // Mettre à jour les statistiques de manches
        equipe1.ajouterManchesGagnees(manchesEquipe1);
        equipe1.ajouterManchesPerdues(manchesEquipe2);
        equipe2.ajouterManchesGagnees(manchesEquipe2);
        equipe2.ajouterManchesPerdues(manchesEquipe1);
        
        // Ajouter ce match à l'historique des équipes
        equipe1.ajouterMatch(this);
        equipe2.ajouterMatch(this);
    }
    
    @Override
    public String toString() {
        String score = manchesEquipe1 + "-" + manchesEquipe2;
        if (termine) {
            if (vainqueur != null) {
                return "Match " + numeroMatch + ": " + equipe1.getNomEquipe() + " vs " + 
                       equipe2.getNomEquipe() + " (" + score + ") - Vainqueur: " + vainqueur.getNomEquipe();
            } else {
                return "Match " + numeroMatch + ": " + equipe1.getNomEquipe() + " vs " + 
                       equipe2.getNomEquipe() + " (" + score + ") - Égalité";
            }
        } else {
            return "Match " + numeroMatch + ": " + equipe1.getNomEquipe() + " vs " + 
                   equipe2.getNomEquipe() + " (En cours: " + score + ")";
        }
    }
}
