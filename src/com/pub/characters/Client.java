package com.pub.characters;

import com.pub.bar.Boisson;

public class Client extends Human {
    private Boisson boissonFavorite;
    private Boisson boissonActuelle;
    private double niveauAlcoolemie;
    private String criSignificatif;
    private String identifiantGenre;
    
    public Client(String prenom, String surnom, double porteMonnaie, int popularite, 
                  String criSignificatif, Boisson boissonFavorite, Boisson boissonActuelle, String identifiantGenre) {
        super(prenom, surnom, porteMonnaie, popularite);
        this.boissonFavorite = boissonFavorite;
        this.boissonActuelle = boissonActuelle;
        this.niveauAlcoolemie = 0.0;
        this.criSignificatif = criSignificatif;
        this.identifiantGenre = identifiantGenre;
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
        if (boisson instanceof com.pub.bar.BoissonAlcoolisee) {
            com.pub.bar.BoissonAlcoolisee boissonAlcoolisee = (com.pub.bar.BoissonAlcoolisee) boisson;
            this.niveauAlcoolemie += boissonAlcoolisee.getDegreAlcool() * 0.01;
        }
        parler(criSignificatif + " Ça fait du bien!");
    }
    
    @Override
    public void sePresenter() {
        parler("Hi! I am " + getPrenom() + " called '" + getSurnom() + "'.");
        if (identifiantGenre != null && !identifiantGenre.isEmpty()) {
            parler("Do you like my " + identifiantGenre + " ?");
        }
        if (boissonFavorite != null) {
            parler("My favorite drink is " + boissonFavorite.getNom() + ".");
        }
        parler("I have " + String.format("%.2f", getPorteMonnaie()) + "€ in my wallet.");
    }
}
