/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatosTest;

import java.util.*;

/**
 *
 * @author Ozeias
 */
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean compute(String input) {
        return computeWithTransitionsDict(input, initial);
    }

    private boolean computeWithTransitionsDict(String input, int currentNode) {
        if (input.isEmpty()) {
            return finalStates.contains(currentNode);
        }

        String key = currentNode + input.substring(0, 1);
        String voidKey = currentNode + "None";
        List<Integer> toGoList = new ArrayList<>();
        if (transitionsDict.containsKey(key)) {
            toGoList.addAll(transitionsDict.get(key));
        }

        if (transitionsDict.containsKey(voidKey)) {
            toGoList.addAll(transitionsDict.get(voidKey));
        }

        if (toGoList.isEmpty()) {
            return false;
        }

        for (int node : toGoList) {
            if (computeWithTransitionsDict(input.substring(1), node)) {
                return true;
            }
        }

        return false;
    }

    private Map<String, List<Integer>> convertTransitionsToDict() {
        Map<String, List<Integer>> transitionsDict = new HashMap<>();
        for (Transition transition : transitions) {
            String key = transition.getFrom() + transition.getRead();
            transitionsDict.putIfAbsent(key, new ArrayList<>());
            transitionsDict.get(key).add(transition.getTo());
        }

        return transitionsDict;
    }
    
    @Override
    public String toString(){
        StringBuilder transitionsStr = new StringBuilder();
        for(Transition transition : transitions){
            transitionsStr.append(transition).append("\\n");
        }
        
        return String.format("Initial: %d, Finals: %s,[\\n%s\\n]", initial, finalStates, transitionsStr);
    }
}
