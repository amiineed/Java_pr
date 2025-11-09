package com.pub.exceptions;

/**
 * Exception levée lorsqu'une boisson n'est pas disponible dans le stock du bar.
 * <p>
 * Cette exception checked est lancée par le Barman lorsqu'il tente de servir une boisson
 * qui n'existe pas dans le stock ou dont la quantité disponible est épuisée.
 * </p>
 * 
 * @author Système de gestion du Bar
 * @version 1.0
 * @see com.pub.characters.Barman#servirBoisson(com.pub.bar.Boisson)
 */
public class StockRuptureException extends Exception {
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Le message décrivant la rupture de stock
     */
    public StockRuptureException(String message) {
        super(message);
    }
    
    /**
     * Constructeur avec message d'erreur et cause.
     * 
     * @param message Le message décrivant la rupture de stock
     * @param cause La cause de l'exception
     */
    public StockRuptureException(String message, Throwable cause) {
        super(message, cause);
    }
}
