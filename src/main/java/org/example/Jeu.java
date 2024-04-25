package org.example;

public class Jeu {
    private final Banque banque;
    private boolean ouvert;

    public Jeu(Banque labanque) {
        this.banque = labanque;
        ouvert = true;
    }

    public boolean jouer(Joueur joueur, De de1, De de2) {
        if (!ouvert) {
            return false;
        }

        // Débit de la mise du joueur
        int mise = joueur.mise();
        try {
            joueur.debiter(mise);
        } catch (DebitImpossibleException e) {
            ouvert = false;
            return false;
        }

        // Lancer des dés
        int resultat = de1.lancer() + de2.lancer();

        // Vérification de la somme des dés
        if (resultat != 7) {
            ouvert = false;
            return false;
        }

        // Paiement du gain au joueur
        joueur.crediter(2 * mise);

        // Vérification de la solvabilité de la banque
        if (!banque.est_solvable()) {
            ouvert = false;
            return false;
        }

        return true;
    }

    public void fermer() {
        ouvert = false;
    }

    public boolean estOuvert() {
        return ouvert;
    }
}
