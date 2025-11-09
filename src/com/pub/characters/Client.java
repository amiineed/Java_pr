package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.game.JoueurBelote;

public class Client extends Human implements JoueurBelote {
    private Boisson boissonFavorite;
    private Boisson boissonActuelle;
    private double niveauAlcoolemie;
    private String criSignificatif;
    private String identifiantGenre;
    private String genre;
    
    // Statistiques de consommation
    private int nombreVerresConsommes;
    
    public Client(String prenom, String surnom, double porteMonnaie, int popularite, 
                  String criSignificatif, Boisson boissonFavorite, Boisson boissonActuelle, String identifiantGenre, String genre) {
        super(prenom, surnom, porteMonnaie, popularite);
        this.boissonFavorite = boissonFavorite;
        this.boissonActuelle = boissonActuelle;
        this.niveauAlcoolemie = 0.0;
        this.criSignificatif = criSignificatif;
        this.identifiantGenre = identifiantGenre;
        this.genre = genre;
        // Initialisation des statistiques de consommation
        this.nombreVerresConsommes = 0;
    }
    
    public Boisson getBoissonFavorite() {
        return boissonFavorite;
    }
    
    public void setBoissonFavorite(Boisson boissonFavorite) {
        this.boissonFavorite = boissonFavorite;
    }
    
    public Boisson getBoissonActuelle() {
        return boissonActuelle;
    }
    
    public void setBoissonActuelle(Boisson boissonActuelle) {
        this.boissonActuelle = boissonActuelle;
    }
    
    public double getNiveauAlcoolemie() {
        return niveauAlcoolemie;
    }
    
    public void setNiveauAlcoolemie(double niveauAlcoolemie) {
        this.niveauAlcoolemie = niveauAlcoolemie;
    }
    
    public String getCriSignificatif() {
        return criSignificatif;
    }
    
    public void setCriSignificatif(String criSignificatif) {
        this.criSignificatif = criSignificatif;
    }
    
    public String getIdentifiantGenre() {
        return identifiantGenre;
    }
    
    public void setIdentifiantGenre(String identifiantGenre) {
        this.identifiantGenre = identifiantGenre;
    }
    
    public void boire(Boisson boisson) {
        this.boissonActuelle = boisson;
        this.nombreVerresConsommes++;
        if (boisson instanceof com.pub.bar.BoissonAlcoolisee) {
            com.pub.bar.BoissonAlcoolisee boissonAlcoolisee = (com.pub.bar.BoissonAlcoolisee) boisson;
            this.niveauAlcoolemie += boissonAlcoolisee.getDegreAlcool() * 0.01;
        }
        parler(criSignificatif + " Ça fait du bien!");
    }
    
    // Getters et setters pour les statistiques
    public int getNombreVerresConsommes() {
        return nombreVerresConsommes;
    }
    
    public int getMatchsTournoiJoues() {
        return matchsTournoiJoues;
    }
    
    public int getMatchsTournoiGagnes() {
        return matchsTournoiGagnes;
    }
    
    public int getMatchsTournoiPerdus() {
        return matchsTournoiPerdus;
    }
    
    public int getPointsTournoi() {
        return pointsTournoi;
    }
    
    /**
     * Enregistre le résultat d'un match de tournoi pour ce joueur.
     * Met à jour les statistiques individuelles (matchs joués, victoires, défaites, points).
     * 
     * @param victoire true si le joueur a gagné le match, false sinon
     */
    public void enregistrerMatchTournoi(boolean victoire) {
        this.matchsTournoiJoues++;
        if (victoire) {
            this.matchsTournoiGagnes++;
            this.pointsTournoi += 3; // 3 points pour une victoire
        } else {
            this.matchsTournoiPerdus++;
        }
    }
    
    /**
     * Fournit une chaîne formatée affichant les statistiques détaillées du Client,
     * incluant le nombre de verres consommés total et les statistiques de tournoi
     * (matchs joués, victoires/défaites, points de tournoi).
     * 
     * @return String représentant les statistiques formatées en console
     */
    public String getStatistiquesDetailles() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n╔═══════════════════════════════════════════════════════════╗\n");
        stats.append("║        STATISTIQUES DE ").append(getPrenom().toUpperCase());
        // Padding pour aligner
        int nameLen = getPrenom().length();
        int padding = 32 - nameLen;
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        // Informations générales
        stats.append("║ Surnom : ").append(getSurnom());
        padding = 48 - getSurnom().length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        stats.append(String.format("║ Argent disponible : %.2f euros", getPorteMonnaie()));
        String argentStr = String.format("%.2f euros", getPorteMonnaie());
        padding = 36 - argentStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        stats.append(String.format("║ Niveau d'alcoolémie : %.3f g/L", niveauAlcoolemie));
        String alcoolStr = String.format("%.3f g/L", niveauAlcoolemie);
        padding = 32 - alcoolStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        stats.append("║  STATISTIQUES DE CONSOMMATION                             ║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Total de verres consommés : %d", nombreVerresConsommes));
        String verresStr = String.valueOf(nombreVerresConsommes);
        padding = 33 - verresStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        if (boissonFavorite != null) {
            stats.append("║ Boisson favorite : ").append(boissonFavorite.getNom());
            padding = 38 - boissonFavorite.getNom().length();
            for (int i = 0; i < padding; i++) {
                stats.append(" ");
            }
            stats.append("║\n");
        }
        
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        stats.append("║  STATISTIQUES DE TOURNOI                                  ║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Matchs joués : %d", matchsTournoiJoues));
        String matchsStr = String.valueOf(matchsTournoiJoues);
        padding = 43 - matchsStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        stats.append(String.format("║ Victoires : %d", matchsTournoiGagnes));
        String victoiresStr = String.valueOf(matchsTournoiGagnes);
        padding = 46 - victoiresStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        stats.append(String.format("║ Défaites : %d", matchsTournoiPerdus));
        String defaitesStr = String.valueOf(matchsTournoiPerdus);
        padding = 47 - defaitesStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        stats.append(String.format("║ Points de tournoi : %d", pointsTournoi));
        String pointsStr = String.valueOf(pointsTournoi);
        padding = 38 - pointsStr.length();
        for (int i = 0; i < padding; i++) {
            stats.append(" ");
        }
        stats.append("║\n");
        
        // Calcul du taux de victoire
        if (matchsTournoiJoues > 0) {
            double tauxVictoire = (double) matchsTournoiGagnes / matchsTournoiJoues * 100;
            stats.append(String.format("║ Taux de victoire : %.1f%%", tauxVictoire));
            String tauxStr = String.format("%.1f%%", tauxVictoire);
            padding = 39 - tauxStr.length();
            for (int i = 0; i < padding; i++) {
                stats.append(" ");
            }
            stats.append("║\n");
        }
        
        stats.append("╚═══════════════════════════════════════════════════════════╝\n");
        
        return stats.toString();
    }
    
    @Override
    public void sePresenter() {
        parler("Hi! I am " + getPrenom() + " called '" + getSurnom() + "'.");
        
        // Corrected gender-specific presentation
        if (identifiantGenre != null && !identifiantGenre.isEmpty()) {
            if ("homme".equalsIgnoreCase(genre)) {
                parler("Do you like my " + identifiantGenre + " tee-shirt?");
            } else if ("femme".equalsIgnoreCase(genre)) {
                parler("Do you like my " + identifiantGenre + "?");
            }
        }

        if (boissonFavorite != null) {
            parler("My favorite drink is " + boissonFavorite.getNom() + ".");
        }
        
        // Corrected currency display
        parler("I have " + String.format("%.2f", getPorteMonnaie()) + " euros in my wallet.");
    }
}
