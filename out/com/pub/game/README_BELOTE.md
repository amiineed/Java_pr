# Jeu de Belote - Documentation

## Vue d'ensemble

Cette implémentation complète du jeu de Belote (Partie 2) comprend tous les éléments nécessaires pour gérer une partie de belote classique avec 4 joueurs répartis en 2 équipes.

## Classes créées

### 1. Classes de base (déjà existantes)
- **CouleurCarte** : Enum des couleurs (PIQUE, CARREAU, TREFLE, COEUR)
- **ValeurCarte** : Enum des valeurs (SEPT à AS) avec points et ordres
- **Carte** : Classe immuable représentant une carte
- **JeuDeCarte** : Gestion du paquet de 32 cartes avec mélange et distribution

### 2. Nouvelles classes créées

#### Joueur.java
- Représente un joueur de belote
- Gère la main du joueur (8 cartes)
- Méthodes pour jouer des cartes et vérifier les couleurs possédées
- Placeholder qui pourra être lié à vos classes de personnages plus tard

#### Equipe.java
- Représente une équipe de 2 joueurs
- Gère les scores (partie et total)
- Gère les annonces de l'équipe

#### TypeAnnonce.java
- Enum des types d'annonces possibles :
  - TIERCE (20 points)
  - CINQUANTE (50 points)
  - CENT (100 points)
  - CARRE_NEUF (150 points)
  - CARRE_VALET (200 points)
  - CARRE_STANDARD (100 points)
  - BELOTE_REBELOTE (20 points)

#### Annonce.java
- Représente une annonce avec son type et ses cartes

#### PartieDeBelote.java
- **Classe principale** gérant toute la logique du jeu
- Fonctionnalités :
  - Distribution des cartes (3+2, puis 3 après la prise)
  - Prise d'atout en deux tours
  - Gestion des 8 plis
  - Détection automatique des annonces
  - Calcul des scores avec contrat (82 points)
  - Bonus "10 de der" pour le dernier pli
  - Gestion du "dedans" (160 points à l'adversaire)
  - Partie complète jusqu'à 1010 points

## Fonctionnalités implémentées

### Distribution
- Mélange, coupe, et distribution en deux phases (5 puis 3 cartes)
- Carte retournée pour proposition d'atout

### Prise d'atout
- Premier tour : proposition de la couleur retournée
- Deuxième tour : choix de n'importe quelle couleur
- Le preneur récupère la carte retournée

### Jeu des plis
- Respect des règles de suivi de couleur
- Obligation de couper si on ne peut pas fournir
- Détermination automatique du gagnant du pli
- Comptage des points (différents pour atout/non-atout)

### Annonces
Détection automatique de :
- **Suites** : Tierce (3), Cinquante (4), Cent (5+)
- **Carrés** : 4 cartes identiques (points selon la valeur)
- **Belote-Rebelote** : Roi et Dame d'atout

### Scoring
- Points des plis selon atout/non-atout
- Bonus 10 de der
- Vérification du contrat (82 points minimum)
- Système "dedans" si contrat échoué (160 points à l'adversaire + toutes les annonces)
- Victoire à 1010 points

## Utilisation

### Test rapide
```java
// La classe PartieDeBelote contient une méthode main pour tester
PartieDeBelote partie = new PartieDeBelote("Alice", "Bob", "Charlie", "Diana");
partie.jouerPartie();
```

### Intégration dans votre projet
```java
// Créer une partie avec 4 joueurs
PartieDeBelote partie = new PartieDeBelote(
    nomJoueur1, // Équipe 1
    nomJoueur2, // Équipe 2
    nomJoueur3, // Équipe 1
    nomJoueur4  // Équipe 2
);

// Lancer la partie complète
partie.jouerPartie();

// Accéder aux informations
Equipe equipe1 = partie.getEquipe1();
Equipe equipe2 = partie.getEquipe2();
CouleurCarte atout = partie.getAtout();
```

## Points d'amélioration possibles

1. **Intelligence artificielle** : Actuellement, les joueurs jouent automatiquement. Vous pourriez ajouter une stratégie plus intelligente ou une interface utilisateur.

2. **Interaction utilisateur** : Permettre aux joueurs humains de choisir leurs cartes via Scanner.

3. **Lien avec les personnages** : Intégrer la classe Joueur avec vos classes Client/Serveur du package characters.

4. **Tournoi** : Utiliser cette classe pour implémenter la Partie 3 (tournoi).

5. **Règles avancées** : 
   - Priorité des annonces (l'équipe avec la meilleure annonce marque seule)
   - Obligation de monter à l'atout
   - Variantes régionales

## Notes techniques

- Toutes les classes sont documentées avec Javadoc
- Respect des conventions Java
- Code modulaire et réutilisable
- Gestion immuable des cartes
- Séparation claire des responsabilités

## Conformité aux spécifications

✅ Jeu de 32 cartes
✅ Ordres de force (normal, atout, annonces)
✅ Points des cartes (différents pour atout)
✅ Distribution en deux phases
✅ Prise d'atout en deux tours
✅ Gestion des 8 plis
✅ Règles de suivi et coupe
✅ Détection des annonces
✅ Système de scoring complet
✅ Contrat et "dedans"
✅ 10 de der
✅ Victoire à 1010 points
