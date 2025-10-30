package com.pub.characters;

import com.pub.bar.Boisson;
import com.pub.bar.Caisse;
import com.pub.bar.BoissonAlcoolisee;
import com.pub.exceptions.OutOfStockException;
import com.pub.exceptions.NotEnoughMoneyException;
import java.util.Map; // For stocks

// Concept: extends (Inheritance) (Java1.3)
public class Barman extends Human {

    private Map<Boisson, Integer> stock; // Simplified stock
    private Caisse caisse;

    // Concept: Constructor (Java1.2)
    public Barman(String prenom, String surnom, double porteMonnaie, Map<Boisson, Integer> stock, Caisse caisse) {
        // Concept: super() call (Java1.3)
        super(prenom, surnom, porteMonnaie, 10, "Santé!"); // Barman starts popular
        this.stock = stock;
        this.caisse = caisse;
    }

    // Concept: Method Override (@Override) (Java1.3)
    @Override
    public void sePresenter() {
        super.parler("Je suis " + getPrenom() + ", votre barman attitré.");
    }

    // Concept: Method Override (@Override) to add "coco" (Java1.3)
    @Override
    public void parler(String message) {
        // Calling super.parler would print the unmodified version first.
        // We want to modify the output string directly.
        System.out.println("Barman " + this.getPrenom() + ": " + message + ", coco.");
    }

    // Barman specific actions
    public Boisson servirBoisson(Boisson boissonDemandee) throws OutOfStockException {
        parler("Un(e) " + boissonDemandee.getNom() + ", ça arrive!");
        int currentStock = stock.getOrDefault(boissonDemandee, 0);
        if (currentStock > 0) {
            stock.put(boissonDemandee, currentStock - 1);
            return boissonDemandee;
        } else {
            // Concept: Throw custom unchecked exception (Java1.6)
            throw new OutOfStockException("Désolé, plus de " + boissonDemandee.getNom() + " en stock");
        }
    }

     public void recevoirPaiement(Client client, double montant) {
         parler("Merci " + client.getPrenom() + ".");
         // Concept: try-catch for potential exception (Java1.6)
         try {
            client.payer(montant);
            caisse.ajouter(montant);
            parler("Montant reçu. La caisse contient " + String.format("%.2f", caisse.getMontantTotal()));
         } catch (NotEnoughMoneyException e) {
             parler("Attendez voir... " + e.getMessage() + ". Vous vous moquez de moi ?");
             // Maybe call Patron?
         }
     }

     public void rendreMonnaie(Client client, double montantARendre) {
         if (caisse.getMontantTotal() >= montantARendre) {
             caisse.retirer(montantARendre);
             client.recevoir(montantARendre);
             parler("Et voilà la monnaie pour " + client.getPrenom() + ".");
         } else {
             parler("Oups, je n'ai pas assez de monnaie en caisse!");
         }
     }


    @Override
    public void boire(Boisson boisson) {
        if (boisson instanceof BoissonAlcoolisee) {
            parler("Ah non, pas d'alcool pour moi pendant le service, coco.");
        } else {
            super.boire(boisson);
        }
    }

     @Override
     public void payer(double montant) {
         parler("Je ne paye pas mes consommations ici, coco.");
         // Barman doesn't pay
     }

     // Barman can offer drinks in specific cases (paid by him)
     public void offrirVerreSpecial(Client client) {
         // Simplified condition: client is regular (high popularity?) and favorite drink is in stock
         if (client.getPopularite() > 15 && client.getBoissonFavorite() != null && stock.getOrDefault(client.getBoissonFavorite(), 0) > 0) {
             parler("Tiens " + client.getPrenom() + ", celle-ci est pour la maison!");
             try {
                Boisson boissonOfferte = servirBoisson(client.getBoissonFavorite()); // Decrements stock
                // The barman pays from his own pocket (as per requirement)
                super.payer(boissonOfferte.getPrixVente()); // Use super.payer to bypass the Barman's "I don't pay" logic
                client.recevoirVerre(boissonOfferte, this);
             } catch (OutOfStockException | NotEnoughMoneyException e ) {
                 parler("Ah mince, petit problème... une autre fois peut-être!");
             }
         } else {
             parler("Désolé " + client.getPrenom() + ", pas d'offre spéciale pour le moment.");
         }
     }


    // Concept: toString override (Java1.2)
    @Override
    public String toString() {
        return super.toString().replace("}", "") + // Get Human part
                ", gère la caisse" +
                '}';
    }

    // Equals/hashCode rely on Human's implementation (based on prenom)
}