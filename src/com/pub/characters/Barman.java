package com.pub.characters;

import com.pub.bar.*;
import com.pub.exceptions.*;
import java.util.Map;

public class Barman extends Human {
    private Map<Boisson, Integer> stock;
    private Caisse caisse;
    
    public Barman(String prenom, String surnom, double porteMonnaie, Map<Boisson, Integer> stock, Caisse caisse) {
        super(prenom, surnom, porteMonnaie, 50, "Hey !");
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
        
        recevoirArgent(montant * 0.1); 
    }
    
  
    public void passerCommande(Fournisseur fournisseur, Boisson boisson, int quantite) {
        if (fournisseur == null || boisson == null || quantite <= 0) {
            parler("Commande invalide!");
            return;
        }
        
        parler("Je passe commande de " + quantite + " unités de " + boisson.getNom() + " auprès de " + fournisseur.getPrenom() + ".");
        
        fournisseur.livrerBoisson(boisson.getNom(), quantite);
        
        if (stock == null) {
            stock = new java.util.HashMap<>();
        }
        
        int stockActuel = stock.getOrDefault(boisson, 0);
        stock.put(boisson, stockActuel + quantite);
        
        parler("Stock mis à jour ! Nous avons maintenant " + stock.get(boisson) + " unités de " + boisson.getNom() + ".");
    }
    
    @Override
    public void parler(String message) {
        super.parler(message + ", coco.");
    }
    
    @Override
    public void boire(Boisson boisson) {
        if (boisson == null) {
            parler("Je n'ai rien à boire");
            return;
        }
        
        if (boisson instanceof BoissonAlcoolisee) {
            parler("Je ne bois pas d'alcool en service");
        } else {
            super.boire(boisson);
        }
    }
    
    @Override
    public void sePresenter() {
        parler("Salut! Je suis " + getPrenom() + " '" + getSurnom() + "', votre barman attitré!");
    }
}
