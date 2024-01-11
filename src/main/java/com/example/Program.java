package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Program {
    private static final String STATS_JSON_PATH = "/statistiques.json";
    public static void main(String[] args) {
        System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
        System.out.println("Bienvenue au Casino de Céladopole !");
        System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`\n");

        Statistiques gameStats = loadGameStats();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                playRound(scanner, gameStats);

                System.out.println("\nVoulez-vous continuer ? (Oui/Non)");
                String choice = scanner.nextLine().toLowerCase();
                if (!choice.equals("oui")) {
                    saveGameStats(gameStats);
                    break;
                }
            }
        }
    }

    private static void playRound(Scanner scanner, Statistiques gameStats) {
        System.out.println("\nJetons possédés : " + gameStats.getCoins());
    
        int bet = getBet(scanner);
        if (bet == 0) {
            System.out.println("Au revoir !");
            saveGameStats(gameStats);
            System.exit(0);
        }
    
        System.out.println("Lancement des rouleaux...");
        ColumnsHandler columnsHandler = ColumnsHandler.loadColumns();
        Machine machine = new Machine(columnsHandler.getColumns());
        
    
        displayMachineState(machine);
    
        int winnings = machine.checkGains(bet);
        System.out.println(winnings);
        gameStats.updateGameStats(bet, winnings);
    
        System.out.println("\nRésultat :");
        displayMachineState(machine);
        System.out.println("\nGains : " + winnings);
        System.out.println("Jetons possédés : " + gameStats.getCoins());
    
        // Mise à jour des colonnes après chaque tour
        columnsHandler.refreshColumns();
    }

    private static int getBet(Scanner scanner) {
        while (true) {
            System.out.print("\nCombien de jetons ? (1, 2 ou 3) : ");
            String betInput = scanner.nextLine();

            if (StringUtils.isNumeric(betInput)) {
                int bet = Integer.parseInt(betInput);
                if (bet >= 1 && bet <= 3) {
                    return bet;
                }
            }

            System.out.println("Veuillez saisir un nombre valide de jetons (1, 2 ou 3).");
        }
    }

    private static void displayMachineState(Machine machine) {
        Symbol[][] matrix = machine.getMatrix();

        System.out.println("\n-----------------------------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("(" + matrix[i][j].getName() + ") | ");
            }
            System.out.println();
        }
    }

    private static Statistiques loadGameStats() {
        try (InputStreamReader reader = new InputStreamReader(Program.class.getResourceAsStream(STATS_JSON_PATH))) {
            if (reader != null) {
                Gson gson = new Gson();
                return gson.fromJson(reader, Statistiques.class);
            } else {
                System.out.println("Le fichier statistiques.json n'a pas été trouvé.");
                return new Statistiques(50, 0, 0, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void saveGameStats(Statistiques gameStats) {
        try {
            URL resource = Program.class.getResource(STATS_JSON_PATH);
            if (resource != null) {
                File file = new File(resource.toURI());
                try (FileWriter fileWriter = new FileWriter(file)) {
                    Gson gson = new Gson();
                    // Convertir les statistiques en format JSON et sauvegarder dans le fichier
                    String statsJson = gameStats.toJson();
                    fileWriter.write(statsJson);
                }
            } else {
                System.out.println("Le fichier statistiques.json n'a pas été trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
