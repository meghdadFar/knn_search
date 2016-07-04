/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unige.cui.meghdad.knnsearch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Transform contains methods that are required to transform vectors. 
 * 
 * For instance transforming a vector of strings to a vector of doubles. 
 *
 * @author Meghdad Farahmand
 * @since 1.6.2016
 *
 *
 *
 */
public class Transform {


    /**
     * Create matrix (list<list<Double>>) from file.
     *
     *
     * @param path2file path to the file that incorporates a number of rows
     * (with an equal number of columns). 
     * For mat of each line of the file:
     * ^0.738564819839492 0.0108166478681009 0.519085762351915... 
     * -1.20043308554895 -0.658262063820496\n
     * 
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws IOException
     * 
     * 
     * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
     * @since 1.6.2016
     * 
     * 
     */
    public List<List<Double>> createFromFile(String path2file,int length) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        List<List<Double>> matrix = new ArrayList<>();
        
        BufferedReader matrixFile = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path2file), "UTF8"));

        /*
         This method assumes that the vectors have the following format:
         ^0.738564819839492 0.0108166478681009 0.519085762351915... 
         -1.20043308554895 -0.658262063820496\n
         */
        System.out.println("Reading matrix rows...");
        String row = "";
        int i = 0; //row index
        while ((row = matrixFile.readLine()) != null) {

            String[] elements = row.split(" ");

            
            //validate whether each row has the same number of columns
            if (elements.length != length) {
                System.out.println("matrix cannnot be created.");
                System.out.println("number of columns mismatch at row " + (i + 1) + ".");
                System.out.println("returning.");
                return null;
            }
            
            
            List<Double> tmp = new ArrayList<>();
            for (String e : elements) {
                tmp.add(Double.parseDouble(e));
            }
            matrix.add(tmp);
            i++;
        }
        return matrix;
    }

    public List<List<Double>> createFromList(List<String> vectors, int length) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        /*
         This method assumes that the vectors have the following format:
         0.738564819839492 0.0108166478681009 0.519085762351915... 
         -1.20043308554895 -0.658262063820496
         */
        
        List<List<Double>> matrix = new ArrayList<>();
        
        for (int i = 0; i < vectors.size(); i++) {
            String row = vectors.get(i);

            String[] elements = row.split(" ");

            if (elements.length != length) {
                System.out.println("matrix cannnot be created.");
                System.out.println("number of columns mismatch at row " + (i + 1) + ".");
                System.out.println("returning.");
                return null;
            }
            List<Double> tmp = new ArrayList<>();
            for (String e : elements) {
                tmp.add(Double.parseDouble(e));
            }
            matrix.add(tmp);
        }
        return matrix;
    }

    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Transform m = new Transform();

    }

}
