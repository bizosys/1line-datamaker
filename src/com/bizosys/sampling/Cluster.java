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

import java.util.List;
import java.util.ArrayList;

public class Cluster {

	public List<Double> data;
	public List<List<Integer>> rowIndices;
	public double min, max, ftest;
	public int finalk;
	public double mean, variance;
	private int k;
	private List<Double> range;
	double[] means;
	public List<List<Double>> clusters;

	public Cluster(){
	}
	
	public Cluster(List<Double> data, double min, double max){
		double sum = 0;
		this.data = data;
		this.min = min;
		this.max = max;
		for ( double d : data){
			sum = sum + d;
		}
		mean = sum / data.size();
		sum = 0;
		for ( double d : data){
			sum = sum + (d - mean)*(d- mean);
		}
		variance = sum/data.size(); 
		k = 2;
		range = new ArrayList<Double>();
		clusters = new ArrayList<List<Double>>();
		rowIndices = new ArrayList<List<Integer>>();
	}
	
	public void formClusters(){
		ftest = 0;
		do
		{
			doCluster();
			ftest = fTest();
			k++;
		} while ( ftest < 0.8 && k < Math.sqrt(data.size()/2));
		
	}
	
	public void doCluster(){
		// chose initial centres one from each part
		// then do k-means clustering
		range.clear();
		range.add(min);
		means = new double[k];
		
		clusters.clear();
				
		for ( int i =0; i<k; i++) 
			{
			clusters.add(new ArrayList<Double>());
			rowIndices.add(new ArrayList<Integer>());
			range.add(min+(i+1)*(max-min)/k);
			}

		int parts = k;
		// chose initial centres as midpoints of ranges.
		for ( int i = 0; i < k ; i ++) {
			means[i]=(range.get(i)+range.get(i+1))/2;
		}
		
		int setindex,i;
		i = 0;
		for ( double d : data){
			setindex = getSet(d);
			clusters.get(setindex).add(d);
			rowIndices.get(setindex).add(i);
			updateMean(setindex,d);
			i++;
		}
			
	}
	
	private int getSet(double d){
		int i;
		int index = -1;
		double dist = 0;
		double mindist = Long.MAX_VALUE;
		for( i =0; i< means.length; i++){
			dist = (d- means[i])* ( d- means[i]);
			if ( dist < mindist) {
				mindist = dist;
				index = i;
			}
		}
		return index;
	}
	
	private void updateMean(int index, double value){
		List<Double> cluster;
		
		cluster = clusters.get(index);
		means[index] = (means[index]*(cluster.size()-1)+value)/ (cluster.size());
	}
	
	private double fTest()
	{
		double eVar =0;
		// return between group variance / total variance
		for( int i =0;i < clusters.size(); i++)
		{
			eVar =eVar + (means[i]-mean)*(means[i]-mean)*clusters.get(i).size();
		}
		eVar = eVar / (k - 1);
		return eVar/ (variance*data.size());
	}
	
}