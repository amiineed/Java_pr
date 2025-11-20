package com.pub.game;


public enum ValeurCarte {
   
    SEPT("7", 0, 0, 0, 0),
    HUIT("8", 0, 0, 1, 1),
    NEUF("9", 0, 14, 2, 5), 
    DIX("10", 10, 10, 3, 3),
    VALET("Valet", 2, 20, 4, 6), 
    DAME("Dame", 3, 3, 5, 2),
    ROI("Roi", 4, 4, 6, 4),
    AS("As", 11, 11, 7, 7);

    private final String representation;
    private final int pointsNormal;
    private final int pointsAtout;
    private final int ordreAnnonce;
    private final int ordreForceNormal; 

    ValeurCarte(String representation, int pointsNormal, int pointsAtout, int ordreAnnonce, int ordreForceNormal) {
        this.representation = representation;
        this.pointsNormal = pointsNormal;
        this.pointsAtout = pointsAtout;
        this.ordreAnnonce = ordreAnnonce;
        this.ordreForceNormal = ordreForceNormal;
    }

    public int getPoints(boolean isAtout) {
        return isAtout ? pointsAtout : pointsNormal;
    }

    public int getOrdreForceNormal() {
        return ordreForceNormal;
    }

     public int getOrdreForceAtout(ValeurCarte v) {
         switch(v) {
            case VALET: return 8;
            case NEUF: return 7;
            case AS: return 6;
            case DIX: return 5;
            case ROI: return 4;
            case DAME: return 3;
            case HUIT: return 2;
            case SEPT: return 1;
            default: return 0;
         }
     }

    public int getOrdreAnnonce() {
        return ordreAnnonce;
    }

    @Override
    public String toString() {
        return representation;
    }
}