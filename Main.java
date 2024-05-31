
package automatosTest;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) { //  Verifica se o número de argumentos é menor que 3
            System.err.println("Use: java Main <automata_file> <test_file> <output_file>");
            System.exit(1);
        }

         // Obtém os caminhos dos arquivos a partir dos argumentos
        String automataFilePath = args[0];
        String testFilePath = args[1];
        String outputFilePath = args[2];

        try {  // Lê e parseia o arquivo JSON do autômato
            JSONObject automataJson = new JSONObject(Files.readString(Paths.get(automataFilePath)));
            List<Transition> transitions = new ArrayList<>(); // Cria lista para armazenar transições
            JSONArray transitionsJson = automataJson.getJSONArray("transitions"); // Obtém o array de transições do JSON
            for (int i = 0; i < transitionsJson.length(); i++) { // Itera sobre o array de transições
                JSONObject transitionJson = transitionsJson.getJSONObject(i); // Obtém o objeto JSON de transição
                 // Cria uma nova transição e adiciona à lista de transições
                transitions.add(new Transition(
                        transitionJson.getInt("from"),
                        transitionJson.optString("read", null),
                        transitionJson.getInt("to")));
            }

            // Cria o autômato com os dados lidos do JSON
            Automata automata = new Automata(
                    automataJson.getInt("initial"),
                    jsonArrayToList(automataJson.getJSONArray("final")),
                    transitions);

             // Lê os casos de teste do arquivo de entrada
            List<String[]> testCases = getInputList(Files.readString(Paths.get(testFilePath)));

             // Cria um BufferedWriter para escrever os resultados no arquivo de saída
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
                for (String[] testCase : testCases) { // Itera sobre os casos de test
                    String input = testCase[0]; // Entrada do caso de teste
                    String expected = testCase[1]; // Resultado esperado do caso de teste
                    long startTime = System.currentTimeMillis(); // Registra o tempo de início
                    boolean result = automata.compute(input); // Computa o resultado do autômato para a entrada
                    long endTime = System.currentTimeMillis(); // Registra o tempo de término
                    // Escreve o resultado no arquivo de saída no formato especificado
                    writer.write(String.format("%s;%s;%d;%.3f\n",
                            input, expected, result ? 1 : 0, (endTime - startTime) / 1000.0));
                }
            }
        } catch (IOException | JSONException e) { // Captura exceções de IO e JSON
        }
    }

     // Método auxiliar que converte um JSONArray em uma lista de inteiros
    private static List<Integer> jsonArrayToList(JSONArray array) throws JSONException {
        List<Integer> list = new ArrayList<>(); // Cria uma nova lista de inteiros
        for (int i = 0; i < array.length(); i++) { // Itera sobre o JSONArray
            list.add(array.getInt(i)); // Adiciona cada elemento do JSONArray à lista
        }
        return list;
    }

     // Método auxiliar que converte a string de entrada em uma lista de arrays de strings
    private static List<String[]> getInputList(String input) {
        List<String[]> inputList = new ArrayList<>(); // Cria uma nova lista de arrays de strings
        String[] lines = input.strip().replace(";", " ").split("\n"); // Remove espaços em branco, substitui ";" por espaço e divide em linhas
        for (String line : lines) {  // Itera sobre as linhas
            inputList.add(line.split(" ")); // Adiciona cada linha como um array de strings à lista
        }
        return inputList;
    }
}

