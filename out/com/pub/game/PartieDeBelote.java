package com.pub.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Représente une partie complète de belote.
 * Gère le déroulement du jeu: distribution, prise d'atout, jeu des plis, annonces, et scoring.
 * 
 * Concept: Basic Class (Java1.2)
 * Concept: Gestion d'état complexe
 */
public class PartieDeBelote {
    // Constantes
    private static final int POINTS_VICTOIRE = 1010;
    private static final int POINTS_CONTRAT = 82;
    private static final int BONUS_DIX_DE_DER = 10;
    private static final int POINTS_DEDANS = 160;
    
    // Composants du jeu
    private JeuDeCarte jeu;
    private List<Joueur> joueurs;
    private Equipe equipe1;
    private Equipe equipe2;
    
    // État du jeu
    private CouleurCarte atout;
    private Joueur donneur;
    private Joueur preneur; // Le joueur qui a pris l'atout
    private Equipe equipePreneuse;
    private Carte carteRetournee;
    private int indexDonneur;
    
    // Plis et tours
    private List<Carte> pliEnCours;
    private List<Joueur> joueursPli;
    private Joueur premierJoueurPli;
    
    /**
     * Constructeur de la partie de belote.
     * Initialise le jeu avec 4 joueurs répartis en 2 équipes.
     * 
     * @param nomJoueur1 Nom du joueur 1 (Équipe 1)
     * @param nomJoueur2 Nom du joueur 2 (Équipe 2)
     * @param nomJoueur3 Nom du joueur 3 (Équipe 1)
     * @param nomJoueur4 Nom du joueur 4 (Équipe 2)
     */
    public PartieDeBelote(String nomJoueur1, String nomJoueur2, String nomJoueur3, String nomJoueur4) {
        this.jeu = new JeuDeCarte(true); // Jeu de 32 cartes
        this.joueurs = new ArrayList<>();
        
        // Créer les équipes
        this.equipe1 = new Equipe("Équipe 1");
        this.equipe2 = new Equipe("Équipe 2");
        
        // Créer et assigner les joueurs aux équipes
        Joueur j1 = new Joueur(nomJoueur1);
        Joueur j2 = new Joueur(nomJoueur2);
        Joueur j3 = new Joueur(nomJoueur3);
        Joueur j4 = new Joueur(nomJoueur4);
        
        equipe1.ajouterJoueur(j1);
        equipe2.ajouterJoueur(j2);
        equipe1.ajouterJoueur(j3);
        equipe2.ajouterJoueur(j4);
        
        // Les joueurs alternent: J1(E1), J2(E2), J3(E1), J4(E2)
        joueurs.add(j1);
        joueurs.add(j2);
        joueurs.add(j3);
        joueurs.add(j4);
        
        this.indexDonneur = 0;
        this.donneur = joueurs.get(indexDonneur);
        
        this.pliEnCours = new ArrayList<>();
        this.joueursPli = new ArrayList<>();
    }
    
    /**
     * Lance une partie complète de belote jusqu'à ce qu'une équipe atteigne 1010 points.
     */
    public void jouerPartie() {
        System.out.println("=== DÉBUT DE LA PARTIE DE BELOTE ===");
        System.out.println(equipe1.getNom() + ": " + equipe1.getJoueurs().get(0).getNom() + 
                          " et " + equipe1.getJoueurs().get(1).getNom());
        System.out.println(equipe2.getNom() + ": " + equipe2.getJoueurs().get(0).getNom() + 
                          " et " + equipe2.getJoueurs().get(1).getNom());
        System.out.println();
        
        while (equipe1.getScoreTotal() < POINTS_VICTOIRE && equipe2.getScoreTotal() < POINTS_VICTOIRE) {
            jouerManche();
            afficherScores();
        }
        
        // Déterminer le gagnant
        Equipe gagnante = (equipe1.getScoreTotal() >= POINTS_VICTOIRE) ? equipe1 : equipe2;
        System.out.println("\n=== PARTIE TERMINÉE ===");
        System.out.println("🏆 " + gagnante.getNom() + " remporte la partie avec " + 
                          gagnante.getScoreTotal() + " points !");
    }
    
    /**
     * Joue une manche complète (une donne).
     */
    private void jouerManche() {
        System.out.println("\n=== NOUVELLE MANCHE ===");
        System.out.println("Donneur: " + donneur.getNom());
        
        // Réinitialiser les scores de partie et les annonces
        equipe1.reinitialiserScorePartie();
        equipe2.reinitialiserScorePartie();
        
        // Vider les mains des joueurs
        for (Joueur joueur : joueurs) {
            joueur.viderMain();
        }
        
        // Distribution initiale (5 cartes: 3 puis 2)
        distribuerCartesInitiales();
        
        // Prise d'atout
        boolean atoutPris = gererPriseDAtout();
        
        if (!atoutPris) {
            System.out.println("Personne n'a pris. Cartes remises.");
            changerDonneur();
            return;
        }
        
        // Distribution finale (3 cartes restantes)
        distribuerCartesFinales();
        
        // Détecter les annonces
        detecterAnnonces();
        
        // Jouer les 8 plis
        Joueur gagnantDernierPli = null;
        Joueur premierJoueur = getJoueurApres(donneur);
        
        for (int i = 0; i < 8; i++) {
            System.out.println("\n--- Pli " + (i + 1) + " ---");
            gagnantDernierPli = jouerPli(premierJoueur);
            premierJoueur = gagnantDernierPli;
        }
        
        // Bonus 10 de der pour le dernier pli
        if (gagnantDernierPli != null) {
            gagnantDernierPli.getEquipe().ajouterPointsPartie(BONUS_DIX_DE_DER);
            System.out.println("Bonus 10 de der pour " + gagnantDernierPli.getEquipe().getNom());
        }
        
        // Calculer et attribuer les scores
        calculerScoreManche();
        
        // Changer le donneur
        changerDonneur();
    }
    
    /**
     * Distribue les cartes initiales (5 cartes par joueur: 3 puis 2).
     */
    private void distribuerCartesInitiales() {
        jeu.remettreEnOrdre(true);
        jeu.melanger();
        jeu.couper();
        
        Joueur premierJoueur = getJoueurApres(donneur);
        int indexPremier = joueurs.indexOf(premierJoueur);
        
        // Premier tour: 3 cartes
        for (int i = 0; i < 4; i++) {
            Joueur joueur = joueurs.get((indexPremier + i) % 4);
            for (int j = 0; j < 3; j++) {
                joueur.recevoirCarte(jeu.tirerCarte());
            }
        }
        
        // Deuxième tour: 2 cartes
        for (int i = 0; i < 4; i++) {
            Joueur joueur = joueurs.get((indexPremier + i) % 4);
            for (int j = 0; j < 2; j++) {
                joueur.recevoirCarte(jeu.tirerCarte());
            }
        }
        
        // Retourner une carte pour la proposition d'atout
        carteRetournee = jeu.tirerCarte();
        System.out.println("Carte retournée: " + carteRetournee);
    }
    
    /**
     * Gère la prise d'atout (deux tours).
     * 
     * @return true si quelqu'un a pris, false sinon
     */
    private boolean gererPriseDAtout() {
        Joueur premierJoueur = getJoueurApres(donneur);
        int indexPremier = joueurs.indexOf(premierJoueur);
        
        // Premier tour: proposition de la couleur retournée
        System.out.println("\n--- Premier tour de prise ---");
        for (int i = 0; i < 4; i++) {
            Joueur joueur = joueurs.get((indexPremier + i) % 4);
            System.out.println(joueur.getNom() + ", prenez-vous " + carteRetournee.getCouleur() + " ? (oui/non)");
            // Simulation: on considère que personne ne prend au premier tour pour simplifier
            System.out.println(joueur.getNom() + ": Je passe");
        }
        
        // Deuxième tour: choix de n'importe quelle couleur
        System.out.println("\n--- Deuxième tour de prise ---");
        for (int i = 0; i < 4; i++) {
            Joueur joueur = joueurs.get((indexPremier + i) % 4);
            // Simulation: le premier joueur prend toujours au deuxième tour
            if (i == 0) {
                atout = carteRetournee.getCouleur(); // Simplification: on prend la couleur retournée
                preneur = joueur;
                equipePreneuse = joueur.getEquipe();
                System.out.println(joueur.getNom() + " prend " + atout + " !");
                joueur.recevoirCarte(carteRetournee); // Le preneur récupère la carte retournée
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Distribue les 3 cartes restantes après la prise d'atout.
     */
    private void distribuerCartesFinales() {
        Joueur premierJoueur = getJoueurApres(donneur);
        int indexPremier = joueurs.indexOf(premierJoueur);
        
        for (int i = 0; i < 4; i++) {
            Joueur joueur = joueurs.get((indexPremier + i) % 4);
            int nbCartes = (joueur == preneur) ? 2 : 3; // Le preneur a déjà la carte retournée
            for (int j = 0; j < nbCartes; j++) {
                Carte carte = jeu.tirerCarte();
                if (carte != null) {
                    joueur.recevoirCarte(carte);
                }
            }
        }
        
        System.out.println("Distribution terminée. Chaque joueur a 8 cartes.");
    }
    
    /**
     * Joue un pli complet et retourne le gagnant.
     * 
     * @param premierJoueur Le joueur qui commence le pli
     * @return Le joueur qui remporte le pli
     */
    private Joueur jouerPli(Joueur premierJoueur) {
        pliEnCours.clear();
        joueursPli.clear();
        premierJoueurPli = premierJoueur;
        
        int indexPremier = joueurs.indexOf(premierJoueur);
        Carte carteGagnante = null;
        Joueur joueurGagnant = null;
        CouleurCarte couleurDemandee = null;
        
        // Chaque joueur joue une carte
        for (int i = 0; i < 4; i++) {
            Joueur joueur = joueurs.get((indexPremier + i) % 4);
            System.out.println("\nTour de " + joueur.getNom());
            joueur.afficherMain();
            
            Carte carteJouee = choisirCarte(joueur, couleurDemandee);
            
            if (i == 0) {
                couleurDemandee = carteJouee.getCouleur();
            }
            
            pliEnCours.add(carteJouee);
            joueursPli.add(joueur);
            joueur.jouerCarte(carteJouee);
            
            System.out.println(joueur.getNom() + " joue: " + carteJouee);
            
            // Déterminer la carte gagnante
            if (i == 0) {
                carteGagnante = carteJouee;
                joueurGagnant = joueur;
            } else {
                if (carteJouee.estPlusForteQue(carteGagnante, atout)) {
                    carteGagnante = carteJouee;
                    joueurGagnant = joueur;
                }
            }
        }
        
        System.out.println("\n" + joueurGagnant.getNom() + " remporte le pli avec " + carteGagnante);
        
        // Compter les points du pli
        int pointsPli = 0;
        for (Carte carte : pliEnCours) {
            boolean estAtout = (carte.getCouleur() == atout);
            pointsPli += carte.getPoints(estAtout);
        }
        
        joueurGagnant.getEquipe().ajouterPointsPartie(pointsPli);
        System.out.println("Points du pli: " + pointsPli + " pour " + joueurGagnant.getEquipe().getNom());
        
        return joueurGagnant;
    }
    
    /**
     * Choisit une carte à jouer pour un joueur (simplifié: prend la première carte valide).
     * 
     * @param joueur Le joueur qui doit jouer
     * @param couleurDemandee La couleur demandée (null si premier joueur)
     * @return La carte choisie
     */
    private Carte choisirCarte(Joueur joueur, CouleurCarte couleurDemandee) {
        List<Carte> main = joueur.getMain();
        
        // Premier joueur: joue la première carte
        if (couleurDemandee == null) {
            return main.get(0);
        }
        
        // Vérifier si le joueur peut fournir la couleur
        for (Carte carte : main) {
            if (carte.getCouleur() == couleurDemandee) {
                return carte; // Simplification: joue la première carte de la couleur
            }
        }
        
        // Ne peut pas fournir: doit couper si possible
        if (couleurDemandee != atout && joueur.possedeAtout(atout)) {
            for (Carte carte : main) {
                if (carte.getCouleur() == atout) {
                    return carte;
                }
            }
        }
        
        // Joue n'importe quelle carte
        return main.get(0);
    }
    
    /**
     * Détecte toutes les annonces possibles pour les deux équipes.
     */
    private void detecterAnnonces() {
        System.out.println("\n=== Détection des annonces ===");
        
        for (Joueur joueur : joueurs) {
            detecterAnnoncesPourJoueur(joueur);
        }
        
        // Afficher les annonces
        if (equipe1.getAnnonces().size() > 0 || equipe2.getAnnonces().size() > 0) {
            System.out.println("\nAnnonces détectées:");
            if (equipe1.getAnnonces().size() > 0) {
                System.out.println(equipe1.getNom() + ":");
                for (Annonce annonce : equipe1.getAnnonces()) {
                    System.out.println("  - " + annonce);
                }
            }
            if (equipe2.getAnnonces().size() > 0) {
                System.out.println(equipe2.getNom() + ":");
                for (Annonce annonce : equipe2.getAnnonces()) {
                    System.out.println("  - " + annonce);
                }
            }
        } else {
            System.out.println("Aucune annonce.");
        }
    }
    
    /**
     * Détecte les annonces pour un joueur spécifique.
     * 
     * @param joueur Le joueur pour lequel détecter les annonces
     */
    private void detecterAnnoncesPourJoueur(Joueur joueur) {
        List<Carte> main = new ArrayList<>(joueur.getMain());
        
        // Détecter belote-rebelote (Roi et Dame d'atout)
        detecterBeloteRebelote(joueur, main);
        
        // Détecter les carrés
        detecterCarres(joueur, main);
        
        // Détecter les suites (tierce, cinquante, cent)
        detecterSuites(joueur, main);
    }
    
    /**
     * Détecte belote-rebelote pour un joueur.
     */
    private void detecterBeloteRebelote(Joueur joueur, List<Carte> main) {
        boolean aRoi = false;
        boolean aDame = false;
        
        for (Carte carte : main) {
            if (carte.getCouleur() == atout) {
                if (carte.getValeur() == ValeurCarte.ROI) aRoi = true;
                if (carte.getValeur() == ValeurCarte.DAME) aDame = true;
            }
        }
        
        if (aRoi && aDame) {
            List<Carte> cartesBelote = new ArrayList<>();
            for (Carte carte : main) {
                if (carte.getCouleur() == atout && 
                    (carte.getValeur() == ValeurCarte.ROI || carte.getValeur() == ValeurCarte.DAME)) {
                    cartesBelote.add(carte);
                }
            }
            Annonce annonce = new Annonce(TypeAnnonce.BELOTE_REBELOTE, cartesBelote);
            joueur.getEquipe().ajouterAnnonce(annonce);
        }
    }
    
    /**
     * Détecte les carrés pour un joueur.
     */
    private void detecterCarres(Joueur joueur, List<Carte> main) {
        // Compter chaque valeur
        int[] compteurs = new int[ValeurCarte.values().length];
        for (Carte carte : main) {
            compteurs[carte.getValeur().ordinal()]++;
        }
        
        // Vérifier les carrés
        for (ValeurCarte valeur : ValeurCarte.values()) {
            if (compteurs[valeur.ordinal()] == 4) {
                List<Carte> cartesCarre = new ArrayList<>();
                for (Carte carte : main) {
                    if (carte.getValeur() == valeur) {
                        cartesCarre.add(carte);
                    }
                }
                
                TypeAnnonce type;
                if (valeur == ValeurCarte.VALET) {
                    type = TypeAnnonce.CARRE_VALET;
                } else if (valeur == ValeurCarte.NEUF) {
                    type = TypeAnnonce.CARRE_NEUF;
                } else {
                    type = TypeAnnonce.CARRE_STANDARD;
                }
                
                Annonce annonce = new Annonce(type, cartesCarre);
                joueur.getEquipe().ajouterAnnonce(annonce);
            }
        }
    }
    
    /**
     * Détecte les suites pour un joueur.
     */
    private void detecterSuites(Joueur joueur, List<Carte> main) {
        // Pour chaque couleur, vérifier les suites
        for (CouleurCarte couleur : CouleurCarte.values()) {
            List<Carte> cartesCouleur = new ArrayList<>();
            for (Carte carte : main) {
                if (carte.getCouleur() == couleur) {
                    cartesCouleur.add(carte);
                }
            }
            
            if (cartesCouleur.size() >= 3) {
                // Trier par ordre d'annonce
                Collections.sort(cartesCouleur, new Comparator<Carte>() {
                    @Override
                    public int compare(Carte c1, Carte c2) {
                        return Integer.compare(c1.getValeur().getOrdreAnnonce(), 
                                             c2.getValeur().getOrdreAnnonce());
                    }
                });
                
                // Chercher la plus longue suite
                List<Carte> suiteCourante = new ArrayList<>();
                suiteCourante.add(cartesCouleur.get(0));
                
                for (int i = 1; i < cartesCouleur.size(); i++) {
                    int ordrePrec = cartesCouleur.get(i-1).getValeur().getOrdreAnnonce();
                    int ordreCourant = cartesCouleur.get(i).getValeur().getOrdreAnnonce();
                    
                    if (ordreCourant == ordrePrec + 1) {
                        suiteCourante.add(cartesCouleur.get(i));
                    } else {
                        // Suite interrompue, vérifier si elle est valide
                        if (suiteCourante.size() >= 3) {
                            ajouterAnnonceSuite(joueur, suiteCourante);
                        }
                        suiteCourante.clear();
                        suiteCourante.add(cartesCouleur.get(i));
                    }
                }
                
                // Vérifier la dernière suite
                if (suiteCourante.size() >= 3) {
                    ajouterAnnonceSuite(joueur, suiteCourante);
                }
            }
        }
    }
    
    /**
     * Ajoute une annonce de suite (tierce, cinquante, cent).
     */
    private void ajouterAnnonceSuite(Joueur joueur, List<Carte> suite) {
        TypeAnnonce type;
        if (suite.size() == 3) {
            type = TypeAnnonce.TIERCE;
        } else if (suite.size() == 4) {
            type = TypeAnnonce.CINQUANTE;
        } else if (suite.size() >= 5) {
            type = TypeAnnonce.CENT;
        } else {
            return;
        }
        
        Annonce annonce = new Annonce(type, new ArrayList<>(suite));
        joueur.getEquipe().ajouterAnnonce(annonce);
    }
    
    /**
     * Calcule et attribue les scores de la manche.
     */
    private void calculerScoreManche() {
        System.out.println("\n=== Calcul des scores ===");
        
        int pointsEquipe1 = equipe1.getScorePartie();
        int pointsEquipe2 = equipe2.getScorePartie();
        int annoncesEquipe1 = equipe1.getTotalPointsAnnonces();
        int annoncesEquipe2 = equipe2.getTotalPointsAnnonces();
        
        System.out.println(equipe1.getNom() + ": " + pointsEquipe1 + " points (+ " + annoncesEquipe1 + " annonces)");
        System.out.println(equipe2.getNom() + ": " + pointsEquipe2 + " points (+ " + annoncesEquipe2 + " annonces)");
        
        // Vérifier si l'équipe preneuse a réussi son contrat
        boolean contratReussi;
        int scoreEquipePreneuse = (equipePreneuse == equipe1) ? pointsEquipe1 : pointsEquipe2;
        
        if (scoreEquipePreneuse >= POINTS_CONTRAT) {
            contratReussi = true;
            System.out.println("\nContrat réussi par " + equipePreneuse.getNom() + " !");
            
            // Chaque équipe marque ses points + annonces
            equipe1.ajouterPointsTotal(pointsEquipe1 + annoncesEquipe1);
            equipe2.ajouterPointsTotal(pointsEquipe2 + annoncesEquipe2);
        } else {
            contratReussi = false;
            System.out.println("\nContrat échoué ! " + equipePreneuse.getNom() + " est dedans.");
            
            // L'équipe adverse marque 160 + ses annonces
            Equipe equipeAdverse = (equipePreneuse == equipe1) ? equipe2 : equipe1;
            int pointsAdverse = POINTS_DEDANS + annoncesEquipe2 + annoncesEquipe1; // L'équipe adverse prend toutes les annonces
            
            if (equipeAdverse == equipe1) {
                equipe1.ajouterPointsTotal(pointsAdverse);
            } else {
                equipe2.ajouterPointsTotal(pointsAdverse);
            }
        }
    }
    
    /**
     * Affiche les scores totaux des deux équipes.
     */
    private void afficherScores() {
        System.out.println("\n=== Scores Totaux ===");
        System.out.println(equipe1.getNom() + ": " + equipe1.getScoreTotal() + " points");
        System.out.println(equipe2.getNom() + ": " + equipe2.getScoreTotal() + " points");
    }
    
    /**
     * Change le donneur pour la prochaine manche.
     */
    private void changerDonneur() {
        indexDonneur = (indexDonneur + 1) % 4;
        donneur = joueurs.get(indexDonneur);
    }
    
    /**
     * Retourne le joueur après le joueur donné.
     * 
     * @param joueur Le joueur de référence
     * @return Le joueur suivant
     */
    private Joueur getJoueurApres(Joueur joueur) {
        int index = joueurs.indexOf(joueur);
        return joueurs.get((index + 1) % 4);
    }
    
    /**
     * Retourne l'équipe 1.
     * 
     * @return L'équipe 1
     */
    public Equipe getEquipe1() {
        return equipe1;
    }
    
    /**
     * Retourne l'équipe 2.
     * 
     * @return L'équipe 2
     */
    public Equipe getEquipe2() {
        return equipe2;
    }
    
    /**
     * Retourne la couleur d'atout.
     * 
     * @return La couleur d'atout
     */
    public CouleurCarte getAtout() {
        return atout;
    }
    
    /**
     * Retourne le joueur preneur.
     * 
     * @return Le joueur preneur
     */
    public Joueur getPreneur() {
        return preneur;
    }
    
    /**
     * Méthode principale pour tester le jeu de belote.
     */
    public static void main(String[] args) {
        System.out.println("=== TEST DU JEU DE BELOTE ===");
        
        PartieDeBelote partie = new PartieDeBelote("Alice", "Bob", "Charlie", "Diana");
        partie.jouerPartie();
        
        System.out.println("\n=== FIN DU TEST ===");
    }
}
