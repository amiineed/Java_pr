package com.pub.game;

import com.pub.bar.Bar;
import com.pub.bar.Boisson;
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
    
    
    public static class Match {
        private final Equipe equipe1;
        private final Equipe equipe2;
        private boolean joue;
        private Equipe gagnant;
        private int scoreGagnant;
        private int scorePerdant;
        
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
        
        public Equipe getGagnant() {
            return gagnant;
        }
        
        public int getScoreGagnant() {
            return scoreGagnant;
        }
        
        public int getScorePerdant() {
            return scorePerdant;
        }
        
        public void setResultat(Equipe gagnant, int scoreGagnant, int scorePerdant) {
            this.joue = true;
            this.gagnant = gagnant;
            this.scoreGagnant = scoreGagnant;
            this.scorePerdant = scorePerdant;
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
        // TRANSLATED
        System.out.println("Tournament registrations are now open!");
    }
    
    
    public void fermerInscriptions() {
        this.inscriptionsOuvertes = false;
        // TRANSLATED
        System.out.println("Tournament registrations are now closed.");
    }
    
   
    public void inscrireEquipe(String nomEquipe, Human joueur1, Human joueur2) {
        if (!inscriptionsOuvertes) {
            // TRANSLATED
            System.out.println("Registrations are not open!");
            return;
        }
        
        if (tournoiDemarre) {
            // TRANSLATED
            System.out.println("The tournament has already started!");
            return;
        }
        
        for (Equipe equipe : equipesInscrites) {
            if (equipe.getJoueur1() == joueur1 || equipe.getJoueur2() == joueur1 ||
                equipe.getJoueur1() == joueur2 || equipe.getJoueur2() == joueur2) {
                // TRANSLATED
                System.out.println("One of the players is already registered in a team!");
                return;
            }
        }
        
        double fraisParJoueur = fraisInscription / 2.0;
        if (joueur1.getPorteMonnaie() < fraisParJoueur || joueur2.getPorteMonnaie() < fraisParJoueur) {
            // TRANSLATED
            System.out.println("One of the players does not have enough money to pay the registration fee!");
            return;
        }
        
        int totalPersonnel = 0;
        if (bar.getPersonnel() != null) {
            for (Human personnel : bar.getPersonnel()) {
                if (personnel instanceof com.pub.game.JoueurBelote && !(personnel instanceof Client)) {
                    totalPersonnel++;
                }
            }
        }
        
        int personnelInscrits = 0;
        for (Equipe equipe : equipesInscrites) {
            if (equipe.getJoueur1() instanceof com.pub.game.JoueurBelote && !(equipe.getJoueur1() instanceof Client)) {
                personnelInscrits++;
            }
            if (equipe.getJoueur2() instanceof com.pub.game.JoueurBelote && !(equipe.getJoueur2() instanceof Client)) {
                personnelInscrits++;
            }
        }
        
        int nouveauxPersonnelInscrits = 0;
        if (joueur1 instanceof com.pub.game.JoueurBelote && !(joueur1 instanceof Client)) {
            nouveauxPersonnelInscrits++;
        }
        if (joueur2 instanceof com.pub.game.JoueurBelote && !(joueur2 instanceof Client)) {
            nouveauxPersonnelInscrits++;
        }
        
        int personnelDisponibleApres = totalPersonnel - personnelInscrits - nouveauxPersonnelInscrits;
        if (nouveauxPersonnelInscrits > 0 && personnelDisponibleApres < 1) {
            // TRANSLATED
            System.out.println("Action impossible. At least one staff member must remain to serve clients.");
            System.out.println("Total staff: " + totalPersonnel + 
                             " | Already registered: " + personnelInscrits + 
                             " | This team: " + nouveauxPersonnelInscrits);
            return;
        }
        
        joueur1.payer(fraisParJoueur);
        joueur2.payer(fraisParJoueur);
        
        Equipe equipe = new Equipe(nomEquipe, joueur1, joueur2);
        equipesInscrites.add(equipe);
        feuilleDeScore.ajouterEquipe(equipe);
        
        // TRANSLATED
        System.out.println("Team '" + nomEquipe + "' registered successfully!");
    }
    
    
    public void demarrerTournoi() {
        if (equipesInscrites.size() < 2) {
            // TRANSLATED
            System.out.println("Not enough teams to start the tournament (minimum 2 required)!");
            return;
        }
        
        fermerInscriptions();
        tournoiDemarre = true;
        
        calendrierDesMatchs.clear();
        for (int i = 0; i < equipesInscrites.size(); i++) {
            for (int j = i + 1; j < equipesInscrites.size(); j++) {
                Match match = new Match(equipesInscrites.get(i), equipesInscrites.get(j));
                calendrierDesMatchs.add(match);
            }
        }
        
        int nombreMatchs = calendrierDesMatchs.size();
        // TRANSLATED
        System.out.println("The Belote tournament starts with " + equipesInscrites.size() + " teams!");
        System.out.println("Format: Round Robin (League) - " + nombreMatchs + " total matches.");
        System.out.println("Each team will face every other team.");
    }
    
  
    public boolean jouerProchainMatch(String nomJoueurHumain) {
        if (!tournoiDemarre || tournoiTermine) {
            // TRANSLATED
            System.out.println("The tournament has not started or is already finished.");
            return false;
        }
        
        Match prochainMatch = null;
        for (Match match : calendrierDesMatchs) {
            if (!match.isJoue()) {
                prochainMatch = match;
                break;
            }
        }
        
        if (prochainMatch == null) {
            tournoiTermine = true;
            // TRANSLATED
            System.out.println("» The tournament is finished!");
            System.out.println("\n" + "=".repeat(50));
            feuilleDeScore.afficherClassement();
            System.out.println("=".repeat(50));
            return false;
        }
        
        Equipe equipe1 = prochainMatch.getEquipe1();
        Equipe equipe2 = prochainMatch.getEquipe2();
        
        jouerMatch(equipe1, equipe2);
        
        boolean joueurHumainParticipe = false;
        if (nomJoueurHumain != null) {
            joueurHumainParticipe = 
                equipe1.getJoueur1().getPrenom().equalsIgnoreCase(nomJoueurHumain) ||
                equipe1.getJoueur2().getPrenom().equalsIgnoreCase(nomJoueurHumain) ||
                equipe2.getJoueur1().getPrenom().equalsIgnoreCase(nomJoueurHumain) ||
                equipe2.getJoueur2().getPrenom().equalsIgnoreCase(nomJoueurHumain);
        }
        
        PartieDeBelote partie;
        if (joueurHumainParticipe) {
            // TRANSLATED
            System.out.println("\n» INTERACTIVE MODE: You are participating in this match!");
            partie = new PartieDeBelote(equipe1, equipe2, true, nomJoueurHumain);
        } else {
            // TRANSLATED
            System.out.println("\n» SIMULATION MODE: Match played by AI...");
            partie = new PartieDeBelote(equipe1, equipe2, false, null);
        }
        
        Equipe equipeGagnante = partie.demarrerPartie();
        
        int score1 = partie.getScoreEquipe1();
        int score2 = partie.getScoreEquipe2();
        
        if (equipeGagnante.equals(equipe1)) {
            equipe1.enregistrerMatch(true);
            equipe2.enregistrerMatch(false);
            equipe1.getJoueur1().enregistrerMatchTournoi(true);
            equipe1.getJoueur2().enregistrerMatchTournoi(true);
            equipe2.getJoueur1().enregistrerMatchTournoi(false);
            equipe2.getJoueur2().enregistrerMatchTournoi(false);
            
            equipe1.getJoueur1().setPopularite(equipe1.getJoueur1().getPopularite() + 5);
            equipe1.getJoueur2().setPopularite(equipe1.getJoueur2().getPopularite() + 5);
            
            Barman barman = bar.getBarman();
            Boisson biere = bar.trouverBoisson("Beer");
            
            if (barman != null && biere != null) {
                // TRANSLATED
                System.out.println("\n--- Post-match: Losers buy drinks ---");
                equipe2.getJoueur1().offrirVerre(equipe1.getJoueur1(), biere, barman);
                equipe2.getJoueur2().offrirVerre(equipe1.getJoueur2(), biere, barman);
            }
            
            // TRANSLATED
            feuilleDeScore.ajouterResultat(equipe1.getNom() + " beats " + equipe2.getNom());
            prochainMatch.setResultat(equipe1, score1, score2);
        } else {
            equipe1.enregistrerMatch(false);
            equipe2.enregistrerMatch(true);
            equipe1.getJoueur1().enregistrerMatchTournoi(false);
            equipe1.getJoueur2().enregistrerMatchTournoi(false);
            equipe2.getJoueur1().enregistrerMatchTournoi(true);
            equipe2.getJoueur2().enregistrerMatchTournoi(true);
            
            equipe2.getJoueur1().setPopularite(equipe2.getJoueur1().getPopularite() + 5);
            equipe2.getJoueur2().setPopularite(equipe2.getJoueur2().getPopularite() + 5);
            
            Barman barman = bar.getBarman();
            Boisson biere = bar.trouverBoisson("Beer");
            
            if (barman != null && biere != null) {
                // TRANSLATED
                System.out.println("\n--- Post-match: Losers buy drinks ---");
                equipe1.getJoueur1().offrirVerre(equipe2.getJoueur1(), biere, barman);
                equipe1.getJoueur2().offrirVerre(equipe2.getJoueur2(), biere, barman);
            }
            
            // TRANSLATED
            feuilleDeScore.ajouterResultat(equipe2.getNom() + " beats " + equipe1.getNom());
            prochainMatch.setResultat(equipe2, score2, score1);
        }
        
        
        return true;
    }
    
    private void jouerMatch(Equipe equipe1, Equipe equipe2) {
        System.out.println("\n=== MATCH: " + equipe1.getNom() + " vs " + equipe2.getNom() + " ===");
        // TRANSLATED
        System.out.println("Players are battling at Belote...");
    }
    
   
    public void jouerTournoiComplet() throws TournoiException {
        if (!tournoiDemarre) {
            // TRANSLATED
            throw new TournoiException("The tournament has not started.");
        }
        
        if (tournoiTermine) {
            // TRANSLATED
            throw new TournoiException("The tournament is already finished.");
        }
        
        // TRANSLATED
        System.out.println("\n» Launching full tournament in SIMULATION mode...");
        System.out.println("All matches will be played automatically by AI.");
        System.out.println("Format: Round Robin - Each team meets all others.\n");
        
        int nombreMatchsTotal = calendrierDesMatchs.size();
        int nombreMatchsJoues = 0;
        
        for (Match match : calendrierDesMatchs) {
            if (match.isJoue()) {
                nombreMatchsJoues++;
            }
        }
        
        // TRANSLATED
        System.out.println("Remaining matches: " + (nombreMatchsTotal - nombreMatchsJoues) + "/" + nombreMatchsTotal + "\n");
        
        int compteur = nombreMatchsJoues;
        while (jouerProchainMatch(null)) {
            compteur++;
            // TRANSLATED
            System.out.println("\nMatch " + compteur + "/" + nombreMatchsTotal + " finished.");
            System.out.println("-".repeat(50));
        }
        
        if (compteur == nombreMatchsJoues) {
            // TRANSLATED
            System.out.println("» No matches to play.");
        } else {
            // TRANSLATED
            System.out.println("\n» Tournament finished : " + (compteur - nombreMatchsJoues) + " match(es) played.");
            
            distribuerRecompenses();
        }
    }
    
   
    public void afficherEquipesInscrites() {
        // TRANSLATED
        System.out.println("\n=== REGISTERED TEAMS ===");
        if (equipesInscrites.isEmpty()) {
            System.out.println("No teams registered.");
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
    
   
    private void distribuerRecompenses() throws TournoiException {
        System.out.println("\n" + "=".repeat(60));
        // TRANSLATED
        System.out.println("REWARD DISTRIBUTION");
        System.out.println("=".repeat(60));
        
        
        double potTotal = fraisInscription * equipesInscrites.size();
        // TRANSLATED
        System.out.println("\nTotal Pot (Registration fees): " + String.format("%.2f", potTotal) + " euros");
        
        Barman barman = bar.getBarman();
        if (barman == null) {
            // TRANSLATED
            throw new TournoiException("No bartender found to access the cash register.");
        }
        
        Caisse caisse = barman.getCaisse();
        if (caisse == null) {
            // TRANSLATED
            throw new TournoiException("No cash register found.");
        }
        
        if (caisse.getMontantTotal() < potTotal) {
            // TRANSLATED
            throw new TournoiException("Insufficient funds in the register to pay winnings! " +
                                     "Required: " + String.format("%.2f", potTotal) + " euros, " +
                                     "Available: " + String.format("%.2f", caisse.getMontantTotal()) + " euros");
        }
        
        boolean retireReussi = caisse.retirerMontant(potTotal);
        if (!retireReussi) {
            // TRANSLATED
            throw new TournoiException("Failed to withdraw funds from the register.");
        }
        
        // TRANSLATED
        System.out.println("» Amount withdrawn from register.");
        
        Patron patronne = bar.getPatronne();
        if (patronne == null) {
            // TRANSLATED
            throw new TournoiException("No owner found to receive their share.");
        }
        
        double partPatronne = potTotal * 0.5;
        patronne.recevoirArgent(partPatronne);
        // TRANSLATED
        System.out.println("» Owner's share (50%): " + String.format("%.2f", partPatronne) + " euros paid to " + patronne.getPrenom());
        
        
        List<Equipe> equipesTriees = new ArrayList<>(equipesInscrites);
        equipesTriees.sort((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()));
        
        if (equipesTriees.isEmpty()) {
            // TRANSLATED
            System.out.println("No teams to reward.");
            return;
        }
        
        List<Equipe> gagnants = new ArrayList<>();
        gagnants.add(equipesTriees.get(0)); 
        
        int pointsMax = equipesTriees.get(0).getPoints();
        
        for (int i = 1; i < equipesTriees.size(); i++) {
            if (equipesTriees.get(i).getPoints() == pointsMax) {
                gagnants.add(equipesTriees.get(i));
            } else {
                break; 
            }
        }
        
        double partGagnants = potTotal * 0.5;
        double partParEquipe = partGagnants / gagnants.size();
        double partParJoueur = partParEquipe / 2.0;
        
        System.out.println("\n" + "-".repeat(60));
        // TRANSLATED
        System.out.println("TOURNAMENT WINNERS");
        System.out.println("-".repeat(60));
        
        if (gagnants.size() == 1) {
            // TRANSLATED
            System.out.println("» Victorious Team: " + gagnants.get(0).getNom() + " (" + pointsMax + " points)");
        } else {
            // TRANSLATED
            System.out.println("» Tie! " + gagnants.size() + " winning teams (" + pointsMax + " points):");
        }
        
        StringBuilder nomsGagnants = new StringBuilder();
        
        for (Equipe equipe : gagnants) {
            System.out.println("  • " + equipe.getNom() + " : " + 
                             equipe.getJoueur1().getPrenom() + " & " + 
                             equipe.getJoueur2().getPrenom());
            
            equipe.getJoueur1().recevoirArgent(partParJoueur);
            equipe.getJoueur2().recevoirArgent(partParJoueur);
            
            // TRANSLATED
            System.out.println("    » Reward per team: " + String.format("%.2f", partParEquipe) + " euros");
            System.out.println("    » Reward per player: " + String.format("%.2f", partParJoueur) + " euros");
            
            if (nomsGagnants.length() > 0) {
                nomsGagnants.append(", ");
            }
            nomsGagnants.append(equipe.getNom());
        }
        
        System.out.println("-".repeat(60));
        // TRANSLATED
        System.out.println("Total distributed to winners: " + String.format("%.2f", partGagnants) + " euros");
        System.out.println("=".repeat(60));
        
        
        com.pub.main.Helper.ensureDirectoryExists("logs");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("logs/tournoi_resume.txt", true))) {
            writer.println("=".repeat(60));
            // TRANSLATED: Log file content
            writer.println("TOURNAMENT FINISHED - " + new java.util.Date());
            writer.println("=".repeat(60));
            writer.println("Bar: " + bar.getNom());
            writer.println("Number of teams: " + equipesInscrites.size());
            writer.println("Total pot: " + String.format("%.2f", potTotal) + " euros");
            writer.println("Owner Share (50%): " + String.format("%.2f", partPatronne) + " euros");
            writer.println("Winners Share (50%): " + String.format("%.2f", partGagnants) + " euros");
            writer.println("\nWinners: " + nomsGagnants.toString());
            if (gagnants.size() > 1) {
                writer.println("(Tie between " + gagnants.size() + " teams)");
            }
            writer.println("Points: " + pointsMax);
            writer.println("Reward per team: " + String.format("%.2f", partParEquipe) + " euros");
            writer.println("Reward per player: " + String.format("%.2f", partParJoueur) + " euros");
            writer.println("=".repeat(60));
            writer.println();
            
            // TRANSLATED
            System.out.println("\n» Summary saved in 'logs/tournoi_resume.txt'");
        } catch (IOException e) {
            // TRANSLATED
            System.out.println("[!] Error writing summary file: " + e.getMessage());
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
    
    public List<Match> getCalendrierDesMatchs() {
        return calendrierDesMatchs;
    }
    
    public double getFraisInscription() {
        return fraisInscription;
    }
}