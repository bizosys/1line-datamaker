/**
 * LGPL License
 * All copy rights reserved to Bizosys. 
 * Don't use it without prior permission
 */

package com.bizosys.models;

import java.util.Random;

public class RandomGenerator {
	
	public static void main(String[] args) {
		RandomGenerator rg = new RandomGenerator();
		while ( true ) {
			double next = rg.generate(-11d, 20d); 
			double diff = next - 12.0F;
			diff = diff * diff;
			
			if ( diff < 0.001) {
				System.out.println( next + "\t\t\t" + diff);
				break;
			}
		}
	}
	
	double minVal = Long.MIN_VALUE;
	double maxVal = Long.MAX_VALUE;
	Random randomizer = new Random();

	public double generate() {
		return randomizer.nextDouble();
	}
	
	public double  generate(double minVal, double maxVal) {
		float nextVal = randomizer.nextFloat();
		return minVal + (nextVal * (maxVal - minVal) );
	}

	public int generate(int minVal, int maxVal) {
		float nextVal = randomizer.nextFloat();
		return (int) ( minVal + (nextVal * (maxVal - minVal) ));
	}

	public float generate(float minVal, float maxVal) {
		float nextVal = randomizer.nextFloat();
		return (float) ( minVal + (nextVal * (maxVal - minVal) ));
	}

	public long generate(long minVal, long maxVal) {
		float nextVal = randomizer.nextFloat();
		return (long) ( minVal + (nextVal * (maxVal - minVal) ));
	}

	public byte generate(byte minVal, byte maxVal) {
		float nextVal = randomizer.nextFloat();
		return (byte) ( minVal + (nextVal * (maxVal - minVal) ));
	}

	public short generate(short minVal, short maxVal) {
		float nextVal = randomizer.nextFloat();
		return (short) ( minVal + (nextVal * (maxVal - minVal) ));
	}

}
