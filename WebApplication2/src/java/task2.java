/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
/**
 *
 * @author Rahul Raj
 */
public class task2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet selection</title>");            
            out.println("</head>");
            out.println("<body>");
            CSVLoader loader=new CSVLoader();
		loader.setSource(new File("C:/Users//Raguvinoth/Desktop/5339.csv"));
		Instances data=loader.getDataSet();

		//Save ARFF
		ArffSaver saver=new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File("\"C:/Users/Raguvinoth/Desktop/5339_converted.arff"));
		saver.writeBatch();

		BufferedReader reader = new BufferedReader(new FileReader("C://Users//Raguvinoth//Desktop//weka1//5339_nominal.arff"));
		Instances data1 = new Instances(reader);

		if (data1.classIndex() == -1)
			data1.setClassIndex(data1.numAttributes() - 14);
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
		    
		
                
                for(int i=0;i<5;i++){
        out.println("<p>" + "selected attribute indices:\n" + Utils.arrayToString(indices[i]) + "</p>");
                }
        out.println("<p>" +"\n 4. Linear-Regression on above selected attributes" + "</p>");
        out.println("<p>" +lr.toString() + "</p>");
         out.println("<p>" +"Total time taken for building the model: " + timeTaken + " seconds" + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
                catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                }}}				
	

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}