package com.example;

import com.google.gson.Gson;

public class Statistiques {
    private int coins;
    private int gamesPlayed;
    private int gamesWon;
    private int coinsSpent;

    public Statistiques(int coins, int gamesPlayed, int gamesWon, int coinsSpent) {
        this.coins = coins;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.coinsSpent = coinsSpent;
    }

    public int getCoins() {
        return coins;
    }

    public void updateGameStats(int bet, int winnings) {
        gamesPlayed++;
        coinsSpent += bet;

        if (winnings > 0) {
            gamesWon++;
            coins += winnings;
        } else {
            coins -= bet;
        }
    }

    // Ajoutez cette méthode pour convertir toutes les statistiques en format JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
