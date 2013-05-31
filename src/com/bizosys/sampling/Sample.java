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
import java.util.List;
import cern.colt.matrix.*;

public class Sample {

	public int columns, rows;
	public DoubleMatrix1D means;
	public DoubleMatrix2D matrix;

	public Sample() {
		columns = rows = 0;
	}

	public Sample(DoubleMatrix2D data) {

		matrix = data;
		columns = data.columns();
		rows = data.rows();
		means = DoubleFactory1D.dense.make(columns);
		double value = 0, mean = 0;
		for (int j = 0; j < matrix.columns(); j++) {
			value = 0;
			for (int i = 0; i < matrix.rows(); i++) {
				value = matrix.getQuick(i, j);
				mean = mean + value;
			}
			mean = mean / rows;
			means.set(j, mean);
			mean = 0;
		}

	}

	public Sample(List<List<Double>> data) {
		int i, j;
		double value;
		i = j = 0;
		columns = data.size();
		rows = data.get(j).size();
		double mean = 0;
		matrix = cern.colt.matrix.DoubleFactory2D.dense.make(rows, columns);
		means = cern.colt.matrix.DoubleFactory1D.dense.make(columns);
		for (j = 0; j < data.size(); j++) {
			value = 0;
			for (i = 0; i < data.get(j).size(); i++) {
				value = data.get(j).get(i);
				matrix.set(i, j, value);
				mean = mean + value;
			}
			mean = mean / rows;
			means.set(j, mean);
			mean = 0;
		}
	}
	
	public static void main(String[] args) {
		List<List<Double>> data = new ArrayList<List<Double>>();
		List<Double> cells11 = new ArrayList<Double>();
		List<Double> cells21 = new ArrayList<Double>();
		List<Double> cells31 = new ArrayList<Double>();
		List<Double> cells41 = new ArrayList<Double>();
		
		cells11.add(1d);cells11.add(10d);
		cells21.add(2d);cells21.add(40d);
		cells31.add(3d);cells31.add(50d);
		cells41.add(4d);cells41.add(80d);
		
		data.add(cells11);
		data.add(cells21);
		data.add(cells31);
		data.add(cells41);
		
		Sample sample = new Sample(data);
		double[][] o = sample.matrix.toArray();
		for (double[] ds : o) {
			for (double x : ds) System.out.println(x );
		}
		System.out.println("===");
	}

}