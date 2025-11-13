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
            choix = lireChoixUtilisateur(0, 8);

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

        // Créer un fournisseur par défaut
        fournisseur = new Fournisseur("Jacques", "Le Grossiste", 500.0, 30, "Boissons & Co");

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
        System.out.println("6. Save");
        System.out.println("7. Load");
        System.out.println("8. Special actions");
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

    private static void sauvegarderEtatSimplifie(String nomFichierDefaut) {
        // Toujours demander un nom de sauvegarde
        System.out.println("\n=== SAUVEGARDER VOTRE PARTIE ===");
        System.out.print("Entrez un nom pour votre sauvegarde (ex: partie_amine) : ");
        String nomBase = scanner.nextLine().trim();
        
        if (nomBase.isEmpty()) {
            nomBase = "bar_state";
            System.out.println("Nom vide. Utilisation du nom par défaut: " + nomBase);
        }
        
        // Toujours utiliser l'extension .barsave
        String nomFichier = nomBase.endsWith(".barsave") ? nomBase : nomBase + ".barsave";
        
        // S'assurer que le dossier saves/ existe
        Helper.ensureDirectoryExists("saves");
        
        // Construire le chemin complet dans le dossier saves/
        String cheminComplet = "saves/" + nomFichier;
        
        // Vérifier si le fichier existe déjà
        File fichier = new File(cheminComplet);
        if (fichier.exists()) {
            System.out.print("[!] Le fichier '" + nomFichier + "' existe déjà. Écraser? (oui/non) : ");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (!reponse.equals("oui") && !reponse.equals("o") && !reponse.equals("y") && !reponse.equals("yes")) {
                System.out.println("Sauvegarde annulée.");
                return;
            }
        }
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(cheminComplet));
            
            // === SECTION BAR ===
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
            
            // === SECTION TOURNOI ===
            writer.println("=== TOURNOI ===");
            if (tournoiEnCours == null) {
                writer.println("TournoiActif: false");
            } else {
                writer.println("TournoiActif: true");
                writer.println("FraisInscription: " + tournoiEnCours.getFraisInscription());
                writer.println("InscriptionsOuvertes: " + tournoiEnCours.isInscriptionsOuvertes());
                writer.println("TournoiDemarre: " + tournoiEnCours.isTournoiDemarre());
                writer.println("TournoiTermine: " + tournoiEnCours.isTournoiTermine());
                
                // Sauvegarder les équipes inscrites avec leurs scores
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
                
                // Note: Les matchs du calendrier ne sont pas sauvegardés car ils seront
                // recréés automatiquement lors du chargement si le tournoi est démarré
            }
            
            System.out.println("» État sauvegardé dans '" + cheminComplet + "'");
        } catch (IOException e) {
            System.err.println("Erreur de sauvegarde: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }

    private static void chargerEtatSimplifie(String nomFichierDefaut) {
        if (leBar == null) {
            System.out.println("Bar not initialized. Cannot load state.");
            return;
        }
        
        // Rechercher tous les fichiers .barsave dans le dossier saves/
        File repertoireSaves = new File("saves");
        
        // Vérifier si le dossier saves/ existe
        if (!repertoireSaves.exists() || !repertoireSaves.isDirectory()) {
            System.out.println("[!] Aucun fichier de sauvegarde trouvé (.barsave)");
            System.out.println("Le dossier 'saves/' n'existe pas encore.");
            System.out.println("Utilisez le menu 6 pour créer une sauvegarde.");
            return;
        }
        
        File[] fichiersSave = repertoireSaves.listFiles((dir, name) -> name.endsWith(".barsave"));
        
        if (fichiersSave == null || fichiersSave.length == 0) {
            System.out.println("[!] Aucun fichier de sauvegarde trouvé (.barsave)");
            System.out.println("Utilisez le menu 6 pour créer une sauvegarde.");
            return;
        }
        
        // Afficher la liste des fichiers disponibles
        System.out.println("\n=== VOS PARTIES SAUVEGARDÉES ===");
        for (int i = 0; i < fichiersSave.length; i++) {
            System.out.println((i + 1) + ". " + fichiersSave[i].getName());
        }
        System.out.println("0. Annuler");
        
        // Demander à l'utilisateur de choisir
        System.out.print("\nQuelle partie voulez-vous charger ? ");
        int choix = lireChoixUtilisateur(0, fichiersSave.length);
        
        if (choix == 0) {
            System.out.println("Chargement annulé.");
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
            String currentSection = "";  // Track which section we're in
            
            // Variables pour reconstruire le tournoi
            boolean tournoiActif = false;
            double fraisInscription = 0;
            boolean inscriptionsOuvertes = false;
            boolean tournoiDemarre = false;
            boolean tournoiTermine = false;
            List<String[]> equipesData = new ArrayList<>();
            
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
                            System.out.println("[!] Cannot parse cash value: " + cashValue);
                        }
                    }
                    continue;
                }
                
                // Track section headers
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
                
                // Parse tournament configuration
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
                
                // Parse lines based on current section
                if (line.contains(";")) {
                    String[] data = line.split(";");
                    
                    if (currentSection.equals("clients") && data.length >= 3) {
                        // Parse client line (format: prenom;porteMonnaie;niveauAlcoolemie)
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
                            System.out.println("[!] Cannot parse client data: " + line);
                        }
                    } else if (currentSection.equals("personnel") && data.length >= 5) {
                        // Parse personnel line (format: TYPE;prenom;surnom;porteMonnaie;attributSpecifique)
                        try {
                            String type = data[0].trim();
                            String prenom = data[1].trim();
                            String surnom = data[2].trim();
                            double porteMonnaie = Double.parseDouble(data[3].trim());
                            int attributSpecifique = Integer.parseInt(data[4].trim());
                            
                            // Find existing personnel or create new one
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
                                    // Update existing Serveur
                                    personnelExistant.setPorteMonnaie(porteMonnaie);
                                    ((com.pub.characters.Serveur) personnelExistant).setTailleBiceps(attributSpecifique);
                                } else {
                                    // Create new Serveur
                                    com.pub.characters.Serveur serveur = new com.pub.characters.Serveur(
                                        prenom, surnom, porteMonnaie, attributSpecifique
                                    );
                                    leBar.ajouterPersonnel(serveur);
                                }
                                personnelLoaded++;
                            } else if (type.equals("Serveuse")) {
                                if (personnelExistant instanceof com.pub.characters.Serveuse) {
                                    // Update existing Serveuse
                                    personnelExistant.setPorteMonnaie(porteMonnaie);
                                    ((com.pub.characters.Serveuse) personnelExistant).setNiveauCharme(attributSpecifique);
                                } else {
                                    // Create new Serveuse
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
                        // Store equipes data for later reconstruction
                        // Format: nomEquipe;joueur1Prenom;joueur2Prenom;points;matchsJoues;matchsGagnes
                        equipesData.add(data);
                        equipesLoaded++;
                    }
                }
            }
            
            // Reconstruire le tournoi si nécessaire
            if (tournoiActif) {
                try {
                    tournoiEnCours = new Tournoi(leBar, fraisInscription);
                    
                    // Ouvrir les inscriptions et inscrire les équipes
                    if (!equipesData.isEmpty()) {
                        tournoiEnCours.ouvrirInscriptions();
                        
                        for (String[] equipeInfo : equipesData) {
                            String nomEquipe = equipeInfo[0].trim();
                            String prenom1 = equipeInfo[1].trim();
                            String prenom2 = equipeInfo[2].trim();
                            int points = Integer.parseInt(equipeInfo[3].trim());
                            int matchsJoues = Integer.parseInt(equipeInfo[4].trim());
                            int matchsGagnes = Integer.parseInt(equipeInfo[5].trim());
                            
                            // Trouver les joueurs dans le bar
                            Human joueur1 = Helper.trouverJoueurParPrenom(leBar, prenom1);
                            Human joueur2 = Helper.trouverJoueurParPrenom(leBar, prenom2);
                            
                            if (joueur1 != null && joueur2 != null) {
                                tournoiEnCours.inscrireEquipe(nomEquipe, joueur1, joueur2);
                                
                                // Restaurer les scores de l'équipe ET des joueurs
                                List<Equipe> equipes = tournoiEnCours.getEquipesInscrites();
                                for (Equipe equipe : equipes) {
                                    if (equipe.getNom().equals(nomEquipe)) {
                                        // Mettre à jour les statistiques de l'équipe et des joueurs
                                        for (int i = 0; i < matchsJoues; i++) {
                                            boolean victoire = (i < matchsGagnes);
                                            
                                            // Mettre à jour l'équipe
                                            equipe.enregistrerMatch(victoire);
                                            
                                            // Mettre à jour les statistiques individuelles des joueurs
                                            joueur1.enregistrerMatchTournoi(victoire);
                                            joueur2.enregistrerMatchTournoi(victoire);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    
                    // Restaurer l'état du tournoi
                    if (!inscriptionsOuvertes) {
                        tournoiEnCours.fermerInscriptions();
                    }
                    if (tournoiDemarre) {
                        tournoiEnCours.demarrerTournoi();
                    }
                    
                    tournoiRestored = true;
                } catch (Exception e) {
                    System.out.println("[!] Erreur lors de la reconstruction du tournoi: " + e.getMessage());
                }
            }
            
            System.out.println("» État du bar chargé avec succès.");
            if (cashUpdated) {
                System.out.println("  • Caisse mise à jour");
            }
            if (clientsLoaded > 0) {
                System.out.println("  • " + clientsLoaded + " client(s) chargé(s)");
            }
            if (personnelLoaded > 0) {
                System.out.println("  • " + personnelLoaded + " membre(s) du personnel chargé(s)");
            }
            if (tournoiRestored) {
                System.out.println("  • Tournoi restauré (" + equipesLoaded + " équipe(s))");
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
    
    // ========== MENU ACTIONS SPÉCIALES ==========
    
    private static void actionsSpeciales() {
        System.out.println("\n--- Actions Spéciales ---");
        System.out.println("1. Actions de la Patronne");
        System.out.println("2. Actions Sociales");
        System.out.println("0. Retour");
        System.out.print("Votre choix: ");
        
        int choix = lireChoixUtilisateur(0, 2);
        
        switch (choix) {
            case 1:
                actionsPatronne();
                break;
            case 2:
                actionsSociales();
                break;
            case 0:
                System.out.println("Retour au menu principal...");
                break;
            default:
                System.out.println("Choix invalide.");
                break;
        }
    }
    
    private static void actionsPatronne() {
        System.out.println("\n--- Actions de la Patronne ---");
        System.out.println("1. Récupérer l'argent de la Caisse");
        System.out.println("2. Exclure un client");
        System.out.println("0. Retour");
        System.out.print("Votre choix: ");
        
        int choix = lireChoixUtilisateur(0, 2);
        
        switch (choix) {
            case 1:
                actionRecupererArgentCaisse();
                break;
            case 2:
                actionExclureClient();
                break;
            case 0:
                System.out.println("Retour...");
                break;
            default:
                System.out.println("Choix invalide.");
                break;
        }
    }
    
    private static void actionRecupererArgentCaisse() {
        System.out.println("\n=== RÉCUPÉRER ARGENT DE LA CAISSE ===");
        
        if (leBar == null || leBar.getPatronne() == null) {
            System.out.println("[!] Bar ou patronne non initialisé.");
            return;
        }
        
        Patron patronne = leBar.getPatronne();
        Barman barman = leBar.getBarman();
        
        if (barman == null || barman.getCaisse() == null) {
            System.out.println("[!] Pas de barman ou de caisse disponible.");
            return;
        }
        
        Caisse caisse = barman.getCaisse();
        
        System.out.println("Montant actuel dans la caisse: " + 
                           String.format("%.2f", caisse.getMontantTotal()) + " euros");
        System.out.println("Portefeuille de la patronne: " + 
                           String.format("%.2f", patronne.getPorteMonnaie()) + " euros");
        
        System.out.print("\nMontant à récupérer (0 pour annuler): ");
        double montant = lireDoubleUtilisateur("");
        
        if (montant <= 0) {
            System.out.println("Opération annulée.");
            return;
        }
        
        patronne.recupererArgentCaisse(caisse, montant);
    }
    
    private static void actionExclureClient() {
        System.out.println("\n=== EXCLURE UN CLIENT ===");
        
        if (leBar == null || leBar.getPatronne() == null) {
            System.out.println("[!] Bar ou patronne non initialisé.");
            return;
        }
        
        if (leBar.getClients().isEmpty()) {
            System.out.println("Aucun client présent dans le bar.");
            return;
        }
        
        System.out.println("\nClients présents:");
        int i = 1;
        for (Client client : leBar.getClients()) {
            System.out.println(i++ + ". " + client.getPrenom() + " '" + client.getSurnom() + "'");
        }
        System.out.println("0. Annuler");
        
        System.out.print("\nQuel client voulez-vous exclure? ");
        int choix = lireChoixUtilisateur(0, leBar.getClients().size());
        
        if (choix == 0) {
            System.out.println("Opération annulée.");
            return;
        }
        
        Client clientAExclure = leBar.getClients().get(choix - 1);
        leBar.getPatronne().parler("Je suis désolée " + clientAExclure.getPrenom() + 
                                    ", mais je dois vous demander de partir.");
        clientAExclure.parler(clientAExclure.getCriSignificatif() + " D'accord, je pars...");
        
        leBar.getClients().remove(clientAExclure);
        System.out.println("\n» " + clientAExclure.getPrenom() + " a été exclu(e) du bar.");
    }
    
    private static void actionsSociales() {
        System.out.println("\n--- Actions Sociales ---");
        System.out.println("1. Offrir un verre");
        System.out.println("2. Commander au Fournisseur");
        System.out.println("0. Retour");
        System.out.print("Votre choix: ");
        
        int choix = lireChoixUtilisateur(0, 2);
        
        switch (choix) {
            case 1:
                actionOffrirVerre();
                break;
            case 2:
                actionCommanderFournisseur();
                break;
            case 0:
                System.out.println("Retour...");
                break;
            default:
                System.out.println("Choix invalide.");
                break;
        }
    }
    
    private static void actionOffrirVerre() {
        System.out.println("\n=== OFFRIR UN VERRE ===");
        
        if (leBar == null) {
            System.out.println("[!] Bar non initialisé.");
            return;
        }
        
        // Créer une liste de tous les humains
        List<Human> tousLesHumains = new ArrayList<>();
        
        // Ajouter la patronne
        if (leBar.getPatronne() != null) {
            tousLesHumains.add(leBar.getPatronne());
        }
        
        // Ajouter le personnel
        if (leBar.getPersonnel() != null) {
            tousLesHumains.addAll(leBar.getPersonnel());
        }
        
        // Ajouter les clients
        if (leBar.getClients() != null) {
            tousLesHumains.addAll(leBar.getClients());
        }
        
        // Ajouter le fournisseur
        if (fournisseur != null) {
            tousLesHumains.add(fournisseur);
        }
        
        if (tousLesHumains.size() < 2) {
            System.out.println("[!] Pas assez de personnes pour offrir un verre.");
            return;
        }
        
        // Demander qui offre
        System.out.println("\nQui offre ?");
        for (int i = 0; i < tousLesHumains.size(); i++) {
            Human h = tousLesHumains.get(i);
            System.out.println((i + 1) + ". " + h.getPrenom() + " '" + h.getSurnom() + "' [" + 
                             h.getClass().getSimpleName() + "] (" + 
                             String.format("%.2f", h.getPorteMonnaie()) + " euros)");
        }
        System.out.println("0. Annuler");
        
        System.out.print("\nChoisissez celui qui offre: ");
        int choixOffrant = lireChoixUtilisateur(0, tousLesHumains.size());
        
        if (choixOffrant == 0) {
            System.out.println("État annulé.");
            return;
        }
        
        Human offrant = tousLesHumains.get(choixOffrant - 1);
        
        // Demander qui reçoit
        System.out.println("\nQui reçoit ?");
        for (int i = 0; i < tousLesHumains.size(); i++) {
            Human h = tousLesHumains.get(i);
            if (h != offrant) {
                System.out.println((i + 1) + ". " + h.getPrenom() + " '" + h.getSurnom() + "' [" + 
                                 h.getClass().getSimpleName() + "]");
            }
        }
        System.out.println("0. Annuler");
        
        System.out.print("\nChoisissez celui qui reçoit: ");
        int choixReceveur = lireChoixUtilisateur(0, tousLesHumains.size());
        
        if (choixReceveur == 0) {
            System.out.println("État annulé.");
            return;
        }
        
        Human receveur = tousLesHumains.get(choixReceveur - 1);
        
        if (offrant == receveur) {
            System.out.println("[!] Une personne ne peut pas s'offrir un verre à elle-même !");
            return;
        }
        
        // Demander quelle boisson
        System.out.println("\nQuelle boisson ?");
        List<Boisson> boissonsMenu = (leBar != null) ? leBar.getConsommationsProposees() : Collections.emptyList();
        
        if (boissonsMenu == null || boissonsMenu.isEmpty()) {
            System.out.println("[!] Aucune boisson disponible.");
            return;
        }
        
        for (int i = 0; i < boissonsMenu.size(); i++) {
            Boisson b = boissonsMenu.get(i);
            System.out.println((i + 1) + ". " + b.getNom() + " (" + 
                             String.format("%.2f", b.getPrixVente()) + " euros)");
        }
        System.out.println("0. Annuler");
        
        System.out.print("\nChoisissez une boisson: ");
        int choixBoisson = lireChoixUtilisateur(0, boissonsMenu.size());
        
        if (choixBoisson == 0) {
            System.out.println("État annulé.");
            return;
        }
        
        Boisson boissonChoisie = boissonsMenu.get(choixBoisson - 1);
        Barman barman = (leBar != null) ? leBar.getBarman() : null;
        
        if (barman == null) {
            System.out.println("[!] Pas de barman disponible pour servir.");
            return;
        }
        
        // Gérer la transaction
        try {
            System.out.println("\n--- Transaction en cours ---");
            offrant.parler("Je t'offre un verre, " + receveur.getPrenom() + " !");
            
            // Le barman sert la boisson
            barman.servirBoisson(boissonChoisie);
            
            // L'offrant paie
            barman.recevoirPaiement(offrant, boissonChoisie.getPrixVente());
            
            // Le receveur boit (seulement si c'est un Client)
            if (receveur instanceof Client) {
                ((Client) receveur).boire(boissonChoisie);
            } else {
                receveur.parler("Merci " + offrant.getPrenom() + " ! Santé !");
            }
            
            System.out.println("\n» " + offrant.getPrenom() + " a offert un " + boissonChoisie.getNom() + 
                             " à " + receveur.getPrenom() + " !");
            
        } catch (OutOfStockException | NotEnoughMoneyException e) {
            System.err.println("[!] Transaction échouée: " + e.getMessage());
            barman.parler("Désolé, je ne peux pas servir...");
        } catch (Exception e) {
            System.err.println("[!] Erreur inattendue: " + e.getMessage());
        }
    }
    
    private static void actionCommanderFournisseur() {
        System.out.println("\n=== COMMANDER AU FOURNISSEUR ===");
        
        if (leBar == null || leBar.getPatronne() == null) {
            System.out.println("[!] Bar ou patronne non initialisé.");
            return;
        }
        
        if (fournisseur == null) {
            System.out.println("[!] Aucun fournisseur disponible.");
            return;
        }
        
        Barman barman = leBar.getBarman();
        if (barman == null) {
            System.out.println("[!] Pas de barman disponible.");
            return;
        }
        
        Patron patronne = leBar.getPatronne();
        
        // Afficher les boissons disponibles
        System.out.println("\nQuelle boisson commander ?");
        List<Boisson> boissonsMenu = (leBar != null) ? leBar.getConsommationsProposees() : Collections.emptyList();
        
        if (boissonsMenu == null || boissonsMenu.isEmpty()) {
            System.out.println("[!] Aucune boisson disponible au catalogue.");
            return;
        }
        
        for (int i = 0; i < boissonsMenu.size(); i++) {
            Boisson b = boissonsMenu.get(i);
            int stockActuel = barman.getStock().getOrDefault(b, 0);
            System.out.println((i + 1) + ". " + b.getNom() + " - Prix d'achat: " + 
                             String.format("%.2f", b.getPrixAchat()) + " euros (Stock actuel: " + 
                             stockActuel + ")");
        }
        System.out.println("0. Annuler");
        
        System.out.print("\nChoisissez une boisson: ");
        int choixBoisson = lireChoixUtilisateur(0, boissonsMenu.size());
        
        if (choixBoisson == 0) {
            System.out.println("Opération annulée.");
            return;
        }
        
        Boisson boissonChoisie = boissonsMenu.get(choixBoisson - 1);
        
        // Demander la quantité
        System.out.print("\nQuelle quantité commander ? ");
        int quantite = (int) lireDoubleUtilisateur("");
        
        if (quantite <= 0) {
            System.out.println("[!] Quantité invalide.");
            return;
        }
        
        // Calculer le coût total
        double coutTotal = boissonChoisie.getPrixAchat() * quantite;
        
        System.out.println("\nRécapitulatif de la commande:");
        System.out.println("Boisson: " + boissonChoisie.getNom());
        System.out.println("Quantité: " + quantite);
        System.out.println("Coût total: " + String.format("%.2f", coutTotal) + " euros");
        System.out.println("\nPortefeuille de la patronne: " + 
                         String.format("%.2f", patronne.getPorteMonnaie()) + " euros");
        
        if (patronne.getPorteMonnaie() < coutTotal) {
            System.out.println("\n[!] La patronne n'a pas assez d'argent pour payer cette commande !");
            return;
        }
        
        System.out.print("\nConfirmer la commande ? (oui/non): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (!confirmation.equals("oui") && !confirmation.equals("o") && !confirmation.equals("y") && !confirmation.equals("yes")) {
            System.out.println("Commande annulée.");
            return;
        }
        
        // Exécuter la commande
        System.out.println("\n--- Commande en cours ---");
        
        // Le barman passe commande
        barman.passerCommande(fournisseur, boissonChoisie, quantite);
        
        // La patronne paie le fournisseur
        patronne.payerFournisseur(fournisseur, coutTotal);
        
        System.out.println("\n» Commande effectuée avec succès !");
        System.out.println("Nouveau stock de " + boissonChoisie.getNom() + ": " + 
                         barman.getStock().get(boissonChoisie) + " unités");
    }
}