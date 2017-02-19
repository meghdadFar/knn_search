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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * KNN implements exhaustive search for k nearest neighbors.
 *
 * @author Meghdad Farahmand
 * @since 1.6.2016
 *
 *@param vectors list of vectors for each one of them k nearest neighbors 
 * will be returned
 * @param m list of vectors from which nearest neighbors will be extracted. 
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
        List<ListEntry> allDotProducts = new ArrayList<>();

        //for each input vector l1 find neighbours
        int dc = 0; //datapoint count
        for (List<Double> l1 : vectors) {

            //TODO make print in the same line not new lines
            System.out.print("    finding nearest neighbors for entry "+dc+"/"+vectors.size()+"\r");
            dc++;
            
            /*
            find the value of dot product berween l1 and all l2s in m and save it 
            in the list of products (allDotProducts)
            */

            for (int i = 0; i < m.size(); i++) {
                List<Double> l2 = m.get(i);
                double product = vo.dotProduct(l1, l2);
                ListEntry le = new ListEntry(i, product);
                allDotProducts.add(le);
            }
            //sort the list of products
            //TODO optimize sort (we need only top k)
            Collections.sort(allDotProducts);

            //select the top k neighbours and put them in n_i (list of l1 neighbours)
            List<Integer> n_i = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                n_i.add(allDotProducts.get(j).getIndex());
            }
            neighbours.add(n_i);
            allDotProducts.clear();
        }
        return neighbours;
    }

    public static void main(String[] args) {
        
    }
}
