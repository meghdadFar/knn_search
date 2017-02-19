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
