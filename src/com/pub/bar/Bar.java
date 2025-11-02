package com.pub.bar;

import com.pub.characters.*;
import java.util.*;

public class Bar {
    private String nom;
    private Patron patronne;
    private List<Human> personnel;
    private List<Client> clients;
    private List<Boisson> consommationsProposees;
    
    public Bar(String nom, Patron patronne) {
        this.nom = nom;
        this.patronne = patronne;
        this.personnel = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.consommationsProposees = new ArrayList<>();
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
    
    public void ajouterPersonnel(Human humain) {
        if (humain != null && !personnel.contains(humain)) {
            personnel.add(humain);
        }
    }
    
    public void ajouterClient(Client client) {
        if (client != null && !clients.contains(client)) {
            clients.add(client);
        }
    }
    
    public void ajouterConsommation(Boisson boisson) {
        if (boisson != null && !consommationsProposees.contains(boisson)) {
            consommationsProposees.add(boisson);
        }
    }
    
    public Client trouverClient(String nom) {
        for (Client client : clients) {
            if (client.getPrenom().equalsIgnoreCase(nom)) {
                return client;
            }
        }
        return null;
    }
    
    public Barman getBarman() {
        for (Human humain : personnel) {
            if (humain instanceof Barman) {
                return (Barman) humain;
            }
        }
        return null;
    }
    
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
