package com.example;

public class Symbol {
    private String name;
    private int payout; // Ajout de la variable pour le gain associé

    public Symbol(String name, int payout) {
        this.name = name;
        this.payout = payout;
    }

    public String getName() {
        return name;
    }

    public int getPayout() {
        return payout;
    }
}