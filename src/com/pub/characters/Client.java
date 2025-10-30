package com.pub.characters;



import com.pub.bar.Boisson;
import java.util.Objects;
import java.util.List;

// Concept: extends (Inheritance) (Java1.3)
// Concept: implements (Interface) (Java1.3 / 1.5)
public class Client extends Human {

    // Concept: Encapsulation (private) (Java1.2)
    private Boisson boissonFavorite;
    private Boisson boissonSecondaire;
    private String identifiantGenre;
    private int niveauAlcoolemie;

    // Concept: Overloaded Constructors (Java1.2)
    public Client(String prenom, String surnom, double porteMonnaie, int popularite, String cri,
                  Boisson boissonFavorite, Boisson boissonSecondaire, String identifiantGenre) {
        // Concept: super() call (Java1.3)
        super(prenom, surnom, porteMonnaie, popularite, cri);
        this.boissonFavorite = boissonFavorite;
        this.boissonSecondaire = boissonSecondaire;
        this.niveauAlcoolemie = 0;
        this.identifiantGenre = identifiantGenre;
        // Simplified gender identification
    }

    // Default constructor example
     public Client(String prenom) {
         // Concept: Constructor Chaining with 'this()' could be used, but calling super directly is common
         super(prenom, "Le Nouveau", 20.0, 5, "Hé!");
         // Default drinks - requires Boisson class to be defined or use nulls
         this.boissonFavorite = null; // Set proper defaults later
         this.boissonSecondaire = null;
         this.niveauAlcoolemie = 0;
     }

    // Concept: Accessors (Java1.2)
    public Boisson getBoissonFavorite() { return boissonFavorite; }
    public Boisson getBoissonSecondaire() { return boissonSecondaire; }
    public int getNiveauAlcoolemie() { return niveauAlcoolemie; }
    public boolean isHomme() { return this.identifiantGenre != null && this.identifiantGenre.toLowerCase().startsWith("homme"); }
    public String getTeeShirtCouleur() { return identifiantGenre != null && identifiantGenre.startsWith("Homme ") ? identifiantGenre.substring(6) : null; }
    public List<String> getBijoux() { return identifiantGenre != null && identifiantGenre.startsWith("Femme ") ? List.of(identifiantGenre.substring(6).split(",")) : null; }


    // Concept: Mutators with validation (Java1.2)
    public void setBoissonFavorite(Boisson boissonFavorite) { this.boissonFavorite = boissonFavorite; }
    public void setBoissonSecours(Boisson boissonSecours) { this.boissonSecondaire = boissonSecours; }
    // niveauAlcoolemie changes via boire()

    // Gender change method as per requirement (odd!)
    public void devenirFemme(List<String> nouveauxBijoux) {
        if (isHomme()) {
            this.identifiantGenre = "Femme " + (nouveauxBijoux == null ? "Simple boucle d'oreille" : String.join(",", nouveauxBijoux));
            parler("Je me sens... différente !");
        }
    }
     public void devenirHomme(String couleurTeeShirt) {
         if (!isHomme()) {
             this.identifiantGenre = "Homme " + (couleurTeeShirt == null ? "Gris" : couleurTeeShirt);
             parler("Qu'est-ce qui m'arrive ?");
         }
     }


    // Concept: Method Override (@Override) (Java1.3 / 1.5)
    // Concept: Polymorphism in practice (different sePresenter)
    @Override
    public void sePresenter() {
        // Concept: Calling superclass method (Java1.3)
        super.parler("Salut! Je suis " + getPrenom() +
                     (surnom != null && !surnom.isEmpty() ? " dit '" + surnom + "'" : "") + ".");
        if (isHomme()) {
            super.parler("Regardez mon beau t-shirt " + this.getTeeShirtCouleur() + ".");
        } else {
             super.parler("Vous aimez mes bijoux (" + String.join(", ", this.getBijoux()) + ") ?");
        }
        if (boissonFavorite != null) {
            super.parler("Ma boisson préférée est " + boissonFavorite.getNom() + ".");
        }
         if (niveauAlcoolemie > 5) { // Arbitrary drunk threshold
             super.parler("Hip! Je me sens un peu pompette.");
         }
    }

    // Concept: Method Override (@Override) (Java1.3)
    @Override
    public void boire(Boisson boisson) {
        super.boire(boisson); // Call the Human version first
        // Add specific Client logic
        if (boisson instanceof com.pub.bar.BoissonAlcoolisee) { // Assumes BoissonAlcoolisee exists
            int points = extractPointsAlcool(boisson);
            this.niveauAlcoolemie += points;
            parler("Whoa, ça monte! Mon niveau est à " + this.niveauAlcoolemie);
        } else {
             // Maybe reduce alcohol level slightly?
             if (this.niveauAlcoolemie > 0) this.niveauAlcoolemie--;
        }
    }

    // Try to obtain alcohol points via reflection (handles classes missing getPointsAlcool)
    private int extractPointsAlcool(Boisson boisson) {
        if (boisson == null) return 0;
        try {
            Class<?> cls = boisson.getClass();
            String[] candidates = {"getPointsAlcool", "getPointsAlcohol", "getTeneurAlcool", "getDegreAlcool", "getDegre", "getTauxAlcool", "getPourcentageAlcool", "getAlcoolPoints", "getAlcool"};
            for (String name : candidates) {
                try {
                    java.lang.reflect.Method m = cls.getMethod(name);
                    Object val = m.invoke(boisson);
                    if (val instanceof Number) return ((Number) val).intValue();
                    if (val instanceof String) {
                        try { return (int) Double.parseDouble((String) val); } catch (Exception e) { }
                    }
                } catch (NoSuchMethodException nsme) {
                    // try next candidate
                }
            }
        } catch (Exception e) {
            // ignore and fallback
        }
        // Fallback default
        return 1;
    }

    // Concept: Method Override (@Override) for drunk talk (Java1.3)
     @Override
     public void parler(String message) {
         String prefix = this.getPrenom() + ": ";
         String suffix = "";

         // Check if drunk and talking to Serveur/Serveuse of opposite gender
         // This requires passing the recipient or context, which complicates the simple parler method.
         // Let's simplify: Add suffix *always* when drunk, regardless of recipient for demonstration.
         if (this.niveauAlcoolemie > 10) { // Higher drunk threshold for special talk
             if (isHomme()) {
                 suffix = ", ma jolie!"; // Talking *like* they are talking to a serveuse
             } else {
                 suffix = ", mon beau!"; // Talking *like* they are talking to a serveur
             }
         }
         System.out.println(prefix + message + suffix);
     }

     // Override receiving drink to potentially change mind if favorite is offered
     @Override
     public void recevoirVerre(Boisson boisson, Human offreur) {
         if (boisson.equals(boissonFavorite)) {
            parler("Oh! Ma boisson préférée! Merci " + offreur.getPrenom() + "!");
         } else {
            parler("Merci " + offreur.getPrenom() + " pour le/la " + boisson.getNom() + ".");
         }
         boire(boisson);
     }


    // Concept: toString override (Java1.2) - adding specific fields
    @Override
    public String toString() {
        return super.toString().replace("}", "") + // Get Human part, remove closing brace
                ", boissonFavorite=" + (boissonFavorite != null ? boissonFavorite.getNom() : "Aucune") +
                ", alcoolemie=" + niveauAlcoolemie +
                ", genreDetails='" + (isHomme() ? "TeeShirt:" + getTeeShirtCouleur() : "Bijoux:" + getBijoux()) + '\'' +
                '}'; // Add new fields and closing brace
    }

     // Concept: equals override (Java1.2) - relies on Human's equals (prenom)
     // No need to override if Human's equals is sufficient. If we need to compare
     // other client-specific fields, we would override it here and call super.equals().
     // public boolean equals(Object o) { ... super.equals(o) && this.field == other.field ... }

     // Concept: hashCode override (Java1.2) - relies on Human's hashCode
     // Only override if equals is overridden.
}