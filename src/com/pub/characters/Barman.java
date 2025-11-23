package com.pub.characters;

import com.pub.bar.*;
import com.pub.exceptions.*;
import java.util.Map;

/**
 * Represents the bartender who manages the drink stock and payments.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Barman extends Human {
    private Map<Boisson, Integer> stock;
    private Caisse caisse;
    
    /**
     * Constructs a new bartender.
     * * @param prenom the first name of the bartender
     * @param surnom the nickname of the bartender
     * @param porteMonnaie the amount of money the bartender has
     * @param stock the stock of drinks managed by the bartender
     * @param caisse the bar's cash register
     */
    public Barman(String prenom, String surnom, double porteMonnaie, Map<Boisson, Integer> stock, Caisse caisse) {
        super(prenom, surnom, porteMonnaie, 50, "Hey!");
        this.stock = stock;
        this.caisse = caisse;
    }
    
    /**
     * Returns the drink stock.
     * * @return the drink stock
     */
    public Map<Boisson, Integer> getStock() {
        return stock;
    }
    
    /**
     * Sets the drink stock.
     * * @param stock the new drink stock
     */
    public void setStock(Map<Boisson, Integer> stock) {
        this.stock = stock;
    }
    
    /**
     * Returns the bar's cash register.
     * * @return the cash register
     */
    public Caisse getCaisse() {
        return caisse;
    }
    
    /**
     * Sets the bar's cash register.
     * * @param caisse the new cash register
     */
    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }
    
    /**
     * Serves a drink by decrementing the stock.
     * * @param boisson the drink to serve
     * @throws OutOfStockException if the drink is unavailable or out of stock
     */
    public void servirBoisson(Boisson boisson) throws OutOfStockException {
        if (stock == null || !stock.containsKey(boisson)) {
            // TRANSLATED EXCEPTION
            throw new OutOfStockException("Drink unavailable: " + boisson.getNom());
        }
        
        int quantite = stock.get(boisson);
        if (quantite <= 0) {
            // TRANSLATED EXCEPTION
            throw new OutOfStockException("No more " + boisson.getNom() + " in stock!");
        }
        
        stock.put(boisson, quantite - 1);
    }
    
    /**
     * Receives a payment from a client and adds the amount to the cash register.
     * * @param client the client paying
     * @param montant the amount to pay
     * @throws NotEnoughMoneyException if the client doesn't have enough money
     */
    public void recevoirPaiement(Human client, double montant) throws NotEnoughMoneyException {
        if (!client.payer(montant)) {
            // TRANSLATED EXCEPTION
            throw new NotEnoughMoneyException(client.getPrenom() + " doesn't have enough money!");
        }
        
        if (caisse != null) {
            caisse.ajouterMontant(montant);
        }
        
        recevoirArgent(montant * 0.1); 
    }
    
    /**
     * Places an order with a supplier to restock drinks.
     * * @param fournisseur the supplier to order from
     * @param boisson the drink to order
     * @param quantite the quantity to order
     */
    public void passerCommande(Fournisseur fournisseur, Boisson boisson, int quantite) {
        if (fournisseur == null || boisson == null || quantite <= 0) {
            // TRANSLATED DIALOGUE
            parler("Invalid order!");
            return;
        }
        
        // TRANSLATED DIALOGUE
        parler("I am ordering " + quantite + " units of " + boisson.getNom() + " from " + fournisseur.getPrenom() + ".");
        
        fournisseur.livrerBoisson(boisson.getNom(), quantite);
        
        if (stock == null) {
            stock = new java.util.HashMap<>();
        }
        
        int stockActuel = stock.getOrDefault(boisson, 0);
        stock.put(boisson, stockActuel + quantite);
        
        // TRANSLATED DIALOGUE
        parler("Stock updated! We now have " + stock.get(boisson) + " units of " + boisson.getNom() + ".");
    }
    
    @Override
    public void parler(String message) {
        // TRANSLATED SLANG: "coco" -> "buddy"
        super.parler(message + ", buddy.");
    }
    
    @Override
    public void boire(Boisson boisson) {
        if (boisson == null) {
            // TRANSLATED DIALOGUE
            parler("I have nothing to drink");
            return;
        }
        
        if (boisson instanceof BoissonAlcoolisee) {
            // TRANSLATED DIALOGUE
            parler("I don't drink alcohol while on duty");
        } else {
            super.boire(boisson);
        }
    }
    
    @Override
    public void sePresenter() {
        // TRANSLATED DIALOGUE
        parler("Hi! I am " + getPrenom() + " '" + getSurnom() + "', your designated bartender!");
    }
}