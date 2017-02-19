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

import java.util.HashMap;
import java.util.List;

/**
 * Write the output on the console or in a file.
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 1.7.2016
 * 
 * @param X list of the words for which the neighbors are being printed
 * @param neighborInds list of indices of neighbors for each word in words
 * @param words list of all words
 * 
 */
public class Output {
    
    public static void writeConsole(List<String> X, List<List<Integer>> neighborInds,List<String> words){
        
        for(int i=0;i<X.size();i++){
            String w = X.get(i);
            System.out.println(w+":");
            
            List<Integer> neighbors = neighborInds.get(i);
            for(int neighbInd : neighbors){
                System.out.print(words.get(neighbInd)+" ");
            }
            System.out.println();
            
            //System.out.println(neighbors);
            System.out.println("-------------------------------");
        }
    }
    public static void writeFile(){   
        
    }
    
    
    
}
