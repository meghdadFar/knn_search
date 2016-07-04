/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unige.cui.meghdad.knnsearch;

import java.util.HashMap;
import java.util.List;

/**
 * Write the output on the console or in a file. 
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 1.7.2016
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
