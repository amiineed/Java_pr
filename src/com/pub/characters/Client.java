package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.bar.BoissonAlcoolisee;
import com.pub.game.JoueurBelote;
import com.pub.game.NiveauBelote; // <--- ADDED THIS IMPORT

/**
 * Represents a bar client with their preferences and statistics.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Client extends Human implements JoueurBelote {
    private Boisson boissonFavorite;
    private Boisson boissonFavoriteSecours;
    private Boisson boissonActuelle;
    private double niveauAlcoolemie;
    private String identifiantGenre;
    private String genre;
    
    private int nombreVerresConsommes;
    
    // Added strictly for the interface implementation logic to work
    private int matchsTournoiJoues;
    private int matchsTournoiGagnes;
    private int matchsTournoiPerdus;
    private int pointsTournoi;
    private NiveauBelote niveauBelote; // <--- CHANGED FROM int TO NiveauBelote

    /**
     * Constructs a new client.
     * * @param prenom the first name
     * @param surnom the nickname
     * @param porteMonnaie the money available
     * @param popularite the popularity level
     * @param criSignificatif the client's signature yell/catchphrase
     * @param boissonFavorite the favorite drink
     * @param boissonFavoriteSecours the backup favorite drink
     * @param boissonActuelle the current drink
     * @param identifiantGenre the gender identifier (e.g., text on t-shirt)
     * @param genre the gender (for logic)
     */
    public Client(String prenom, String surnom, double porteMonnaie, int popularite, 
                  String criSignificatif, Boisson boissonFavorite, Boisson boissonFavoriteSecours, Boisson boissonActuelle, String identifiantGenre, String genre) {
        super(prenom, surnom, porteMonnaie, popularite, criSignificatif);
        this.boissonFavorite = boissonFavorite;
        this.boissonFavoriteSecours = boissonFavoriteSecours;
        this.boissonActuelle = boissonActuelle;
        this.niveauAlcoolemie = 0.0;
        this.identifiantGenre = identifiantGenre;
        this.genre = genre;
        this.nombreVerresConsommes = 0;
    }
    
    /**
     * Copy constructor.
     * * @param other the client to copy
     */
    public Client(Client other) {
        super(other.getPrenom(), other.getSurnom(), other.getPorteMonnaie(), other.getPopularite(), other.getCriSignificatif());
        this.boissonFavorite = other.boissonFavorite;
        this.boissonFavoriteSecours = other.boissonFavoriteSecours;
        this.boissonActuelle = other.boissonActuelle;
        this.niveauAlcoolemie = other.niveauAlcoolemie;
        this.identifiantGenre = other.identifiantGenre;
        this.genre = other.genre;
        this.nombreVerresConsommes = other.nombreVerresConsommes;
        this.setNiveauBelote(other.getNiveauBelote());
       
    }
    
    // [Standard Getters and Setters]
    
    public Boisson getBoissonFavorite() {
        return boissonFavorite;
    }
    
    public void setBoissonFavorite(Boisson boissonFavorite) {
        this.boissonFavorite = boissonFavorite;
    }
    
    public Boisson getBoissonFavoriteSecours() {
        return boissonFavoriteSecours;
    }
    
    public void setBoissonFavoriteSecours(Boisson boissonFavoriteSecours) {
        this.boissonFavoriteSecours = boissonFavoriteSecours;
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
    
    public String getIdentifiantGenre() {
        return identifiantGenre;
    }
    
    public void setIdentifiantGenre(String identifiantGenre) {
        this.identifiantGenre = identifiantGenre;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // FIXED: Changed 'int' to 'NiveauBelote' to match the interface
    public NiveauBelote getNiveauBelote() { 
        return niveauBelote; 
    }
    
    public void setNiveauBelote(NiveauBelote niveau) { 
        this.niveauBelote = niveau; 
    }
    
    @Override
    public void boire(Boisson boisson) {
        if (boisson == null) {
            parler("I have nothing to drink.");
            return;
        }
        this.boissonActuelle = boisson;
        this.nombreVerresConsommes++;
        if (boisson instanceof BoissonAlcoolisee) {
            BoissonAlcoolisee boissonAlcoolisee = (BoissonAlcoolisee) boisson;
            this.niveauAlcoolemie += boissonAlcoolisee.getDegreAlcool() * 0.01;
        }
        String cri = getCriSignificatif();
        if (cri == null || cri.isEmpty()) {
            cri = "";
        } else {
            cri = cri + " ";
        }
        parler(cri + "That hits the spot!");
    }
    
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
    
    public void enregistrerMatchTournoi(boolean victoire) {
        this.matchsTournoiJoues++;
        if (victoire) {
            this.matchsTournoiGagnes++;
            this.pointsTournoi += 3; 
        } else {
            this.matchsTournoiPerdus++;
        }
    }
    
    public String getStatistiquesDetailles() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n╔═══════════════════════════════════════════════════════════╗\n");
        stats.append(String.format("║        STATISTICS OF %-36s║\n", getPrenom().toUpperCase()));
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Nickname : %-46s║\n", getSurnom()));
        stats.append(String.format("║ Available cash : %-40s║\n", 
                                   String.format("%.2f euros", getPorteMonnaie())));
        stats.append(String.format("║ Blood Alcohol Level : %-33s║\n", 
                                   String.format("%.3f g/L", niveauAlcoolemie)));
        
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        stats.append("║  CONSUMPTION STATISTICS                                   ║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Total drinks consumed : %-33s║\n", nombreVerresConsommes));
        
        if (boissonFavorite != null) {
            stats.append(String.format("║ Favorite drink : %-40s║\n", boissonFavorite.getNom()));
        }
        
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        stats.append("║  TOURNAMENT STATISTICS                                    ║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Matches played : %-40s║\n", matchsTournoiJoues));
        stats.append(String.format("║ Wins : %-50s║\n", matchsTournoiGagnes));
        stats.append(String.format("║ Losses : %-48s║\n", matchsTournoiPerdus));
        stats.append(String.format("║ Tournament points : %-37s║\n", pointsTournoi));
        
        if (matchsTournoiJoues > 0) {
            double tauxVictoire = (double) matchsTournoiGagnes / matchsTournoiJoues * 100;
            stats.append(String.format("║ Win rate : %-46s║\n", 
                                       String.format("%.1f%%", tauxVictoire)));
        }
        
        stats.append("╚═══════════════════════════════════════════════════════════╝\n");
        
        return stats.toString();
    }
    
    public void parler(String message, Human destinataire) {
        if (destinataire != null && genre != null) {
            if (this.niveauAlcoolemie > 0.8) {
                if ("homme".equalsIgnoreCase(genre) && destinataire instanceof Serveuse) {
                    message = message + ", gorgeous!";
                } else if ("femme".equalsIgnoreCase(genre) && destinataire instanceof Serveur) {
                    message = message + ", handsome!";
                }
            }
        }
        super.parler(message);
    }
    
    @Override
    public void sePresenter() {
        parler("Hi! I am " + getPrenom() + " called '" + getSurnom() + "'.");
        
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
        
        parler("I have " + String.format("%.2f", getPorteMonnaie()) + " euros in my wallet.");
    }
}