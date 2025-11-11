package com.pub.main;

import com.pub.characters.Human;
import com.pub.characters.Client;
import com.pub.bar.Bar;
import java.io.File;

public class Helper {
    /**
     * Trouve un joueur (Client ou Personnel) dans le bar par son prénom
     */
    public static Human trouverJoueurParPrenom(Bar bar, String prenom) {
        if (bar == null || prenom == null) {
            return null;
        }
        
        // Chercher dans les clients
        if (bar.getClients() != null) {
            for (Client client : bar.getClients()) {
                if (client.getPrenom().equals(prenom)) {
                    return client;
                }
            }
        }
        
        // Chercher dans le personnel
        if (bar.getPersonnel() != null) {
            for (Human personnel : bar.getPersonnel()) {
                if (personnel.getPrenom().equals(prenom)) {
                    return personnel;
                }
            }
        }
        
        return null;
    }
    
    /**
     * S'assure qu'un dossier existe, le crée si nécessaire
     * @param path Chemin du dossier à créer
     * @return true si le dossier existe ou a été créé avec succès, false sinon
     */
    public static boolean ensureDirectoryExists(String path) {
        File directory = new File(path);
        
        // Si le dossier existe déjà, retourner true
        if (directory.exists()) {
            return directory.isDirectory();
        }
        
        // Créer le dossier (et tous les dossiers parents si nécessaire)
        return directory.mkdirs();
    }
}
