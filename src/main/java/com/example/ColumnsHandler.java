package com.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ColumnsHandler {
    private List<List<Symbol>> columns;

    public ColumnsHandler(List<List<Symbol>> columns) {
        this.columns = columns;
        randomizePositions();
    }

    public void refreshColumns() {
        // Mettez à jour les colonnes avec de nouvelles positions aléatoires
        Collections.shuffle(columns);
    }

    private void randomizePositions() {
        Random random = new Random();

        for (List<Symbol> column : columns) {
            int randomPosition = random.nextInt(column.size());
            Collections.rotate(column, randomPosition);
        }
    }

    public List<List<Symbol>> getColumns() {
        return columns;
    }

    // Chargement des colonnes depuis le fichier JSON
    public static ColumnsHandler loadColumns() {
        try (InputStreamReader reader = new InputStreamReader(ColumnsHandler.class.getResourceAsStream("/colonnes.json"))) {
            Type listType = new TypeToken<List<List<String>>>() {}.getType();
            Gson gson = new Gson();
            List<List<String>> columnsAsString = gson.fromJson(reader, listType);

            // Convertir les symboles de type String en objets Symbol
            List<List<Symbol>> columns = convertToSymbolList(columnsAsString);
            
            return new ColumnsHandler(columns);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<List<Symbol>> convertToSymbolList(List<List<String>> columnsAsString) {
        List<List<Symbol>> columns = new ArrayList<>();

        for (List<String> column : columnsAsString) {
            List<Symbol> symbolColumn = new ArrayList<>();
            for (String symbolString : column) {
                Symbol symbol = createSymbol(symbolString);
                symbolColumn.add(symbol);
            }
            columns.add(symbolColumn);
        }

        return columns;
    }

    private static Symbol createSymbol(String symbolString) {
        // Logique pour convertir une chaîne de caractères (symbolString) en objet Symbol
        // Vous pouvez ajouter des règles spécifiques ici pour chaque symbole du jeu.

        switch (symbolString) {
            case "7":
                return new Symbol("7", 300);
            case "C":
                return new Symbol("C", 100);
            case "BAR":
                return new Symbol("BAR", 15);
            case "T":
                return new Symbol("T", 15);
            case "P":
                return new Symbol("P", 15);
            case "R":
                return new Symbol("R", 8);
            default:
                throw new IllegalArgumentException("Symbole inconnu : " + symbolString);
        }
    }
}
