package com.pub.game;

public enum Couleur {
    PIQUE,
    CARREAU,
    TREFLE,
    COEUR;

    @Override
    public String toString() {
        switch(this) {
            case PIQUE: return "Spades";
            case CARREAU: return "Diamonds";
            case TREFLE: return "Clubs";
            case COEUR: return "Hearts";
            default: return super.toString();
        }
    }
}