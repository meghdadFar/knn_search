/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unige.cui.meghdad.knnsearch;

import java.util.List;

/**
 * VectorOperations implements some useful vector methods e.g., dot dotProduct.
 *
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 1.6.2016
 *
 */
public class VectorOperations {
    

    public double dotProduct(List<Double> a, List<Double> b) {

        double result = 0;

        if (a.size() == b.size()) {

            for (int i = 0; i < a.size(); i++) {
                double tmp = a.get(i) * b.get(i);
                result += tmp;
            }
        } else {
            System.out.println("Sizes of the vectors must match.");
            return -1;

        }

        return result;
    }

    public double cosineSim(List<Double> a, List<Double> b) {

        double result = 0.0;

        double dotProduct = 0.0;
        double norma = 0.0;
        double normb = 0.0;

        if (a.size() == b.size()) {

            for (int i = 0; i < a.size(); i++) {
                dotProduct = a.get(i) * b.get(i);
                norma += Math.pow(a.get(i), 2);
                normb += Math.pow(b.get(i), 2);

            }

        } else {
            System.out.println("Sizes of the vectors must match.");
            return -1;

        }

        result = dotProduct / (Math.sqrt(normb) * Math.sqrt(normb));
        
        return result;

    }

}
