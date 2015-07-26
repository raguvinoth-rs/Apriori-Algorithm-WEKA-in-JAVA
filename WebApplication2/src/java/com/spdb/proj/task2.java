package com.spdb.proj;

/* IT IS FEATURE SELECTION CODE */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class task2 {
	public static void main(String args[])throws IOException,Exception{
		//Load CSV
		CSVLoader loader=new CSVLoader();
		loader.setSource(new File("C:/Users/Raguvinoth/Desktop/5339.csv"));
		Instances data=loader.getDataSet();

		//Save ARFF
		ArffSaver saver=new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File("C:/Users/Raguvinoth/Desktop/5339_converted.arff"));
		saver.writeBatch();

		BufferedReader reader = new BufferedReader(new FileReader("C://Users//Raguvinoth//Desktop//weka1//5339_nominal.arff"));
		Instances data1 = new Instances(reader);

		if (data1.classIndex() == -1)
			data1.setClassIndex(data1.numAttributes() - 1);
		// 1. meta-classifier
		// useClassifier(data);

		// 2. AttributeSelector
		try {
			AttributeSelection attsel = new AttributeSelection();
			GreedyStepwise search = new GreedyStepwise();
			CfsSubsetEval eval = new CfsSubsetEval();
			attsel.setEvaluator(eval);
			attsel.setSearch(search);
			attsel.SelectAttributes(data);
			int[] indices = attsel.selectedAttributes();
			System.out.println("selected attribute indices:\n"
					+ Utils.arrayToString(indices));
			System.out.println("\n 4. Linear-Regression on above selected attributes");
			long time1=System.currentTimeMillis();
		    long sec1=time1/1000;
			BufferedReader reader1 = new BufferedReader(new FileReader(
					"C://Users//Raguvinoth//Desktop//weka1//5339_linear2.arff"));
			Instances data2 = new Instances(reader1);
			data2.setClassIndex(0);
			LinearRegression lr = new LinearRegression();
			lr.buildClassifier(data2);
			
			System.out.println(lr.toString());
			long time2=System.currentTimeMillis();
		    long sec2=time2/1000;
			long timeTaken=sec2-sec1;
		    System.out.println("Total time taken for building the model: "+ timeTaken +" seconds");
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		
			
			
		}
	}
}
