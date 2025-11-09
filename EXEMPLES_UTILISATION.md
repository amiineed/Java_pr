# 📚 Exemples d'Utilisation des Nouvelles Fonctionnalités

## 1. Utilisation des Exceptions Personnalisées

### 🔴 StockRuptureException (Checked)

#### Exemple dans `Barman.java` :

```java
public void servirBoisson(Boisson boisson) throws StockRuptureException {
    if (stock == null || !stock.containsKey(boisson)) {
        throw new StockRuptureException(
            "La boisson " + boisson.getNom() + " n'est pas dans notre catalogue"
        );
    }
    
    int quantite = stock.get(boisson);
    if (quantite <= 0) {
        throw new StockRuptureException(
            "Rupture de stock pour " + boisson.getNom() + 
            ". Veuillez réapprovisionner."
        );
    }
    
    stock.put(boisson, quantite - 1);
}
```

#### Gestion dans le code appelant :

```java
try {
    barman.servirBoisson(beer);
    System.out.println("Boisson servie avec succès!");
} catch (StockRuptureException e) {
    System.err.println("Erreur de stock : " + e.getMessage());
    barman.parler("Désolé, nous n'avons plus cette boisson...");
}
```

---

### 💰 InsufficientFundsException (Checked)

#### Exemple dans `Tournoi.java` :

```java
public void inscrireEquipe(String nomEquipe, Client joueur1, Client joueur2) 
        throws InsufficientFundsException {
    
    // Vérifier les fonds de chaque joueur
    double totalNecessaire = fraisInscription * 2;
    double totalDisponible = joueur1.getPorteMonnaie() + joueur2.getPorteMonnaie();
    
    if (joueur1.getPorteMonnaie() < fraisInscription) {
        throw new InsufficientFundsException(
            joueur1.getPrenom() + " ne dispose que de " + 
            joueur1.getPorteMonnaie() + "€, il manque " + 
            (fraisInscription - joueur1.getPorteMonnaie()) + "€"
        );
    }
    
    if (joueur2.getPorteMonnaie() < fraisInscription) {
        throw new InsufficientFundsException(
            joueur2.getPrenom() + " ne dispose que de " + 
            joueur2.getPorteMonnaie() + "€, il manque " + 
            (fraisInscription - joueur2.getPorteMonnaie()) + "€"
        );
    }
    
    // Inscription réussie
    joueur1.payer(fraisInscription);
    joueur2.payer(fraisInscription);
    // ...
}
```

#### Gestion dans le code appelant :

```java
try {
    tournoi.inscrireEquipe("Les Champions", client1, client2);
    System.out.println("Équipe inscrite avec succès!");
} catch (InsufficientFundsException e) {
    System.err.println("Inscription impossible : " + e.getMessage());
    System.out.println("Veuillez donner de l'argent à vos joueurs.");
}
```

---

### 🚫 InscriptionCloseException (Unchecked)

#### Exemple dans `Tournoi.java` :

```java
public void inscrireEquipe(String nomEquipe, Client joueur1, Client joueur2) {
    
    // Vérification des inscriptions
    if (!inscriptionsOuvertes) {
        throw new InscriptionCloseException(
            "Les inscriptions ne sont pas encore ouvertes pour ce tournoi"
        );
    }
    
    if (tournoiDemarre) {
        throw new InscriptionCloseException(
            "Le tournoi a déjà commencé! " +
            "Inscriptions closes depuis " + heureDebut
        );
    }
    
    if (tournoiTermine) {
        throw new InscriptionCloseException(
            "Le tournoi est terminé. Impossible de s'inscrire."
        );
    }
    
    // Inscription OK
    // ...
}
```

#### Gestion (optionnelle car unchecked) :

```java
try {
    tournoi.inscrireEquipe("Team Rocket", client3, client4);
} catch (InscriptionCloseException e) {
    System.err.println("⚠️ " + e.getMessage());
    System.out.println("Attendez le prochain tournoi!");
}
```

---

### ⛔ UnauthorizedActionException (Unchecked)

#### Exemple dans `Barman.java` :

```java
public void boireAlcool(BoissonAlcoolisee boisson) {
    throw new UnauthorizedActionException(
        "Un barman en service ne peut pas consommer d'alcool! " +
        "C'est contraire au règlement du bar."
    );
}

public void participerAuTournoi(Tournoi tournoi) {
    throw new UnauthorizedActionException(
        "Le barman doit rester neutre et ne peut pas " +
        "participer aux tournois de clients."
    );
}
```

#### Exemple dans `Client.java` (vérification d'âge) :

```java
public void acheterAlcool(BoissonAlcoolisee boisson) {
    if (age < 18) {
        throw new UnauthorizedActionException(
            "Vente d'alcool interdite aux mineurs! " +
            getPrenom() + " n'a que " + age + " ans."
        );
    }
    // Achat autorisé
    // ...
}
```

#### Gestion dans le code appelant :

```java
try {
    barman.boireAlcool(whisky);
} catch (UnauthorizedActionException e) {
    System.err.println("❌ Action interdite : " + e.getMessage());
}
```

---

## 2. Utilisation des Statistiques

### 📊 Afficher les Statistiques d'un Joueur

```java
// Récupérer un client
Client joueur = leBar.trouverClient("Peter");

// Afficher ses statistiques formatées
System.out.println(joueur.getStatistiquesDetailles());
```

**Output attendu :**
```
╔═══════════════════════════════════════════════════════════╗
║        STATISTIQUES DE PETER                             ║
╠═══════════════════════════════════════════════════════════╣
║ Surnom : The Shy                                          ║
║ Argent disponible : 25.50€                               ║
║ Niveau d'alcoolémie : 0.035 g/L                          ║
╠═══════════════════════════════════════════════════════════╣
║  STATISTIQUES DE CONSOMMATION                             ║
╠═══════════════════════════════════════════════════════════╣
║ Total de verres consommés : 7                             ║
║ Boisson favorite : Beer                                   ║
╠═══════════════════════════════════════════════════════════╣
║  STATISTIQUES DE TOURNOI                                  ║
╠═══════════════════════════════════════════════════════════╣
║ Matchs joués : 3                                          ║
║ Victoires : 2                                             ║
║ Défaites : 1                                              ║
║ Points de tournoi : 6                                     ║
║ Taux de victoire : 66.7%                                  ║
╚═══════════════════════════════════════════════════════════╝
```

---

### 🔄 Enregistrer un Match de Tournoi

**Automatique dans `Tournoi.java`** (déjà implémenté) :

```java
// Après un match
if (equipeGagnante.equals(equipe1)) {
    // Enregistrer pour l'équipe gagnante
    equipe1.getJoueur1().enregistrerMatchTournoi(true);
    equipe1.getJoueur2().enregistrerMatchTournoi(true);
    
    // Enregistrer pour l'équipe perdante
    equipe2.getJoueur1().enregistrerMatchTournoi(false);
    equipe2.getJoueur2().enregistrerMatchTournoi(false);
}
```

**Utilisation manuelle si nécessaire :**

```java
// Après un match amical (hors tournoi)
if (gagne) {
    joueur.enregistrerMatchTournoi(true);
    System.out.println(joueur.getPrenom() + " a gagné! +3 points");
} else {
    joueur.enregistrerMatchTournoi(false);
    System.out.println(joueur.getPrenom() + " a perdu.");
}
```

---

### 📈 Consulter les Statistiques Programmatiquement

```java
Client joueur = leBar.trouverClient("Julie");

// Accéder aux statistiques individuellement
int verres = joueur.getNombreVerresConsommes();
int matchs = joueur.getMatchsTournoiJoues();
int victoires = joueur.getMatchsTournoiGagnes();
int defaites = joueur.getMatchsTournoiPerdus();
int points = joueur.getPointsTournoi();

// Calculer le taux de victoire
double tauxVictoire = (matchs > 0) 
    ? (double) victoires / matchs * 100 
    : 0.0;

System.out.println(joueur.getPrenom() + " - Statistiques:");
System.out.println("  Consommation: " + verres + " verres");
System.out.println("  Tournoi: " + victoires + "/" + matchs + " (" + 
                   String.format("%.1f%%", tauxVictoire) + ")");
```

---

## 3. Scénario Complet d'Utilisation

### 🎮 Exemple : Une Soirée au Bar avec Tournoi

```java
public class SoireeTournoi {
    
    public static void organiserSoiree(Bar leBar) {
        System.out.println("=== DÉBUT DE LA SOIRÉE ===\n");
        
        // 1. Commander des boissons
        System.out.println("1️⃣ Phase de consommation:");
        Client peter = leBar.trouverClient("Peter");
        Client julie = leBar.trouverClient("Julie");
        Barman barman = leBar.getBarman();
        
        try {
            // Peter commande 3 bières
            for (int i = 0; i < 3; i++) {
                barman.servirBoisson(leBar.trouverBoisson("Beer"));
                peter.boire(leBar.trouverBoisson("Beer"));
            }
            System.out.println("✓ Peter a bu 3 bières");
            
            // Julie commande 2 vins
            for (int i = 0; i < 2; i++) {
                barman.servirBoisson(leBar.trouverBoisson("Wine"));
                julie.boire(leBar.trouverBoisson("Wine"));
            }
            System.out.println("✓ Julie a bu 2 vins");
            
        } catch (StockRuptureException e) {
            System.err.println("Problème de stock: " + e.getMessage());
        }
        
        // 2. Créer et lancer un tournoi
        System.out.println("\n2️⃣ Création du tournoi:");
        Tournoi tournoi = new Tournoi(leBar, 5.0);
        tournoi.ouvrirInscriptions();
        
        try {
            // Vérifier que le barman ne peut pas s'inscrire
            try {
                tournoi.inscrireEquipe("Barmen", barman, barman);
            } catch (UnauthorizedActionException e) {
                System.out.println("❌ " + e.getMessage());
            }
            
            // Inscrire les clients
            tournoi.inscrireEquipe("Team Peter", peter, julie);
            System.out.println("✓ Équipe inscrite");
            
        } catch (InsufficientFundsException e) {
            System.err.println("Problème de paiement: " + e.getMessage());
            return;
        } catch (InscriptionCloseException e) {
            System.err.println("Inscription impossible: " + e.getMessage());
            return;
        }
        
        // 3. Lancer le tournoi
        System.out.println("\n3️⃣ Déroulement du tournoi:");
        tournoi.demarrerTournoi();
        tournoi.jouerTournoiComplet();
        
        // 4. Afficher les statistiques
        System.out.println("\n4️⃣ Statistiques finales:");
        System.out.println(peter.getStatistiquesDetailles());
        System.out.println(julie.getStatistiquesDetailles());
        
        System.out.println("\n=== FIN DE LA SOIRÉE ===");
    }
}
```

---

## 4. Bonnes Pratiques

### ✅ Gestion des Exceptions Checked

```java
// ❌ MAUVAIS : Ignorer l'exception
try {
    barman.servirBoisson(boisson);
} catch (StockRuptureException e) {
    // Ne rien faire
}

// ✅ BON : Gérer proprement
try {
    barman.servirBoisson(boisson);
} catch (StockRuptureException e) {
    System.err.println("Stock insuffisant: " + e.getMessage());
    barman.parler("Désolé, nous devons réapprovisionner.");
    barman.commanderStock(boisson, 10); // Réapprovisionner
}
```

### ✅ Propagation des Exceptions

```java
// Propager l'exception pour la gérer à un niveau supérieur
public void traiterCommande(Client client, Boisson boisson) 
        throws StockRuptureException, InsufficientFundsException {
    barman.servirBoisson(boisson);
    barman.recevoirPaiement(client, boisson.getPrixVente());
    client.boire(boisson);
}
```

### ✅ Exceptions Multiples

```java
// Gérer plusieurs types d'exceptions
try {
    traiterCommande(client, boisson);
} catch (StockRuptureException e) {
    System.err.println("Stock: " + e.getMessage());
} catch (InsufficientFundsException e) {
    System.err.println("Paiement: " + e.getMessage());
} catch (Exception e) {
    System.err.println("Erreur inattendue: " + e.getMessage());
}
```

---

## 5. Tests Unitaires Suggérés

### 🧪 Test des Exceptions

```java
@Test(expected = StockRuptureException.class)
public void testStockRupture() throws StockRuptureException {
    Barman barman = new Barman(...);
    Boisson beer = new Boisson("Beer", 0.5, 3.0);
    
    // Vider le stock
    barman.getStock().put(beer, 0);
    
    // Devrait lever l'exception
    barman.servirBoisson(beer);
}

@Test
public void testStatistiquesIncrementation() {
    Client client = new Client(...);
    
    assertEquals(0, client.getNombreVerresConsommes());
    
    client.boire(new Boisson("Water", 0.1, 1.0));
    assertEquals(1, client.getNombreVerresConsommes());
    
    client.enregistrerMatchTournoi(true);
    assertEquals(1, client.getMatchsTournoiJoues());
    assertEquals(1, client.getMatchsTournoiGagnes());
    assertEquals(3, client.getPointsTournoi());
}
```

---

## 📝 Notes Importantes

1. **Exceptions Checked** (StockRuptureException, InsufficientFundsException)
   - DOIVENT être gérées avec try-catch ou propagées avec throws
   - Utilisées pour les erreurs récupérables

2. **Exceptions Unchecked** (InscriptionCloseException, UnauthorizedActionException)
   - PEUVENT être gérées mais ce n'est pas obligatoire
   - Utilisées pour les erreurs de programmation ou violations de règles métier

3. **Statistiques**
   - Mises à jour automatiquement lors des actions
   - Persistantes pendant toute la durée de vie de l'objet Client
   - Affichage formaté prêt pour l'utilisateur final

---

## 🎯 Prochaines Étapes Suggérées

1. ✅ Tester chaque exception dans différents scénarios
2. ✅ Vérifier l'affichage des statistiques avec plusieurs joueurs
3. ✅ Organiser un tournoi complet et vérifier les stats finales
4. ✅ Ajouter des validations supplémentaires si nécessaire
5. ✅ Documenter tout comportement spécifique à votre projet

**Le code est prêt à être utilisé et étendu !** 🚀
