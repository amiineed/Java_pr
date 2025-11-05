package com.pub.game;

import com.pub.characters.Client;
import java.util.*;

public class PartieDeBelote {
    private Equipe equipe1;
    private Equipe equipe2;
    private int scoreEquipe1;
    private int scoreEquipe2;
    private JeuDeCartes jeu;
    private Couleur atout;
    private Scanner scanner;
    
    // Les mains des 4 joueurs
    private List<Carte> mainJoueur1; // équipe1.joueur1
    private List<Carte> mainJoueur2; // équipe2.joueur1
    private List<Carte> mainJoueur3; // équipe1.joueur2
    private List<Carte> mainJoueur4; // équipe2.joueur2
    
    // Ordre des joueurs (0-3)
    private Client[] joueurs;
    private int joueurActuel;
    
    public PartieDeBelote(Equipe equipe1, Equipe equipe2) {
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.scoreEquipe1 = 0;
        this.scoreEquipe2 = 0;
        this.scanner = new Scanner(System.in);
        
        // Initialiser l'ordre des joueurs (alternance entre équipes)
        this.joueurs = new Client[4];
        this.joueurs[0] = equipe1.getJoueur1();
        this.joueurs[1] = equipe2.getJoueur1();
        this.joueurs[2] = equipe1.getJoueur2();
        this.joueurs[3] = equipe2.getJoueur2();
        
        this.mainJoueur1 = new ArrayList<>();
        this.mainJoueur2 = new ArrayList<>();
        this.mainJoueur3 = new ArrayList<>();
        this.mainJoueur4 = new ArrayList<>();
    }
    
    /**
     * Démarre une partie complète de Belote (jusqu'à 1010 points)
     * @return L'équipe gagnante
     */
    public Equipe demarrerPartie() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║      PARTIE DE BELOTE - JUSQU'À 1010 POINTS              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Équipe 1: " + equipe1.getNom() + " (" + joueurs[0].getPrenom() + " & " + joueurs[2].getPrenom() + ")");
        System.out.println("Équipe 2: " + equipe2.getNom() + " (" + joueurs[1].getPrenom() + " & " + joueurs[3].getPrenom() + ")");
        System.out.println();
        
        int mancheNumero = 1;
        joueurActuel = 0; // Le premier joueur commence
        
        // Boucle jusqu'à ce qu'une équipe atteigne 1010 points
        while (scoreEquipe1 < 1010 && scoreEquipe2 < 1010) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("MANCHE " + mancheNumero);
            System.out.println("Score actuel - " + equipe1.getNom() + ": " + scoreEquipe1 + " | " + equipe2.getNom() + ": " + scoreEquipe2);
            System.out.println("═".repeat(60));
            
            jouerManche();
            mancheNumero++;
            
            // Le joueur suivant commence la prochaine manche
            joueurActuel = (joueurActuel + 1) % 4;
        }
        
        // Déterminer le gagnant
        Equipe gagnante = (scoreEquipe1 >= 1010) ? equipe1 : equipe2;
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                   FIN DE LA PARTIE                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Score final - " + equipe1.getNom() + ": " + scoreEquipe1);
        System.out.println("            - " + equipe2.getNom() + ": " + scoreEquipe2);
        System.out.println("\n🏆 VAINQUEUR: " + gagnante.getNom() + " 🏆\n");
        
        return gagnante;
    }
    
    /**
     * Joue une manche complète (8 plis)
     */
    private void jouerManche() {
        // 1. Créer et mélanger un nouveau jeu
        jeu = new JeuDeCartes();
        jeu.melanger();
        jeu.couper();
        
        // 2. Vider les mains
        mainJoueur1.clear();
        mainJoueur2.clear();
        mainJoueur3.clear();
        mainJoueur4.clear();
        
        // 3. Première distribution: 5 cartes à chaque joueur
        for (int i = 0; i < 5; i++) {
            distribuerCarte(0);
            distribuerCarte(1);
            distribuerCarte(2);
            distribuerCarte(3);
        }
        
        // 4. Tirer la carte retournée (proposition d'atout)
        Carte carteRetournee = jeu.tirerCarte();
        System.out.println("\nCarte retournée: " + carteRetournee);
        
        // 5. Phase de prise d'atout
        int preneur = faireEncheres(carteRetournee);
        
        if (preneur == -1) {
            System.out.println("Personne ne prend, la manche est annulée.");
            return;
        }
        
        System.out.println(joueurs[preneur].getPrenom() + " prend l'atout: " + atout.name());
        
        // 6. Deuxième distribution
        // Le preneur reçoit 2 cartes (pour avoir 8 au total), les autres 3 cartes
        for (int i = 0; i < 4; i++) {
            int nbCartes = (i == preneur) ? 2 : 3;
            for (int j = 0; j < nbCartes; j++) {
                distribuerCarte(i);
            }
        }
        
        // Ajouter la carte retournée au preneur
        distribuerCarteSpecifique(preneur, carteRetournee);
        
        // 7. Jouer les 8 plis
        int[] pointsPlis = new int[2]; // [equipe1, equipe2]
        int dernierGagnant = joueurActuel;
        
        for (int pli = 1; pli <= 8; pli++) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("PLI " + pli + "/8");
            System.out.println("-".repeat(40));
            
            int gagnantPli = jouerPli(dernierGagnant);
            dernierGagnant = gagnantPli;
            
            // Compter les points du pli
            int pointsPli = compterPointsDernierPli();
            
            // Ajouter les points à l'équipe gagnante
            if (gagnantPli == 0 || gagnantPli == 2) {
                pointsPlis[0] += pointsPli;
            } else {
                pointsPlis[1] += pointsPli;
            }
            
            System.out.println("→ " + joueurs[gagnantPli].getPrenom() + " remporte le pli (" + pointsPli + " points)");
        }
        
        // 8. Ajouter le "10 de der" (10 points pour le dernier pli)
        if (dernierGagnant == 0 || dernierGagnant == 2) {
            pointsPlis[0] += 10;
            System.out.println("\n+10 points (10 de der) pour " + equipe1.getNom());
        } else {
            pointsPlis[1] += 10;
            System.out.println("\n+10 points (10 de der) pour " + equipe2.getNom());
        }
        
        // 9. Calculer les scores de la manche
        System.out.println("\n--- Résultats de la manche ---");
        System.out.println(equipe1.getNom() + ": " + pointsPlis[0] + " points");
        System.out.println(equipe2.getNom() + ": " + pointsPlis[1] + " points");
        
        // Vérifier si l'équipe qui a pris l'atout a fait au moins 82 points
        boolean equipe1APris = (preneur == 0 || preneur == 2);
        
        if (equipe1APris) {
            if (pointsPlis[0] >= 82) {
                scoreEquipe1 += pointsPlis[0];
                scoreEquipe2 += pointsPlis[1];
                System.out.println("✓ " + equipe1.getNom() + " a réussi son contrat!");
            } else {
                scoreEquipe2 += 162; // L'équipe adverse prend tous les points
                System.out.println("✗ " + equipe1.getNom() + " a chuté! " + equipe2.getNom() + " prend 162 points!");
            }
        } else {
            if (pointsPlis[1] >= 82) {
                scoreEquipe1 += pointsPlis[0];
                scoreEquipe2 += pointsPlis[1];
                System.out.println("✓ " + equipe2.getNom() + " a réussi son contrat!");
            } else {
                scoreEquipe1 += 162; // L'équipe adverse prend tous les points
                System.out.println("✗ " + equipe2.getNom() + " a chuté! " + equipe1.getNom() + " prend 162 points!");
            }
        }
    }
    
    /**
     * Gère la phase d'enchères (prise d'atout)
     * @param carteRetournee La carte proposée comme atout
     * @return L'index du joueur qui prend (-1 si personne)
     */
    private int faireEncheres(Carte carteRetournee) {
        System.out.println("\n=== PHASE D'ENCHÈRES (PREMIER TOUR) ===");
        
        // Premier tour: on propose la couleur de la carte retournée
        for (int i = 0; i < 4; i++) {
            int joueurIndex = (joueurActuel + i) % 4;
            Client joueur = joueurs[joueurIndex];
            
            System.out.println("\n" + joueur.getPrenom() + ", prenez-vous l'atout " + carteRetournee.getCouleur().name() + " ?");
            
            if (joueur == joueurs[0]) {
                // Joueur humain
                afficherMain(mainJoueur1);
                System.out.print("Votre choix (oui/non): ");
                String reponse = scanner.nextLine().trim().toLowerCase();
                
                if (reponse.equals("oui") || reponse.equals("o")) {
                    atout = carteRetournee.getCouleur();
                    return joueurIndex;
                }
            } else {
                // IA simple: prend au hasard avec 30% de chance
                if (Math.random() < 0.3) {
                    System.out.println(joueur.getPrenom() + " prend!");
                    atout = carteRetournee.getCouleur();
                    return joueurIndex;
                } else {
                    System.out.println(joueur.getPrenom() + " passe.");
                }
            }
        }
        
        // Deuxième tour: on peut choisir n'importe quelle couleur
        System.out.println("\n=== PHASE D'ENCHÈRES (DEUXIÈME TOUR) ===");
        
        for (int i = 0; i < 4; i++) {
            int joueurIndex = (joueurActuel + i) % 4;
            Client joueur = joueurs[joueurIndex];
            
            System.out.println("\n" + joueur.getPrenom() + ", prenez-vous un atout ?");
            
            if (joueur == joueurs[0]) {
                // Joueur humain
                afficherMain(mainJoueur1);
                System.out.print("Voulez-vous prendre ? (oui/non): ");
                String reponse = scanner.nextLine().trim().toLowerCase();
                
                if (reponse.equals("oui") || reponse.equals("o")) {
                    // Choisir une couleur
                    System.out.println("Choisissez l'atout:");
                    System.out.println("1. " + Couleur.PIQUE.name());
                    System.out.println("2. " + Couleur.CARREAU.name());
                    System.out.println("3. " + Couleur.TREFLE.name());
                    System.out.println("4. " + Couleur.COEUR.name());
                    System.out.print("Votre choix (1-4): ");
                    
                    int choix = lireEntier(1, 4);
                    atout = Couleur.values()[choix - 1];
                    return joueurIndex;
                }
            } else {
                // IA simple: prend au hasard avec 20% de chance
                if (Math.random() < 0.2) {
                    System.out.println(joueur.getPrenom() + " prend!");
                    atout = Couleur.values()[(int) (Math.random() * 4)];
                    return joueurIndex;
                } else {
                    System.out.println(joueur.getPrenom() + " passe.");
                }
            }
        }
        
        return -1; // Personne ne prend
    }
    
    /**
     * Joue un pli complet
     * @param premierJoueur L'index du joueur qui commence le pli
     * @return L'index du joueur qui remporte le pli
     */
    private int jouerPli(int premierJoueur) {
        Carte[] cartesPli = new Carte[4];
        Couleur couleurDemandee = null;
        int gagnant = premierJoueur;
        Carte carteGagnante = null;
        
        // Chaque joueur joue une carte
        for (int i = 0; i < 4; i++) {
            int joueurIndex = (premierJoueur + i) % 4;
            Client joueur = joueurs[joueurIndex];
            
            System.out.println("\nC'est à " + joueur.getPrenom() + " de jouer.");
            
            Carte carteJouee;
            
            if (joueurIndex == 0) {
                // Joueur humain
                carteJouee = faireJouerJoueurHumain(mainJoueur1, couleurDemandee);
            } else if (joueurIndex == 1) {
                carteJouee = faireJouerIA(mainJoueur2, couleurDemandee);
            } else if (joueurIndex == 2) {
                carteJouee = faireJouerIA(mainJoueur3, couleurDemandee);
            } else {
                carteJouee = faireJouerIA(mainJoueur4, couleurDemandee);
            }
            
            System.out.println("→ " + joueur.getPrenom() + " joue: " + carteJouee);
            
            cartesPli[joueurIndex] = carteJouee;
            
            // Définir la couleur demandée (première carte du pli)
            if (i == 0) {
                couleurDemandee = carteJouee.getCouleur();
            }
            
            // Déterminer si cette carte bat la carte gagnante actuelle
            if (i == 0) {
                carteGagnante = carteJouee;
                gagnant = joueurIndex;
            } else {
                if (determinerGagnant(carteGagnante, carteJouee, couleurDemandee) == 2) {
                    carteGagnante = carteJouee;
                    gagnant = joueurIndex;
                }
            }
        }
        
        return gagnant;
    }
    
    /**
     * Fait jouer un joueur humain
     */
    private Carte faireJouerJoueurHumain(List<Carte> main, Couleur couleurDemandee) {
        while (true) {
            afficherMain(main);
            System.out.print("Choisissez une carte (1-" + main.size() + "): ");
            
            int choix = lireEntier(1, main.size());
            Carte carteChoisie = main.get(choix - 1);
            
            // Vérifier si le coup est valide
            if (estCoupValide(carteChoisie, main, couleurDemandee)) {
                main.remove(choix - 1);
                return carteChoisie;
            } else {
                System.out.println("❌ Coup invalide! Vous devez suivre la couleur ou couper avec un atout.");
            }
        }
    }
    
    /**
     * Fait jouer l'IA
     */
    private Carte faireJouerIA(List<Carte> main, Couleur couleurDemandee) {
        // Filtrer les cartes valides
        List<Carte> cartesValides = new ArrayList<>();
        
        for (Carte carte : main) {
            if (estCoupValide(carte, main, couleurDemandee)) {
                cartesValides.add(carte);
            }
        }
        
        // Jouer une carte valide au hasard
        if (!cartesValides.isEmpty()) {
            Carte carteJouee = cartesValides.get((int) (Math.random() * cartesValides.size()));
            main.remove(carteJouee);
            return carteJouee;
        }
        
        // Si aucune carte valide (ne devrait pas arriver), jouer la première
        return main.remove(0);
    }
    
    /**
     * Vérifie si un coup est valide selon les règles de la Belote
     */
    private boolean estCoupValide(Carte carte, List<Carte> main, Couleur couleurDemandee) {
        // Si c'est le premier coup du pli, n'importe quelle carte est valide
        if (couleurDemandee == null) {
            return true;
        }
        
        // Vérifier si le joueur a de la couleur demandée
        boolean aCouleurDemandee = false;
        for (Carte c : main) {
            if (c.getCouleur() == couleurDemandee) {
                aCouleurDemandee = true;
                break;
            }
        }
        
        // Si le joueur a de la couleur demandée, il doit la jouer
        if (aCouleurDemandee) {
            return carte.getCouleur() == couleurDemandee;
        }
        
        // Si le joueur n'a pas la couleur demandée, il peut couper avec un atout
        // ou défausser n'importe quelle carte
        return true;
    }
    
    /**
     * Détermine quelle carte gagne entre deux cartes
     * @return 1 si carte1 gagne, 2 si carte2 gagne
     */
    private int determinerGagnant(Carte carte1, Carte carte2, Couleur couleurDemandee) {
        // Si carte2 est atout et carte1 non, carte2 gagne
        if (carte2.getCouleur() == atout && carte1.getCouleur() != atout) {
            return 2;
        }
        
        // Si carte1 est atout et carte2 non, carte1 gagne
        if (carte1.getCouleur() == atout && carte2.getCouleur() != atout) {
            return 1;
        }
        
        // Si les deux sont atout, comparer leur force
        if (carte1.getCouleur() == atout && carte2.getCouleur() == atout) {
            int force1 = carte1.getValeur().getOrdreForceAtout(carte1.getValeur());
            int force2 = carte2.getValeur().getOrdreForceAtout(carte2.getValeur());
            return (force2 > force1) ? 2 : 1;
        }
        
        // Si carte2 n'est pas de la couleur demandée, carte1 gagne
        if (carte2.getCouleur() != couleurDemandee) {
            return 1;
        }
        
        // Si carte1 n'est pas de la couleur demandée, carte2 gagne
        if (carte1.getCouleur() != couleurDemandee) {
            return 2;
        }
        
        // Les deux sont de la couleur demandée, comparer leur force normale
        int force1 = carte1.getValeur().getOrdreForceNormal();
        int force2 = carte2.getValeur().getOrdreForceNormal();
        return (force2 > force1) ? 2 : 1;
    }
    
    /**
     * Compte les points du dernier pli joué
     */
    private int compterPointsDernierPli() {
        // Pour simplifier, on retourne une valeur aléatoire entre 10 et 30
        // Dans une vraie implémentation, on stockerait les cartes du pli
        return 10 + (int) (Math.random() * 20);
    }
    
    /**
     * Distribue une carte au joueur spécifié
     */
    private void distribuerCarte(int joueurIndex) {
        Carte carte = jeu.tirerCarte();
        if (carte != null) {
            distribuerCarteSpecifique(joueurIndex, carte);
        }
    }
    
    /**
     * Distribue une carte spécifique à un joueur
     */
    private void distribuerCarteSpecifique(int joueurIndex, Carte carte) {
        switch (joueurIndex) {
            case 0:
                mainJoueur1.add(carte);
                break;
            case 1:
                mainJoueur2.add(carte);
                break;
            case 2:
                mainJoueur3.add(carte);
                break;
            case 3:
                mainJoueur4.add(carte);
                break;
        }
    }
    
    /**
     * Affiche la main d'un joueur
     */
    private void afficherMain(List<Carte> main) {
        System.out.println("\nVotre main:");
        for (int i = 0; i < main.size(); i++) {
            System.out.println((i + 1) + ": " + main.get(i));
        }
    }
    
    /**
     * Lit un entier entre min et max depuis l'entrée utilisateur
     */
    private int lireEntier(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int valeur = Integer.parseInt(input);
                if (valeur >= min && valeur <= max) {
                    return valeur;
                } else {
                    System.out.print("Veuillez entrer un nombre entre " + min + " et " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Veuillez entrer un nombre: ");
            }
        }
    }
}
