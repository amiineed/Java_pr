package com.pub.game;

/**
 * Interface pour tous les personnages pouvant participer à un tournoi de Belote.
 * Implémentée par Client, Serveur, et Serveuse.
 */
public interface JoueurBelote {
    /**
     * Gets the role of the player in the game.
     * @return The role (default is "Joueur")
     */
    default String getRoleJoueur() {
        return "Joueur";
    }
}
