package com.pub.bar;

import com.pub.characters.*;
import java.util.*;

/**
 * Représente un bar avec son personnel, ses clients et ses boissons.
 * 
 * <p>Un bar est géré par une patronne et possède une équipe de personnel
 * (barman, serveurs, serveuses). Il accueille des clients et propose
 * différentes boissons à la consommation.</p>
 * 
 * <p>Cette classe gère les listes de personnel, clients et boissons disponibles,
 * ainsi que les opérations d'ajout et de recherche.</p>
 */
public class Bar {
    private String nom;
    private Patron patronne;
    private List<Human> personnel;
    private List<Client> clients;
    private List<Boisson> consommationsProposees;
    private List<Table> tables;
    
    /**
     * Construit un nouveau bar avec un nom et une patronne.
     * 
     * @param nom Le nom du bar
     * @param patronne La patronne qui gère le bar
     */
    public Bar(String nom, Patron patronne) {
        this.nom = nom;
        this.patronne = patronne;
        this.personnel = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.consommationsProposees = new ArrayList<>();
        this.tables = new ArrayList<>();
        
        // Initialiser 5 tables
        for (int i = 0; i < 5; i++) {
            tables.add(new Table());
        }
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Patron getPatronne() {
        return patronne;
    }
    
    public void setPatronne(Patron patronne) {
        this.patronne = patronne;
    }
    
    public List<Human> getPersonnel() {
        return personnel;
    }
    
    public List<Client> getClients() {
        return clients;
    }
    
    public List<Boisson> getConsommationsProposees() {
        return consommationsProposees;
    }
    
    /**
     * Ajoute un membre du personnel au bar.
     * 
     * @param humain Le membre du personnel à ajouter (Barman, Serveur, Serveuse)
     */
    public void ajouterPersonnel(Human humain) {
        if (humain != null && !personnel.contains(humain)) {
            personnel.add(humain);
        }
    }
    
    /**
     * Ajoute un client à la liste des clients du bar.
     * Cherche une table disponible pour asseoir le client.
     * 
     * @param client Le client à ajouter
     */
    public void ajouterClient(Client client) {
        if (client == null || clients.contains(client)) {
            return;
        }
        
        // Chercher une table avec de la place
        for (Table table : tables) {
            if (!table.estPleine()) {
                if (table.ajouterClient(client)) {
                    clients.add(client);
                    System.out.println("» " + client.getPrenom() + " a été installé(e) à une table.");
                    return;
                }
            }
        }
        
        // Si aucune table n'est disponible
        System.out.println("[!] Le bar est plein, impossible d'ajouter " + client.getPrenom());
    }
    
    /**
     * Ajoute une boisson à la carte des consommations proposées.
     * 
     * @param boisson La boisson à ajouter à la carte
     */
    public void ajouterConsommation(Boisson boisson) {
        if (boisson != null && !consommationsProposees.contains(boisson)) {
            consommationsProposees.add(boisson);
        }
    }
    
    /**
     * Recherche un client par son prénom (insensible à la casse).
     * 
     * @param nom Le prénom du client à rechercher
     * @return Le client trouvé, ou null si aucun client ne correspond
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
     * Récupère le barman du bar.
     * 
     * @return Le barman du bar, ou null si aucun barman n'est présent
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
     * Recherche une boisson par son nom (recherche partielle, insensible à la casse).
     * 
     * @param nom Le nom (ou une partie du nom) de la boisson à rechercher
     * @return La boisson trouvée, ou null si aucune boisson ne correspond
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
