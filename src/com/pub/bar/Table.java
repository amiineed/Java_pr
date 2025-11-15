package com.pub.bar;

import com.pub.characters.Client;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une table dans le bar pouvant accueillir jusqu'à 4 clients.
 */
public class Table {
    private final int CAPACITE_MAX = 4;
    private List<Client> clientsALaTable = new ArrayList<>();
    
    /**
     * Vérifie si la table est pleine.
     * 
     * @return true si la table a atteint sa capacité maximale, false sinon
     */
    public boolean estPleine() {
        return clientsALaTable.size() >= CAPACITE_MAX;
    }
    
    /**
     * Ajoute un client à la table si elle n'est pas pleine.
     * 
     * @param client Le client à ajouter
     * @return true si le client a été ajouté, false si la table est pleine
     */
    public boolean ajouterClient(Client client) {
        if (client == null) {
            return false;
        }
        
        if (!estPleine()) {
            clientsALaTable.add(client);
            return true;
        }
        
        return false;
    }
    
    /**
     * Récupère la liste des clients assis à cette table.
     * 
     * @return La liste des clients
     */
    public List<Client> getClientsALaTable() {
        return clientsALaTable;
    }
    
    /**
     * Récupère le nombre de places disponibles.
     * 
     * @return Le nombre de places libres
     */
    public int getPlacesDisponibles() {
        return CAPACITE_MAX - clientsALaTable.size();
    }
}
