/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatosTest;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java Main <automata_file> <test_file> <output_file>");
            System.exit(1);
        }

        String automataFilePath = args[0];
        String testFilePath = args[1];
        String outputFilePath = args[2];

        try {
            JSONObject automataJson = new JSONObject(Files.readString(Paths.get(automataFilePath)));
            List<Transition> transitions = new ArrayList<>();
            JSONArray transitionsJson = automataJson.getJSONArray("transitions");
            for (int i = 0; i < transitionsJson.length(); i++) {
                JSONObject transitionJson = transitionsJson.getJSONObject(i);
                transitions.add(new Transition(
                        transitionJson.getInt("from"),
                        transitionJson.optString("read", null),
                        transitionJson.getInt("to")));
            }

            Automata automata = new Automata(
                    automataJson.getInt("initial"),
                    jsonArrayToList(automataJson.getJSONArray("final")),
                    transitions);

            List<String[]> testCases = getInputList(Files.readString(Paths.get(testFilePath)));

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
                for (String[] testCase : testCases) {
                    String input = testCase[0];
                    String expected = testCase[1];
                    long startTime = System.currentTimeMillis();
                    boolean result = automata.compute(input);
                    long endTime = System.currentTimeMillis();
                    writer.write(String.format("%s;%s;%d;%.3f\n",
                            input, expected, result ? 1 : 0, (endTime - startTime) / 1000.0));
                }
            }
        } catch (IOException | JSONException e) {
        }
    }

    private static List<Integer> jsonArrayToList(JSONArray array) throws JSONException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getInt(i));
        }
        return list;
    }

    private static List<String[]> getInputList(String input) {
        List<String[]> inputList = new ArrayList<>();
        String[] lines = input.strip().replace(";", " ").split("\n");
        for (String line : lines) {
            inputList.add(line.split(" "));
        }
        return inputList;
    }
}

