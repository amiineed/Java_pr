package com.pub.game;

import com.pub.bar.Bar;
import com.pub.bar.Caisse;
import com.pub.characters.Barman;
import com.pub.characters.Client;
import com.pub.characters.Human;
import com.pub.characters.Patron;
import com.pub.exceptions.TournoiException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Tournoi {
    private Bar bar;
    private double fraisInscription;
    private List<Equipe> equipesInscrites;
    private FeuilleDeScore feuilleDeScore;
    private boolean inscriptionsOuvertes;
    private boolean tournoiDemarre;
    private boolean tournoiTermine;
    private List<Match> calendrierDesMatchs;
    
    /**
     * Classe interne représentant un match entre deux équipes
     */
    private static class Match {
        private final Equipe equipe1;
        private final Equipe equipe2;
        private boolean joue;
        
        public Match(Equipe equipe1, Equipe equipe2) {
            this.equipe1 = equipe1;
            this.equipe2 = equipe2;
            this.joue = false;
        }
        
        public Equipe getEquipe1() {
            return equipe1;
        }
        
        public Equipe getEquipe2() {
            return equipe2;
        }
        
        public boolean isJoue() {
            return joue;
        }
        
        public void setJoue(boolean joue) {
            this.joue = joue;
        }
    }
    
    public Tournoi(Bar bar, double fraisInscription) {
        this.bar = bar;
        this.fraisInscription = fraisInscription;
        this.equipesInscrites = new ArrayList<>();
        this.feuilleDeScore = new FeuilleDeScore();
        this.inscriptionsOuvertes = false;
        this.tournoiDemarre = false;
        this.tournoiTermine = false;
        this.calendrierDesMatchs = new ArrayList<>();
    }
    
    public void ouvrirInscriptions() {
        this.inscriptionsOuvertes = true;
        System.out.println("Les inscriptions pour le tournoi sont maintenant ouvertes!");
    }
    
    public void fermerInscriptions() {
        this.inscriptionsOuvertes = false;
        System.out.println("Les inscriptions pour le tournoi sont maintenant fermées.");
    }
    
    public void inscrireEquipe(String nomEquipe, Human joueur1, Human joueur2) {
        if (!inscriptionsOuvertes) {
            System.out.println("Les inscriptions ne sont pas ouvertes!");
            return;
        }
        
        if (tournoiDemarre) {
            System.out.println("Le tournoi a déjà démarré!");
            return;
        }
        
        // Vérifier que les joueurs ne sont pas déjà dans une équipe
        for (Equipe equipe : equipesInscrites) {
            if (equipe.getJoueur1() == joueur1 || equipe.getJoueur2() == joueur1 ||
                equipe.getJoueur1() == joueur2 || equipe.getJoueur2() == joueur2) {
                System.out.println("Un des joueurs est déjà inscrit dans une équipe!");
                return;
            }
        }
        
        // PRIORITÉ 1: Vérifier que les joueurs peuvent payer les frais d'inscription
        double fraisParJoueur = fraisInscription / 2.0;
        if (joueur1.getPorteMonnaie() < fraisParJoueur || joueur2.getPorteMonnaie() < fraisParJoueur) {
            System.out.println("Un des joueurs n'a pas assez d'argent pour payer les frais d'inscription!");
            return;
        }
        
        // PRIORITÉ 2: Vérifier qu'il reste au moins un membre du personnel disponible
        // Compter le nombre total de membres du personnel (Serveur/Serveuse)
        int totalPersonnel = 0;
        if (bar.getPersonnel() != null) {
            for (Human personnel : bar.getPersonnel()) {
                if (personnel instanceof com.pub.game.JoueurBelote && !(personnel instanceof Client)) {
                    totalPersonnel++;
                }
            }
        }
        
        // Compter combien de membres du personnel sont déjà inscrits au tournoi
        int personnelInscrits = 0;
        for (Equipe equipe : equipesInscrites) {
            if (equipe.getJoueur1() instanceof com.pub.game.JoueurBelote && !(equipe.getJoueur1() instanceof Client)) {
                personnelInscrits++;
            }
            if (equipe.getJoueur2() instanceof com.pub.game.JoueurBelote && !(equipe.getJoueur2() instanceof Client)) {
                personnelInscrits++;
            }
        }
        
        // Vérifier si les joueurs sélectionnés sont du personnel
        int nouveauxPersonnelInscrits = 0;
        if (joueur1 instanceof com.pub.game.JoueurBelote && !(joueur1 instanceof Client)) {
            nouveauxPersonnelInscrits++;
        }
        if (joueur2 instanceof com.pub.game.JoueurBelote && !(joueur2 instanceof Client)) {
            nouveauxPersonnelInscrits++;
        }
        
        // Si l'inscription de cette équipe laisse 0 personnel disponible, refuser
        int personnelDisponibleApres = totalPersonnel - personnelInscrits - nouveauxPersonnelInscrits;
        if (nouveauxPersonnelInscrits > 0 && personnelDisponibleApres < 1) {
            System.out.println("❌ Action impossible. Il doit rester au moins un membre du personnel pour servir les clients.");
            System.out.println("Personnel total: " + totalPersonnel + 
                             " | Déjà inscrits: " + personnelInscrits + 
                             " | Cette équipe: " + nouveauxPersonnelInscrits);
            return;
        }
        
        // Payer les frais d'inscription (chaque joueur paie sa moitié)
        joueur1.payer(fraisParJoueur);
        joueur2.payer(fraisParJoueur);
        
        Equipe equipe = new Equipe(nomEquipe, joueur1, joueur2);
        equipesInscrites.add(equipe);
        feuilleDeScore.ajouterEquipe(equipe);
        
        System.out.println("Équipe '" + nomEquipe + "' inscrite avec succès!");
    }
    
    public void demarrerTournoi() {
        if (equipesInscrites.size() < 2) {
            System.out.println("Pas assez d'équipes pour démarrer le tournoi (minimum 2 requis)!");
            return;
        }
        
        fermerInscriptions();
        tournoiDemarre = true;
        
        // Générer le calendrier complet des matchs (Round Robin)
        // Chaque équipe rencontre toutes les autres une fois
        calendrierDesMatchs.clear();
        for (int i = 0; i < equipesInscrites.size(); i++) {
            for (int j = i + 1; j < equipesInscrites.size(); j++) {
                Match match = new Match(equipesInscrites.get(i), equipesInscrites.get(j));
                calendrierDesMatchs.add(match);
            }
        }
        
        int nombreMatchs = calendrierDesMatchs.size();
        System.out.println("Le tournoi de Belote démarre avec " + equipesInscrites.size() + " équipes!");
        System.out.println("Format: Round Robin (championnat) - " + nombreMatchs + " matchs au total.");
        System.out.println("Chaque équipe rencontrera toutes les autres équipes.");
    }
    
    /**
     * Joue le prochain match du tournoi en mode interactif si un joueur humain participe
     * @param nomJoueurHumain Nom du joueur humain pour le mode interactif (null = simulation)
     * @return true si un match a été joué, false sinon
     */
    public boolean jouerProchainMatch(String nomJoueurHumain) {
        if (!tournoiDemarre || tournoiTermine) {
            System.out.println("Le tournoi n'est pas démarré ou est déjà terminé.");
            return false;
        }
        
        // Trouver le premier match non joué
        Match prochainMatch = null;
        for (Match match : calendrierDesMatchs) {
            if (!match.isJoue()) {
                prochainMatch = match;
                break;
            }
        }
        
        // Si tous les matchs ont été joués, terminer le tournoi
        if (prochainMatch == null) {
            tournoiTermine = true;
            System.out.println("» Le tournoi est terminé!");
            System.out.println("\n" + "=".repeat(50));
            feuilleDeScore.afficherClassement();
            System.out.println("=".repeat(50));
            return false;
        }
        
        // Récupérer les équipes
        Equipe equipe1 = prochainMatch.getEquipe1();
        Equipe equipe2 = prochainMatch.getEquipe2();
        
        jouerMatch(equipe1, equipe2);
        
        // Vérifier si le joueur humain participe à ce match
        boolean joueurHumainParticipe = false;
        if (nomJoueurHumain != null) {
            joueurHumainParticipe = 
                equipe1.getJoueur1().getPrenom().equalsIgnoreCase(nomJoueurHumain) ||
                equipe1.getJoueur2().getPrenom().equalsIgnoreCase(nomJoueurHumain) ||
                equipe2.getJoueur1().getPrenom().equalsIgnoreCase(nomJoueurHumain) ||
                equipe2.getJoueur2().getPrenom().equalsIgnoreCase(nomJoueurHumain);
        }
        
        // Jouer une partie complète de Belote
        PartieDeBelote partie;
        if (joueurHumainParticipe) {
            System.out.println("\n» MODE INTERACTIF: Vous participez à ce match!");
            partie = new PartieDeBelote(equipe1, equipe2, true, nomJoueurHumain);
        } else {
            System.out.println("\n» MODE SIMULATION: Match joué par l'IA...");
            partie = new PartieDeBelote(equipe1, equipe2, false, null);
        }
        
        Equipe equipeGagnante = partie.demarrerPartie();
        
        // Enregistrer le résultat
        if (equipeGagnante.equals(equipe1)) {
            equipe1.enregistrerMatch(true);
            equipe2.enregistrerMatch(false);
            // Enregistrer les statistiques individuelles des joueurs
            equipe1.getJoueur1().enregistrerMatchTournoi(true);
            equipe1.getJoueur2().enregistrerMatchTournoi(true);
            equipe2.getJoueur1().enregistrerMatchTournoi(false);
            equipe2.getJoueur2().enregistrerMatchTournoi(false);
            feuilleDeScore.ajouterResultat(equipe1.getNom() + " bat " + equipe2.getNom());
        } else {
            equipe1.enregistrerMatch(false);
            equipe2.enregistrerMatch(true);
            // Enregistrer les statistiques individuelles des joueurs
            equipe1.getJoueur1().enregistrerMatchTournoi(false);
            equipe1.getJoueur2().enregistrerMatchTournoi(false);
            equipe2.getJoueur1().enregistrerMatchTournoi(true);
            equipe2.getJoueur2().enregistrerMatchTournoi(true);
            feuilleDeScore.ajouterResultat(equipe2.getNom() + " bat " + equipe1.getNom());
        }
        
        // Marquer le match comme joué
        prochainMatch.setJoue(true);
        
        return true;
    }
    
    private void jouerMatch(Equipe equipe1, Equipe equipe2) {
        System.out.println("\n=== MATCH: " + equipe1.getNom() + " vs " + equipe2.getNom() + " ===");
        System.out.println("Les joueurs s'affrontent à la Belote...");
    }
    
    /**
     * Joue tous les matchs restants du tournoi en mode simulation automatique (IA)
     * @throws TournoiException si le tournoi n'est pas démarré ou est déjà terminé
     */
    public void jouerTournoiComplet() throws TournoiException {
        if (!tournoiDemarre) {
            throw new TournoiException("Le tournoi n'est pas démarré.");
        }
        
        if (tournoiTermine) {
            throw new TournoiException("Le tournoi est déjà terminé.");
        }
        
        System.out.println("\n» Lancement du tournoi complet en mode SIMULATION...");
        System.out.println("Tous les matchs seront joués automatiquement par l'IA.");
        System.out.println("Format: Round Robin - Chaque équipe rencontre toutes les autres.\n");
        
        int nombreMatchsTotal = calendrierDesMatchs.size();
        int nombreMatchsJoues = 0;
        
        // Compter les matchs déjà joués
        for (Match match : calendrierDesMatchs) {
            if (match.isJoue()) {
                nombreMatchsJoues++;
            }
        }
        
        System.out.println("Matchs restants: " + (nombreMatchsTotal - nombreMatchsJoues) + "/" + nombreMatchsTotal + "\n");
        
        int compteur = nombreMatchsJoues;
        while (jouerProchainMatch(null)) {
            compteur++;
            System.out.println("\nMatch " + compteur + "/" + nombreMatchsTotal + " terminé.");
            System.out.println("-".repeat(50));
        }
        
        if (compteur == nombreMatchsJoues) {
            System.out.println("» Aucun match à jouer.");
        } else {
            System.out.println("\n» Tournoi terminé : " + (compteur - nombreMatchsJoues) + " match(s) joué(s).");
            
            // Distribuer les récompenses maintenant que le tournoi est terminé
            distribuerRecompenses();
        }
    }
    
    public void afficherEquipesInscrites() {
        System.out.println("\n=== ÉQUIPES INSCRITES ===");
        if (equipesInscrites.isEmpty()) {
            System.out.println("Aucune équipe inscrite.");
            return;
        }
        
        for (int i = 0; i < equipesInscrites.size(); i++) {
            Equipe equipe = equipesInscrites.get(i);
            System.out.println((i + 1) + ". " + equipe.getNom() + 
                             " (" + equipe.getJoueur1().getPrenom() + " & " + 
                             equipe.getJoueur2().getPrenom() + ")");
        }
        System.out.println();
    }
    
    /**
     * Distribue les récompenses à la fin du tournoi.
     * 50% vont à la Patronne, 50% sont distribués aux équipes gagnantes.
     * Gère les égalités (ex æquo).
     * @throws TournoiException si la caisse n'a pas assez de fonds
     */
    private void distribuerRecompenses() throws TournoiException {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DISTRIBUTION DES RÉCOMPENSES");
        System.out.println("=".repeat(60));
        
        // Tâche 1: Gérer la caisse et la Patronne
        
        // Calculer le pot total
        double potTotal = fraisInscription * equipesInscrites.size();
        System.out.println("\nPot total (frais d'inscription): " + String.format("%.2f", potTotal) + " euros");
        
        // Vérifier que le Barman existe
        Barman barman = bar.getBarman();
        if (barman == null) {
            throw new TournoiException("Aucun barman trouvé pour accéder à la caisse.");
        }
        
        // Vérifier que la caisse existe
        Caisse caisse = barman.getCaisse();
        if (caisse == null) {
            throw new TournoiException("Aucune caisse trouvée.");
        }
        
        // Vérifier que la caisse a assez d'argent
        if (caisse.getMontantTotal() < potTotal) {
            throw new TournoiException("Fonds insuffisants dans la caisse pour payer les gains ! " +
                                     "Requis: " + String.format("%.2f", potTotal) + " euros, " +
                                     "Disponible: " + String.format("%.2f", caisse.getMontantTotal()) + " euros");
        }
        
        // Retirer le pot de la caisse
        boolean retireReussi = caisse.retirerMontant(potTotal);
        if (!retireReussi) {
            throw new TournoiException("Échec du retrait des fonds de la caisse.");
        }
        
        System.out.println("» Montant retiré de la caisse.");
        
        // Donner 50% à la Patronne
        Patron patronne = bar.getPatronne();
        if (patronne == null) {
            throw new TournoiException("Aucune patronne trouvée pour recevoir sa part.");
        }
        
        double partPatronne = potTotal * 0.5;
        patronne.recevoirArgent(partPatronne);
        System.out.println("» Part de la Patronne (50%): " + String.format("%.2f", partPatronne) + " euros versés à " + patronne.getPrenom());
        
        // Tâche 2: Gérer les Gagnants
        
        // Récupérer et trier la liste des équipes
        List<Equipe> equipesTriees = new ArrayList<>(equipesInscrites);
        equipesTriees.sort((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()));
        
        if (equipesTriees.isEmpty()) {
            System.out.println("⚠️ Aucune équipe à récompenser.");
            return;
        }
        
        // Trouver toutes les équipes gagnantes (gestion des égalités)
        List<Equipe> gagnants = new ArrayList<>();
        gagnants.add(equipesTriees.get(0)); // Première équipe
        
        int pointsMax = equipesTriees.get(0).getPoints();
        
        // Parcourir le reste pour trouver les ex æquo
        for (int i = 1; i < equipesTriees.size(); i++) {
            if (equipesTriees.get(i).getPoints() == pointsMax) {
                gagnants.add(equipesTriees.get(i));
            } else {
                break; // Plus d'égalité
            }
        }
        
        // Distribuer la récompense (50% restants)
        double partGagnants = potTotal * 0.5;
        double partParEquipe = partGagnants / gagnants.size();
        double partParJoueur = partParEquipe / 2.0;
        
        System.out.println("\n" + "-".repeat(60));
        System.out.println("GAGNANTS DU TOURNOI");
        System.out.println("-".repeat(60));
        
        if (gagnants.size() == 1) {
            System.out.println("» Équipe victorieuse: " + gagnants.get(0).getNom() + " (" + pointsMax + " points)");
        } else {
            System.out.println("» Égalité! " + gagnants.size() + " équipes gagnantes (" + pointsMax + " points):");
        }
        
        StringBuilder nomsGagnants = new StringBuilder();
        
        // Distribuer aux joueurs
        for (Equipe equipe : gagnants) {
            System.out.println("  • " + equipe.getNom() + " : " + 
                             equipe.getJoueur1().getPrenom() + " & " + 
                             equipe.getJoueur2().getPrenom());
            
            // Donner la part à chaque joueur
            equipe.getJoueur1().recevoirArgent(partParJoueur);
            equipe.getJoueur2().recevoirArgent(partParJoueur);
            
            System.out.println("    » Récompense par équipe: " + String.format("%.2f", partParEquipe) + " euros");
            System.out.println("    » Récompense par joueur: " + String.format("%.2f", partParJoueur) + " euros");
            
            if (nomsGagnants.length() > 0) {
                nomsGagnants.append(", ");
            }
            nomsGagnants.append(equipe.getNom());
        }
        
        System.out.println("-".repeat(60));
        System.out.println("Total distribué aux gagnants: " + String.format("%.2f", partGagnants) + " euros");
        System.out.println("=".repeat(60));
        
        // Tâche 3: Gestion de Fichiers
        
        // S'assurer que le dossier logs/ existe
        com.pub.main.Helper.ensureDirectoryExists("logs");
        
        // Écrire le résumé dans un fichier
        try (PrintWriter writer = new PrintWriter(new FileWriter("logs/tournoi_resume.txt", true))) {
            writer.println("=".repeat(60));
            writer.println("TOURNOI TERMINÉ - " + new java.util.Date());
            writer.println("=".repeat(60));
            writer.println("Bar: " + bar.getNom());
            writer.println("Nombre d'équipes: " + equipesInscrites.size());
            writer.println("Pot total: " + String.format("%.2f", potTotal) + " euros");
            writer.println("Part Patronne (50%): " + String.format("%.2f", partPatronne) + " euros");
            writer.println("Part Gagnants (50%): " + String.format("%.2f", partGagnants) + " euros");
            writer.println("\nGagnants: " + nomsGagnants.toString());
            if (gagnants.size() > 1) {
                writer.println("(Égalité entre " + gagnants.size() + " équipes)");
            }
            writer.println("Points: " + pointsMax);
            writer.println("Récompense par équipe: " + String.format("%.2f", partParEquipe) + " euros");
            writer.println("Récompense par joueur: " + String.format("%.2f", partParJoueur) + " euros");
            writer.println("=".repeat(60));
            writer.println();
            
            System.out.println("\n» Résumé enregistré dans 'logs/tournoi_resume.txt'");
        } catch (IOException e) {
            System.out.println("[!] Erreur lors de l'écriture du fichier de résumé: " + e.getMessage());
        }
    }
    
    public FeuilleDeScore getFeuilleDeScore() {
        return feuilleDeScore;
    }
    
    public boolean isTournoiTermine() {
        return tournoiTermine;
    }
    
    public boolean isInscriptionsOuvertes() {
        return inscriptionsOuvertes;
    }
    
    public boolean isTournoiDemarre() {
        return tournoiDemarre;
    }
    
    public List<Equipe> getEquipesInscrites() {
        return new ArrayList<>(equipesInscrites);
    }
    
    public double getFraisInscription() {
        return fraisInscription;
    }
}
