package com.pub.game;

import com.pub.bar.Bar;
import com.pub.characters.Client;
import java.util.*;

public class Tournoi {
    private Bar bar;
    private double fraisInscription;
    private List<Equipe> equipesInscrites;
    private FeuilleDeScore feuilleDeScore;
    private boolean inscriptionsOuvertes;
    private boolean tournoiDemarre;
    private boolean tournoiTermine;
    private Queue<Equipe> matchsEnAttente;
    
    public Tournoi(Bar bar, double fraisInscription) {
        this.bar = bar;
        this.fraisInscription = fraisInscription;
        this.equipesInscrites = new ArrayList<>();
        this.feuilleDeScore = new FeuilleDeScore();
        this.inscriptionsOuvertes = false;
        this.tournoiDemarre = false;
        this.tournoiTermine = false;
        this.matchsEnAttente = new LinkedList<>();
    }
    
    public void ouvrirInscriptions() {
        this.inscriptionsOuvertes = true;
        System.out.println("Les inscriptions pour le tournoi sont maintenant ouvertes!");
    }
    
    public void fermerInscriptions() {
        this.inscriptionsOuvertes = false;
        System.out.println("Les inscriptions pour le tournoi sont maintenant fermées.");
    }
    
    public void inscrireEquipe(String nomEquipe, Client joueur1, Client joueur2) {
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
        
        // Vérifier que les joueurs peuvent payer les frais d'inscription
        if (joueur1.getPorteMonnaie() < fraisInscription || joueur2.getPorteMonnaie() < fraisInscription) {
            System.out.println("Un des joueurs n'a pas assez d'argent pour payer les frais d'inscription!");
            return;
        }
        
        // Payer les frais d'inscription
        joueur1.payer(fraisInscription);
        joueur2.payer(fraisInscription);
        
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
        
        // Préparer la liste des matchs
        matchsEnAttente.addAll(equipesInscrites);
        
        System.out.println("Le tournoi de Belote démarre avec " + equipesInscrites.size() + " équipes!");
    }
    
    public boolean jouerProchainMatch() {
        if (!tournoiDemarre || tournoiTermine) {
            return false;
        }
        
        if (matchsEnAttente.size() < 2) {
            tournoiTermine = true;
            System.out.println("Le tournoi est terminé!");
            feuilleDeScore.afficherClassement();
            return false;
        }
        
        Equipe equipe1 = matchsEnAttente.poll();
        Equipe equipe2 = matchsEnAttente.poll();
        
        if (equipe1 != null && equipe2 != null) {
            jouerMatch(equipe1, equipe2);
            
            // Les équipes gagnantes continuent, les perdantes sont éliminées
            if (Math.random() > 0.5) { // équipe1 gagne
                equipe1.enregistrerMatch(true);
                equipe2.enregistrerMatch(false);
                matchsEnAttente.add(equipe1);
                feuilleDeScore.ajouterResultat(equipe1.getNom() + " bat " + equipe2.getNom());
            } else { // équipe2 gagne
                equipe1.enregistrerMatch(false);
                equipe2.enregistrerMatch(true);
                matchsEnAttente.add(equipe2);
                feuilleDeScore.ajouterResultat(equipe2.getNom() + " bat " + equipe1.getNom());
            }
        }
        
        return true;
    }
    
    private void jouerMatch(Equipe equipe1, Equipe equipe2) {
        System.out.println("\n=== MATCH: " + equipe1.getNom() + " vs " + equipe2.getNom() + " ===");
        System.out.println("Les joueurs s'affrontent à la Belote...");
        
        // Simuler un délai pour le match
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void jouerTournoiComplet() {
        System.out.println("Lancement du tournoi complet...");
        while (jouerProchainMatch()) {
            // Continuer直到 tous les matchs sont joués
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
}
