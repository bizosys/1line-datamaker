/**
 * LGPL License
 * All copy rights reserved to Bizosys. 
 * Don't use it without prior permission
 */

package com.bizosys.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomWeightedGenerator {
	
	public static void main(String[] args) {
		RandomWeightedGenerator rg = new RandomWeightedGenerator();
		Map<Float, Double> weights = new HashMap<Float, Double>();
		weights.put(1.23f, .1);
		weights.put(2.11f, .1);
		weights.put(3.89f, .8);
		
		for ( int i=0; i<100; i++) {
			Float d = rg.generate(weights);
			System.out.print(d+"|");
		}
	}
	
	Random randomizer = new Random();
	public <E> E generate(Map<E, Double> weights) {
	    E result = null;
	    double bestValue = Double.MAX_VALUE;

	    for (E element : weights.keySet()) {
	        double value = -Math.log(randomizer.nextDouble()) / weights.get(element);

	        if (value < bestValue) {
	            bestValue = value;
	            result = element;
	        }
	    }
	    return result;
	}
}
