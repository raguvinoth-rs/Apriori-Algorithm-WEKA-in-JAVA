/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;


import helper.DataHelper;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;

public class Main {
	public static void main(String args[]) {
		/**
		String filename = args[0];
		Double minSupport = Double.parseDouble(args[1]);
		Double minConfidence = Double.parseDouble(args[2]);
		**/
		/**
		 * Test code. Remove and uncomment above
		 */
		
		//String filename = "temp.csv";
                //BufferedReader reader = new BufferedReader(new FileReader("C://Users//Raguvinoth//Desktop//weka1//5339_nominal.arff"));
                System.out.println("Apriori Association Algorithm:");
                String filename = "input2.csv";
		Double minSupport = 0.001;
		Double minConfidence = 0.1;
		DataHelper.init(minSupport, minConfidence);
		Apriori aprioriHelper = new Apriori();
		
		try {
			aprioriHelper.doApriori(minSupport, minConfidence, filename);
		} catch (IOException e) {
			System.err.println("I/O failed "+ e.toString());
		}
	}

}
