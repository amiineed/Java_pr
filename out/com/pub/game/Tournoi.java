package com.pub.game;

import com.pub.bar.Bar;
import com.pub.characters.Barman;
import com.pub.characters.Client;
import com.pub.characters.Patron;
import com.pub.exceptions.BarException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Représente un tournoi de belote dans le bar.
 * Gère les inscriptions, les matchs, et la distribution des gains.
 * 
 * Concept: Complex state management
 * Concept: Integration with existing bar system
 */
public class Tournoi {
    private final Bar bar;
    private final Patron patronne;
    private final Barman barman;
    private final double fraisInscription;
    private List<EquipeTournoi> equipesInscrites;
    private FeuilleDeScore feuilleDeScore;
    private List<Match> matchsAProgrammer;
    private boolean inscriptionsOuvertes;
    private boolean tournoiDemarre;
    private boolean tournoiTermine;
    private double cagnotte;
    private int numeroMatchCourant;
    
    /**
     * Constructeur du tournoi.
     * 
     * @param bar Le bar qui organise le tournoi
     * @param fraisInscription Les frais d'inscription par équipe
     */
    public Tournoi(Bar bar, double fraisInscription) {
        this.bar = bar;
        this.patronne = bar.getPatronne();
        this.barman = bar.getBarman();
        this.fraisInscription = fraisInscription;
        this.equipesInscrites = new ArrayList<>();
        this.feuilleDeScore = new FeuilleDeScore();
        this.matchsAProgrammer = new ArrayList<>();
        this.inscriptionsOuvertes = true;
        this.tournoiDemarre = false;
        this.tournoiTermine = false;
        this.cagnotte = 0.0;
        this.numeroMatchCourant = 1;
    }
    
    /**
     * Ouvre les inscriptions au tournoi.
     */
    public void ouvrirInscriptions() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║   TOURNOI DE BELOTE - INSCRIPTIONS OUVERTES !        ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║ Bar: " + String.format("%-47s", bar.getNom()) + "║");
        System.out.println("║ Organisé par: " + String.format("%-40s", patronne.getPrenom()) + "║");
        System.out.println("║ Frais d'inscription: " + String.format("%6.2f€", fraisInscription) + " par équipe           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        this.inscriptionsOuvertes = true;
    }
    
    /**
     * Inscrit une équipe au tournoi.
     * 
     * @param nomEquipe Le nom de l'équipe
     * @param joueur1 Le premier joueur
     * @param joueur2 Le deuxième joueur
     * @return true si l'inscription a réussi, false sinon
     */
    public boolean inscrireEquipe(String nomEquipe, Client joueur1, Client joueur2) {
        if (!inscriptionsOuvertes) {
            System.out.println("❌ Les inscriptions sont fermées !");
            return false;
        }
        
        if (tournoiDemarre) {
            System.out.println("❌ Le tournoi a déjà commencé !");
            return false;
        }
        
        // Vérifier que les joueurs ne sont pas déjà inscrits
        for (EquipeTournoi equipe : equipesInscrites) {
            if (equipe.contientJoueur(joueur1) || equipe.contientJoueur(joueur2)) {
                System.out.println("❌ Un des joueurs est déjà inscrit dans une autre équipe !");
                return false;
            }
        }
        
        // Vérifier que les joueurs ont assez d'argent
        if (joueur1.getPorteMonnaie() < fraisInscription / 2) {
            System.out.println("❌ " + joueur1.getPrenom() + " n'a pas assez d'argent pour s'inscrire !");
            return false;
        }
        if (joueur2.getPorteMonnaie() < fraisInscription / 2) {
            System.out.println("❌ " + joueur2.getPrenom() + " n'a pas assez d'argent pour s'inscrire !");
            return false;
        }
        
        // Prélever les frais d'inscription
        try {
            joueur1.depenser(fraisInscription / 2);
            joueur2.depenser(fraisInscription / 2);
            cagnotte += fraisInscription;
            
            // Créer et inscrire l'équipe
            EquipeTournoi nouvelleEquipe = new EquipeTournoi(nomEquipe, joueur1, joueur2);
            equipesInscrites.add(nouvelleEquipe);
            feuilleDeScore.ajouterEquipe(nouvelleEquipe);
            
            System.out.println("✓ Équipe \"" + nomEquipe + "\" inscrite avec succès !");
            System.out.println("  Joueurs: " + joueur1.getPrenom() + " & " + joueur2.getPrenom());
            System.out.println("  Frais payés: " + fraisInscription + "€");
            
            return true;
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'inscription: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Ferme les inscriptions et génère les matchs.
     */
    public void cloturerInscriptions() {
        if (!inscriptionsOuvertes) {
            System.out.println("Les inscriptions sont déjà fermées.");
            return;
        }
        
        if (equipesInscrites.size() < 2) {
            System.out.println("❌ Pas assez d'équipes inscrites (minimum 2).");
            return;
        }
        
        this.inscriptionsOuvertes = false;
        System.out.println("\n✓ Inscriptions clôturées !");
        System.out.println("  Nombre d'équipes: " + equipesInscrites.size());
        System.out.println("  Cagnotte totale: " + cagnotte + "€");
        
        // Générer les matchs (round-robin)
        genererMatchs();
    }
    
    /**
     * Génère tous les matchs du tournoi (chaque équipe joue contre toutes les autres).
     */
    private void genererMatchs() {
        matchsAProgrammer.clear();
        
        for (int i = 0; i < equipesInscrites.size(); i++) {
            for (int j = i + 1; j < equipesInscrites.size(); j++) {
                Match match = new Match(equipesInscrites.get(i), equipesInscrites.get(j), numeroMatchCourant++);
                matchsAProgrammer.add(match);
            }
        }
        
        System.out.println("  Nombre de matchs à jouer: " + matchsAProgrammer.size());
    }
    
    /**
     * Démarre le tournoi.
     */
    public void demarrerTournoi() {
        if (tournoiDemarre) {
            System.out.println("Le tournoi a déjà démarré !");
            return;
        }
        
        if (inscriptionsOuvertes) {
            cloturerInscriptions();
        }
        
        if (matchsAProgrammer.isEmpty()) {
            System.out.println("❌ Aucun match à jouer !");
            return;
        }
        
        this.tournoiDemarre = true;
        
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║          🏆 DÉBUT DU TOURNOI DE BELOTE 🏆            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        afficherEquipesInscrites();
    }
    
    /**
     * Joue le prochain match du tournoi.
     * 
     * @return true si un match a été joué, false s'il n'y a plus de matchs
     */
    public boolean jouerProchainMatch() {
        if (matchsAProgrammer.isEmpty()) {
            if (!tournoiTermine) {
                terminerTournoi();
            }
            return false;
        }
        
        Match match = matchsAProgrammer.remove(0);
        System.out.println("\n" + "═".repeat(60));
        System.out.println("  " + match.getEquipe1().getNomEquipe() + " vs " + match.getEquipe2().getNomEquipe());
        System.out.println("═".repeat(60));
        
        // Jouer les manches jusqu'à ce qu'une équipe gagne (simplification: on simule)
        jouerMatchComplet(match);
        
        // Enregistrer le match
        feuilleDeScore.ajouterMatch(match);
        
        // Afficher le résultat
        System.out.println("\n" + match);
        
        // Afficher le classement intermédiaire
        if (matchsAProgrammer.size() % 3 == 0 || matchsAProgrammer.isEmpty()) {
            feuilleDeScore.afficherClassement();
        }
        
        return true;
    }
    
    /**
     * Joue un match complet (simulation simplifiée).
     * 
     * @param match Le match à jouer
     */
    private void jouerMatchComplet(Match match) {
        // Simulation: on joue jusqu'à 2 manches gagnées
        int manchesMax = 2;
        int manchesEquipe1 = 0;
        int manchesEquipe2 = 0;
        
        while (manchesEquipe1 < manchesMax && manchesEquipe2 < manchesMax) {
            // Simuler une partie de belote
            System.out.println("\n--- Manche " + (manchesEquipe1 + manchesEquipe2 + 1) + " ---");
            
            // Simulation simple: chance aléatoire
            boolean equipe1Gagne = Math.random() > 0.5;
            
            if (equipe1Gagne) {
                manchesEquipe1++;
                match.enregistrerVictoireManche(match.getEquipe1());
                System.out.println("✓ " + match.getEquipe1().getNomEquipe() + " remporte la manche !");
            } else {
                manchesEquipe2++;
                match.enregistrerVictoireManche(match.getEquipe2());
                System.out.println("✓ " + match.getEquipe2().getNomEquipe() + " remporte la manche !");
            }
            
            System.out.println("Score: " + match.getEquipe1().getNomEquipe() + " " + 
                             manchesEquipe1 + "-" + manchesEquipe2 + " " + 
                             match.getEquipe2().getNomEquipe());
        }
        
        match.terminerMatch();
    }
    
    /**
     * Termine le tournoi et distribue les gains.
     */
    private void terminerTournoi() {
        this.tournoiTermine = true;
        
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║          🎉 FIN DU TOURNOI DE BELOTE 🎉              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        // Afficher le classement final
        feuilleDeScore.afficherClassement();
        
        // Distribuer les gains
        distribuerGains();
    }
    
    /**
     * Distribue les gains: 50% à la patronne, le reste aux équipes gagnantes.
     */
    private void distribuerGains() {
        System.out.println("\n=== DISTRIBUTION DES GAINS ===");
        System.out.println("Cagnotte totale: " + cagnotte + "€");
        
        // 50% à la patronne
        double partPatronne = cagnotte * 0.5;
        patronne.ajouterArgent(partPatronne);
        System.out.println("Part de la patronne (" + patronne.getPrenom() + "): " + 
                         String.format("%.2f€", partPatronne));
        
        // Le reste est symbolique (dans un vrai tournoi, on pourrait distribuer aux gagnants)
        double reste = cagnotte - partPatronne;
        System.out.println("Reste (pour l'équipe gagnante): " + String.format("%.2f€", reste));
        
        EquipeTournoi gagnant = feuilleDeScore.getEquipeGagnante();
        if (gagnant != null) {
            System.out.println("\n🏆 ÉQUIPE VICTORIEUSE: " + gagnant.getNomEquipe() + " 🏆");
            System.out.println("   " + gagnant.getJoueur1().getPrenom() + " & " + 
                             gagnant.getJoueur2().getPrenom());
            
            // Distribuer aux joueurs gagnants
            double partParJoueur = reste / 2;
            gagnant.getJoueur1().ajouterArgent(partParJoueur);
            gagnant.getJoueur2().ajouterArgent(partParJoueur);
            System.out.println("   Gain par joueur: " + String.format("%.2f€", partParJoueur));
        }
    }
    
    /**
     * Joue tout le tournoi automatiquement.
     */
    public void jouerTournoiComplet() {
        if (!tournoiDemarre) {
            demarrerTournoi();
        }
        
        while (jouerProchainMatch()) {
            // Continue jusqu'à la fin
        }
    }
    
    /**
     * Affiche les équipes inscrites.
     */
    public void afficherEquipesInscrites() {
        System.out.println("\n=== ÉQUIPES INSCRITES ===");
        for (int i = 0; i < equipesInscrites.size(); i++) {
            EquipeTournoi equipe = equipesInscrites.get(i);
            System.out.println((i + 1) + ". " + equipe.getNomEquipe() + 
                             " (" + equipe.getJoueur1().getPrenom() + " & " + 
                             equipe.getJoueur2().getPrenom() + ")");
        }
    }
    
    /**
     * Retourne la feuille de score.
     * 
     * @return La feuille de score
     */
    public FeuilleDeScore getFeuilleDeScore() {
        return feuilleDeScore;
    }
    
    /**
     * Retourne si le tournoi est terminé.
     * 
     * @return true si le tournoi est terminé
     */
    public boolean isTournoiTermine() {
        return tournoiTermine;
    }
    
    /**
     * Retourne le nombre de matchs restants.
     * 
     * @return Le nombre de matchs restants
     */
    public int getMatchsRestants() {
        return matchsAProgrammer.size();
    }
}
