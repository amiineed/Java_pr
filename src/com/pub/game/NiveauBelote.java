package com.pub.game;

public enum NiveauBelote {
    DEBUTANT,
    MOYEN; // Added semicolon so we can add the translation method below

    @Override
    public String toString() {
        switch(this) {
            case DEBUTANT: return "Beginner";
            case MOYEN: return "Intermediate";
            default: return super.toString();
        }
    }
}