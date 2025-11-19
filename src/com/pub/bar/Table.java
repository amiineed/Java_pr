package com.pub.bar;

import com.pub.characters.Client;
import java.util.ArrayList;
import java.util.List;


public class Table {
    private final int CAPACITE_MAX = 4;
    private List<Client> clientsALaTable = new ArrayList<>();
    

    public boolean estPleine() {
        return clientsALaTable.size() >= CAPACITE_MAX;
    }
    

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
    
  
    public List<Client> getClientsALaTable() {
        return clientsALaTable;
    }
    

    public int getPlacesDisponibles() {
        return CAPACITE_MAX - clientsALaTable.size();
    }
}
