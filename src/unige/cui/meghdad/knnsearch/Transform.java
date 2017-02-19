/* 
 * Copyright (C) 2017 Meghdad Farahmand<meghdad.farahmand@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
 * This class contains methods that are required to transform vectors. 
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
        String row;
        String[] elements;
        
        
        System.out.println("Number of elements to be processed: "+vectors.size());
        
        for (int i = 0; i < vectors.size(); i++) {
            
            if(i % 100000 == 0){
                System.out.println("row: "+i);
            }
            
            row = vectors.get(i);
            elements = row.split(" ");

            if (elements.length != length) {
                System.out.println("matrix cannnot be created.");
                System.out.println("number of columns mismatch the input argument length at row " + (i + 1) + ".");
                System.out.println("argument length: "+length +" number of columns: "+elements.length);
                System.out.println("returning null.");
                return null;
            }
            List<Double> tmp = new ArrayList<>();
            for (String e : elements) {
                tmp.add(Double.parseDouble(e));
            }
            matrix.add(tmp);
        }
//        for(List l : matrix){
//            System.out.println(l);
//        }
        return matrix;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Transform m = new Transform();

    }

}
