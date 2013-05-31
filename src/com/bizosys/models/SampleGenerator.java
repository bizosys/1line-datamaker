/**
 * LGPL License
 * All copy rights reserved to Bizosys. 
 * Don't use it without prior permission
 */
package com.bizosys.models;

import java.util.ArrayList;
import java.util.List;

import com.bizosys.sampling.Normal;

public class SampleGenerator<E> {
	
	public static void main(String[] args) {
		SampleGenerator<Integer> rg = new SampleGenerator<Integer>();
		List<Long> sample = new ArrayList<Long>();
		sample.add(112l);
		sample.add(114l);
		sample.add(132l);
		
		int i=0;
		for (Long val : rg.generate(sample, 100, 200, 18000)) {
			System.out.println(++i + "\t" + val);
		}
	}
	
	
	public List<Double> generate (List<Double> inputSample, double min, double max, int size) {

		Normal normal = new Normal();
		normal.sample(inputSample);

		List<Double> outSample = new ArrayList<Double>();
		
		int curSize= 0;
		while (curSize < size) {
			for (Double db : normal.generate(size)) {
				int ran =  db.intValue();
				if ( ran >= min && ran <= max ) {
					outSample.add( db );
					curSize++;
					if ( curSize == size) break;
				}
			}
			if ( curSize == 0 ) break;
		}
		return outSample;
	}
	
	public List<Integer> generate (List<Integer> inputSample, int min, int max, int size) {

		Normal normal = new Normal();
		List<Double> inputSampleD = new ArrayList<Double>();
		for (Integer sampleVal : inputSample) {
			inputSampleD.add(sampleVal.doubleValue());
		}
		normal.sample(inputSampleD);

		List<Integer> outSample = new ArrayList<Integer>();
		
		int curSize= 0;
		while (curSize < size) {
			for (Double db : normal.generate(size)) {
				int ran =  db.intValue();
				if ( ran >= min && ran <= max ) {
					outSample.add( db.intValue() );
					curSize++;
					if ( curSize == size) break;
				}
			}
			if ( curSize == 0 ) break;
		}
		return outSample;
	}
	
	public List<Float> generate (List<Float> inputSample, float min, float max, int size) {

		Normal normal = new Normal();
		List<Double> inputSampleD = new ArrayList<Double>();
		for (Float sampleVal : inputSample) {
			inputSampleD.add(sampleVal.doubleValue());
		}
		normal.sample(inputSampleD);

		List<Float> outSample = new ArrayList<Float>();
		
		int curSize= 0;
		while (curSize < size) {
			for (Double db : normal.generate(size)) {
				int ran =  db.intValue();
				if ( ran >= min && ran <= max ) {
					outSample.add( db.floatValue() );
					curSize++;
					if ( curSize == size) break;
				}
			}
			if ( curSize == 0 ) break;
		}
		return outSample;
	}	
	
	public List<Long> generate (List<Long> inputSample, long min, long max, int size) {

		Normal normal = new Normal();
		List<Double> inputSampleD = new ArrayList<Double>();
		for (Long sampleVal : inputSample) {
			inputSampleD.add(sampleVal.doubleValue());
		}
		normal.sample(inputSampleD);

		List<Long> outSample = new ArrayList<Long>();
		
		int curSize= 0;
		while (curSize < size) {
			for (Double db : normal.generate(size)) {
				int ran =  db.intValue();
				if ( ran >= min && ran <= max ) {
					outSample.add( db.longValue() );
					curSize++;
					if ( curSize == size) break;
				}
			}
			if ( curSize == 0 ) break;
		}
		return outSample;
	}		
	
}
