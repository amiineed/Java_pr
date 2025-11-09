# 🎴 Guide d'Utilisation - Belote Corrigée

## 🚀 Lancer le Jeu

```bash
# Compiler
javac -encoding UTF-8 -d bin -sourcepath src src\com\pub\main\Main.java

# Exécuter
java -cp bin com.pub.main.Main
```

---

## 🎮 Flux de Jeu avec les Nouvelles Fonctionnalités

### 1. Démarrage d'un Tournoi

```
--- Main Menu ---
1. Create a persona
2. List personas
3. Order a drink
4. Manage Belote Tournament   ← Choisir cette option
5. View player statistics
6. Save state (simplified)
7. Load state (simplified)
0. Exit
Your choice: 4
```

### 2. Création et Inscription

```
--- GESTION DU TOURNOI DE BELOTE ---
1. Créer un nouveau tournoi
2. Inscrire une équipe
3. Démarrer le tournoi
4. Jouer le prochain match
5. Jouer tout le tournoi
6. Afficher le classement
7. Afficher les équipes inscrites
0. Retour
Votre choix: 1

Frais d'inscription par équipe: 5
✓ Tournoi créé avec succès !
```

### 3. Phase de Distribution et Annonces

```
============================================================
MANCHE 1
Score actuel - Les Champions: 0 | Les Pros: 0
============================================================

Carte retournée: Valet de COEUR

=== PHASE D'ENCHÈRES (PREMIER TOUR) ===

amine, prenez-vous l'atout COEUR ?
Votre main:
1: 9 de COEUR
2: Roi de PIQUE
3: 8 de PIQUE
4: As de COEUR
5: 10 de TREFLE
6: Roi de COEUR
7: 10 de CARREAU
8: Valet de COEUR
Votre choix (oui/non): oui

amine prend l'atout: COEUR

=== ANNONCES DÉTECTÉES ===         ← NOUVEAU !

Les Champions:
  → Tierce (20 pts)

Les Pros:
  → Belote et Rebelote (20 pts)
  
Les annonces seront validées au 2ème pli.
```

---

## 🎯 Validation des Coups - Exemples

### Exemple 1 : Obligation de Suivre

```
C'est à amine de jouer.

Votre main:
1: 9 de COEUR
2: Roi de PIQUE      ← A du PIQUE
3: 8 de PIQUE        ← A du PIQUE
4: As de COEUR
5: 10 de TREFLE
Choisissez une carte (1-5): 1

❌ Coup invalide! Vous devez respecter les règles de la Belote.

📋 Règles applicables:
  → Vous DEVEZ jouer la couleur demandée (PIQUE)
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
3: As de TREFLE
Choisissez une carte (1-3): 3

❌ Coup invalide! Vous devez respecter les règles de la Belote.

📋 Règles applicables:
  → Un adversaire a coupé, vous devez SURCOUPER si possible

Choisissez une carte (1-3): 1  ← Correct !
→ amine joue: 9 de COEUR
```

### Exemple 3 : Partenaire Mène

```
Pli actuel:
- Partenaire a joué: Valet de COEUR (atout le plus fort)

C'est à amine de jouer.

Votre main:
1: 7 de COEUR        ← Atout plus faible
2: Dame de PIQUE
3: As de TREFLE
Choisissez une carte (1-3): 2  ← Peut défausser !
→ amine joue: Dame de PIQUE

✅ Coup valide : Le partenaire mène, pas d'obligation de monter
```

### Exemple 4 : Monter à l'Atout

```
Couleur demandée: COEUR (atout)
Pli actuel:
- Adversaire a joué: 8 de COEUR

C'est à amine de jouer.

Votre main:
1: 7 de COEUR        ← Plus faible
2: Roi de COEUR      ← Plus fort !
3: Dame de PIQUE
Choisissez une carte (1-3): 1

❌ Coup invalide! Vous devez respecter les règles de la Belote.

📋 Règles applicables:
  → Vous DEVEZ jouer la couleur demandée (COEUR)
  → Vous devez MONTER à l'atout si possible

Choisissez une carte (1-3): 2  ← Correct !
→ amine joue: Roi de COEUR
```

---

## 📊 Validation des Annonces au 2ème Pli

```
----------------------------------------
PLI 2/8
----------------------------------------

╔══════════════════════════════════════╗    ← NOUVEAU !
║  VALIDATION DES ANNONCES (PLI 2)    ║
╚══════════════════════════════════════╝

Comparaison des annonces:
- Les Champions: Tierce (20 pts)
- Les Pros: Belote-Rebelote (20 pts)

Égalité d'annonces : aucune ne compte.
✓ Les Pros a la Belote-Rebelote (+20 pts)

[Les annonces Belote-Rebelote comptent toujours]
```

---

## 🏆 Résultat de Manche avec Annonces

```
--- Résultats de la manche ---
Les Champions: 85 points
Les Pros: 77 points

✓ Les Champions a réussi son contrat!

Score après ajout des annonces:
- Les Champions: 85 + 0 (annonce perdue) = 85
- Les Pros: 77 + 20 (Belote) = 97

[Note: Les annonces ne comptent que si l'équipe fait 82+ points]
```

---

## 🎲 Cas Spéciaux et Règles

### Carré de Valets (200 pts)

Si un joueur a les 4 Valets :

```
=== ANNONCES DÉTECTÉES ===

Les Champions:
  → Carré de Valets (200 pts)  ← Annonce la plus forte !

Les Pros:
  → 100 (Quinte) (100 pts)
  
Les annonces seront validées au 2ème pli.

...

╔══════════════════════════════════════╗
║  VALIDATION DES ANNONCES (PLI 2)    ║
╚══════════════════════════════════════╝
✓ Les Champions marque: Carré de Valets (200 pts)
```

### Belote-Rebelote (20 pts)

```
Détection automatique si le joueur a:
- Roi d'atout
- Dame d'atout

→ Comptabilisée automatiquement au 2ème pli
→ Compte TOUJOURS, même si l'équipe perd
```

### Ordre de Force des Atouts

```
Ordre croissant (du plus faible au plus fort):
1. 7 d'atout
2. 8 d'atout
3. Dame d'atout
4. Roi d'atout
5. 10 d'atout
6. As d'atout
7. 9 d'atout
8. Valet d'atout  ← LE PLUS FORT !

Points:
- Valet d'atout: 20 pts
- 9 d'atout: 14 pts
- As d'atout: 11 pts
- 10 d'atout: 10 pts
- Roi d'atout: 4 pts
- Dame d'atout: 3 pts
- 8, 7 d'atout: 0 pt
```

---

## 🐛 Débogage et Tests

### Vérifier les Annonces

Pour tester si les annonces sont bien détectées :

1. Modifiez temporairement `PartieDeBelote.java` :

```java
// Après distribution, afficher les mains pour debug
System.out.println("\n=== DEBUG MAINS ===");
System.out.println("Main Joueur 1:");
mainJoueur1.forEach(c -> System.out.println("  " + c));
```

2. Vérifiez la détection :
   - Cherchez des suites (7-8-9, 10-V-D-R, etc.)
   - Cherchez des carrés (4 cartes même valeur)
   - Vérifiez Roi+Dame d'atout

### Tester la Surcoupe

Scénario de test :

```java
// Forcer une situation de surcoupe
1. Joueur 1 joue CARREAU
2. Joueur 2 (adversaire) coupe avec 7 de COEUR (atout)
3. Vérifier que Joueur 3 DOIT jouer un atout > 7 s'il en a
```

---

## 📝 Checklist de Validation

Avant de soumettre, vérifier :

- [ ] Les annonces sont détectées après les 8 cartes
- [ ] L'affichage des annonces est clair
- [ ] La validation se fait au 2ème pli
- [ ] Belote-Rebelote compte toujours
- [ ] Seule la meilleure annonce compte
- [ ] Obligation de suivre la couleur
- [ ] Obligation de couper si pas la couleur
- [ ] Obligation de surcouper si adversaire coupe
- [ ] Pas d'obligation si partenaire mène
- [ ] Obligation de monter à l'atout
- [ ] Les points sont correctement comptés
- [ ] Les messages d'erreur sont explicites

---

## 💡 Astuces

### Pour le Joueur Humain

- **Lisez attentivement** les règles affichées quand un coup est invalide
- **Vérifiez** qui a joué quoi avant de choisir votre carte
- **Attention** à l'obligation de monter à l'atout !

### Pour l'IA

L'IA respecte maintenant **strictement** toutes les règles. Elle :
- Ne jouera **jamais** un coup invalide
- Surcoupera **systématiquement** si elle peut
- Montera à l'atout si elle le doit

---

## 🎯 Exemple Complet de Partie

```
Carte retournée: Dame de PIQUE
amine prend l'atout: PIQUE

=== ANNONCES DÉTECTÉES ===

Les Champions:
  → 50 (Quarte) (50 pts)
  → Belote et Rebelote (20 pts)

Les Pros:
  → Tierce (20 pts)
  
Les annonces seront validées au 2ème pli.

----------------------------------------
PLI 1/8
----------------------------------------
[... pli joué ...]

----------------------------------------
PLI 2/8
----------------------------------------

╔══════════════════════════════════════╗
║  VALIDATION DES ANNONCES (PLI 2)    ║
╚══════════════════════════════════════╝
✓ Les Champions marque: 50 (Quarte) (50 pts)
✓ Les Champions a la Belote-Rebelote (+20 pts)

[... plis 3-8 ...]

--- Résultats de la manche ---
Les Champions: 98 points
Les Pros: 64 points

✓ Les Champions a réussi son contrat!

Points finaux avec annonces:
- Les Champions: 98 + 50 + 20 = 168 points
- Les Pros: 64 points
```

---

## 🚀 Pour Aller Plus Loin

### Améliorations Possibles

1. **Afficher les cartes des annonces**
   - Utiliser `Annonce.cartes` pour montrer quelles cartes forment l'annonce

2. **Statistiques d'annonces**
   - Tracker combien d'annonces chaque équipe a faites

3. **IA plus intelligente**
   - Faire jouer l'IA en fonction de ses annonces
   - Privilégier la conservation des suites

4. **Mode tournoi avancé**
   - Comptabiliser les annonces dans le classement
   - Afficher le meilleur annonceur du tournoi

---

## ✅ Résumé des Changements

**Avant** :
- ❌ Pas d'annonces
- ❌ Validation des coups approximative
- ❌ Surcoupe non gérée

**Après** :
- ✅ Système d'annonces complet
- ✅ Validation stricte des coups
- ✅ Surcoupe obligatoire
- ✅ Messages d'aide explicites
- ✅ Comptage réel des points

**Le jeu est maintenant conforme aux règles académiques de la Belote !** 🎉
