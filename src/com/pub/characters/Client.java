package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.bar.BoissonAlcoolisee;
import com.pub.game.JoueurBelote;

public class Client extends Human implements JoueurBelote {
    private Boisson boissonFavorite;
    private Boisson boissonFavoriteSecours;
    private Boisson boissonActuelle;
    private double niveauAlcoolemie;
    private String identifiantGenre;
    private String genre;
    
    private int nombreVerresConsommes;
    
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
    
    @Override
    public void boire(Boisson boisson) {
        if (boisson == null) {
            parler("Je n'ai rien à boire.");
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
        parler(cri + "Ça fait du bien!");
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
        stats.append(String.format("║        STATISTIQUES DE %-32s║\n", getPrenom().toUpperCase()));
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Surnom : %-48s║\n", getSurnom()));
        stats.append(String.format("║ Argent disponible : %-36s║\n", 
                                   String.format("%.2f euros", getPorteMonnaie())));
        stats.append(String.format("║ Niveau d'alcoolémie : %-32s║\n", 
                                   String.format("%.3f g/L", niveauAlcoolemie)));
        
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        stats.append("║  STATISTIQUES DE CONSOMMATION                             ║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Total de verres consommés : %-33s║\n", nombreVerresConsommes));
        
        if (boissonFavorite != null) {
            stats.append(String.format("║ Boisson favorite : %-38s║\n", boissonFavorite.getNom()));
        }
        
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        stats.append("║  STATISTIQUES DE TOURNOI                                  ║\n");
        stats.append("╠═══════════════════════════════════════════════════════════╣\n");
        
        stats.append(String.format("║ Matchs joués : %-43s║\n", matchsTournoiJoues));
        stats.append(String.format("║ Victoires : %-46s║\n", matchsTournoiGagnes));
        stats.append(String.format("║ Défaites : %-47s║\n", matchsTournoiPerdus));
        stats.append(String.format("║ Points de tournoi : %-38s║\n", pointsTournoi));
        
        if (matchsTournoiJoues > 0) {
            double tauxVictoire = (double) matchsTournoiGagnes / matchsTournoiJoues * 100;
            stats.append(String.format("║ Taux de victoire : %-39s║\n", 
                                       String.format("%.1f%%", tauxVictoire)));
        }
        
        stats.append("╚═══════════════════════════════════════════════════════════╝\n");
        
        return stats.toString();
    }
    
    public void parler(String message, Human destinataire) {
        if (destinataire != null && genre != null) {
            if (this.niveauAlcoolemie > 0.8) {
                if ("homme".equalsIgnoreCase(genre) && destinataire instanceof Serveuse) {
                    message = message + " ma jolie !";
                } else if ("femme".equalsIgnoreCase(genre) && destinataire instanceof Serveur) {
                    message = message + " mon beau !";
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
