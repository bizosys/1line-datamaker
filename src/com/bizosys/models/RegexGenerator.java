/**
 * LGPL License
 * All copy rights reserved to Bizosys. 
 * Don't use it without prior permission
 */

package com.bizosys.models;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import dk.brics.automaton.RegExp;

import java.util.Random;
import java.util.List;

public class RegexGenerator {
	
	public static void main(String[] args) {
		String regex = "[A-Z]{4}";
		RegexGenerator generator = new RegexGenerator(regex);
		
		for ( int i=0; i<1000000; i++) {
			String result = generator.generate();
			System.out.println(result);
		}
	}

	private final Automaton automaton;
    private final Random random;

    public RegexGenerator(String regex, Random random) {
        assert regex != null;
        assert random != null;
        this.automaton = new RegExp(regex).toAutomaton();
        this.random = random;
    }

    public RegexGenerator(String regex) {
        this(regex, new Random());
    }

    public String generate() {
        StringBuilder builder = new StringBuilder();
        generate(builder, automaton.getInitialState());
        return builder.toString();
    }

    private void generate(StringBuilder builder, State state) {
        List<Transition> transitions = state.getSortedTransitions(true);
        if (transitions.size() == 0) {
            assert state.isAccept();
            return;
        }
        int nroptions = state.isAccept() ? transitions.size() : transitions.size() - 1;
        int option = getRandomInt(0, nroptions, random);
        if (state.isAccept() && option == 0) {          // 0 is considered stop
            return;
        }
        // Moving on to next transition
        Transition transition = transitions.get(option - (state.isAccept() ? 1 : 0));
        appendChoice(builder, transition);
        generate(builder, transition.getDest());
    }

    private void appendChoice(StringBuilder builder, Transition transition) {
        char c = (char) getRandomInt(transition.getMin(), transition.getMax(), random);
        builder.append(c);
    }
    
    public final static int getRandomInt(int min, int max, Random random) {
        int dif = max - min;
        float number = random.nextFloat();              // 0 <= number < 1
        return min + Math.round(number * dif);
    }
}