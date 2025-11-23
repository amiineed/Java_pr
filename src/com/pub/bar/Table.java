package com.pub.bar;

import com.pub.characters.Client;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table in the bar with a limited capacity of clients.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Table {
    private static final int CAPACITE_MAX = 4;
    private List<Client> clientsALaTable = new ArrayList<>();
    
    /**
     * Checks if the table is full.
     * * @return true if the table is full, false otherwise
     */
    public boolean estPleine() {
        return clientsALaTable.size() >= CAPACITE_MAX;
    }
    
    /**
     * Adds a client to the table if it is not full.
     * * @param client the client to add
     * @return true if the client was added, false otherwise
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
     * Returns the list of clients seated at the table.
     * * @return the list of clients
     */
    public List<Client> getClientsALaTable() {
        return clientsALaTable;
    }
    
    /**
     * Returns the number of available seats at the table.
     * * @return the number of available seats
     */
    public int getPlacesDisponibles() {
        return CAPACITE_MAX - clientsALaTable.size();
    }
}