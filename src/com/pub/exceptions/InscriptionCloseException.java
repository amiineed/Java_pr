package com.pub.exceptions;

/**
 * Exception runtime levée lorsqu'une tentative d'inscription est faite après le début du tournoi.
 * <p>
 * Cette exception unchecked est lancée automatiquement lorsqu'un joueur ou une équipe
 * tente de s'inscrire à un tournoi dont les inscriptions sont fermées ou qui a déjà démarré.
 * En tant qu'exception runtime, elle n'a pas besoin d'être déclarée dans la signature
 * des méthodes.
 * </p>
 * 
 * @author Système de gestion du Bar
 * @version 1.0
 * @see com.pub.game.Tournoi#inscrireEquipe(String, com.pub.characters.Client, com.pub.characters.Client)
 */
public class InscriptionCloseException extends RuntimeException {
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Le message décrivant pourquoi l'inscription est refusée
     */
    public InscriptionCloseException(String message) {
        super(message);
    }
    
    /**
     * Constructeur avec message d'erreur et cause.
     * 
     * @param message Le message décrivant pourquoi l'inscription est refusée
     * @param cause La cause de l'exception
     */
    public InscriptionCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
