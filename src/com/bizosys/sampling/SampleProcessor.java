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

import cern.colt.matrix.*;
import cern.colt.matrix.linalg.*;
import cern.colt.matrix.doublealgo.*;
import cern.jet.random.engine.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SampleProcessor {

	public DoubleMatrix2D data, meanAdjustedData, zData, newData;
	public DoubleMatrix2D covariance, eigenvector, eigenvectorT;
	public DoubleMatrix1D eigenValues, means;
	public DoubleMatrix1D sigma;
	public DoubleMatrix1D yMean, ySigma;
	private Sample sample;
	
	public SampleProcessor(){
		
	}
	public SampleProcessor(Sample data){
		this.data = data.matrix;
		this.means = data.means;
		sample = data;
	}
	
	public void calculateMeanAdjusted(){
		DoubleMatrix1D h = DoubleFactory1D.dense.make(data.rows());
		h.assign(1.0);
		// Xm = X - hMu h = rows * 1 matrix and Mu = 1 * cols matrix;
		// or go through each element and subtract the mean.
		meanAdjustedData = data.copy();
		int i,j=0;
		for ( j= 0; j<sample.columns; j++ ){
			for (i =0; i< sample.rows;i++){
				meanAdjustedData.setQuick(i, j, meanAdjustedData.getQuick(i, j)- means.get(j));
			}
		}
	}
	
	public void calculateEigen(){
		covariance = Statistic.covariance(meanAdjustedData);
		this.sigma = DoubleFactory1D.dense.make(covariance.columns());
		for(int i =0; i< sigma.size();i++)
		{
			sigma.set(i, Math.sqrt(covariance.getQuick(i, i)));
		}
		zData = meanAdjustedData.copy();
		for(int j=0;j< sigma.size(); j++)
			for(int i =0; i<zData.rows();i++)
			{
				zData.setQuick(i, j, zData.getQuick(i,j)/sigma.get(j));
			}
		//covariance = Statistic.covariance(zData);
		
		EigenvalueDecomposition evg = new EigenvalueDecomposition(covariance);
		Algebra ag = new Algebra();
		this.eigenvector = evg.getV();
		this.eigenValues = evg.getRealEigenvalues();
		// sort eigenvector as per eigenvalues.
		double[] evtemp = eigenValues.toArray();
		int[] work = new int[evtemp.length];
		for ( int i =0; i< eigenValues.size(); i++) evtemp[i] = -evtemp[i];
			
		Arrays.sort(evtemp);
		int[] index = new int[evtemp.length];
		for ( int i =0; i< eigenValues.size(); i++)
		for ( int j=0;j< eigenValues.size();j++){
			if ( eigenValues.get(i)== -evtemp[j]) {
				index[i]= j;
				break;
			}
		}
		ag.permuteColumns(eigenvector, index,work);
		this.eigenvectorT = ag.transpose(eigenvector);
		
		DoubleMatrix2D y,x;
		
		y = ag.mult(meanAdjustedData, eigenvector);
		
		double mean =0, variance = 0;
		
		yMean = DoubleFactory1D.dense.make(y.columns());
		ySigma = yMean.copy();
		for ( int j =0; j < y.columns(); j++)
		{
			for (int i= 0 ;i< y.rows(); i++)
			{
				mean = y.getQuick(i, j) + mean;
				
			}
			mean = mean / y.rows();
			yMean.setQuick(j, mean);
			mean = 0;
		}	
		for ( int j =0; j < y.columns(); j++)
		{
			mean = yMean.get(j);
			for (int i= 0 ;i< y.rows(); i++)
			{
				variance = Math.pow((y.getQuick(i, j) - mean ),2);
				
			}
			variance = variance / y.rows();
			variance = Math.sqrt(variance);
			ySigma.setQuick(j, variance);
			variance = 0;
		}	

		x = ag.mult(y, eigenvectorT);
	}
	
	
	public void generateMoreData(int size){
		List<RandomEngine > engines = new ArrayList<RandomEngine>();
		RandomEngine eng;
		DoubleMatrix2D J = null;
		double[] numbers;
		numbers = new double[size];
		eng = null;
		eng = new cern.jet.random.engine.MersenneTwister(); 
		
		for(int i =0; i<sample.columns; i++)
			{
			cern.jet.random.AbstractDistribution dist = new cern.jet.random.Normal(yMean.get(i),ySigma.get(i),eng);
			
			for (int j=0;j< size;j++){
				numbers[j]= dist.nextDouble();
			}
			if ( i ==0){
			 J	= DoubleFactory2D.dense.make(numbers, numbers.length);
			}
			else{
				J = DoubleFactory2D.dense.appendColumns(J, DoubleFactory2D.dense.make(numbers, numbers.length));
			}
			
			}
			
		Algebra ag = new Algebra();
		newData = ag.mult(J, eigenvectorT);
		// Add means back to get original data...
		// coding to be done.
		double mean =0;
		for(int j =0; j<newData.columns(); j++)
		{
		mean = sample.means.get(j);
		for (int i=0;i< newData.rows();i++){
			newData.setQuick(i, j, newData.getQuick(i, j)+mean);
		}
		
		}
	}
}