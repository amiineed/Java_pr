package com.pub.bar;

import com.pub.characters.*;
import java.util.*;

public class Bar {
    private String nom;
    private Patron patronne;
    private List<Human> personnel;
    private List<Client> clients;
    private List<Boisson> consommations;
    private Barman barman;

    public Bar(String nom, Patron patronne) {
        this.nom = nom;
        this.patronne = patronne;
        this.personnel = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.consommations = new ArrayList<>();
    }

    public String getNom() { return nom; }
    public Patron getPatronne() { return patronne; }
    public List<Human> getPersonnel() { return personnel; }
    public List<Client> getClients() { return clients; }
    public List<Boisson> getConsommationsProposees() { return consommations; }
    public Barman getBarman() { return barman; }

    public void ajouterPersonnel(Human person) {
        personnel.add(person);
        if (person instanceof Barman) {
            barman = (Barman)person;
        }
    }

    public void ajouterClient(Client client) {
        clients.add(client);
    }

    public void ajouterConsommation(Boisson boisson) {
        consommations.add(boisson);
    }

    public Client trouverClient(String nom) {
        return clients.stream()
            .filter(c -> c.getPrenom().equalsIgnoreCase(nom))
            .findFirst()
            .orElse(null);
    }

    public Boisson trouverBoisson(String nom) {
        return consommations.stream()
            .filter(b -> b.getNom().equalsIgnoreCase(nom))
            .findFirst()
            .orElse(null);
    }
}
