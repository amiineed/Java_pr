package com.pub.exceptions;

/**
 * Exception levée lorsqu'un Client ou une Équipe ne dispose pas de fonds suffisants.
 * <p>
 * Cette exception checked est lancée lorsqu'un paiement ne peut pas être effectué
 * en raison d'un solde insuffisant. Elle est utilisée pour les transactions de
 * boissons ainsi que pour les frais d'inscription aux tournois.
 * </p>
 * 
 * @author Système de gestion du Bar
 * @version 1.0
 * @see com.pub.characters.Barman#recevoirPaiement(com.pub.characters.Human, double)
 * @see com.pub.game.Tournoi#inscrireEquipe(String, com.pub.characters.Client, com.pub.characters.Client)
 */
public class InsufficientFundsException extends Exception {
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Le message décrivant l'insuffisance de fonds
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    /**
     * Constructeur avec message d'erreur et cause.
     * 
     * @param message Le message décrivant l'insuffisance de fonds
     * @param cause La cause de l'exception
     */
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
