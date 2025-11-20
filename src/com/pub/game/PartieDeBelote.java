package com.pub.game;

import com.pub.characters.Human;
import java.util.*;

public class PartieDeBelote {
    private Equipe equipe1;
    private Equipe equipe2;
    private int scoreEquipe1;
    private int scoreEquipe2;
    private JeuDeCartes jeu;
    private Couleur atout;
    private Scanner scanner;
    
    private List<Carte> mainJoueur1; 
    private List<Carte> mainJoueur2; 
    private List<Carte> mainJoueur3; 
    private List<Carte> mainJoueur4; 
    
    private Human[] joueurs;
    private int joueurActuel;
    
    private Map<Integer, List<Annonce>> annoncesParEquipe; 
    private boolean annoncesValidees;
    private int pliActuel;
    private int preneurIndex;
    private Carte[] cartesPliActuel; 
    private int nombreCartesJouees; 
    
    private boolean modeInteractif; 
    private String nomJoueurHumain; 
    
    
    private static class Annonce {
        enum TypeAnnonce {
            TIERCE(20, "Tierce"),
            CINQUANTE(50, "50 (Quarte)"),
            CENT(100, "100 (Quinte)"),
            CARRE_VALETS(200, "Carré de Valets"),
            CARRE_NEUF(150, "Carré de 9"),
            CARRE_AS(100, "Carré d'As"),
            CARRE_DIX(100, "Carré de 10"),
            CARRE_ROIS(100, "Carré de Rois"),
            CARRE_DAMES(100, "Carré de Dames"),
            BELOTE_REBELOTE(20, "Belote et Rebelote");
            
            final int points;
            final String nom;
            
            TypeAnnonce(int points, String nom) {
                this.points = points;
                this.nom = nom;
            }
        }
        
        TypeAnnonce type;
        List<Carte> cartes;
        int joueurIndex;
        
        Annonce(TypeAnnonce type, List<Carte> cartes, int joueurIndex) {
            this.type = type;
            this.cartes = cartes;
            this.joueurIndex = joueurIndex;
        }
        
        int getPoints() {
            return type.points;
        }
        
        @Override
        public String toString() {
            return type.nom + " (" + type.points + " pts)";
        }
    }
    
    
    public PartieDeBelote(Equipe equipe1, Equipe equipe2) {
        this(equipe1, equipe2, false, null);
    }
    

    public PartieDeBelote(Equipe equipe1, Equipe equipe2, boolean modeInteractif, String nomJoueurHumain) {
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.scoreEquipe1 = 0;
        this.scoreEquipe2 = 0;
        this.scanner = new Scanner(System.in);
        this.modeInteractif = modeInteractif;
        this.nomJoueurHumain = nomJoueurHumain;
        
        this.joueurs = new Human[4];
        this.joueurs[0] = equipe1.getJoueur1();
        this.joueurs[1] = equipe2.getJoueur1();
        this.joueurs[2] = equipe1.getJoueur2();
        this.joueurs[3] = equipe2.getJoueur2();
        
        this.mainJoueur1 = new ArrayList<>();
        this.mainJoueur2 = new ArrayList<>();
        this.mainJoueur3 = new ArrayList<>();
        this.mainJoueur4 = new ArrayList<>();
        
        this.annoncesParEquipe = new HashMap<>();
        this.annoncesParEquipe.put(0, new ArrayList<>());
        this.annoncesParEquipe.put(1, new ArrayList<>());
        this.annoncesValidees = false;
        this.pliActuel = 0;
        this.cartesPliActuel = new Carte[4];
        this.nombreCartesJouees = 0;
    }
    
    public int getScoreEquipe1() { return scoreEquipe1; }
    public int getScoreEquipe2() { return scoreEquipe2; }
    

    public Equipe demarrerPartie() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║      PARTIE DE BELOTE - JUSQU'À 1010 POINTS              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Équipe 1: " + equipe1.getNom() + " (" + joueurs[0].getPrenom() + " & " + joueurs[2].getPrenom() + ")");
        System.out.println("Équipe 2: " + equipe2.getNom() + " (" + joueurs[1].getPrenom() + " & " + joueurs[3].getPrenom() + ")");
        System.out.println();
        
        int mancheNumero = 1;
        joueurActuel = 0; 
        
        while (scoreEquipe1 < 1010 && scoreEquipe2 < 1010) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("MANCHE " + mancheNumero);
            System.out.println("Score actuel - " + equipe1.getNom() + ": " + scoreEquipe1 + " | " + equipe2.getNom() + ": " + scoreEquipe2);
            System.out.println("═".repeat(60));
            
            jouerManche();
            mancheNumero++;
            
            joueurActuel = (joueurActuel + 1) % 4;
        }
        
        Equipe gagnante = (scoreEquipe1 >= 1010) ? equipe1 : equipe2;
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                   FIN DE LA PARTIE                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Score final - " + equipe1.getNom() + ": " + scoreEquipe1);
        System.out.println("            - " + equipe2.getNom() + ": " + scoreEquipe2);
        System.out.println("\n» VAINQUEUR: " + gagnante.getNom() + " «\n");
        
        return gagnante;
    }
    
    
    private void jouerManche() {
        jeu = new JeuDeCartes();
        jeu.melanger();
        jeu.couper();
        
        mainJoueur1.clear();
        mainJoueur2.clear();
        mainJoueur3.clear();
        mainJoueur4.clear();
        
        for (int i = 0; i < 5; i++) {
            distribuerCarte(0);
            distribuerCarte(1);
            distribuerCarte(2);
            distribuerCarte(3);
        }
        
        Carte carteRetournee = jeu.tirerCarte();
        System.out.println("\nCarte retournée: " + carteRetournee);
        
        int preneur = faireEncheres(carteRetournee);
        
        if (preneur == -1) {
            System.out.println("Personne ne prend, la manche est annulée.");
            return;
        }
        
        System.out.println(joueurs[preneur].getPrenom() + " prend l'atout: " + atout.name());
        
        for (int i = 0; i < 4; i++) {
            int nbCartes = (i == preneur) ? 2 : 3;
            for (int j = 0; j < nbCartes; j++) {
                distribuerCarte(i);
            }
        }
        
        distribuerCarteSpecifique(preneur, carteRetournee);
        preneurIndex = preneur;
        
        detecterAnnonces();
        afficherAnnonces();
        
        int[] pointsPlis = new int[2]; 
        int dernierGagnant = joueurActuel;
        annoncesValidees = false;
        pliActuel = 0; 
        
        for (int pli = 1; pli <= 8; pli++) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("PLI " + pli + "/8");
            System.out.println("-".repeat(40));
            
            int gagnantPli = jouerPli(dernierGagnant);
            dernierGagnant = gagnantPli;
            
            int pointsPli = compterPointsDernierPli();
            
            if (gagnantPli == 0 || gagnantPli == 2) {
                pointsPlis[0] += pointsPli;
            } else {
                pointsPlis[1] += pointsPli;
            }
            
            System.out.println("» " + joueurs[gagnantPli].getPrenom() + " remporte le pli (" + pointsPli + " points)");
        }
        
        if (dernierGagnant == 0 || dernierGagnant == 2) {
            pointsPlis[0] += 10;
            System.out.println("\n+10 points (10 de der) pour " + equipe1.getNom());
        } else {
            pointsPlis[1] += 10;
            System.out.println("\n+10 points (10 de der) pour " + equipe2.getNom());
        }
        
        System.out.println("\n--- Résultats de la manche ---");
        System.out.println(equipe1.getNom() + ": " + pointsPlis[0] + " points");
        System.out.println(equipe2.getNom() + ": " + pointsPlis[1] + " points");
        
        boolean equipe1APris = (preneur == 0 || preneur == 2);
        
        if (equipe1APris) {
            if (pointsPlis[0] >= 82) {
                scoreEquipe1 += pointsPlis[0];
                scoreEquipe2 += pointsPlis[1];
                System.out.println("» " + equipe1.getNom() + " a réussi son contrat!");
            } else {
                scoreEquipe2 += 162; 
                System.out.println("✗ " + equipe1.getNom() + " a chuté! " + equipe2.getNom() + " prend 162 points!");
            }
        } else {
            if (pointsPlis[1] >= 82) {
                scoreEquipe1 += pointsPlis[0];
                scoreEquipe2 += pointsPlis[1];
                System.out.println("» " + equipe2.getNom() + " a réussi son contrat!");
            } else {
                scoreEquipe1 += 162; 
                System.out.println("✗ " + equipe2.getNom() + " a chuté! " + equipe1.getNom() + " prend 162 points!");
            }
        }
    }
    
    private int faireEncheres(Carte carteRetournee) {
        System.out.println("\n=== PHASE D'ENCHÈRES (PREMIER TOUR) ===");
        
        for (int i = 0; i < 4; i++) {
            int joueurIndex = (joueurActuel + i) % 4;
            Human joueur = joueurs[joueurIndex];
            
            System.out.println("\n" + joueur.getPrenom() + ", prenez-vous l'atout " + carteRetournee.getCouleur().name() + " ?");
            
            boolean estJoueurHumain = modeInteractif && nomJoueurHumain != null && 
                                     joueur.getPrenom().equalsIgnoreCase(nomJoueurHumain);
            
            if (estJoueurHumain) {
                List<Carte> mainJoueur = getMainJoueur(joueurIndex);
                afficherMain(mainJoueur);
                System.out.print("Votre choix (oui/non): ");
                String reponse = scanner.nextLine().trim().toLowerCase();
                
                if (reponse.equals("oui") || reponse.equals("o")) {
                    atout = carteRetournee.getCouleur();
                    return joueurIndex;
                }
            } else {
                if (enchereIA(true)) {
                    System.out.println(joueur.getPrenom() + " prend!");
                    atout = carteRetournee.getCouleur();
                    return joueurIndex;
                } else {
                    System.out.println(joueur.getPrenom() + " passe.");
                }
            }
        }
        
        System.out.println("\n=== PHASE D'ENCHÈRES (DEUXIÈME TOUR) ===");
        
        for (int i = 0; i < 4; i++) {
            int joueurIndex = (joueurActuel + i) % 4;
            Human joueur = joueurs[joueurIndex];
            
            System.out.println("\n" + joueur.getPrenom() + ", prenez-vous un atout ?");
            
            boolean estJoueurHumain = modeInteractif && nomJoueurHumain != null && 
                                     joueur.getPrenom().equalsIgnoreCase(nomJoueurHumain);
            
            if (estJoueurHumain) {
                List<Carte> mainJoueur = getMainJoueur(joueurIndex);
                afficherMain(mainJoueur);
                System.out.print("Voulez-vous prendre ? (oui/non): ");
                String reponse = scanner.nextLine().trim().toLowerCase();
                
                if (reponse.equals("oui") || reponse.equals("o")) {
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
                if (enchereIA(false)) {
                    System.out.println(joueur.getPrenom() + " prend!");
                    atout = choixCouleurIA();
                    return joueurIndex;
                } else {
                    System.out.println(joueur.getPrenom() + " passe.");
                }
            }
        }
        
        return -1; 
    }
    

    private int jouerPli(int premierJoueur) {
        pliActuel++;
        cartesPliActuel = new Carte[4];
        nombreCartesJouees = 0;
        Couleur couleurDemandee = null;
        int gagnant = premierJoueur;
        Carte carteGagnante = null;
        
        for (int i = 0; i < 4; i++) {
            int joueurIndex = (premierJoueur + i) % 4;
            Human joueur = joueurs[joueurIndex];
            
            System.out.println("\nC'est à " + joueur.getPrenom() + " de jouer.");
            
            Carte carteJouee;
            
            boolean estJoueurHumain = modeInteractif && nomJoueurHumain != null && 
                                     joueur.getPrenom().equalsIgnoreCase(nomJoueurHumain);
            
            if (joueurIndex == 0) {
                if (estJoueurHumain) {
                    carteJouee = faireJouerJoueurHumain(mainJoueur1, couleurDemandee, joueurIndex);
                } else {
                    carteJouee = faireJouerIA(mainJoueur1, couleurDemandee, joueurIndex);
                }
            } else if (joueurIndex == 1) {
                if (estJoueurHumain) {
                    carteJouee = faireJouerJoueurHumain(mainJoueur2, couleurDemandee, joueurIndex);
                } else {
                    carteJouee = faireJouerIA(mainJoueur2, couleurDemandee, joueurIndex);
                }
            } else if (joueurIndex == 2) {
                if (estJoueurHumain) {
                    carteJouee = faireJouerJoueurHumain(mainJoueur3, couleurDemandee, joueurIndex);
                } else {
                    carteJouee = faireJouerIA(mainJoueur3, couleurDemandee, joueurIndex);
                }
            } else {
                if (estJoueurHumain) {
                    carteJouee = faireJouerJoueurHumain(mainJoueur4, couleurDemandee, joueurIndex);
                } else {
                    carteJouee = faireJouerIA(mainJoueur4, couleurDemandee, joueurIndex);
                }
            }
            
            System.out.println("» " + joueur.getPrenom() + " joue: " + carteJouee);
            
            cartesPliActuel[joueurIndex] = carteJouee;
            nombreCartesJouees++;
            
            if (i == 0) {
                couleurDemandee = carteJouee.getCouleur();
            }
            
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
        
        if (pliActuel == 2 && !annoncesValidees) {
            validerEtComptabiliserAnnonces();
            annoncesValidees = true;
        }
        
        return gagnant;
    }
    
    
    private Carte faireJouerJoueurHumain(List<Carte> main, Couleur couleurDemandee, int joueurIndex) {
        while (true) {
            afficherMain(main);
            System.out.print("Choisissez une carte (1-" + main.size() + "): ");
            
            int choix = lireEntier(1, main.size());
            Carte carteChoisie = main.get(choix - 1);
            
            if (estCoupValide(carteChoisie, main, couleurDemandee, joueurIndex)) {
                main.remove(choix - 1);
                return carteChoisie;
            } else {
                System.out.println("❌ Coup invalide! Vous devez respecter les règles de la Belote.");
                afficherReglesCoup(couleurDemandee, joueurIndex);
            }
        }
    }
    
   
    private Carte faireJouerIA(List<Carte> main, Couleur couleurDemandee, int joueurIndex) {
        List<Carte> cartesValides = new ArrayList<>();
        
        for (Carte carte : main) {
            if (estCoupValide(carte, main, couleurDemandee, joueurIndex)) {
                cartesValides.add(carte);
            }
        }
        
        if (cartesValides.isEmpty()) {
            return main.remove(0);
        }
        
        NiveauBelote niveau = joueurs[joueurIndex].getNiveauBelote();
        
        if (niveau == null) {
            niveau = NiveauBelote.DEBUTANT;
        }
        
        Carte carteJouee;
        
        if (niveau == NiveauBelote.DEBUTANT) {
            carteJouee = cartesValides.get((int) (Math.random() * cartesValides.size()));
        } else {
            carteJouee = trouverCarteLaPlusForte(cartesValides, couleurDemandee);
        }
        
        main.remove(carteJouee);
        return carteJouee;
    }
    

    private Carte trouverCarteLaPlusForte(List<Carte> cartesValides, Couleur couleurDemandee) {
        if (cartesValides.isEmpty()) {
            return null;
        }
        
        Carte meilleurecarte = cartesValides.get(0);
        
        for (int i = 1; i < cartesValides.size(); i++) {
            Carte carte = cartesValides.get(i);
            
            if (carte.getCouleur() == atout && meilleurecarte.getCouleur() != atout) {
                meilleurecarte = carte;
            } else if (carte.getCouleur() == atout && meilleurecarte.getCouleur() == atout) {
                if (carte.getValeur().getOrdreForceAtout(carte.getValeur()) > 
                    meilleurecarte.getValeur().getOrdreForceAtout(meilleurecarte.getValeur())) {
                    meilleurecarte = carte;
                }
            } else if (carte.getCouleur() != atout && meilleurecarte.getCouleur() != atout) {
                if (couleurDemandee != null) {
                    if (carte.getCouleur() == couleurDemandee && meilleurecarte.getCouleur() != couleurDemandee) {
                        meilleurecarte = carte;
                    } else if (carte.getCouleur() == couleurDemandee && meilleurecarte.getCouleur() == couleurDemandee) {
                        if (carte.getValeur().getOrdreForceNormal() > meilleurecarte.getValeur().getOrdreForceNormal()) {
                            meilleurecarte = carte;
                        }
                    }
                } else {
                    if (carte.getPoints(atout) > meilleurecarte.getPoints(atout)) {
                        meilleurecarte = carte;
                    }
                }
            }
        }
        
        return meilleurecarte;
    }

    private boolean estCoupValide(Carte carte, List<Carte> main, Couleur couleurDemandee, int joueurIndex) {
        if (couleurDemandee == null || nombreCartesJouees == 0) {
            return true;
        }
        
        boolean aCouleurDemandee = main.stream().anyMatch(c -> c.getCouleur() == couleurDemandee);
        
        if (aCouleurDemandee) {
            if (carte.getCouleur() != couleurDemandee) {
                return false;
            }
            
            if (couleurDemandee == atout) {
                return verifierObligationMonterAtout(carte, main, joueurIndex);
            }
            
            return true;
        }
        
        boolean partenaireMene = verifiePartenaireMene(joueurIndex);
        
        if (partenaireMene) {
            return true;
        }
        
        Carte plusFortAtoutDuPli = trouverPlusFortAtoutDuPli();
        
        if (plusFortAtoutDuPli != null) {
            boolean peutSurcouper = main.stream()
                .anyMatch(c -> c.getCouleur() == atout && 
                    c.getValeur().getOrdreForceAtout(c.getValeur()) > 
                    plusFortAtoutDuPli.getValeur().getOrdreForceAtout(plusFortAtoutDuPli.getValeur()));
            
            if (peutSurcouper) {
                return carte.getCouleur() == atout && 
                    carte.getValeur().getOrdreForceAtout(carte.getValeur()) > 
                    plusFortAtoutDuPli.getValeur().getOrdreForceAtout(plusFortAtoutDuPli.getValeur());
            } else {
                return true;
            }
        }
        
        boolean aAtout = main.stream().anyMatch(c -> c.getCouleur() == atout);
        if (aAtout) {
            return carte.getCouleur() == atout;
        }
        
        return true;
    }
    

    private int determinerGagnant(Carte carte1, Carte carte2, Couleur couleurDemandee) {
        if (carte2.getCouleur() == atout && carte1.getCouleur() != atout) {
            return 2;
        }
        
        if (carte1.getCouleur() == atout && carte2.getCouleur() != atout) {
            return 1;
        }
        
        if (carte1.getCouleur() == atout && carte2.getCouleur() == atout) {
            int force1 = carte1.getValeur().getOrdreForceAtout(carte1.getValeur());
            int force2 = carte2.getValeur().getOrdreForceAtout(carte2.getValeur());
            return (force2 > force1) ? 2 : 1;
        }
        
        if (carte2.getCouleur() != couleurDemandee) {
            return 1;
        }
        
        if (carte1.getCouleur() != couleurDemandee) {
            return 2;
        }
        
        int force1 = carte1.getValeur().getOrdreForceNormal();
        int force2 = carte2.getValeur().getOrdreForceNormal();
        return (force2 > force1) ? 2 : 1;
    }
    
   
    private int compterPointsDernierPli() {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            if (cartesPliActuel[i] != null) {
                total += cartesPliActuel[i].getPoints(atout);
            }
        }
        return total;
    }
    
    
    private void distribuerCarte(int joueurIndex) {
        Carte carte = jeu.tirerCarte();
        if (carte != null) {
            distribuerCarteSpecifique(joueurIndex, carte);
        }
    }
    
   
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
    
   
    private void afficherMain(List<Carte> main) {
        System.out.println("\nVotre main:");
        for (int i = 0; i < main.size(); i++) {
            System.out.println((i + 1) + ": " + main.get(i));
        }
    }
    

    private void detecterAnnonces() {
        annoncesParEquipe.get(0).clear();
        annoncesParEquipe.get(1).clear();
        
        detecterAnnoncesJoueur(mainJoueur1, 0);
        detecterAnnoncesJoueur(mainJoueur2, 1);
        detecterAnnoncesJoueur(mainJoueur3, 2);
        detecterAnnoncesJoueur(mainJoueur4, 3);
    }
    
   
    private void detecterAnnoncesJoueur(List<Carte> main, int joueurIndex) {
        int equipeIndex = (joueurIndex == 0 || joueurIndex == 2) ? 0 : 1;
        
        boolean aRoiAtout = main.stream().anyMatch(c -> c.getCouleur() == atout && c.getValeur() == ValeurCarte.ROI);
        boolean aDameAtout = main.stream().anyMatch(c -> c.getCouleur() == atout && c.getValeur() == ValeurCarte.DAME);
        
        if (aRoiAtout && aDameAtout) {
            List<Carte> cartesBelote = new ArrayList<>();
            cartesBelote.add(main.stream().filter(c -> c.getCouleur() == atout && c.getValeur() == ValeurCarte.ROI).findFirst().get());
            cartesBelote.add(main.stream().filter(c -> c.getCouleur() == atout && c.getValeur() == ValeurCarte.DAME).findFirst().get());
            annoncesParEquipe.get(equipeIndex).add(new Annonce(Annonce.TypeAnnonce.BELOTE_REBELOTE, cartesBelote, joueurIndex));
        }
        
        for (ValeurCarte valeur : ValeurCarte.values()) {
            long count = main.stream().filter(c -> c.getValeur() == valeur).count();
            if (count == 4) {
                List<Carte> cartesCarre = main.stream().filter(c -> c.getValeur() == valeur).collect(java.util.stream.Collectors.toList());
                Annonce.TypeAnnonce typeCarre = getTypeCarreSelonValeur(valeur);
                if (typeCarre != null) {
                    annoncesParEquipe.get(equipeIndex).add(new Annonce(typeCarre, cartesCarre, joueurIndex));
                }
            }
        }
        
        for (Couleur couleur : Couleur.values()) {
            List<Carte> cartesCouleur = main.stream()
                .filter(c -> c.getCouleur() == couleur)
                .sorted((c1, c2) -> Integer.compare(c1.getValeur().getOrdreAnnonce(), c2.getValeur().getOrdreAnnonce()))
                .collect(java.util.stream.Collectors.toList());
            
            if (cartesCouleur.size() >= 3) {
                List<Carte> suiteMax = trouverPlusLongueSuite(cartesCouleur);
                if (suiteMax.size() >= 5) {
                    annoncesParEquipe.get(equipeIndex).add(new Annonce(Annonce.TypeAnnonce.CENT, suiteMax, joueurIndex));
                } else if (suiteMax.size() == 4) {
                    annoncesParEquipe.get(equipeIndex).add(new Annonce(Annonce.TypeAnnonce.CINQUANTE, suiteMax, joueurIndex));
                } else if (suiteMax.size() == 3) {
                    annoncesParEquipe.get(equipeIndex).add(new Annonce(Annonce.TypeAnnonce.TIERCE, suiteMax, joueurIndex));
                }
            }
        }
    }
    
    
    private Annonce.TypeAnnonce getTypeCarreSelonValeur(ValeurCarte valeur) {
        switch (valeur) {
            case VALET: return Annonce.TypeAnnonce.CARRE_VALETS;
            case NEUF: return Annonce.TypeAnnonce.CARRE_NEUF;
            case AS: return Annonce.TypeAnnonce.CARRE_AS;
            case DIX: return Annonce.TypeAnnonce.CARRE_DIX;
            case ROI: return Annonce.TypeAnnonce.CARRE_ROIS;
            case DAME: return Annonce.TypeAnnonce.CARRE_DAMES;
            default: return null; // Les autres carrés ne comptent pas
        }
    }
    
    
    private List<Carte> trouverPlusLongueSuite(List<Carte> cartes) {
        if (cartes.isEmpty()) return new ArrayList<>();
        
        List<Carte> suiteCourante = new ArrayList<>();
        List<Carte> suiteMax = new ArrayList<>();
        suiteCourante.add(cartes.get(0));
        
        for (int i = 1; i < cartes.size(); i++) {
            int ordrePrec = cartes.get(i - 1).getValeur().getOrdreAnnonce();
            int ordreCourant = cartes.get(i).getValeur().getOrdreAnnonce();
            
            if (ordreCourant == ordrePrec + 1) {
                suiteCourante.add(cartes.get(i));
            } else {
                if (suiteCourante.size() > suiteMax.size()) {
                    suiteMax = new ArrayList<>(suiteCourante);
                }
                suiteCourante.clear();
                suiteCourante.add(cartes.get(i));
            }
        }
        
        if (suiteCourante.size() > suiteMax.size()) {
            suiteMax = suiteCourante;
        }
        
        return suiteMax;
    }
    
   
    private void afficherAnnonces() {
        System.out.println("\n=== ANNONCES DÉTECTÉES ===");
        
        if (annoncesParEquipe.get(0).isEmpty() && annoncesParEquipe.get(1).isEmpty()) {
            System.out.println("Aucune annonce.");
            return;
        }
        
        if (!annoncesParEquipe.get(0).isEmpty()) {
            System.out.println("\n" + equipe1.getNom() + ":");
            Annonce meilleureAnnonce = trouverMeilleureAnnonce(annoncesParEquipe.get(0));
            if (meilleureAnnonce != null) {
                System.out.println("  » " + meilleureAnnonce);
            }
        }
        
        if (!annoncesParEquipe.get(1).isEmpty()) {
            System.out.println("\n" + equipe2.getNom() + ":");
            Annonce meilleureAnnonce = trouverMeilleureAnnonce(annoncesParEquipe.get(1));
            if (meilleureAnnonce != null) {
                System.out.println("  » " + meilleureAnnonce);
            }
        }
        
        System.out.println("\nLes annonces seront validées au 2ème pli.");
    }
    
    
    private Annonce trouverMeilleureAnnonce(List<Annonce> annonces) {
        Annonce meilleure = null;
        int maxPoints = 0;
        
        for (Annonce a : annonces) {
            if (a.type != Annonce.TypeAnnonce.BELOTE_REBELOTE && a.getPoints() > maxPoints) {
                maxPoints = a.getPoints();
                meilleure = a;
            }
        }
        
        return meilleure;
    }
    
    
    private void validerEtComptabiliserAnnonces() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  VALIDATION DES ANNONCES (PLI 2)    ║");
        System.out.println("╚══════════════════════════════════════╝");
        
       
        Annonce meilleureE1 = trouverMeilleureAnnonce(annoncesParEquipe.get(0));
        Annonce meilleureE2 = trouverMeilleureAnnonce(annoncesParEquipe.get(1));
        
        if (meilleureE1 != null && meilleureE2 != null) {
            if (meilleureE1.getPoints() > meilleureE2.getPoints()) {
                System.out.println("» " + equipe1.getNom() + " marque: " + meilleureE1);
            } else if (meilleureE2.getPoints() > meilleureE1.getPoints()) {
                System.out.println("» " + equipe2.getNom() + " marque: " + meilleureE2);
            } else {
                System.out.println("Égalité d'annonces : aucune ne compte.");
            }
        } else if (meilleureE1 != null) {
            System.out.println("» " + equipe1.getNom() + " marque: " + meilleureE1);
        } else if (meilleureE2 != null) {
            System.out.println("» " + equipe2.getNom() + " marque: " + meilleureE2);
        }
        
        for (Annonce a : annoncesParEquipe.get(0)) {
            if (a.type == Annonce.TypeAnnonce.BELOTE_REBELOTE) {
                System.out.println("» " + equipe1.getNom() + " a la Belote-Rebelote (+20 pts)");
            }
        }
        for (Annonce a : annoncesParEquipe.get(1)) {
            if (a.type == Annonce.TypeAnnonce.BELOTE_REBELOTE) {
                System.out.println("» " + equipe2.getNom() + " a la Belote-Rebelote (+20 pts)");
            }
        }
    }
    
    
    private boolean verifierObligationMonterAtout(Carte carte, List<Carte> main, int joueurIndex) {
        Carte plusFortAtout = trouverPlusFortAtoutDuPli();
        
        if (plusFortAtout == null) {
            return true;
        }
        
        if (verifiePartenaireMene(joueurIndex)) {
            return true;
        }
        
        boolean peutMonter = main.stream()
            .anyMatch(c -> c.getCouleur() == atout && 
                c.getValeur().getOrdreForceAtout(c.getValeur()) > 
                plusFortAtout.getValeur().getOrdreForceAtout(plusFortAtout.getValeur()));
        
        if (peutMonter) {
            return carte.getValeur().getOrdreForceAtout(carte.getValeur()) > 
                   plusFortAtout.getValeur().getOrdreForceAtout(plusFortAtout.getValeur());
        }
        
        return true; 
    }
    

    private Carte trouverPlusFortAtoutDuPli() {
        Carte plusFort = null;
        int forceMax = -1;
        
        for (int i = 0; i < nombreCartesJouees; i++) {
            Carte c = cartesPliActuel[i];
            if (c != null && c.getCouleur() == atout) {
                int force = c.getValeur().getOrdreForceAtout(c.getValeur());
                if (force > forceMax) {
                    forceMax = force;
                    plusFort = c;
                }
            }
        }
        
        return plusFort;
    }
    

    private boolean verifiePartenaireMene(int joueurIndex) {
        if (nombreCartesJouees == 0 || cartesPliActuel[0] == null) {
            return false;
        }
        
        Carte carteGagnante = cartesPliActuel[0];
        int gagnantActuel = 0;
        Couleur couleurDemandee = carteGagnante.getCouleur();
        
        for (int i = 1; i < nombreCartesJouees; i++) {
            if (cartesPliActuel[i] != null) {
                if (determinerGagnant(carteGagnante, cartesPliActuel[i], couleurDemandee) == 2) {
                    carteGagnante = cartesPliActuel[i];
                    gagnantActuel = i;
                }
            }
        }
        
        boolean equipe1 = (joueurIndex == 0 || joueurIndex == 2);
        boolean gagnantEquipe1 = (gagnantActuel == 0 || gagnantActuel == 2);
        
        return equipe1 == gagnantEquipe1;
    }
    
    
    private void afficherReglesCoup(Couleur couleurDemandee, int joueurIndex) {
        System.out.println("\n» Règles applicables:");
        
        List<Carte> main = getMainJoueur(joueurIndex);
        boolean aCouleur = main.stream().anyMatch(c -> c.getCouleur() == couleurDemandee);
        
        if (aCouleur) {
            System.out.println("  » Vous DEVEZ jouer la couleur demandée (" + couleurDemandee + ")");
            if (couleurDemandee == atout) {
                System.out.println("  » Vous devez MONTER à l'atout si possible");
            }
        } else {
            Carte plusFortAtout = trouverPlusFortAtoutDuPli();
            if (plusFortAtout != null && !verifiePartenaireMene(joueurIndex)) {
                System.out.println("  » Un adversaire a coupé, vous devez SURCOUPER si possible");
            } else {
                boolean aAtout = main.stream().anyMatch(c -> c.getCouleur() == atout);
                if (aAtout) {
                    System.out.println("  » Vous devez COUPER avec un atout");
                } else {
                    System.out.println("  » Vous pouvez défausser n'importe quelle carte");
                }
            }
        }
    }
    
   
    private List<Carte> getMainJoueur(int joueurIndex) {
        switch (joueurIndex) {
            case 0: return mainJoueur1;
            case 1: return mainJoueur2;
            case 2: return mainJoueur3;
            case 3: return mainJoueur4;
            default: return new ArrayList<>();
        }
    }
    
    
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
    

    private boolean enchereIA(boolean premierTour) {
        if (premierTour) {
            return Math.random() < 0.25;
        } else {
            return Math.random() < 0.20;
        }
    }
    

    private Couleur choixCouleurIA() {
        return Couleur.values()[(int) (Math.random() * 4)];
    }
}
