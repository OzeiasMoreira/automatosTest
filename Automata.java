
package automatosTest;

import java.util.*;

public class Automata {

    private int initial;
    private List<Integer> finalStates;
    private List<Transition> transitions;
    private Map<String, List<Integer>> transitionsDict;

    public Automata(int initial, List<Integer> finalStates, List<Transition> transitions, Map<String, List<Integer>> transitionsDict) {
        this.initial = initial;
        this.finalStates = finalStates;
        this.transitions = transitions;
        this.transitionsDict = convertTransitionsToDict();
    }

    Automata(int aInt, List<Integer> jsonArrayToList, List<Transition> transitions) {
        throw new UnsupportedOperationException("Not support"); // Exceção lançada
    }

    // Metodo p/ inicializar a computação de automato com a entrada fornecida 
    public boolean compute(String input) {
        return computeWithTransitionsDict(input, initial);
    }

    // Metodo que processa a entrada e verifica sé é aceita pelo automato
    private boolean computeWithTransitionsDict(String input, int currentNode) {
        if (input.isEmpty()) {
            return finalStates.contains(currentNode); // Verifica se o estado atual é um estado final
        }

        String key = currentNode + input.substring(0, 1); // Cria chave para a transição com o próximo símbolo de entrada
        String voidKey = currentNode + "None"; // Cria chave para a transição vazia (None)
        List<Integer> toGoList = new ArrayList<>(); // Lista para armazenar os próximos estados possíveis
        // Se a transição com o símbolo específico existe, adiciona os estados de destino à lista
        if (transitionsDict.containsKey(key)) {
            toGoList.addAll(transitionsDict.get(key));
        }

        // Se a transição vazia existe, adiciona os estados de destino à lista
        if (transitionsDict.containsKey(voidKey)) {
            toGoList.addAll(transitionsDict.get(voidKey));
        }

        // Se não houver próximos estados possíveis, retorna false
        if (toGoList.isEmpty()) {
            return false;
        }

         // P/ cada estado de destino possível, chama recursivamente com o restante da entrada
        for (int node : toGoList) {
            if (computeWithTransitionsDict(input.substring(1), node)) {  // Se qualquer chamada recursiva retornar true
                return true;
            }
        }

        return false; // Se nenhuma chamada recursiva retornar true, retorna false
    }

    // Método p/ converter a lista de transições em um mapa
    private Map<String, List<Integer>> convertTransitionsToDict() {
        Map<String, List<Integer>> transitionsDict = new HashMap<>();
        // P/ cada transição na lista de transições
        for (Transition transition : transitions) {
            String key = transition.getFrom() + transition.getRead(); // Cria chave com estado de origem e símbolo de leitura
            transitionsDict.putIfAbsent(key, new ArrayList<>());  // Adiciona a chave ao mapa se ainda não existir
            transitionsDict.get(key).add(transition.getTo()); // Adiciona o estado de destino à lista no mapa
        
        }

        return transitionsDict;
    }
    
    // Método p/ fornecer uma representação em string do autômato
    @Override
    public String toString(){
        StringBuilder transitionsStr = new StringBuilder();
        for(Transition transition : transitions){
            transitionsStr.append(transition).append("\\n"); // Adiciona a transição à string, seguida de uma nova linha
        }
        
         // Retorna a string formatada com estado inicial, estados finais e transições
        return String.format("Initial: %d, Finals: %s,[\\n%s\\n]", initial, finalStates, transitionsStr);
    }
}
