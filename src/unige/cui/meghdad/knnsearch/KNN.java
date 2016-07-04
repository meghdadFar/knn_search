/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unige.cui.meghdad.knnsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * KNN implements exhaustive search for k nearest neighbors.
 *
 * @author Meghdad Farahmand
 * @since 1.6.2016
 *
 *
 */
public class KNN {

    /**
     * knnExhSearch returns the k nearest neighbors of vectors. Since no method
     * overloading possible here (because List<Double> or List<List<Doule>>
     * won't be recognize as different types), vectors even if it only contains
     * one vector must be a List<List<Double>>.
     *
     * @param vectors
     * @param m matrix of all vectors from which nearest neighbors are
     * extracted.
     * @param k number of neighbors to be extracted.
     * @return
     */
    public List knnExhSearch(List<List<Double>> vectors, List<List<Double>> m, int k) {

        VectorOperations vo = new VectorOperations();

        List<List<Integer>> neighbours = new ArrayList<>();

        //for each input vector l1 find neighbours
        for (List<Double> l1 : vectors) {

            /*find the value of dot product berween l1 and all l2s and save it 
             in the list products*/
            List<ListEntry> allDotProducts = new ArrayList<>();

            for (int i = 0; i < m.size(); i++) {
                List<Double> l2 = m.get(i);
                double product = vo.dotProduct(l1, l2);
                ListEntry le = new ListEntry(i, product);
                allDotProducts.add(le);
            }
            //sort the list of products
            Collections.sort(allDotProducts);

            //select the top k neighbours and put it in l1 neighbours (n_i)
            List<Integer> n_i = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                n_i.add(allDotProducts.get(j).getIndex());
            }
            neighbours.add(n_i);
        }
        return neighbours;
    }

    public static void main(String[] args) {

    }
}
