package com.pub.main;

import com.pub.bar.*;
import com.pub.characters.*;
import com.pub.exceptions.*;
import com.pub.game.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Bar leBar;
    private static Tournoi tournoiEnCours;
    private static Fournisseur fournisseur;

    public static void main(String[] args) {
        initialiserBar();

        System.out.println("Command line arguments received: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Arg[" + i + "]: " + args[i]);
            if ("STOP".equalsIgnoreCase(args[i])) {
                System.out.println("Argument 'STOP' detected, early stop requested (demonstration).");
            }
            if ("SKIP".equalsIgnoreCase(args[i])) {
                System.out.println("Argument 'SKIP' detected, ignoring (demonstration).");
                continue;
            }
        }

        int choix;
        do {
            afficherMenuPrincipal();
            choix = lireChoixUtilisateur(0, 9);

            switch (choix) {
                case 1:
                    creerPersonnage();
                    break;
                case 2:
                    listerPersonnages();
                    break;
                case 3:
                    commanderBoisson();
                    break;
                case 4:
                    gererTournoi();
                    break;
                case 5:
                    afficherStatistiquesJoueur();
                    break;
                case 6:
                    sauvegarderEtatSimplifie("bar_state.txt");
                    break;
                case 7:
                    chargerEtatSimplifie("bar_state.txt");
                    break;
                case 8:
                    actionsSpeciales();
                    break;
                case 9:
                    creerPersonnageAutomatique();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choix != 0);
        // TRANSLATED
        System.out.println("\nThank you for using the application. See you soon!");
    }

    private static void initialiserBar() {
        Patron patronne = null;
        try {
            patronne = new Patron("Sarah", "Boss", 1000.0);
        } catch (BarException e) {
            System.err.println("Critical error: " + e.getMessage());
            System.exit(1);
        }

        Caisse caisse = new Caisse(200.0);
        Map<Boisson, Integer> stockInitial = new HashMap<>();

        Boisson water = new Boisson("Water", 0.1, 1.0);
        Boisson coffee = new Boisson("Coffee", 0.2, 1.5);
        Boisson tea = new Boisson("Tea", 0.2, 1.5);

        BoissonAlcoolisee beer = new BoissonAlcoolisee("Beer", 0.5, 3.0, 5);
        BoissonAlcoolisee rum = new BoissonAlcoolisee("Rum", 1.0, 5.0, 40);
        BoissonAlcoolisee whisky = new BoissonAlcoolisee("Whisky", 1.5, 6.0, 43);
        BoissonAlcoolisee gin = new BoissonAlcoolisee("Gin", 1.2, 5.5, 40);
        BoissonAlcoolisee bourbon = new BoissonAlcoolisee("Bourbon", 1.5, 6.5, 45);
        BoissonAlcoolisee tequila = new BoissonAlcoolisee("Tequila", 1.2, 5.5, 38);
        Boisson orangeJuice = new Boisson("Orange Juice", 0.3, 2.0);
        Boisson cola = new Boisson("Coca-Cola", 0.3, 2.5);
        BoissonAlcoolisee mojito = new BoissonAlcoolisee("Mojito", 0.8, 7.0, 12);
        BoissonAlcoolisee wine = new BoissonAlcoolisee("Wine", 0.7, 4.0, 13);

        stockInitial.put(water, 50);
        stockInitial.put(coffee, 30);
        stockInitial.put(tea, 30);
        stockInitial.put(beer, 40);
        stockInitial.put(rum, 20);
        stockInitial.put(whisky, 15);
        stockInitial.put(gin, 18);
        stockInitial.put(bourbon, 10);
        stockInitial.put(tequila, 12);
        stockInitial.put(orangeJuice, 35);
        stockInitial.put(cola, 45);
        stockInitial.put(mojito, 25);
        stockInitial.put(wine, 30);

        // TRANSLATED NICKNAMES
        Barman barman = new Barman("Bob", "The Fast", 50.0, stockInitial, caisse);
        Serveur serveur = new Serveur("Luc", "The Strong", 30.0, 15);
        Serveuse serveuse = new Serveuse("Léa", "The Charmer", 30.0, 8);

        leBar = new Bar("Sarah's Bar", patronne);
        if (leBar != null) {
            leBar.ajouterPersonnel(barman);
            leBar.ajouterPersonnel(serveur);
            leBar.ajouterPersonnel(serveuse);

            leBar.ajouterConsommation(water);
            leBar.ajouterConsommation(coffee);
            leBar.ajouterConsommation(tea);
            leBar.ajouterConsommation(beer);
            leBar.ajouterConsommation(rum);
            leBar.ajouterConsommation(whisky);
            leBar.ajouterConsommation(gin);
            leBar.ajouterConsommation(bourbon);
            leBar.ajouterConsommation(tequila);
            leBar.ajouterConsommation(orangeJuice);
            leBar.ajouterConsommation(cola);
            leBar.ajouterConsommation(mojito);
            leBar.ajouterConsommation(wine);
        }

        // Passing logic strings ("homme"/"femme") is fine here as it's internal setup
        Client client1 = new Client("Peter", "The Shy", 40.0, 6, "Oh!", water, beer, beer, "Blue", "homme");
        Client client2 = new Client("Julie", "The Talkative", 30.0, 8, "Ah!", beer, water, water, "Necklace", "femme");
        if (leBar != null) {
            leBar.ajouterClient(client1);
            leBar.ajouterClient(client2);
        }

        // TRANSLATED
        fournisseur = new Fournisseur("Jacques", "The Wholesaler", 500.0, 30, "Drinks & Co");

        System.out.println("Bar initialized: " + (leBar != null ? leBar.getNom() : "N/A"));
        try {
            System.out.println("Total humans created: " + Human.getHumanCount());
        } catch (Throwable t) {
        }
    }

   
    private static void afficherMenuPrincipal() {
        System.out.println();
        System.out.println("--- Main Menu ---");
        System.out.println("1. Create a persona");
        System.out.println("2. List personas");
        System.out.println("3. Order a drink");
        System.out.println("4. Manage Belote Tournament");
        System.out.println("5. View player statistics");
        System.out.println("6. Save");
        System.out.println("7. Load");
        System.out.println("8. Special actions");
        System.out.println("9. Create auto client");
        System.out.println("0. Quit");
        System.out.print("Your choice: ");
    }

 
    private static int lireChoixUtilisateur(int min, int max) {
        int choix = -1;
        boolean validInput;
        do {
            validInput = true;
            try {
                choix = scanner.nextInt();
                if (choix < min || choix > max) {
                    System.out.print("Choice out of range (" + min + "-" + max + "). Try again: ");
                    validInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next();
                validInput = false;
            }
        } while (!validInput);
        scanner.nextLine();
        return choix;
    }


    private static String lireStringUtilisateur(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    private static double lireDoubleUtilisateur(String prompt) {
        double valeur = -1;
        boolean validInput;
        do {
            validInput = true;
            System.out.print(prompt);
            try {
                valeur = scanner.nextDouble();
                if (valeur < 0) {
                    System.out.print("Value must be positive. Try again: ");
                    validInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next();
                validInput = false;
            }
        } while (!validInput);
        scanner.nextLine();
        return valeur;
    }

    private static void creerPersonnage() {
        System.out.println();
        System.out.println("--- Create Persona ---");
        System.out.println("Type? (Client, Server, Waitress)");
        String type = lireStringUtilisateur("Type: ").toLowerCase();
        String prenom = lireStringUtilisateur("First name: ");
        String surnom = lireStringUtilisateur("Nickname: ");
        double argent = lireDoubleUtilisateur("Initial money: ");

        Human nouveau = null;
        switch (type) {
            case "client":
                System.out.println("Favorite drink? (Water, Beer, Rum, Whisky, Gin, Bourbon, Tequila, Tea, Coffee, Orange Juice, Coca-Cola, Mojito, Wine)");
                String fav = lireStringUtilisateur("Favorite drink: ");
                Boisson boissonFav = (leBar != null) ? leBar.trouverBoisson(fav) : null;
                
                System.out.println("Backup favorite drink? (Water, Beer, Rum, etc.)");
                String favSecours = lireStringUtilisateur("Backup drink: ");
                Boisson boissonFavSecours = (leBar != null) ? leBar.trouverBoisson(favSecours) : null;
                
                // TRANSLATED
                String criSignificatif = lireStringUtilisateur("Signature yell (e.g., 'Ah!', 'Oh!'): ");
                if (criSignificatif.trim().isEmpty()) {
                    criSignificatif = "Hey!";
                }
                
                // TRANSLATED LOGIC: Ask for English, Map to French logic
                String sexeInput;
                String sexeLogic;
                do {
                    sexeInput = lireStringUtilisateur("Gender (Male/Female): ").toLowerCase();
                    if (sexeInput.equals("male") || sexeInput.equals("m")) {
                        sexeLogic = "homme";
                    } else if (sexeInput.equals("female") || sexeInput.equals("f")) {
                        sexeLogic = "femme";
                    } else {
                        sexeLogic = null;
                        System.out.println("Please enter 'Male' or 'Female'.");
                    }
                } while (sexeLogic == null);
                
                String identifiantGenre;
                if (sexeLogic.equals("homme")) {
                    // TRANSLATED
                    identifiantGenre = lireStringUtilisateur("T-shirt color: ");
                } else {
                    // TRANSLATED
                    identifiantGenre = lireStringUtilisateur("Jewelry: ");
                }
                
                // We pass 'sexeLogic' (homme/femme) to keep Client.java logic intact
                nouveau = new Client(prenom, surnom, argent, 5, criSignificatif, boissonFav, boissonFavSecours, (leBar != null) ? leBar.trouverBoisson("Water") : null, identifiantGenre, sexeLogic);
                if (leBar != null && leBar.ajouterClient((Client) nouveau)) {
                    System.out.println("client " + prenom + " created.");
                    if (nouveau != null) nouveau.sePresenter();
                }
                break;
            case "serveur":
            case "server":
                // TRANSLATED
                int biceps = (int) lireDoubleUtilisateur("Bicep size (in cm): ");
                nouveau = new Serveur(prenom, surnom, argent, biceps);
                if (leBar != null) {
                    leBar.ajouterPersonnel(nouveau);
                    System.out.println("server " + prenom + " created.");
                    if (nouveau != null) nouveau.sePresenter();
                }
                break;
            case "serveuse":
            case "waitress":
                // TRANSLATED
                int charme = (int) lireDoubleUtilisateur("Charm level (out of 10): ");
                nouveau = new Serveuse(prenom, surnom, argent, charme);
                if (leBar != null) {
                    leBar.ajouterPersonnel(nouveau);
                    System.out.println("waitress " + prenom + " created.");
                    if (nouveau != null) nouveau.sePresenter();
                }
                break;
            default:
                System.out.println("Unknown type.");
                return;
        }
        try {
            System.out.println("Total humans created: " + Human.getHumanCount());
        } catch (Throwable t) {
        }
    }

    private static void listerPersonnages() {
        if (leBar == null) {
            System.out.println("Bar not initialized.");
            return;
        }
        System.out.println();
        System.out.println("--- Personas at the Bar ---");
        System.out.println("Owner: " + (leBar.getPatronne() != null ? leBar.getPatronne().getPrenom() + " '" + leBar.getPatronne().getSurnom() + "'" : "N/A"));
        System.out.println("-- Staff --");
        List<Human> personnel = leBar.getPersonnel();
        if (personnel != null) {
            for (Human p : personnel) {
                System.out.println(String.format("%s %s (nickname: %s) - popularity: %d", p.getClass().getSimpleName(), p.getPrenom(), p.getSurnom(), p.getPopularite()));
            }
        }
        System.out.println("-- Clients --");
        List<Client> clients = leBar.getClients();
        if (clients != null) {
            for (Client c : clients) {
                String fav = (c.getBoissonFavorite() != null) ? c.getBoissonFavorite().getNom() : "None";
                System.out.println(String.format("%s: %s (\"%s\")", c.getPrenom(), "Hi! I am " + c.getPrenom() + " called '" + c.getSurnom() + "'.", ""));
                System.out.println(String.format("%s: Do you like my %s ?", c.getPrenom(), (c.getIdentifiantGenre() != null ? c.getIdentifiantGenre() : "items")));
                System.out.println(String.format("%s: My favorite drink is %s.", c.getPrenom(), fav));
                System.out.println(String.format("%s: I have %.2f euros in my wallet.", c.getPrenom(), c.getPorteMonnaie()));
            }
        }
    }

    private static void commanderBoisson() {
        System.out.println();
        System.out.println("--- Order ---");
        System.out.println("Who orders?");
        String nomClient = lireStringUtilisateur("Client name: ");
        Client client = (leBar != null) ? leBar.trouverClient(nomClient) : null;
        if (client == null) {
            System.out.println("Unknown client.");
            return;
        }

        System.out.println("Available drinks:");
        List<Boisson> boissonsMenu = (leBar != null) ? leBar.getConsommationsProposees() : Collections.emptyList();
        if (boissonsMenu == null || boissonsMenu.isEmpty()) {
            System.out.println("No drinks available right now.");
            return;
        }
        int i = 1;
        for (Boisson b : boissonsMenu) {
            System.out.println(i++ + ". " + b);
        }
        System.out.println("0. Cancel");
        int choixBoisson = lireChoixUtilisateur(0, boissonsMenu.size());
        if (choixBoisson == 0) return;

        int index = choixBoisson - 1;
        if (index < 0 || index >= boissonsMenu.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Boisson boissonChoisie = boissonsMenu.get(index);
        Barman barman = (leBar != null) ? leBar.getBarman() : null;
        if (barman == null) {
            System.out.println("No barman available to serve.");
            return;
        }
        
        Human serveurDuMoment = null;
        if (leBar.getPersonnel() != null && !leBar.getPersonnel().isEmpty()) {
            List<Human> staffActif = new ArrayList<>();
            for (Human p : leBar.getPersonnel()) {
                if (p instanceof Serveur || p instanceof Serveuse) {
                    staffActif.add(p);
                }
            }
            if (!staffActif.isEmpty()) {
                serveurDuMoment = staffActif.get(new Random().nextInt(staffActif.size()));
                System.out.println("» " + serveurDuMoment.getPrenom() + " brings the drink.");
            }
        }

        try {
            barman.servirBoisson(boissonChoisie);
            barman.recevoirPaiement(client, boissonChoisie.getPrixVente());
            client.boire(boissonChoisie);
            System.out.println(client.getPrenom() + " received and paid for " + boissonChoisie.getNom());
            
            if (serveurDuMoment instanceof Serveuse) {
                Serveuse s = (Serveuse) serveurDuMoment;
                if (s.getNiveauCharme() >= 8) { 
                    client.parler(s.getPrenom() + " is so charming! I'll have another one.");
                    try {
                        barman.servirBoisson(boissonChoisie);
                        barman.recevoirPaiement(client, boissonChoisie.getPrixVente());
                        client.boire(boissonChoisie);
                    } catch (Exception e2) {
                        client.parler("Oh, nevermind then...");
                    }
                }
            } else if (serveurDuMoment instanceof Serveur && boissonChoisie instanceof BoissonAlcoolisee) {
                Serveur s = (Serveur) serveurDuMoment;
                if (s.getTailleBiceps() >= 15) { 
                    client.parler("This server " + s.getPrenom() + " looks tough... I'll behave.");
                }
            }
        } catch (OutOfStockException e) {
            System.err.println("Order failed: " + e.getMessage());
            if (barman != null) barman.parler("Sorry " + client.getPrenom() + "...");
            
            if (boissonChoisie.equals(client.getBoissonFavorite())) {
                // TRANSLATED
                System.out.println("\n[!] Your favorite drink is out of stock!");
                Boisson boissonSecours = client.getBoissonFavoriteSecours();
                
                if (boissonSecours != null) {
                    // TRANSLATED
                    System.out.println("Trying backup drink: " + boissonSecours.getNom());
                    client.parler("Well, I'll try " + boissonSecours.getNom() + " then...");
                    
                    try {
                        barman.servirBoisson(boissonSecours);
                        barman.recevoirPaiement(client, boissonSecours.getPrixVente());
                        client.boire(boissonSecours);
                        System.out.println("» " + client.getPrenom() + " received and paid for " + boissonSecours.getNom() + " (backup drink)");
                    } catch (OutOfStockException | NotEnoughMoneyException e2) {
                        System.err.println("Backup order also failed: " + e2.getMessage());
                        // TRANSLATED
                        if (barman != null) barman.parler("Sorry, honestly...");
                    }
                } else {
                    // TRANSLATED
                    System.out.println("No backup drink defined.");
                }
            }
        } catch (NotEnoughMoneyException e) {
            System.err.println("Order failed: " + e.getMessage());
            if (barman != null) barman.parler("Sorry " + client.getPrenom() + "...");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

   
    private static void gererTournoi() {
        if (leBar == null) {
            System.out.println("Bar not initialized.");
            return;
        }

        System.out.println();
        // TRANSLATED
        System.out.println("--- BELOTE TOURNAMENT MANAGEMENT ---");
        System.out.println("1. Create a new tournament");
        System.out.println("2. Register a team");
        System.out.println("3. Start the tournament");
        System.out.println("4. Play next match");
        System.out.println("5. Play full tournament");
        System.out.println("6. Show ranking");
        System.out.println("7. Show registered teams");
        System.out.println("8. Auto-register teams");
        System.out.println("9. Show summary");
        System.out.println("10. Match details");
        System.out.println("0. Back");
        System.out.print("Your choice: ");

        int choix = lireChoixUtilisateur(0, 10);

        switch (choix) {
            case 1:
                creerTournoi();
                break;
            case 2:
                inscrireEquipeTournoi();
                break;
            case 3:
                demarrerTournoi();
                break;
            case 4:
                jouerProchainMatch();
                break;
            case 5:
                jouerTournoiComplet();
                break;
            case 6:
                afficherClassement();
                break;
            case 7:
                afficherEquipesInscrites();
                break;
            case 8:
                inscrireEquipesAutomatiquement();
                break;
            case 9:
                afficherSyntheseTournoi();
                break;
            case 10:
                afficherDetailsMatchs();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private static void creerTournoi() {
        if (tournoiEnCours != null && !tournoiEnCours.isTournoiTermine()) {
            // TRANSLATED
            System.out.println("A tournament is already in progress!");
            System.out.print("Do you want to cancel it and create a new one? (yes/no): ");
            String reponse = scanner.nextLine().toLowerCase();
            // TRANSLATED CHECK
            if (!reponse.equals("yes") && !reponse.equals("y")) {
                return;
            }
        }

        // TRANSLATED
        double frais = lireDoubleUtilisateur("Registration fee per team: ");
        tournoiEnCours = new Tournoi(leBar, frais);
        tournoiEnCours.ouvrirInscriptions();
        // TRANSLATED
        System.out.println("» Tournament created successfully!");
    }

    private static void inscrireEquipeTournoi() {
        if (tournoiEnCours == null) {
            // TRANSLATED
            System.out.println("No tournament in progress. Create a tournament first.");
            return;
        }

        List<Human> joueursDisponibles = new ArrayList<>();
        
        if (leBar.getClients() != null) {
            joueursDisponibles.addAll(leBar.getClients());
        }
        
        if (leBar.getPersonnel() != null) {
            for (Human personnel : leBar.getPersonnel()) {
                if (personnel instanceof com.pub.game.JoueurBelote) {
                    joueursDisponibles.add(personnel);
                }
            }
        }
        
        if (joueursDisponibles.size() < 2) {
            // TRANSLATED
            System.out.println("Not enough players available to form a team.");
            return;
        }

        // TRANSLATED
        System.out.println("\n=== Available Players ===");
        for (int i = 0; i < joueursDisponibles.size(); i++) {
            Human joueur = joueursDisponibles.get(i);
            String type = joueur.getClass().getSimpleName();
            System.out.println((i + 1) + ". " + joueur.getPrenom() + " '" + joueur.getSurnom() + 
                             "' [" + type + "] (" + String.format("%.2f", joueur.getPorteMonnaie()) + " euros)");
        }

        // TRANSLATED
        System.out.print("\nTeam Name: ");
        String nomEquipe = scanner.nextLine();

        System.out.print("First player number: ");
        int idx1 = lireChoixUtilisateur(1, joueursDisponibles.size()) - 1;

        System.out.print("Second player number: ");
        int idx2 = lireChoixUtilisateur(1, joueursDisponibles.size()) - 1;

        if (idx1 == idx2) {
            // TRANSLATED
            System.out.println("You must choose two different players!");
            return;
        }

        Human joueur1 = joueursDisponibles.get(idx1);
        Human joueur2 = joueursDisponibles.get(idx2);

        tournoiEnCours.inscrireEquipe(nomEquipe, joueur1, joueur2);
    }


    private static void demarrerTournoi() {
        if (tournoiEnCours == null) {
            System.out.println("No tournament in progress.");
            return;
        }

        tournoiEnCours.demarrerTournoi();
    }

    private static void jouerProchainMatch() {
        if (tournoiEnCours == null) {
            System.out.println("No tournament in progress.");
            return;
        }
        
        // TRANSLATED
        System.out.println("\nDo you want to participate in this match manually?");
        System.out.print("Enter your first name (or press Enter for AI simulation): ");
        String nomJoueur = scanner.nextLine().trim();
        
        if (nomJoueur.isEmpty()) {
            nomJoueur = null; 
        }
        
        if (!tournoiEnCours.jouerProchainMatch(nomJoueur)) {
        }
    }

    private static void jouerTournoiComplet() {
        if (tournoiEnCours == null) {
            System.out.println("No tournament in progress.");
            return;
        }

        // TRANSLATED
        System.out.println("Launching full tournament...");
        try {
            tournoiEnCours.jouerTournoiComplet();
        } catch (TournoiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void afficherClassement() {
        if (tournoiEnCours == null) {
            System.out.println("No tournament in progress.");
            return;
        }

        tournoiEnCours.getFeuilleDeScore().afficherClassement();
    }

    private static void afficherEquipesInscrites() {
        if (tournoiEnCours == null) {
            System.out.println("No tournament in progress.");
            return;
        }

        tournoiEnCours.afficherEquipesInscrites();
    }

    private static void inscrireEquipesAutomatiquement() {
        if (tournoiEnCours == null) {
            // TRANSLATED
            System.out.println("[!] No tournament in progress. Create a tournament first.");
            return;
        }
        
        if (!tournoiEnCours.isInscriptionsOuvertes()) {
            // TRANSLATED
            System.out.println("[!] Registrations are not open.");
            return;
        }
        
        // TRANSLATED
        System.out.println("\n=== AUTOMATIC TEAM REGISTRATION ===");
        
        List<Human> joueursDisponibles = new ArrayList<>();
        
        if (leBar.getClients() != null) {
            for (Client client : leBar.getClients()) {
                joueursDisponibles.add(client);
            }
        }
        
        if (leBar.getPersonnel() != null) {
            for (Human personnel : leBar.getPersonnel()) {
                if (personnel instanceof com.pub.game.JoueurBelote) {
                    joueursDisponibles.add(personnel);
                }
            }
        }
        
        List<Equipe> equipesInscrites = tournoiEnCours.getEquipesInscrites();
        List<Human> joueursDejaInscrits = new ArrayList<>();
        for (Equipe equipe : equipesInscrites) {
            joueursDejaInscrits.add(equipe.getJoueur1());
            joueursDejaInscrits.add(equipe.getJoueur2());
        }
        joueursDisponibles.removeAll(joueursDejaInscrits);
        
        if (joueursDisponibles.size() < 2) {
            // TRANSLATED
            System.out.println("[!] Not enough players available to form teams.");
            System.out.println("Available players: " + joueursDisponibles.size());
            return;
        }
        
        Collections.shuffle(joueursDisponibles);
        
        Random random = new Random();
        int equipesCreees = 0;
        
        while (joueursDisponibles.size() >= 2) {
            Human joueur1 = joueursDisponibles.remove(0);
            Human joueur2 = joueursDisponibles.remove(0);
            
            String nomEquipe = "AutoTeam" + random.nextInt(100);
            
            // TRANSLATED
            System.out.println("\nCreating team: " + nomEquipe);
            System.out.println("  Player 1: " + joueur1.getPrenom() + " (" + String.format("%.2f", joueur1.getPorteMonnaie()) + " euros)");
            System.out.println("  Player 2: " + joueur2.getPrenom() + " (" + String.format("%.2f", joueur2.getPorteMonnaie()) + " euros)");
            
            tournoiEnCours.inscrireEquipe(nomEquipe, joueur1, joueur2);
            equipesCreees++;
        }
        
        // TRANSLATED
        System.out.println("\n» " + equipesCreees + " automatic team(s) registered!");
        if (joueursDisponibles.size() > 0) {
            System.out.println("[!] " + joueursDisponibles.size() + " player(s) remaining (odd number).");
        }
    }
    
    
    private static void afficherSyntheseTournoi() {
        if (tournoiEnCours == null) {
            System.out.println("[!] No tournament in progress.");
            return;
        }
        
        if (!tournoiEnCours.isTournoiTermine()) {
            // TRANSLATED
            System.out.println("[!] The tournament is not yet finished.");
            System.out.println("Use option 5 to play the full tournament or option 4 to play match by match.");
            return;
        }
        
        // TRANSLATED
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TOURNAMENT SUMMARY");
        System.out.println("=".repeat(60));
        
        List<Equipe> equipes = tournoiEnCours.getEquipesInscrites();
        
        int nombreJoueurs = equipes.size() * 2;
        // TRANSLATED
        System.out.println("\nNumber of teams : " + equipes.size());
        System.out.println("Number of players : " + nombreJoueurs);
        
        int totalVerres = 0;
        List<String> clientsJoueurs = new ArrayList<>();
        
        for (Equipe equipe : equipes) {
            Human joueur1 = equipe.getJoueur1();
            Human joueur2 = equipe.getJoueur2();
            
            if (joueur1 instanceof Client) {
                Client client = (Client) joueur1;
                int verres = client.getNombreVerresConsommes();
                totalVerres += verres;
                clientsJoueurs.add(client.getPrenom() + ": " + verres + " drink(s)");
            }
            
            if (joueur2 instanceof Client) {
                Client client = (Client) joueur2;
                int verres = client.getNombreVerresConsommes();
                totalVerres += verres;
                clientsJoueurs.add(client.getPrenom() + ": " + verres + " drink(s)");
            }
        }
        
        // TRANSLATED
        System.out.println("\n--- CONSUMPTION ---");
        System.out.println("Total drinks consumed by clients : " + totalVerres);
        
        if (!clientsJoueurs.isEmpty()) {
            System.out.println("\nDetail by client :");
            for (String info : clientsJoueurs) {
                System.out.println("  - " + info);
            }
        }
        
        // TRANSLATED
        System.out.println("\n--- FINAL RANKING ---");
        tournoiEnCours.getFeuilleDeScore().afficherClassement();
        
        System.out.println("=".repeat(60));
    }
   
    private static void afficherDetailsMatchs() {
        if (tournoiEnCours == null || tournoiEnCours.getCalendrierDesMatchs().isEmpty()) {
            System.out.println("[!] No tournament started or no matches scheduled.");
            return;
        }

        System.out.println("\n=== DETAIL OF COMPLETED MATCHES ===");

        boolean aucunMatchJoue = true;
        List<Tournoi.Match> calendrier = tournoiEnCours.getCalendrierDesMatchs();

        for (Tournoi.Match match : calendrier) {
            if (match.isJoue()) {
                aucunMatchJoue = false;
                Equipe e1 = match.getEquipe1();
                Equipe e2 = match.getEquipe2();
                Equipe gagnant = match.getGagnant();

                String nomE1 = e1.getNom() + (e1.equals(gagnant) ? " (Winner)" : "");
                String nomE2 = e2.getNom() + (e2.equals(gagnant) ? " (Winner)" : "");

                System.out.printf(" - Match: %s vs %s\n", e1.getNom(), e2.getNom());
                System.out.printf("   Result: %s [%d] - %s [%d]\n\n", 
                                 nomE1, match.getGagnant().equals(e1) ? match.getScoreGagnant() : match.getScorePerdant(), 
                                 nomE2, match.getGagnant().equals(e2) ? match.getScoreGagnant() : match.getScorePerdant());
            }
        }

        if (aucunMatchJoue) {
            System.out.println("[!] No matches have been played yet.");
        }
    }

    
    private static void sauvegarderEtatSimplifie(String nomFichierDefaut) {
        // TRANSLATED
        System.out.println("\n=== SAVE YOUR GAME ===");
        System.out.print("Enter a name for your save (ex: my_game) : ");
        String nomBase = scanner.nextLine().trim();
        
        if (nomBase.isEmpty()) {
            nomBase = "bar_state";
            System.out.println("Empty name. Using default: " + nomBase);
        }
        
        String nomFichier = nomBase.endsWith(".barsave") ? nomBase : nomBase + ".barsave";
        
        Helper.ensureDirectoryExists("saves");
        
        String cheminComplet = "saves/" + nomFichier;
        
        File fichier = new File(cheminComplet);
        if (fichier.exists()) {
            // TRANSLATED
            System.out.print("[!] File '" + nomFichier + "' already exists. Overwrite? (yes/no) : ");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (!reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("y") && !reponse.equals("yes")) {
                System.out.println("Save cancelled.");
                return;
            }
        }
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(cheminComplet));
            
            writer.println("=== BAR ===");
            writer.println("State of bar: " + (leBar != null ? leBar.getNom() : "UNINITIALIZED"));
            try {
                if (leBar != null && leBar.getBarman() != null && leBar.getBarman().getCaisse() != null) {
                    writer.println("Cash: " + leBar.getBarman().getCaisse().getMontantTotal());
                } else {
                    writer.println("Cash: N/A");
                }
            } catch (Exception ex) {
                writer.println("Cash: N/A");
            }

            writer.println("--- Clients ---");
            if (leBar != null && leBar.getClients() != null) {
                for (Client c : leBar.getClients()) {
                    writer.println(c.getPrenom() + ";" + c.getPorteMonnaie() + ";" + c.getNiveauAlcoolemie());
                }
            }
            
            writer.println("--- Personnel ---");
            if (leBar != null && leBar.getPersonnel() != null) {
                for (Human personnel : leBar.getPersonnel()) {
                    if (personnel instanceof com.pub.characters.Serveur) {
                        com.pub.characters.Serveur serveur = (com.pub.characters.Serveur) personnel;
                        writer.println("Serveur;" + serveur.getPrenom() + ";" + serveur.getSurnom() + ";" + 
                                     serveur.getPorteMonnaie() + ";" + serveur.getTailleBiceps());
                    } else if (personnel instanceof com.pub.characters.Serveuse) {
                        com.pub.characters.Serveuse serveuse = (com.pub.characters.Serveuse) personnel;
                        writer.println("Serveuse;" + serveuse.getPrenom() + ";" + serveuse.getSurnom() + ";" + 
                                     serveuse.getPorteMonnaie() + ";" + serveuse.getNiveauCharme());
                    }
                }
            }
            
            writer.println("=== TOURNOI ===");
            if (tournoiEnCours == null) {
                writer.println("TournoiActif: false");
            } else {
                writer.println("TournoiActif: true");
                writer.println("FraisInscription: " + tournoiEnCours.getFraisInscription());
                writer.println("InscriptionsOuvertes: " + tournoiEnCours.isInscriptionsOuvertes());
                writer.println("TournoiDemarre: " + tournoiEnCours.isTournoiDemarre());
                writer.println("TournoiTermine: " + tournoiEnCours.isTournoiTermine());
                
                writer.println("--- Equipes ---");
                List<Equipe> equipes = tournoiEnCours.getEquipesInscrites();
                for (Equipe equipe : equipes) {
                    writer.println(equipe.getNom() + ";" + 
                                 equipe.getJoueur1().getPrenom() + ";" + 
                                 equipe.getJoueur2().getPrenom() + ";" +
                                 equipe.getPoints() + ";" +
                                 equipe.getMatchsJoues() + ";" +
                                 equipe.getMatchsGagnes());
                }
                

            }
            
            // TRANSLATED
            System.out.println("» State saved in '" + cheminComplet + "'");
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }

    
    private static void chargerEtatSimplifie(String nomFichierDefaut) {
        if (leBar == null) {
            System.out.println("Bar not initialized. Cannot load state.");
            return;
        }
        
        File repertoireSaves = new File("saves");
        
        if (!repertoireSaves.exists() || !repertoireSaves.isDirectory()) {
            // TRANSLATED
            System.out.println("[!] No save files found (.barsave)");
            System.out.println("The 'saves/' directory does not exist yet.");
            System.out.println("Use menu 6 to create a save.");
            return;
        }
        
        File[] fichiersSave = repertoireSaves.listFiles((dir, name) -> name.endsWith(".barsave"));
        
        if (fichiersSave == null || fichiersSave.length == 0) {
            System.out.println("[!] No save files found (.barsave)");
            System.out.println("Use menu 6 to create a save.");
            return;
        }
        
        // TRANSLATED
        System.out.println("\n=== YOUR SAVED GAMES ===");
        for (int i = 0; i < fichiersSave.length; i++) {
            System.out.println((i + 1) + ". " + fichiersSave[i].getName());
        }
        System.out.println("0. Cancel");
        
        // TRANSLATED
        System.out.print("\nWhich game do you want to load? ");
        int choix = lireChoixUtilisateur(0, fichiersSave.length);
        
        if (choix == 0) {
            System.out.println("Load cancelled.");
            return;
        }
        
        String nomFichier = fichiersSave[choix - 1].getName();
        File file = new File("saves/" + nomFichier);
        try (Scanner fileScanner = new Scanner(file)) {
            System.out.println("Loading state from " + nomFichier + "...");
            
            int clientsLoaded = 0;
            int personnelLoaded = 0;
            int equipesLoaded = 0;
            boolean cashUpdated = false;
            boolean tournoiRestored = false;
            String currentSection = "";  
            
            boolean tournoiActif = false;
            double fraisInscription = 0;
            boolean inscriptionsOuvertes = false;
            boolean tournoiDemarre = false;
            boolean tournoiTermine = false;
            List<String[]> equipesData = new ArrayList<>();
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty()) {
                    continue;
                }
                
                if (line.startsWith("Cash: ")) {
                    String cashValue = line.substring(6).trim();
                    if (!cashValue.equals("N/A")) {
                        try {
                            double montant = Double.parseDouble(cashValue);
                            if (leBar.getBarman() != null && leBar.getBarman().getCaisse() != null) {
                                leBar.getBarman().getCaisse().setMontantTotal(montant);
                                cashUpdated = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Cannot parse cash value: " + cashValue);
                        }
                    }
                    continue;
                }
                
                if (line.startsWith("State of bar:") || line.equals("=== BAR ===")) {
                    currentSection = "bar";
                    continue;
                } else if (line.equals("--- Clients ---")) {
                    currentSection = "clients";
                    continue;
                } else if (line.equals("--- Personnel ---")) {
                    currentSection = "personnel";
                    continue;
                } else if (line.equals("=== TOURNOI ===")) {
                    currentSection = "tournoi";
                    continue;
                } else if (line.equals("--- Equipes ---")) {
                    currentSection = "equipes";
                    continue;
                }
                
                if (currentSection.equals("tournoi")) {
                    if (line.startsWith("TournoiActif: ")) {
                        tournoiActif = Boolean.parseBoolean(line.substring(14).trim());
                    } else if (line.startsWith("FraisInscription: ")) {
                        fraisInscription = Double.parseDouble(line.substring(18).trim());
                    } else if (line.startsWith("InscriptionsOuvertes: ")) {
                        inscriptionsOuvertes = Boolean.parseBoolean(line.substring(22).trim());
                    } else if (line.startsWith("TournoiDemarre: ")) {
                        tournoiDemarre = Boolean.parseBoolean(line.substring(16).trim());
                    } else if (line.startsWith("TournoiTermine: ")) {
                        tournoiTermine = Boolean.parseBoolean(line.substring(16).trim());
                    }
                    continue;
                }
                
                if (line.contains(";")) {
                    String[] data = line.split(";");
                    
                    if (currentSection.equals("clients") && data.length >= 3) {
                        try {
                            String prenom = data[0].trim();
                            double porteMonnaie = Double.parseDouble(data[1].trim());
                            double niveauAlcoolemie = Double.parseDouble(data[2].trim());
                            
                            Client client = leBar.trouverClient(prenom);
                            
                            if (client != null) {
                                client.setPorteMonnaie(porteMonnaie);
                                client.setNiveauAlcoolemie(niveauAlcoolemie);
                            } else {
                                Client nouveauClient = new Client(
                                    prenom,
                                    "Loaded",  
                                    porteMonnaie,
                                    5,  
                                    "Hello!",  
                                    null,  
                                    null, 
                                    null,  
                                    "",   
                                    ""    
                                );
                                nouveauClient.setNiveauAlcoolemie(niveauAlcoolemie);
                                leBar.ajouterClient(nouveauClient);
                            }
                            clientsLoaded++;
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Cannot parse client data: " + line);
                        }
                    } else if (currentSection.equals("personnel") && data.length >= 5) {
                        try {
                            String type = data[0].trim();
                            String prenom = data[1].trim();
                            String surnom = data[2].trim();
                            double porteMonnaie = Double.parseDouble(data[3].trim());
                            int attributSpecifique = Integer.parseInt(data[4].trim());
                            
                            Human personnelExistant = null;
                            if (leBar.getPersonnel() != null) {
                                for (Human p : leBar.getPersonnel()) {
                                    if (p.getPrenom().equals(prenom)) {
                                        personnelExistant = p;
                                        break;
                                    }
                                }
                            }
                            
                            if (type.equals("Serveur")) {
                                if (personnelExistant instanceof com.pub.characters.Serveur) {
                                    personnelExistant.setPorteMonnaie(porteMonnaie);
                                    ((com.pub.characters.Serveur) personnelExistant).setTailleBiceps(attributSpecifique);
                                } else {
                                    com.pub.characters.Serveur serveur = new com.pub.characters.Serveur(
                                        prenom, surnom, porteMonnaie, attributSpecifique
                                    );
                                    leBar.ajouterPersonnel(serveur);
                                }
                                personnelLoaded++;
                            } else if (type.equals("Serveuse")) {
                                if (personnelExistant instanceof com.pub.characters.Serveuse) {
                                    personnelExistant.setPorteMonnaie(porteMonnaie);
                                    ((com.pub.characters.Serveuse) personnelExistant).setNiveauCharme(attributSpecifique);
                                } else {
                                    com.pub.characters.Serveuse serveuse = new com.pub.characters.Serveuse(
                                        prenom, surnom, porteMonnaie, attributSpecifique
                                    );
                                    leBar.ajouterPersonnel(serveuse);
                                }
                                personnelLoaded++;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Cannot parse personnel data: " + line);
                        }
                    } else if (currentSection.equals("equipes") && data.length >= 6) {
                        equipesData.add(data);
                        equipesLoaded++;
                    }
                }
            }
            
            if (tournoiActif) {
                try {
                    tournoiEnCours = new Tournoi(leBar, fraisInscription);
                    
                    if (!equipesData.isEmpty()) {
                        tournoiEnCours.ouvrirInscriptions();
                        
                        for (String[] equipeInfo : equipesData) {
                            String nomEquipe = equipeInfo[0].trim();
                            String prenom1 = equipeInfo[1].trim();
                            String prenom2 = equipeInfo[2].trim();
                            int points = Integer.parseInt(equipeInfo[3].trim());
                            int matchsJoues = Integer.parseInt(equipeInfo[4].trim());
                            int matchsGagnes = Integer.parseInt(equipeInfo[5].trim());
                            
                            Human joueur1 = Helper.trouverJoueurParPrenom(leBar, prenom1);
                            Human joueur2 = Helper.trouverJoueurParPrenom(leBar, prenom2);
                            
                            if (joueur1 != null && joueur2 != null) {
                                tournoiEnCours.inscrireEquipe(nomEquipe, joueur1, joueur2);
                                
                                List<Equipe> equipes = tournoiEnCours.getEquipesInscrites();
                                for (Equipe equipe : equipes) {
                                    if (equipe.getNom().equals(nomEquipe)) {
                                        for (int i = 0; i < matchsJoues; i++) {
                                            boolean victoire = (i < matchsGagnes);
                                            
                                            equipe.enregistrerMatch(victoire);
                                            
                                            joueur1.enregistrerMatchTournoi(victoire);
                                            joueur2.enregistrerMatchTournoi(victoire);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (!inscriptionsOuvertes) {
                        tournoiEnCours.fermerInscriptions();
                    }
                    if (tournoiDemarre) {
                        tournoiEnCours.demarrerTournoi();
                    }
                    
                    tournoiRestored = true;
                } catch (Exception e) {
                    System.out.println("[!] Error rebuilding the tournament: " + e.getMessage());
                }
            }
            
            // TRANSLATED
            System.out.println("» Bar state loaded successfully.");
            if (cashUpdated) {
                System.out.println("  • Cash register updated");
            }
            if (clientsLoaded > 0) {
                System.out.println("  • " + clientsLoaded + " client(s) loaded");
            }
            if (personnelLoaded > 0) {
                System.out.println("  • " + personnelLoaded + " staff member(s) loaded");
            }
            if (tournoiRestored) {
                System.out.println("  • Tournament restored (" + equipesLoaded + " team(s))");
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("Save file not found: " + e.getMessage());
        }
    }
    

    private static void afficherStatistiquesJoueur() {
        if (leBar == null) {
            System.out.println("Bar not initialized.");
            return;
        }
        
        List<Human> joueursDisponibles = new ArrayList<>();
        
        if (leBar.getClients() != null) {
            joueursDisponibles.addAll(leBar.getClients());
        }
        
        if (leBar.getPersonnel() != null) {
            for (Human personnel : leBar.getPersonnel()) {
                if (personnel instanceof com.pub.game.JoueurBelote) {
                    joueursDisponibles.add(personnel);
                }
            }
        }
        
        if (joueursDisponibles.isEmpty()) {
            System.out.println("No players available.");
            return;
        }
        
        System.out.println();
        System.out.println("=== Available Players ===");
        for (int i = 0; i < joueursDisponibles.size(); i++) {
            Human joueur = joueursDisponibles.get(i);
            String type = joueur.getClass().getSimpleName();
            System.out.println((i + 1) + ". " + joueur.getPrenom() + " '" + joueur.getSurnom() + "' [" + type + "]");
        }
        
        System.out.print("\nSelect a player (1-" + joueursDisponibles.size() + ") or 0 to cancel: ");
        int choix = lireChoixUtilisateur(0, joueursDisponibles.size());
        
        if (choix == 0) {
            return;
        }
        
        Human joueur = joueursDisponibles.get(choix - 1);
        
        if (joueur instanceof Client) {
            System.out.println(((Client) joueur).getStatistiquesDetailles());
        } else {
            // TRANSLATED
            System.out.println("\n=== Statistics for " + joueur.getPrenom() + " ===");
            System.out.println("Type: " + joueur.getClass().getSimpleName());
            System.out.println("Nickname: " + joueur.getSurnom());
            System.out.println("Wallet: " + String.format("%.2f", joueur.getPorteMonnaie()) + " euros");
            System.out.println("Popularity: " + joueur.getPopularite());
            System.out.println("\n--- Tournament Statistics ---");
            System.out.println("Matches played: " + joueur.getMatchsTournoiJoues());
            System.out.println("Matches won: " + joueur.getMatchsTournoiGagnes());
            System.out.println("Matches lost: " + joueur.getMatchsTournoiPerdus());
            System.out.println("Tournament points: " + joueur.getPointsTournoi());
        }
    }
    

    private static void creerPersonnageAutomatique() {
        if (leBar == null) {
            System.out.println("[!] Bar not initialized.");
            return;
        }
        
        // TRANSLATED
        System.out.println("\n=== AUTOMATIC CLIENT CREATION ===");
        
        Random random = new Random();
        
        String prenom = "AutoClient" + random.nextInt(100);
        String surnom = "Auto" + random.nextInt(100);
        double porteMonnaie = 30.0 + (random.nextDouble() * 70.0); 
        int popularite = 3 + random.nextInt(8); 
        
        String[] cris = {"Ah!", "Oh!", "Wow!", "Cool!", "Super!", "Great!"};
        String criSignificatif = cris[random.nextInt(cris.length)];
        
        List<Boisson> boissonsDisponibles = leBar.getConsommationsProposees();
        Boisson boissonFavorite = null;
        Boisson boissonSecours = null;
        Boisson boissonActuelle = null;
        
        if (boissonsDisponibles != null && !boissonsDisponibles.isEmpty()) {
            boissonFavorite = boissonsDisponibles.get(random.nextInt(boissonsDisponibles.size()));
            boissonSecours = boissonsDisponibles.get(random.nextInt(boissonsDisponibles.size()));
            boissonActuelle = boissonsDisponibles.get(random.nextInt(boissonsDisponibles.size()));
        }
        
        String[] genres = {"homme", "femme"};
        String genre = genres[random.nextInt(genres.length)];
        
        String identifiantGenre;
        if ("homme".equals(genre)) {
            String[] couleurs = {"Red", "Blue", "Green", "Black", "White", "Yellow"};
            identifiantGenre = couleurs[random.nextInt(couleurs.length)];
        } else {
            String[] bijoux = {"Necklace", "Bracelet", "Ring", "Earrings"};
            identifiantGenre = bijoux[random.nextInt(bijoux.length)];
        }
        
        Client nouveauClient = new Client(
            prenom, surnom, porteMonnaie, popularite,
            criSignificatif, boissonFavorite, boissonSecours, boissonActuelle,
            identifiantGenre, genre
        );
        
        if (leBar.ajouterClient(nouveauClient)) {
            // TRANSLATED
            System.out.println("» Auto client created successfully!");
            System.out.println("Name: " + prenom);
            System.out.println("Nickname: " + surnom);
            System.out.println("Money: " + String.format("%.2f", porteMonnaie) + " euros");
            System.out.println("Gender: " + genre);
            System.out.println("Yell: " + criSignificatif);
            if (boissonFavorite != null) {
                System.out.println("Favorite drink: " + boissonFavorite.getNom());
            }
            
            nouveauClient.sePresenter();
            
            try {
                System.out.println("Total humans created: " + Human.getHumanCount());
            } catch (Throwable t) {
            }
        }
    }
    

    private static void actionsSpeciales() {
        // TRANSLATED
        System.out.println("\n--- Special Actions ---");
        System.out.println("1. Owner Actions");
        System.out.println("2. Social Actions");
        System.out.println("0. Back");
        System.out.print("Your choice: ");
        
        int choix = lireChoixUtilisateur(0, 2);
        
        switch (choix) {
            case 1:
                actionsPatronne();
                break;
            case 2:
                actionsSociales();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    
  
    private static void actionsPatronne() {
        // TRANSLATED
        System.out.println("\n--- Owner Actions ---");
        System.out.println("1. Withdraw money from Register");
        System.out.println("2. Kick out a client");
        System.out.println("3. Change client gender");
        System.out.println("0. Back");
        System.out.print("Your choice: ");
        
        int choix = lireChoixUtilisateur(0, 3);
        
        switch (choix) {
            case 1:
                actionRecupererArgentCaisse();
                break;
            case 2:
                actionExclureClient();
                break;
            case 3:
                actionChangerGenreClient();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    
  
    private static void actionRecupererArgentCaisse() {
        // TRANSLATED
        System.out.println("\n=== WITHDRAW CASH ===");
        
        if (leBar == null || leBar.getPatronne() == null) {
            System.out.println("[!] Bar or Owner not initialized.");
            return;
        }
        
        Patron patronne = leBar.getPatronne();
        Barman barman = leBar.getBarman();
        
        if (barman == null || barman.getCaisse() == null) {
            System.out.println("[!] No barman or register available.");
            return;
        }
        
        Caisse caisse = barman.getCaisse();
        
        // TRANSLATED
        System.out.println("Current amount in register: " + 
                           String.format("%.2f", caisse.getMontantTotal()) + " euros");
        System.out.println("Owner's wallet: " + 
                           String.format("%.2f", patronne.getPorteMonnaie()) + " euros");
        
        System.out.print("\nAmount to withdraw (0 to cancel): ");
        double montant = lireDoubleUtilisateur("");
        
        if (montant <= 0) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        patronne.recupererArgentCaisse(caisse, montant);
    }
    
    
    private static void actionExclureClient() {
        // TRANSLATED
        System.out.println("\n=== KICK OUT A CLIENT ===");
        
        if (leBar == null || leBar.getPatronne() == null) {
            System.out.println("[!] Bar or Owner not initialized.");
            return;
        }
        
        if (leBar.getClients().isEmpty()) {
            System.out.println("No clients in the bar.");
            return;
        }
        
        System.out.println("\nClients present:");
        int i = 1;
        for (Client client : leBar.getClients()) {
            System.out.println(i++ + ". " + client.getPrenom() + " '" + client.getSurnom() + "'");
        }
        System.out.println("0. Cancel");
        
        System.out.print("\nWhich client do you want to kick out? ");
        int choix = lireChoixUtilisateur(0, leBar.getClients().size());
        
        if (choix == 0) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Client clientAExclure = leBar.getClients().get(choix - 1);
        // TRANSLATED: Dialogue
        leBar.getPatronne().parler("I am sorry " + clientAExclure.getPrenom() + 
                                   ", but I have to ask you to leave.");
        clientAExclure.parler(clientAExclure.getCriSignificatif() + " Okay, I'm leaving...");
        
        leBar.getClients().remove(clientAExclure);
        System.out.println("\n» " + clientAExclure.getPrenom() + " has been kicked out.");
    }
    
  
    private static void actionChangerGenreClient() {
        System.out.println("\n=== CHANGE CLIENT GENDER ===");
        if (leBar == null || leBar.getClients().isEmpty()) {
            System.out.println("[!] No clients in the bar.");
            return;
        }

        List<Client> clients = leBar.getClients();
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getPrenom() + " (Genre ID: " + clients.get(i).getIdentifiantGenre() + ")");
        }
        System.out.println("0. Cancel");

        System.out.print("Who do you want to modify? ");
        int choix = lireChoixUtilisateur(0, clients.size());
        if (choix == 0) return;

        Client client = clients.get(choix - 1);

        if ("homme".equalsIgnoreCase(client.getGenre())) {
            client.setGenre("femme");
            String bijoux = lireStringUtilisateur("New jewelry: ");
            client.setIdentifiantGenre(bijoux);
            client.parler("I feel fabulous with my new " + bijoux + "!");
        } else {
            client.setGenre("homme");
            String tshirt = lireStringUtilisateur("New t-shirt color: ");
            client.setIdentifiantGenre(tshirt);
            client.parler("This " + tshirt + " t-shirt looks great on me!");
        }
        System.out.println("» " + client.getPrenom() + "'s gender has been updated.");
    }
    

    private static void actionsSociales() {
        // TRANSLATED
        System.out.println("\n--- Social Actions ---");
        System.out.println("1. Treat someone to a drink");
        System.out.println("2. Order from Supplier");
        System.out.println("3. Rounds on the House");
        System.out.println("0. Back");
        System.out.print("Your choice: ");
        
        int choix = lireChoixUtilisateur(0, 3);
        
        switch (choix) {
            case 1:
                actionOffrirVerre();
                break;
            case 2:
                actionCommanderFournisseur();
                break;
            case 3:
                actionTourneeGenerale();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    

    private static void actionOffrirVerre() {
        // TRANSLATED
        System.out.println("\n=== TREAT SOMEONE TO A DRINK ===");
        
        if (leBar == null) {
            System.out.println("[!] Bar not initialized.");
            return;
        }
        
        List<Human> tousLesHumains = new ArrayList<>();
        
        if (leBar.getPatronne() != null) {
            tousLesHumains.add(leBar.getPatronne());
        }
        
        if (leBar.getPersonnel() != null) {
            tousLesHumains.addAll(leBar.getPersonnel());
        }
        
        if (leBar.getClients() != null) {
            tousLesHumains.addAll(leBar.getClients());
        }
        
        if (fournisseur != null) {
            tousLesHumains.add(fournisseur);
        }
        
        if (tousLesHumains.size() < 2) {
            System.out.println("[!] Not enough people to offer a drink.");
            return;
        }
        
        System.out.println("\nWho is buying?");
        for (int i = 0; i < tousLesHumains.size(); i++) {
            Human h = tousLesHumains.get(i);
            System.out.println((i + 1) + ". " + h.getPrenom() + " '" + h.getSurnom() + "' [" + 
                             h.getClass().getSimpleName() + "] (" + 
                             String.format("%.2f", h.getPorteMonnaie()) + " euros)");
        }
        System.out.println("0. Cancel");
        
        System.out.print("\nChoose the buyer: ");
        int choixOffrant = lireChoixUtilisateur(0, tousLesHumains.size());
        
        if (choixOffrant == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        Human offrant = tousLesHumains.get(choixOffrant - 1);
        
        System.out.println("\nWho receives?");
        for (int i = 0; i < tousLesHumains.size(); i++) {
            Human h = tousLesHumains.get(i);
            if (h != offrant) {
                System.out.println((i + 1) + ". " + h.getPrenom() + " '" + h.getSurnom() + "' [" + 
                                 h.getClass().getSimpleName() + "]");
            }
        }
        System.out.println("0. Cancel");
        
        System.out.print("\nChoose the receiver: ");
        int choixReceveur = lireChoixUtilisateur(0, tousLesHumains.size());
        
        if (choixReceveur == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        Human receveur = tousLesHumains.get(choixReceveur - 1);
        
        if (offrant == receveur) {
            System.out.println("[!] You can't buy a drink for yourself here!");
            return;
        }
        
        System.out.println("\nWhich drink?");
        List<Boisson> boissonsMenu = (leBar != null) ? leBar.getConsommationsProposees() : Collections.emptyList();
        
        if (boissonsMenu == null || boissonsMenu.isEmpty()) {
            System.out.println("[!] No drinks available.");
            return;
        }
        
        for (int i = 0; i < boissonsMenu.size(); i++) {
            Boisson b = boissonsMenu.get(i);
            System.out.println((i + 1) + ". " + b.getNom() + " (" + 
                             String.format("%.2f", b.getPrixVente()) + " euros)");
        }
        System.out.println("0. Cancel");
        
        System.out.print("\nChoose a drink: ");
        int choixBoisson = lireChoixUtilisateur(0, boissonsMenu.size());
        
        if (choixBoisson == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        Boisson boissonChoisie = boissonsMenu.get(choixBoisson - 1);
        Barman barman = (leBar != null) ? leBar.getBarman() : null;
        
        if (barman == null) {
            System.out.println("[!] No barman available to serve.");
            return;
        }
        
        try {
            System.out.println("\n--- Transaction in progress ---");
            offrant.offrirVerre(receveur, boissonChoisie, barman);
            
        } catch (Exception e) {
            System.err.println("[!] Unexpected error: " + e.getMessage());
        }
    }
    

    private static void actionCommanderFournisseur() {
        // TRANSLATED
        System.out.println("\n=== ORDER FROM SUPPLIER ===");
        
        if (leBar == null || leBar.getPatronne() == null) {
            System.out.println("[!] Bar or Owner not initialized.");
            return;
        }
        
        if (fournisseur == null) {
            System.out.println("[!] No supplier available.");
            return;
        }
        
        Barman barman = leBar.getBarman();
        if (barman == null) {
            System.out.println("[!] No barman available.");
            return;
        }
        
        Patron patronne = leBar.getPatronne();
        
        System.out.println("\nWhich drink to order?");
        List<Boisson> boissonsMenu = (leBar != null) ? leBar.getConsommationsProposees() : Collections.emptyList();
        
        if (boissonsMenu == null || boissonsMenu.isEmpty()) {
            System.out.println("[!] No drinks in catalog.");
            return;
        }
        
        for (int i = 0; i < boissonsMenu.size(); i++) {
            Boisson b = boissonsMenu.get(i);
            int stockActuel = barman.getStock().getOrDefault(b, 0);
            System.out.println((i + 1) + ". " + b.getNom() + " - Buy Price: " + 
                             String.format("%.2f", b.getPrixAchat()) + " euros (Current Stock: " + 
                             stockActuel + ")");
        }
        System.out.println("0. Cancel");
        
        System.out.print("\nChoose a drink: ");
        int choixBoisson = lireChoixUtilisateur(0, boissonsMenu.size());
        
        if (choixBoisson == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        Boisson boissonChoisie = boissonsMenu.get(choixBoisson - 1);
        
        System.out.print("\nQuantity? ");
        int quantite = (int) lireDoubleUtilisateur("");
        
        if (quantite <= 0) {
            System.out.println("[!] Invalid quantity.");
            return;
        }
        
        double coutTotal = boissonChoisie.getPrixAchat() * quantite;
        
        System.out.println("\nOrder Summary:");
        System.out.println("Drink: " + boissonChoisie.getNom());
        System.out.println("Quantity: " + quantite);
        System.out.println("Total Cost: " + String.format("%.2f", coutTotal) + " euros");
        System.out.println("\nOwner's Wallet: " + 
                          String.format("%.2f", patronne.getPorteMonnaie()) + " euros");
        
        if (patronne.getPorteMonnaie() < coutTotal) {
            System.out.println("\n[!] The owner doesn't have enough money!");
            return;
        }
        
        System.out.print("\nConfirm order? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (!confirmation.equals("oui") && !confirmation.equals("o") && !confirmation.equals("y") && !confirmation.equals("yes")) {
            System.out.println("Order cancelled.");
            return;
        }
        
        System.out.println("\n--- Ordering ---");
        
        barman.passerCommande(fournisseur, boissonChoisie, quantite);
        
        patronne.payerFournisseur(fournisseur, coutTotal);
        
        System.out.println("\n» Order successful!");
        System.out.println("New stock of " + boissonChoisie.getNom() + ": " + 
                          barman.getStock().get(boissonChoisie) + " units");
    }
    
    
    private static void actionTourneeGenerale() {
        System.out.println("\n" + "=".repeat(60));
        // TRANSLATED
        System.out.println("=== ROUNDS ON THE HOUSE! ===");
        System.out.println("=".repeat(60));
        
        if (leBar == null) {
            System.out.println("[!] Bar not initialized.");
            return;
        }
        
        if (leBar.getPatronne() == null) {
            System.out.println("[!] Owner not available.");
            return;
        }
        
        if (leBar.getBarman() == null) {
            System.out.println("[!] Barman not available.");
            return;
        }
        
        if (leBar.getPersonnel() == null || leBar.getPersonnel().isEmpty()) {
            System.out.println("[!] No staff available.");
            return;
        }
        
        if (leBar.getClients() == null || leBar.getClients().isEmpty()) {
            System.out.println("[!] No clients in the bar.");
            return;
        }
        
        System.out.println();
        
        // TRANSLATED
        leBar.getBarman().parler("ROUNDS ON THE HOUSE!");
        
        // TRANSLATED
        leBar.getPatronne().parler("Everything is great, business is booming!");
        
        System.out.println();
        
        // TRANSLATED
        System.out.println("--- Staff Reactions ---");
        for (Human personnel : leBar.getPersonnel()) {
            if (personnel instanceof Serveur || personnel instanceof Serveuse) {
                personnel.parler("I'm on it!");
            }
        }
        
        System.out.println();
        
        // TRANSLATED
        System.out.println("--- Client Reactions ---");
        for (Client client : leBar.getClients()) {
            String cri = client.getCriSignificatif();
            if (cri != null && !cri.isEmpty()) {
                client.parler(cri);
            } else {
                client.parler("Yay!");
            }
        }
        
        System.out.println();
        // TRANSLATED
        System.out.println("» The atmosphere is amazing!");
        System.out.println("=".repeat(60));
    }
}