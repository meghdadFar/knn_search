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
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import unige.cui.meghdad.toolkit.Tools;

/**
 * 
 * Retrieve the k nearest neighbors of a list of words in an n-dimensional space. 
 * 
 * The required command line flags are:
 * 
 * -lw: path to the list of words for which neighbors are to be retrieved. 
 * 
 * -p2wr: path to word representations (words and their representations). The present 
 * format per line is:
 * Malcolm -4.258007 -0.751280 3.748267...$
 * 
 * 
 * 
 * -output: output printed on the console (c) or in a file (f).
 * 
 * -p2out: if -output == f, then this flag must be set.
 *
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 1.6.2016
 *
 *
 *
 */
public class MAIN {

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {

        //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\\\//\\//\\//
        ////\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//
        /*
         COMMAND LINE ARGUMENTS
         */
        Options options = new Options();
        options.addOption("p2wr", true, "Path 2 word representations.");
        options.addOption("C", true, "Path to the list of words for which the neighbors to be returned.");
        options.addOption("output", true, "Write output on the console (c) or in a file (f).");
        options.addOption("p2out", true, "Path to the output file.");
        
        

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (!cmd.hasOption("p2wr")) {
            System.out.println("A valid word representation must be specified.");
            return;
        }

        if (!cmd.hasOption("lw")) {
            System.out.println("C must be specified.");
            return;
        }

        boolean showOutPut = true;
        if (!cmd.hasOption("output")) {
            showOutPut = false;
        }

       //\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\\\//\\//\\//
        ////\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//
        

        //create an instance of class Tools
        Tools T = new Tools();
        //create an instance of class ReadAndFIlterWordRep
        ReadAndFilterWordRep rv = new ReadAndFilterWordRep();

        //word2vec output entries are unique, so the following lists are going 
        //to be lists of unique values (no duplicate)
        List<List<String>> wordsVectors = rv.rfwr(cmd.getOptionValue("p2wr"),100);
        
        
        HashMap<String,Integer> words = new HashMap<>();

        //for words, use HashMap insteaf of list for faster look up (contains)
        //preserve the index of the words as Map values for future use
        for(int i=0 ; i < wordsVectors.get(0).size() ; i++){
          words.put(wordsVectors.get(0).get(i), i);
        }
        //for vectors, use a List
        List<String> vectors = wordsVectors.get(1);

        //read lwFile (containing entries for which neighbors are being searched)
        BufferedReader lwFile = new BufferedReader(
                new InputStreamReader(
                        //new FileInputStream(cmd.getOptionValue("lw")), "UTF8"));
                        new FileInputStream(cmd.getOptionValue("lw")), "UTF8"));
        
        
        
        List<String> avail_lw_Rep = new ArrayList<>();
        List<String> avail_lw_forms = new ArrayList<>();
        
        String l;
        while ((l = lwFile.readLine()) != null) {
            if (words.containsKey(l)) {
                
                int index_of_l = words.get(l);
                avail_lw_Rep.add(vectors.get(index_of_l));
                avail_lw_forms.add(l);
                
            } else {
                System.out.println("Vector representation for\" " + l + "\" is not availble. Skipping this entry.");
            }

        }
        
        //create an instance of Transform class
        //transform the representations from string to double
        Transform m = new Transform();
        List<List<Double>> M = m.createFromList(vectors, 100);
        List<List<Double>> lw = m.createFromList(avail_lw_Rep, 100);
        
        //create an instance of KNN class
        //invoke Knn exhustive search
        KNN knn = new KNN();
        List<List<Integer>>  lwNeighbors = knn.knnExhSearch(lw, M, 5);
        
        
        //print the output
        if(cmd.getOptionValue("output").equals("c")){
            Output.writeConsole(avail_lw_forms, lwNeighbors, wordsVectors.get(0));
        }else{
            Output.writeFile();
        }
    }

}
