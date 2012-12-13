/*
* Copyright 2010 Bizosys Technologies Limited
*
* Licensed to the Bizosys Technologies Limited (Bizosys) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The Bizosys licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.bizosys.sampling;

/**
 * @author bhaskar
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
	
	public static List<Integer> cookIntegers(List<Integer> inputSample, int size) {

		Normal normal = new Normal();
		Collection<Double> doubles = new ArrayList<Double>(inputSample.size());
		for (Integer val : inputSample) doubles.add(new Double(val));
		normal.sample(doubles);
		
		List<Integer> outSample = new ArrayList<Integer>(); 
		for (Double db : normal.generate(size)) {
			outSample.add(db.intValue());
		}
		return outSample;
	}
	
	public static List<Float> cookFloats(List<Float> inputSample, int size) {

		Normal normal = new Normal();
		Collection<Double> doubles = new ArrayList<Double>(inputSample.size());
		for (Float val : inputSample) doubles.add(new Double(val));
		normal.sample(doubles);
		
		List<Float> outSample = new ArrayList<Float>(size); 
		for (Double db : normal.generate(size)) {
			outSample.add(db.floatValue());
		}
		return outSample;
	}

	public static List<Long> cookLong(List<Long> inputSample, int size) {

		Normal normal = new Normal();
		Collection<Double> doubles = new ArrayList<Double>(inputSample.size());
		for (Long val : inputSample) doubles.add(new Double(val));
		normal.sample(doubles);
		
		List<Long> outSample = new ArrayList<Long>(size); 
		for (Double db : normal.generate(size)) {
			outSample.add(db.longValue());
		}
		return outSample;
	}

	public static List<Double> cookDouble(List<Double> inputSample, int size) {

		Normal normal = new Normal();
		normal.sample(inputSample);
		return normal.generate(size);
	}
	
	public static List<Short> cookShort(List<Short> inputSample, int size) {

		Normal normal = new Normal();
		Collection<Double> doubles = new ArrayList<Double>(inputSample.size());
		for (Short val : inputSample) doubles.add(new Double(val));
		normal.sample(doubles);
		
		List<Short> outSample = new ArrayList<Short>(); 
		for (Double db : normal.generate(size)) {
			outSample.add(db.shortValue());
		}
		return outSample;
	}
	
	public static List<Byte> cookByte(List<Byte> inputSample, int size) {

		Normal normal = new Normal();
		Collection<Double> doubles = new ArrayList<Double>(inputSample.size());
		for (Byte val : inputSample) doubles.add(new Double(val));
		normal.sample(doubles);
		
		List<Byte> outSample = new ArrayList<Byte>(); 
		for (Double db : normal.generate(size)) {
			outSample.add(db.byteValue());
		}
		return outSample;
	}

	public static List<Boolean> cookBoolean(List<Boolean> inputSample, int size) {

		Normal normal = new Normal();
		Collection<Double> doubles = new ArrayList<Double>(inputSample.size());
		for (Boolean val : inputSample) {
			int a = (val) ? 1 : 0;
			doubles.add(new Double(a));
		}
		normal.sample(doubles);
		
		List<Boolean> outSample = new ArrayList<Boolean>(); 
		for (Double db : normal.generate(size)) {
			outSample.add( (db < 0.5) );
		}
		return outSample;
	}

	public static void main(String[] args) {
		 
		 List<Byte> input = new ArrayList<Byte>();
		 input.add( (byte) 23);
		 input.add( (byte) 11);
		 input.add((byte) 67);
		 input.add((byte) 128);
		 
		 System.out.println(Main.cookByte(input, 10));
	 }
}
