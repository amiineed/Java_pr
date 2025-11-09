package com.pub.exceptions;

/**
 * Exception runtime levée lorsqu'une action non autorisée est tentée.
 * <p>
 * Cette exception unchecked est utilisée pour bloquer les actions interdites par
 * les règles métier de l'application, telles que :
 * </p>
 * <ul>
 *   <li>Un Barman qui tente de boire une boisson alcoolisée (interdit pendant le service)</li>
 *   <li>Un Barman qui tente de s'inscrire à un tournoi (doit rester neutre)</li>
 *   <li>Toute autre action violant les règles de gestion du bar</li>
 * </ul>
 * <p>
 * En tant qu'exception runtime, elle n'a pas besoin d'être déclarée dans la signature
 * des méthodes.
 * </p>
 * 
 * @author Système de gestion du Bar
 * @version 1.0
 * @see com.pub.characters.Barman
 */
public class UnauthorizedActionException extends RuntimeException {
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Le message décrivant l'action non autorisée
     */
    public UnauthorizedActionException(String message) {
        super(message);
    }
    
    /**
     * Constructeur avec message d'erreur et cause.
     * 
     * @param message Le message décrivant l'action non autorisée
     * @param cause La cause de l'exception
     */
    public UnauthorizedActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
