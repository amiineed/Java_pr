package com.pub.bar;

/**
 * Représente la caisse enregistreuse du bar.
 * 
 * <p>La caisse gère le montant total d'argent disponible dans le bar.
 * Elle permet d'ajouter des revenus (ventes de boissons, inscriptions)
 * et de retirer de l'argent (paiements, retraits de la patronne).</p>
 */
public class Caisse {
    private double montantTotal;
    
    /**
     * Construit une nouvelle caisse avec un montant initial.
     * 
     * @param montantInitial Le montant de départ dans la caisse
     */
    public Caisse(double montantInitial) {
        this.montantTotal = montantInitial;
    }
    
    /**
     * Récupère le montant total actuellement dans la caisse.
     * 
     * @return Le montant total en euros
     */
    public double getMontantTotal() {
        return montantTotal;
    }
    
    /**
     * Ajoute un montant à la caisse (revenus, ventes).
     * 
     * @param montant Le montant à ajouter (doit être positif)
     */
    public void ajouterMontant(double montant) {
        if (montant > 0) {
            this.montantTotal += montant;
        }
    }
    
    /**
     * Retire un montant de la caisse.
     * 
     * @param montant Le montant à retirer (doit être positif et inférieur au total)
     * @return true si le retrait a réussi, false si le montant est invalide ou insuffisant
     */
    public boolean retirerMontant(double montant) {
        if (montant > 0 && montant <= this.montantTotal) {
            this.montantTotal -= montant;
            return true;
        }
        return false;
    }
    
    /**
     * Définit directement le montant total de la caisse.
     * Utilisé principalement pour le chargement d'une sauvegarde.
     * 
     * @param montant Le nouveau montant total
     */
    public void setMontantTotal(double montant) {
        this.montantTotal = montant;
    }
    
    @Override
    public String toString() {
        return "Caisse: " + montantTotal + " euros";
    }
}
