package com.pub.characters;

import com.pub.bar.*;
import com.pub.exceptions.*;
import java.util.Map;

public class Barman extends Human {
    private Map<Boisson, Integer> stock;
    private Caisse caisse;
    
    public Barman(String prenom, String surnom, double porteMonnaie, Map<Boisson, Integer> stock, Caisse caisse) {
        super(prenom, surnom, porteMonnaie, 50);
        this.stock = stock;
        this.caisse = caisse;
    }
    
    public Map<Boisson, Integer> getStock() {
        return stock;
    }
    
    public void setStock(Map<Boisson, Integer> stock) {
        this.stock = stock;
    }
    
    public Caisse getCaisse() {
        return caisse;
    }
    
    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    
    public void servirBoisson(Boisson boisson) throws OutOfStockException {
        if (stock == null || !stock.containsKey(boisson)) {
            throw new OutOfStockException("Boisson non disponible: " + boisson.getNom());
        }
        
        int quantite = stock.get(boisson);
        if (quantite <= 0) {
            throw new OutOfStockException("Plus de " + boisson.getNom() + " en stock!");
        }
        
        stock.put(boisson, quantite - 1);
    }
    
    public void recevoirPaiement(Human client, double montant) throws NotEnoughMoneyException {
        if (!client.payer(montant)) {
            throw new NotEnoughMoneyException(client.getPrenom() + " n'a pas assez d'argent!");
        }
        
        if (caisse != null) {
            caisse.ajouterMontant(montant);
        }
        
        recevoirArgent(montant * 0.1); // Le barman garde 10% de pourboire
    }
    
    @Override
    public void sePresenter() {
        parler("Salut! Je suis " + getPrenom() + " '" + getSurnom() + "', votre barman attitré!");
    }
}
