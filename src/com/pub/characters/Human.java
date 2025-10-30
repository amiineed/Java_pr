package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.exceptions.NotEnoughMoneyException;
import java.util.Objects;

public abstract class Human {
    private final String prenom;
    protected String surnom;
    protected double porteMonnaie;
    protected int popularite;
    protected String criSignificatif;

    private static int humanCount = 0;

    public Human(String prenom, String surnom, double porteMonnaie, int popularite, String criSignificatif) {
        this.prenom = prenom;
        this.surnom = surnom;
        this.porteMonnaie = Math.max(0, porteMonnaie);
        this.popularite = popularite;
        this.criSignificatif = (criSignificatif == null || criSignificatif.isEmpty()) ? "Youpi!" : criSignificatif;
        humanCount++;
    }

    public String getPrenom() { return prenom; }
    public String getSurnom() { return surnom; }
    public double getPorteMonnaie() { return porteMonnaie; }
    public int getPopularite() { return popularite; }
    public String getCriSignificatif() { return criSignificatif; }

    public static int getHumanCount() {
        return humanCount;
    }

    public void setSurnom(String surnom) { this.surnom = surnom; }
    public void setPopularite(int popularite) {
        this.popularite = Math.max(0, popularite);
    }

    public void parler(String message) {
        System.out.println(this.getPrenom() + ": " + message);
    }

    public void boire(Boisson boisson) {
        parler("Glou glou, je bois " + boisson.getNom());
    }

    public void payer(double montant) {
        if (montant < 0) {
             parler("On essaie de me donner de l'argent ? Bizarre.");
             recevoir(montant * -1);
             return;
        }
        if (this.porteMonnaie >= montant) {
            this.porteMonnaie -= montant;
            parler("Voilà " + montant + " euros. Il me reste " + String.format("%.2f", this.porteMonnaie) + " euros.");
        } else {
            throw new NotEnoughMoneyException(this.prenom + " n'a pas assez d'argent! Manque " + String.format("%.2f", (montant - this.porteMonnaie)));
        }
    }

    public void recevoir(double montant) {
        if (montant > 0) {
            this.porteMonnaie += montant;
            parler("Merci! J'ai maintenant " + String.format("%.2f", this.porteMonnaie) + " euros.");
        }
    }

    public void offrirVerre(Human destinataire, Boisson boisson) {
        parler("Tiens " + destinataire.getPrenom() + ", c'est pour moi! Un(e) " + boisson.getNom() + ".");
        try {
            payer(boisson.getPrixVente());
            destinataire.recevoirVerre(boisson, this);
        } catch (NotEnoughMoneyException e) {
            parler("Oups, en fait je n'ai pas assez d'argent... Désolé " + destinataire.getPrenom() + ".");
        }
    }

    public void recevoirVerre(Boisson boisson, Human offreur) {
        parler("Oh merci " + offreur.getPrenom() + " pour le/la " + boisson.getNom() + "!");
        boire(boisson);
    }

    public abstract void sePresenter();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "prenom='" + prenom + '\'' +
                ", surnom='" + surnom + '\'' +
                ", porteMonnaie=" + String.format("%.2f", porteMonnaie) +
                ", popularite=" + popularite +
                ", cri='" + criSignificatif + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(prenom, human.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prenom);
    }
}
