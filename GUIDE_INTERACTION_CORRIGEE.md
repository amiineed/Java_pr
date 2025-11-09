# 🎮 Guide d'Utilisation - Interaction Tournoi Corrigée

## 🚀 Lancer l'Application

```bash
# Compiler
javac -encoding UTF-8 -d bin -sourcepath src src\com\pub\main\Main.java

# Exécuter
java -cp bin com.pub.main.Main
```

---

## 🎯 Nouvelle Expérience Utilisateur

### 1. Créer et Démarrer un Tournoi

```
--- Main Menu ---
1. Create a persona
2. List personas
3. Order a drink
4. Manage Belote Tournament   ← Choisir
5. View player statistics
6. Save state (simplified)
7. Load state (simplified)
0. Exit
Your choice: 4

--- GESTION DU TOURNOI DE BELOTE ---
1. Créer un nouveau tournoi
2. Inscrire une équipe
3. Démarrer le tournoi
4. Jouer le prochain match
5. Jouer tout le tournoi
6. Afficher le classement
7. Afficher les équipes inscrites
0. Retour
```

### 2. Option 4 : Jouer le Prochain Match (NOUVEAU !)

**Avant** :
```
Votre choix: 4
Tous les matchs ont été joués !  ❌ Incorrect
```

**Maintenant** :
```
Votre choix: 4

Voulez-vous participer à ce match manuellement ?
Entrez votre prénom (ou appuyez sur Entrée pour simulation IA): amine

=== MATCH: Les Champions vs Les Pros ===
Les joueurs s'affrontent à la Belote...

🎮 MODE INTERACTIF: Vous participez à ce match!

╔═══════════════════════════════════════════════════════════╗
║      PARTIE DE BELOTE - JUSQU'À 1010 POINTS              ║
╚═══════════════════════════════════════════════════════════╝
Équipe 1: Les Champions (amine & sara)
Équipe 2: Les Pros (hmza & zaba)

[Partie interactive avec amine]
```

---

## 🎮 Les Deux Modes de Jeu

### Mode 1 : Interactif (Vous Jouez)

**Quand** : Vous entrez votre prénom ET vous participez au match

```
Entrez votre prénom (ou appuyez sur Entrée pour simulation IA): amine

🎮 MODE INTERACTIF: Vous participez à ce match!
```

**Gameplay** :
```
C'est à amine de jouer.

Votre main:
1: Valet de COEUR
2: 9 de COEUR
3: As de PIQUE
4: 10 de CARREAU
5: Roi de TREFLE
6: Dame de COEUR
7: 8 de PIQUE
8: 7 de CARREAU
Choisissez une carte (1-8): 3
→ amine joue: As de PIQUE

C'est à sara de jouer.
→ sara joue: 10 de PIQUE
[IA joue automatiquement]

C'est à hmza de jouer.
→ hmza joue: Roi de PIQUE
[IA joue automatiquement]

C'est à zaba de jouer.
→ zaba joue: Dame de PIQUE
[IA joue automatiquement]
```

**Points clés** :
- ✅ Vous jouez vos propres cartes
- ✅ Les autres joueurs sont contrôlés par l'IA
- ✅ Toutes les règles de Belote sont respectées

---

### Mode 2 : Simulation (IA Joue Tout)

**Quand** : Vous appuyez sur Entrée SANS entrer de nom

```
Entrez votre prénom (ou appuyez sur Entrée pour simulation IA): [Entrée]

🤖 MODE SIMULATION: Match joué par l'IA...
```

**Gameplay** :
```
╔═══════════════════════════════════════════════════════════╗
║      PARTIE DE BELOTE - JUSQU'À 1010 POINTS              ║
╚═══════════════════════════════════════════════════════════╝

[Match joué automatiquement]

--- Résultats de la manche ---
Les Champions: 95 points
Les Pros: 67 points

✓ Les Champions a réussi son contrat!
[Match continue automatiquement jusqu'à 1010 points]
```

**Points clés** :
- ✅ Tout est automatique
- ✅ Résultats affichés à la fin
- ✅ Rapide pour tester

---

### Mode 3 : Ne Participe Pas au Match

**Quand** : Vous entrez un nom mais ne participez PAS au match en cours

```
Entrez votre prénom (ou appuyez sur Entrée pour simulation IA): Pierre

🤖 MODE SIMULATION: Match joué par l'IA...
[Pierre ne participe pas à ce match]
```

---

## 🎯 Option 5 : Jouer Tout le Tournoi

**Comportement** : Joue TOUS les matchs restants en mode simulation (IA)

```
Votre choix: 5

🎮 Lancement du tournoi complet en mode SIMULATION...
Tous les matchs seront joués automatiquement par l'IA.

=== MATCH: Les Champions vs Les Pros ===
🤖 MODE SIMULATION: Match joué par l'IA...
[Match 1 joué]

--------------------------------------------------

=== MATCH: Les Winners vs Les Losers ===
🤖 MODE SIMULATION: Match joué par l'IA...
[Match 2 joué]

--------------------------------------------------

✅ Le tournoi est terminé!

==================================================
=== CLASSEMENT FINAL ===
1. Les Champions - Points: 3, Matchs gagnés: 3
2. Les Pros - Points: 1, Matchs gagnés: 1
3. Les Winners - Points: 0, Matchs gagnés: 0
4. Les Losers - Points: 0, Matchs gagnés: 0
==================================================

✅ Tournoi terminé : 2 match(s) joué(s).
```

**Points clés** :
- ✅ Pas d'interaction manuelle
- ✅ Tous les matchs automatiques
- ✅ Affichage du classement final
- ✅ Nombre de matchs joués affiché

---

## 🐛 Correction du Bug Scanner

### Avant

```
--- Main Menu ---
5. View player statistics
Your choice: 5

[Affiche les statistiques]

--- Main Menu ---
Your choice: Exception in thread "main" java.util.NoSuchElementException
        at java.base/java.util.Scanner.next(Scanner.java:1619)
```

### Maintenant

```
--- Main Menu ---
5. View player statistics
Your choice: 5

=== Available Players ===
1. Peter 'The Shy'
2. Julie 'The Talkative'
3. amine 'minox'
Select a player (1-3) or 0 to cancel: 1

╔═══════════════════════════════════════════════════════════╗
║        STATISTIQUES DE PETER                           ║
╚═══════════════════════════════════════════════════════════╝

--- Main Menu ---    ← Retour au menu SANS exception ✅
1. Create a persona
2. List personas
Your choice: 4      ← Fonctionne normalement ✅
```

---

## 📋 Workflow Complet Recommandé

### Étape 1 : Créer des Personnages

```
Your choice: 1
--- Create Persona ---
Type: client
First name: amine
Nickname: minox
Money: 100
[etc.]
```

### Étape 2 : Créer le Tournoi

```
Your choice: 4
Votre choix: 1
Frais d'inscription par équipe: 5
✓ Tournoi créé avec succès !
```

### Étape 3 : Inscrire des Équipes

```
Votre choix: 2
Nom de l'équipe: Les Champions
Numéro du premier joueur: 1  (amine)
Numéro du deuxième joueur: 2  (sara)
Équipe 'Les Champions' inscrite avec succès!

[Répéter pour au moins 2 équipes]
```

### Étape 4 : Démarrer le Tournoi

```
Votre choix: 3
Le tournoi de Belote démarre avec 2 équipes!
```

### Étape 5A : Jouer Manuellement

```
Votre choix: 4
Entrez votre prénom: amine
🎮 MODE INTERACTIF: Vous participez à ce match!
[Jouez la partie]
```

### Étape 5B : OU Simuler Tout

```
Votre choix: 5
🎮 Lancement du tournoi complet en mode SIMULATION...
[Tous matchs joués automatiquement]
✅ Tournoi terminé : 2 match(s) joué(s).
```

### Étape 6 : Voir les Statistiques

```
Your choice: 5
Select a player: 1
[Affiche les statistiques du joueur]
```

### Étape 7 : Voir le Classement

```
Votre choix: 6
=== CLASSEMENT FINAL ===
1. Les Champions - 3 victoires
2. Les Pros - 1 victoire
```

---

## 🎮 Exemples de Partie Interactive

### Exemple 1 : Suivre la Couleur

```
C'est à amine de jouer.

Votre main:
1: Roi de PIQUE
2: Dame de PIQUE
3: As de COEUR
Choisissez une carte (1-3): 3

❌ Coup invalide! Vous devez respecter les règles de la Belote.

📋 Règles applicables:
  → Vous DEVEZ jouer la couleur demandée (PIQUE)

Choisissez une carte (1-3): 1
→ amine joue: Roi de PIQUE
```

### Exemple 2 : Obligation de Surcouper

```
Pli actuel:
- Joueur 1 a joué: 10 de CARREAU
- Joueur 2 (adversaire) a coupé: 7 de COEUR (atout)

C'est à amine de jouer.

Votre main:
1: 9 de COEUR        ← Atout plus fort !
2: Dame de PIQUE
Choisissez une carte (1-2): 2

❌ Coup invalide! Vous devez respecter les règles de la Belote.

📋 Règles applicables:
  → Un adversaire a coupé, vous devez SURCOUPER si possible

Choisissez une carte (1-2): 1
→ amine joue: 9 de COEUR
✅ Surcoupe réussie !
```

---

## ✅ Checklist de Vérification

Avant de jouer, vérifier que :

- [ ] Au moins 2 équipes inscrites
- [ ] Tournoi démarré (option 3)
- [ ] Scanner fonctionne (pas d'exception)
- [ ] Mode interactif disponible (option 4)
- [ ] Mode simulation disponible (option 5)
- [ ] Statistiques accessibles (option 5 menu principal)
- [ ] Classement disponible (option 6)

---

## 🎉 Avantages des Corrections

| Fonctionnalité | Avant | Maintenant |
|----------------|-------|------------|
| **Scanner** | ❌ Exception | ✅ Fonctionne |
| **Interaction manuelle** | ❌ Impossible | ✅ Option 4 |
| **Simulation auto** | ❌ Hardcodée | ✅ Option 5 |
| **Détection joueur** | ❌ Index fixe | ✅ Par nom |
| **Messages** | ❌ Incorrects | ✅ Clairs |
| **Flexibilité** | ❌ Limitée | ✅ Complète |

---

## 🚀 Profitez du Jeu !

Le système est maintenant **pleinement fonctionnel** et offre :

- ✅ **Choix du mode** : Interactif ou Simulation
- ✅ **Détection intelligente** : Sait si vous participez au match
- ✅ **Règles complètes** : Annonces, surcoupe, validation stricte
- ✅ **Interface claire** : Messages explicites à chaque étape
- ✅ **Stabilité** : Plus d'exceptions inattendues

**Bon jeu de Belote !** 🎴🎮
