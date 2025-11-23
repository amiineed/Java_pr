package com.pub.bar;

import com.pub.characters.*;
import java.util.*;

/**
 * Represents a bar with its owner, staff, clients, and tables.
 * * @author Amine MOUSSAIF & Naomi NKETSIAH
 * @version 1.0
 */
public class Bar {
    private static final int NOMBRE_TABLES = 5;
    private String nom;
    private Patron patronne;
    private List<Human> personnel;
    private List<Client> clients;
    private List<Boisson> consommationsProposees;
    private List<Table> tables;
    
    /**
     * Constructs a new bar with a name and an owner.
     * * @param nom the name of the bar
     * @param patronne the owner (boss) of the bar
     */
    public Bar(String nom, Patron patronne) {
        this.nom = nom;
        this.patronne = patronne;
        this.personnel = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.consommationsProposees = new ArrayList<>();
        this.tables = new ArrayList<>();
        
        for (int i = 0; i < NOMBRE_TABLES; i++) {
            tables.add(new Table());
        }
    }
    
    /**
     * Returns the name of the bar.
     * * @return the name of the bar
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Sets the name of the bar.
     * * @param nom the new name of the bar
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Returns the owner of the bar.
     * * @return the owner of the bar
     */
    public Patron getPatronne() {
        return patronne;
    }
    
    /**
     * Sets the owner of the bar.
     * * @param patronne the new owner of the bar
     */
    public void setPatronne(Patron patronne) {
        this.patronne = patronne;
    }
    
    /**
     * Returns the list of staff members.
     * * @return the list of staff
     */
    public List<Human> getPersonnel() {
        return personnel;
    }
    
    /**
     * Returns the list of clients in the bar.
     * * @return the list of clients
     */
    public List<Client> getClients() {
        return clients;
    }
    
    /**
     * Returns the list of drinks available in the bar.
     * * @return the list of available drinks
     */
    public List<Boisson> getConsommationsProposees() {
        return consommationsProposees;
    }
    
    /**
     * Adds a staff member to the bar.
     * * @param humain the staff member to add
     */
    public void ajouterPersonnel(Human humain) {
        if (humain != null && !personnel.contains(humain)) {
            personnel.add(humain);
        }
    }
 
    /**
     * Adds a client to the bar and assigns them to an available table.
     * * @param client the client to add
     * @return true if the client was added, false otherwise
     */
    public boolean ajouterClient(Client client) {
        if (client == null || clients.contains(client)) {
            return false;
        }
        
        for (Table table : tables) {
            if (!table.estPleine()) {
                if (table.ajouterClient(client)) {
                    clients.add(client);
                    // OUTPUT ALREADY ENGLISH
                    System.out.println("» " + client.getPrenom() + " has been seated at a table.");
                    return true;
                }
            }
        }
        
        // OUTPUT ALREADY ENGLISH
        System.out.println("[!] The bar is full, impossible to add " + client.getPrenom());
        return false;
    }
    
    /**
     * Adds a drink to the list of proposed consumptions.
     * * @param boisson the drink to add
     */
    public void ajouterConsommation(Boisson boisson) {
        if (boisson != null && !consommationsProposees.contains(boisson)) {
            consommationsProposees.add(boisson);
        }
    }
    
    /**
     * Finds a client by their first name.
     * * @param nom the first name of the client to search for
     * @return the found client, or null if no match is found
     */
    public Client trouverClient(String nom) {
        for (Client client : clients) {
            if (client.getPrenom().equalsIgnoreCase(nom)) {
                return client;
            }
        }
        return null;
    }
    
    /**
     * Returns the bartender of the bar.
     * * @return the bartender, or null if there isn't one
     */
    public Barman getBarman() {
        for (Human humain : personnel) {
            if (humain instanceof Barman) {
                return (Barman) humain;
            }
        }
        return null;
    }

    /**
     * Finds a drink by its name.
     * * @param nom the name of the drink to search for
     * @return the found drink, or null if no match is found
     */
    public Boisson trouverBoisson(String nom) {
        for (Boisson boisson : consommationsProposees) {
            if (boisson.getNom().equalsIgnoreCase(nom) || 
                boisson.getNom().toLowerCase().contains(nom.toLowerCase())) {
                return boisson;
            }
        }
        return null;
    }
}