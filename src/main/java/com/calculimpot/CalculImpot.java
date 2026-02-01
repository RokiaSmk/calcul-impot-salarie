package com.calculimpot;

public class CalculImpot {

    public double calculerNombreParts(boolean estMarie, int nbEnfants) {
        double parts = estMarie ? 2.0 : 1.5; // Marié 2 parts, Célibataire 1.5
        parts += (nbEnfants * 0.5); // 0.5 par enfant
        return Math.min(parts, 5.0); // Plafond fixé à 5
    }

    public double calculerAbattement(double salaireBrut) {
        double abattement = salaireBrut * 0.30; // 30% du salaire brut
        return Math.min(abattement, 300000); // Plafond à 300.000F
    }

    public double calculerBaseImposable(double salaireBrut, boolean estMarie, int nbEnfants) {
        // Vérification des bornes de salaire
        if (salaireBrut < 60000 || salaireBrut > 3000000) {
            throw new IllegalArgumentException("Salaire hors limites");
        }
        double revenuNet = salaireBrut - calculerAbattement(salaireBrut);
        return revenuNet / calculerNombreParts(estMarie, nbEnfants); // Formule du document
    }
}