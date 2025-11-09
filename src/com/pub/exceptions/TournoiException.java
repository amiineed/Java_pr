package com.pub.exceptions;

/**
 * Exception personnalisée pour les erreurs liées au tournoi de Belote.
 * Lancée lorsque le tournoi est dans un état invalide (non démarré, déjà terminé, etc.).
 */
public class TournoiException extends Exception {
    
    /**
     * Constructeur avec message d'erreur
     * @param message Description de l'erreur
     */
    public TournoiException(String message) {
        super(message);
    }
    
    /**
     * Constructeur avec message et cause
     * @param message Description de l'erreur
     * @param cause Exception à l'origine de cette erreur
     */
    public TournoiException(String message, Throwable cause) {
        super(message, cause);
    }
}
