package com.pub.main;

import com.pub.characters.Human;
import com.pub.characters.Client;
import com.pub.bar.Bar;
import java.io.File;

public class Helper {
    
    public static Human trouverJoueurParPrenom(Bar bar, String prenom) {
        if (bar == null || prenom == null) {
            return null;
        }
        
        if (bar.getClients() != null) {
            for (Client client : bar.getClients()) {
                if (client.getPrenom().equals(prenom)) {
                    return client;
                }
            }
        }
        
        if (bar.getPersonnel() != null) {
            for (Human personnel : bar.getPersonnel()) {
                if (personnel.getPrenom().equals(prenom)) {
                    return personnel;
                }
            }
        }
        
        return null;
    }
    

    public static boolean ensureDirectoryExists(String path) {
        File directory = new File(path);
        
        if (directory.exists()) {
            return directory.isDirectory();
        }
        
        return directory.mkdirs();
    }
}
