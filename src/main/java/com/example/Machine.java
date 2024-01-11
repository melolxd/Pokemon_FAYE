package com.example;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Machine {
    private Symbol[][] matrix;

    public Machine(List<List<Symbol>> columns) {
        this.matrix = new Symbol[3][3];
        refreshMachine(columns);
    }

    public void refreshMachine(List<List<Symbol>> columns) {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            List<Symbol> columnSymbols = columns.get(i);
            int randomPosition = random.nextInt(columnSymbols.size());
            for (int j = 0; j < 3; j++) {
                matrix[j][i] = columnSymbols.get((randomPosition + j) % columnSymbols.size());
            }
        }
    }

    public int checkGains(int bet) {
        int winnings = 0;
    
        // Vérification des lignes horizontales
        if (bet >= 1) {
            winnings += checkHorizontalLine(1) * bet;
        }
    
        if (bet >= 2) {
            winnings += (checkHorizontalLine(2) + checkVerticalLines(2)) * bet;
        }
    
        if (bet >= 3) {
            winnings += (checkHorizontalLine(3) + checkVerticalLines(3) + checkDiagonals(3)) * bet;
        }
    
        return winnings;
    }
    
    private int checkHorizontalLine(int row) {
        Symbol firstSymbol = matrix[row][0];
    
        // Vérifier si toutes les cases de la ligne ont le même nom de symbole
        for (int j = 1; j < 3; j++) {
            if (!matrix[row][j].getName().equals(firstSymbol.getName())) {
                return 0; // Pas de combinaison gagnante
            }
        }
    
        return firstSymbol.getPayout(); // Combinaison gagnante, retourner le gain
    }
    
    private int checkVerticalLines(int col) {
        Symbol firstSymbol = matrix[0][col];
    
        // Vérifier si toutes les cases de la colonne ont le même nom de symbole
        for (int i = 1; i < 3; i++) {
            if (!matrix[i][col].getName().equals(firstSymbol.getName())) {
                return 0; // Pas de combinaison gagnante
            }
        }
    
        return firstSymbol.getPayout(); // Combinaison gagnante, retourner le gain
    }
    
    private int checkDiagonals(int bet) {
        int winnings = 0;
    
        // Vérification de la diagonale principale
        Symbol mainDiagonalSymbol = matrix[0][0];
        if (mainDiagonalSymbol.equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][2])) {
            winnings += mainDiagonalSymbol.getPayout();
        }
    
        // Vérification de l'autre diagonale
        Symbol otherDiagonalSymbol = matrix[0][2];
        if (otherDiagonalSymbol.equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][0])) {
            winnings += otherDiagonalSymbol.getPayout();
        }
    
        return winnings;
    }

    public Symbol[][] getMatrix() {
        return matrix;
    }

}

