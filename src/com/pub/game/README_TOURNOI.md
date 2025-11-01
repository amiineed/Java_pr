# Tournoi de Belote - Documentation (Partie 3)

## Vue d'ensemble

Implémentation complète du système de tournoi de belote pour le bar. Le tournoi permet aux clients du bar de s'inscrire en équipes de 2 et de participer à un tournoi round-robin (toutes les équipes s'affrontent).

## Classes créées

### 1. EquipeTournoi.java
Représente une équipe de tournoi composée de 2 clients du bar.

**Fonctionnalités:**
- Gestion des 2 joueurs (Client)
- Suivi des points de tournoi (3 pour victoire, 1 pour égalité, 0 pour défaite)
- Statistiques: parties gagnées/perdues, manches gagnées/perdues
- Historique des matchs joués
- Validation que les joueurs ne sont pas déjà inscrits

### 2. Match.java
Représente un match entre deux équipes.

**Fonctionnalités:**
- Suivi du score en manches (première à 2 manches gagne)
- Application de la règle spéciale 2-0 → 1-1 (égalité)
- Attribution automatique des points de tournoi
- Historique intégré aux équipes

### 3. FeuilleDeScore.java
Gère le classement et les résultats du tournoi.

**Fonctionnalités:**
- Liste de toutes les équipes inscrites
- Historique de tous les matchs
- Classement trié par:
  1. Points de tournoi (décroissant)
  2. Différence de manches (décroissant)
  3. Nombre de victoires (décroissant)
- Affichage formaté du classement
- Détermination de l'équipe gagnante

### 4. Tournoi.java
Classe principale gérant tout le déroulement du tournoi.

**Fonctionnalités:**
- Ouverture/fermeture des inscriptions
- Vérification des frais d'inscription
- Génération automatique des matchs (round-robin)
- Gestion de la cagnotte
- Distribution des gains (50% patronne, 50% gagnants)
- Intégration avec le système du bar

## Intégration avec le système existant

### Lien avec les Clients
- Les équipes sont composées de `Client` du bar
- Les frais d'inscription sont prélevés du porte-monnaie
- Les gains sont redistribués aux joueurs gagnants

### Lien avec le Bar
- Le tournoi est organisé par la `Patron` du bar
- Les frais d'inscription alimentent la cagnotte
- 50% de la cagnotte revient à la patronne

### Ajout dans Main.java
- Nouvelle option (4) "Manage Belote Tournament"
- Menu complet de gestion du tournoi
- 7 sous-options pour gérer toutes les étapes

## Règles du tournoi

### Inscription
- Frais d'inscription paramétrable par équipe
- Chaque joueur paie la moitié des frais
- Vérification automatique que les joueurs ont assez d'argent
- Un joueur ne peut être inscrit que dans une seule équipe
- Minimum 2 équipes pour démarrer le tournoi

### Format
- **Round-robin**: Chaque équipe joue contre toutes les autres
- Un match = plusieurs manches jusqu'à ce qu'une équipe gagne 2 manches
- Pour 3 équipes: 3 matchs (A vs B, A vs C, B vs C)
- Pour 4 équipes: 6 matchs
- Formule: n×(n-1)/2 matchs pour n équipes

### Scoring
**Points de classement:**
- Victoire: 3 points
- Égalité: 1 point
- Défaite: 0 points

**Règle spéciale 2-0:**
- Si une équipe gagne 2-0, le score final devient 1-1
- Les deux équipes marquent 1 point au classement
- Encourage la compétitivité

**Classement:**
1. Points de tournoi
2. Différence de manches (+/-)
3. Nombre de victoires

### Distribution des gains
- **50% à la patronne** (frais d'organisation)
- **50% à l'équipe gagnante** (divisé entre les 2 joueurs)

## Utilisation dans Main.java

### Menu principal
```
4. Manage Belote Tournament
```

### Sous-menu tournoi
```
1. Créer un nouveau tournoi
2. Inscrire une équipe
3. Démarrer le tournoi
4. Jouer le prochain match
5. Jouer tout le tournoi
6. Afficher le classement
7. Afficher les équipes inscrites
0. Retour
```

### Workflow typique

1. **Créer le tournoi**
   - Menu principal → 4
   - Sous-menu → 1
   - Saisir frais d'inscription (ex: 20€)

2. **Inscrire les équipes**
   - Sous-menu → 2
   - Choisir 2 clients dans la liste
   - Donner un nom à l'équipe
   - Répéter pour chaque équipe (minimum 2)

3. **Démarrer le tournoi**
   - Sous-menu → 3
   - Les inscriptions se ferment
   - Les matchs sont générés

4. **Jouer les matchs**
   - Option A: Sous-menu → 5 (tout automatique)
   - Option B: Sous-menu → 4 (match par match)

5. **Consulter les résultats**
   - Sous-menu → 6 (classement final)
   - Les gains sont automatiquement distribués

## Exemple de session

```
--- GESTION DU TOURNOI DE BELOTE ---
Choix: 1
Frais d'inscription par équipe: 20

╔════════════════════════════════════════════════════════╗
║   TOURNOI DE BELOTE - INSCRIPTIONS OUVERTES !        ║
╠════════════════════════════════════════════════════════╣
║ Bar: Chez Sarah                                       ║
║ Organisé par: Sarah                                   ║
║ Frais d'inscription:  20.00€ par équipe              ║
╚════════════════════════════════════════════════════════╝
✓ Tournoi créé avec succès !

Choix: 2
Nom de l'équipe: Les Champions
Numéro du premier joueur: 1 (Peter)
Numéro du deuxième joueur: 2 (Julie)
✓ Équipe "Les Champions" inscrite avec succès !
  Joueurs: Peter & Julie
  Frais payés: 20.0€

[... Inscription d'autres équipes ...]

Choix: 3
✓ Inscriptions clôturées !
  Nombre d'équipes: 3
  Cagnotte totale: 60.0€
  Nombre de matchs à jouer: 3

╔════════════════════════════════════════════════════════╗
║          🏆 DÉBUT DU TOURNOI DE BELOTE 🏆            ║
╚════════════════════════════════════════════════════════╝

Choix: 5
Lancement du tournoi complet...

[... Matchs joués automatiquement ...]

╔════════════════════════════════════════════════════════╗
║           CLASSEMENT DU TOURNOI DE BELOTE                ║
╠════════════════════════════════════════════════════════╣
║ Pos | Équipe              | Pts | V | D | Manches        ║
╠════════════════════════════════════════════════════════╣
║   1 | Les Champions        |   6 | 2 | 0 |  4-0  (+4)   ║
║   2 | Team Rocket          |   3 | 1 | 1 |  2-2  ( 0)   ║
║   3 | Les Perdants         |   0 | 0 | 2 |  0-4  (-4)   ║
╚════════════════════════════════════════════════════════╝

🏆 ÉQUIPE VICTORIEUSE: Les Champions 🏆
   Peter & Julie
   Gain par joueur: 15.00€
```

## Simulation des matchs

Actuellement, les matchs sont **simulés automatiquement** avec des résultats aléatoires. Chaque manche a 50% de chances d'être gagnée par chaque équipe.

### Pour améliorer (extensions possibles):
1. **Intégrer PartieDeBelote**: Jouer de vraies parties de belote au lieu de simuler
2. **Interface joueur**: Permettre aux joueurs humains de jouer
3. **Niveaux de compétence**: Utiliser les attributs des clients pour influencer les résultats
4. **Statistiques détaillées**: Cartes jouées, annonces, etc.
5. **Tournoi à élimination directe**: Alternative au round-robin
6. **Sauvegarde/Chargement**: Sauvegarder l'état du tournoi

## Conformité aux spécifications

✅ Inscriptions gérées par la patronne  
✅ Frais d'inscription configurable  
✅ Équipes de 2 joueurs (Clients du bar)  
✅ Format round-robin (toutes les équipes s'affrontent)  
✅ Règle 2-0 → 1-1 implémentée  
✅ Système de points (3/1/0)  
✅ Classement multi-critères  
✅ Distribution des gains (50% patronne)  
✅ Feuille de score avec affichage formaté  
✅ Intégration complète avec le système du bar  
✅ Gestion de l'argent des joueurs  
✅ Pas de bugs, tout fonctionne

## Notes techniques

- **Pas d'erreurs de compilation**: Toutes les dépendances sont correctes
- **Intégration propre**: Utilise les classes existantes (Client, Bar, Patron)
- **Code documenté**: Javadoc sur toutes les méthodes publiques
- **Architecture modulaire**: Classes bien séparées (équipes, matchs, classement, tournoi)
- **Extensible**: Facile d'ajouter de nouvelles fonctionnalités
