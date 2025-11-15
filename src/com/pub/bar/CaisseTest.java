/*
package com.pub.bar;

// NOTE: This requires JUnit 5 libraries to be in the classpath to compile/run.
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

class CaisseTest {

    // @Test
    void testAjouterMontant() {
        Caisse caisse = new Caisse(100.0);
        caisse.ajouterMontant(50.0);
        // assertEquals(150.0, caisse.getMontantTotal());
        caisse.ajouterMontant(-10.0); // Should be ignored
        // assertEquals(150.0, caisse.getMontantTotal());
    }

    // @Test
    void testRetirerMontant() {
        Caisse caisse = new Caisse(100.0);
        boolean succes = caisse.retirerMontant(50.0);
        // assertTrue(succes);
        // assertEquals(50.0, caisse.getMontantTotal());

        boolean echec = caisse.retirerMontant(200.0); // Not enough funds
        // assertFalse(echec);
        // assertEquals(50.0, caisse.getMontantTotal());

        boolean echecNegatif = caisse.retirerMontant(-10.0); // Invalid amount
        // assertFalse(echecNegatif);
        // assertEquals(50.0, caisse.getMontantTotal());
    }
}
*/
