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
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class Normal {
	
	public	double average, sigma, kurt, skew, min, max;
	
	public Normal(){
		min = Long.MAX_VALUE;
		max = Long.MIN_VALUE;
		kurt= skew = sigma = average = 0;
		
	}
	public void sample(Collection<Double> inputs){
		
		int count=0;
		for (double i  : inputs) {
			if (i < min) min = i;
			if (i > max) max = i;
			count++;
			average=average+i;
		}
		average = average / count;
		
		// calculate stdev
		for (double i  : inputs) {
			sigma=sigma+(i-average)*(i-average);
		}
		sigma = sigma / (count) ; 
		sigma = Math.pow(sigma, 0.5);
		
		// calculate skew
		for (double i  : inputs) {
			skew =skew+Math.pow((i-average)/sigma,3);
			kurt = skew+Math.pow((i-average)/sigma,4);
		}
		skew = skew / count;
		kurt = kurt / count;
	}

	
	public List<Double> generate(int size){
		List<Double> numbers = new ArrayList<Double>();
		cern.jet.random.engine.RandomEngine engine = new cern.jet.random.engine.MersenneTwister(); 

		// distribution goes here
		cern.jet.random.AbstractDistribution dist = new cern.jet.random.Normal(average,sigma,engine);

		for(int i =0; i< size; i++){
			numbers.add(dist.nextDouble());
		}
		return  numbers;
	}

}