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
            choix = lireChoixUtilisateur(0, 7);

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
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choix != 0);

        // NOTE: Ne JAMAIS fermer le Scanner lié à System.in, car cela ferme le flux définitivement
        // scanner.close(); // ❌ SUPPRIMÉ - cause NoSuchElementException
        System.out.println("\nMerci d'avoir utilisé l'application. À bientôt !");
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

        Barman barman = new Barman("Bob", "Le Rapide", 50.0, stockInitial, caisse);
        Serveur serveur = new Serveur("Luc", "Le Costaud", 30.0, 15);
        Serveuse serveuse = new Serveuse("Léa", "La Charmeuse", 30.0, 8);

        leBar = new Bar("Chez Sarah", patronne);
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

        Client client1 = new Client("Peter", "The Shy", 40.0, 6, "Oh!", water, beer, "Blue", "homme");
        Client client2 = new Client("Julie", "The Talkative", 30.0, 8, "Ah!", beer, water, "Necklace", "femme");
        if (leBar != null) {
            leBar.ajouterClient(client1);
            leBar.ajouterClient(client2);
        }

        System.out.println("Bar initialized: " + (leBar != null ? leBar.getNom() : "N/A"));
        try {
            System.out.println("Total humans created: " + Human.getHumanCount());
        } catch (Throwable t) {
            // Ignore errors if Human.getHumanCount() is not available
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
        System.out.println("6. Save state (simplified)");
        System.out.println("7. Load state (simplified)");
        System.out.println("0. Exit");
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
                
                // Gender selection with validation
                String sexe;
                do {
                    sexe = lireStringUtilisateur("Genre (Homme/Femme): ").toLowerCase();
                    if (!sexe.equals("homme") && !sexe.equals("femme")) {
                        System.out.println("Veuillez entrer 'Homme' ou 'Femme'.");
                    }
                } while (!sexe.equals("homme") && !sexe.equals("femme"));
                
                // Ask for appropriate accessory based on gender
                String identifiantGenre;
                if (sexe.equals("homme")) {
                    identifiantGenre = lireStringUtilisateur("Couleur du tee-shirt: ");
                } else {
                    identifiantGenre = lireStringUtilisateur("Bijoux: ");
                }
                
                nouveau = new Client(prenom, surnom, argent, 5, "Hey!", boissonFav, (leBar != null) ? leBar.trouverBoisson("Water") : null, identifiantGenre, sexe);
                if (leBar != null) leBar.ajouterClient((Client) nouveau);
                break;
            case "serveur":
            case "server":
                int biceps = (int) lireDoubleUtilisateur("Taille du biceps (en cm): ");
                nouveau = new Serveur(prenom, surnom, argent, biceps);
                if (leBar != null) leBar.ajouterPersonnel(nouveau);
                break;
            case "serveuse":
            case "waitress":
                int charme = (int) lireDoubleUtilisateur("Niveau de charme (sur 10): ");
                nouveau = new Serveuse(prenom, surnom, argent, charme);
                if (leBar != null) leBar.ajouterPersonnel(nouveau);
                break;
            default:
                System.out.println("Unknown type.");
                return;
        }
        System.out.println(type + " " + prenom + " created.");
        if (nouveau != null) nouveau.sePresenter();
        try {
            System.out.println("Total humans created: " + Human.getHumanCount());
        } catch (Throwable t) {
            // Ignore errors if Human.getHumanCount() is not available
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

        try {
            barman.servirBoisson(boissonChoisie);
            barman.recevoirPaiement(client, boissonChoisie.getPrixVente());
            client.boire(boissonChoisie);
            System.out.println(client.getPrenom() + " received and paid for " + boissonChoisie.getNom());
        } catch (OutOfStockException | NotEnoughMoneyException e) {
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
        System.out.println("--- GESTION DU TOURNOI DE BELOTE ---");
        System.out.println("1. Créer un nouveau tournoi");
        System.out.println("2. Inscrire une équipe");
        System.out.println("3. Démarrer le tournoi");
        System.out.println("4. Jouer le prochain match");
        System.out.println("5. Jouer tout le tournoi");
        System.out.println("6. Afficher le classement");
        System.out.println("7. Afficher les équipes inscrites");
        System.out.println("0. Retour");
        System.out.print("Votre choix: ");

        int choix = lireChoixUtilisateur(0, 7);

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
            case 0:
                break;
            default:
                System.out.println("Choix invalide.");
                break;
        }
    }

    private static void creerTournoi() {
        if (tournoiEnCours != null && !tournoiEnCours.isTournoiTermine()) {
            System.out.println("Un tournoi est déjà en cours !");
            System.out.print("Voulez-vous l'annuler et en créer un nouveau ? (oui/non): ");
            String reponse = scanner.nextLine().toLowerCase();
            if (!reponse.equals("oui")) {
                return;
            }
        }

        double frais = lireDoubleUtilisateur("Frais d'inscription par équipe: ");
        tournoiEnCours = new Tournoi(leBar, frais);
        tournoiEnCours.ouvrirInscriptions();
        System.out.println("» Tournoi créé avec succès !");
    }

    private static void inscrireEquipeTournoi() {
        if (tournoiEnCours == null) {
            System.out.println("Aucun tournoi en cours. Créez d'abord un tournoi.");
            return;
        }

        // Créer une liste de tous les joueurs disponibles (Clients + Serveurs/Serveuses)
        List<Human> joueursDisponibles = new ArrayList<>();
        
        // Ajouter les clients
        if (leBar.getClients() != null) {
            joueursDisponibles.addAll(leBar.getClients());
        }
        
        // Ajouter le personnel qui peut jouer (Serveur/Serveuse, mais pas Barman/Patronne)
        if (leBar.getPersonnel() != null) {
            for (Human personnel : leBar.getPersonnel()) {
                if (personnel instanceof com.pub.game.JoueurBelote) {
                    joueursDisponibles.add(personnel);
                }
            }
        }
        
        if (joueursDisponibles.size() < 2) {
            System.out.println("Pas assez de joueurs disponibles pour former une équipe.");
            return;
        }

        System.out.println("\n=== Joueurs disponibles ===");
        for (int i = 0; i < joueursDisponibles.size(); i++) {
            Human joueur = joueursDisponibles.get(i);
            String type = joueur.getClass().getSimpleName();
            System.out.println((i + 1) + ". " + joueur.getPrenom() + " '" + joueur.getSurnom() + 
                             "' [" + type + "] (" + String.format("%.2f", joueur.getPorteMonnaie()) + " euros)");
        }

        System.out.print("\nNom de l'équipe: ");
        String nomEquipe = scanner.nextLine();

        System.out.print("Numéro du premier joueur: ");
        int idx1 = lireChoixUtilisateur(1, joueursDisponibles.size()) - 1;

        System.out.print("Numéro du deuxième joueur: ");
        int idx2 = lireChoixUtilisateur(1, joueursDisponibles.size()) - 1;

        if (idx1 == idx2) {
            System.out.println("Vous devez choisir deux joueurs différents !");
            return;
        }

        Human joueur1 = joueursDisponibles.get(idx1);
        Human joueur2 = joueursDisponibles.get(idx2);

        // Laisser Tournoi.inscrireEquipe() gérer toutes les validations dans le bon ordre
        tournoiEnCours.inscrireEquipe(nomEquipe, joueur1, joueur2);
    }

    private static void demarrerTournoi() {
        if (tournoiEnCours == null) {
            System.out.println("Aucun tournoi en cours.");
            return;
        }

        tournoiEnCours.demarrerTournoi();
    }

    private static void jouerProchainMatch() {
        if (tournoiEnCours == null) {
            System.out.println("Aucun tournoi en cours.");
            return;
        }
        
        // Demander si l'utilisateur veut jouer manuellement
        System.out.println("\nVoulez-vous participer à ce match manuellement ?");
        System.out.print("Entrez votre prénom (ou appuyez sur Entrée pour simulation IA): ");
        String nomJoueur = scanner.nextLine().trim();
        
        if (nomJoueur.isEmpty()) {
            nomJoueur = null; // Mode simulation
        }
        
        if (!tournoiEnCours.jouerProchainMatch(nomJoueur)) {
            // Message déjà affiché par la méthode
        }
    }

    private static void jouerTournoiComplet() {
        if (tournoiEnCours == null) {
            System.out.println("Aucun tournoi en cours.");
            return;
        }

        System.out.println("Lancement du tournoi complet...");
        try {
            tournoiEnCours.jouerTournoiComplet();
        } catch (TournoiException e) {
            System.out.println("❌ Erreur: " + e.getMessage());
        }
    }

    private static void afficherClassement() {
        if (tournoiEnCours == null) {
            System.out.println("Aucun tournoi en cours.");
            return;
        }

        tournoiEnCours.getFeuilleDeScore().afficherClassement();
    }

    private static void afficherEquipesInscrites() {
        if (tournoiEnCours == null) {
            System.out.println("Aucun tournoi en cours.");
            return;
        }

        tournoiEnCours.afficherEquipesInscrites();
    }

    private static void sauvegarderEtatSimplifie(String nomFichier) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(nomFichier));
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
            System.out.println("State saved to " + nomFichier);
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }

    private static void chargerEtatSimplifie(String nomFichier) {
        if (leBar == null) {
            System.out.println("Bar not initialized. Cannot load state.");
            return;
        }
        
        File file = new File(nomFichier);
        try (Scanner fileScanner = new Scanner(file)) {
            System.out.println("Loading state from " + nomFichier + "...");
            
            int clientsLoaded = 0;
            boolean cashUpdated = false;
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                
                // Parse and update cash
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
                            System.out.println("⚠️ Cannot parse cash value: " + cashValue);
                        }
                    }
                    continue;
                }
                
                // Skip header lines
                if (line.startsWith("State of bar:") || line.startsWith("--- Clients ---")) {
                    continue;
                }
                
                // Parse client line (format: prenom;porteMonnaie;niveauAlcoolemie)
                if (line.contains(";")) {
                    String[] data = line.split(";");
                    if (data.length >= 3) {
                        try {
                            String prenom = data[0].trim();
                            double porteMonnaie = Double.parseDouble(data[1].trim());
                            double niveauAlcoolemie = Double.parseDouble(data[2].trim());
                            
                            // Find existing client or create new one
                            Client client = leBar.trouverClient(prenom);
                            
                            if (client != null) {
                                // Update existing client
                                client.setPorteMonnaie(porteMonnaie);
                                client.setNiveauAlcoolemie(niveauAlcoolemie);
                            } else {
                                // Create new client with default values
                                Client nouveauClient = new Client(
                                    prenom,
                                    "Loaded",  // surnom
                                    porteMonnaie,
                                    5,  // popularite
                                    "Hello!",  // criSignificatif
                                    null,  // boissonFavorite
                                    null,  // boissonActuelle
                                    "",    // identifiantGenre
                                    ""     // genre
                                );
                                nouveauClient.setNiveauAlcoolemie(niveauAlcoolemie);
                                leBar.ajouterClient(nouveauClient);
                            }
                            clientsLoaded++;
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Cannot parse client data: " + line);
                        }
                    }
                }
            }
            
            System.out.println("» État du bar chargé avec succès.");
            if (cashUpdated) {
                System.out.println("  • Caisse mise à jour");
            }
            if (clientsLoaded > 0) {
                System.out.println("  • " + clientsLoaded + " client(s) chargé(s)");
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
        
        // Créer une liste de tous les joueurs disponibles (Clients + Serveurs/Serveuses)
        List<Human> joueursDisponibles = new ArrayList<>();
        
        // Ajouter les clients
        if (leBar.getClients() != null) {
            joueursDisponibles.addAll(leBar.getClients());
        }
        
        // Ajouter le personnel qui peut jouer (Serveur/Serveuse, mais pas Barman/Patronne)
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
        
        // Afficher les statistiques (uniquement disponibles pour les Clients)
        if (joueur instanceof Client) {
            System.out.println(((Client) joueur).getStatistiquesDetailles());
        } else {
            // Pour Serveur/Serveuse, afficher les statistiques de tournoi de base
            System.out.println("\n=== Statistiques de " + joueur.getPrenom() + " ===");
            System.out.println("Type: " + joueur.getClass().getSimpleName());
            System.out.println("Surnom: " + joueur.getSurnom());
            System.out.println("Porte-monnaie: " + String.format("%.2f", joueur.getPorteMonnaie()) + " euros");
            System.out.println("Popularité: " + joueur.getPopularite());
            System.out.println("\n--- Statistiques de tournoi ---");
            System.out.println("Matchs joués: " + joueur.getMatchsTournoiJoues());
            System.out.println("Matchs gagnés: " + joueur.getMatchsTournoiGagnes());
            System.out.println("Matchs perdus: " + joueur.getMatchsTournoiPerdus());
            System.out.println("Points tournoi: " + joueur.getPointsTournoi());
        }
    }
}