/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unige.cui.meghdad.knnsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Indexer reads a file containing a list of n-grams (p2ToBIndexed) and index 
 * them with respect to a vocabulary (wordsList). 
 * 
 * See Indexer.main and overloaded Indexer.main for more details on parameters. 
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 5.6.2016
 * 
 */

public class Indexer {
    
    /**
     * Returns and index of a set of n-grams with respect to a word-list (vocabulary).
     * 
     * The set of n-grams should be accessible via a txt file whose path is passed 
     * to this method via p2ToBIndexed. 
     * 
     * @param wordsList vocabulary
     * 
     * @param paddy if the vocabulary doesn't contain one or more word of a particular
     * n-gram, a paddy entry (unk unk 1) is inserted in the return list if paddy is true
     * and otherwise the entry won't be indexed (the correspondent entry of the 
     * return list will be eliminated).
     * 
     * @param wFreq do to_be_indexed n-grams have a column for their frequency?
     * 
     * @param p2ToBIndexed path to to_be_indexed n-grams (and their frequency: optional)
     * 
     * @param p2Output optional: if this argument is available, two files 
     * containing the indexed bigrams (indices and forms) will be generated.
     * 
     * @return list of strings representing w1_index,w2_index,..,wn_index,(freq)?
     * 
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */

    public List main(List<String> wordsList, boolean paddy, boolean wFreq, String p2ToBIndexed, String p2Output) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        BufferedReader toBeIndexedNgrams = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(p2ToBIndexed)));//input

        //writing out indexed list of toBeIndexedNgrams
        Writer indexed_bigrs = new BufferedWriter(new OutputStreamWriter(
                //new FileOutputStream(path2Home+"/Resources/corpora/BNC/"+embeddings+"_indexed_"+toBIndexed), "UTF-8"));//output
                //new FileOutputStream(path2Home+"/Resources/schneider/dataset/un-stemmed/lowercased/ALL_WORDS/indexed_files/"+embeddings+"_indexed_"+toBIndexed), "UTF-8"));//output
                new FileOutputStream(p2Output + "/indexed.csv"), "UTF-8"));//output

        //writing out the corresponding forms (because they change after indexing with respect to wordslist)
        Writer indexed_bigrs_forms = new BufferedWriter(new OutputStreamWriter(
                //new FileOutputStream(path2Home+"/Resources/corpora/BNC/"+embeddings+"_indexed_"+toBIndexed), "UTF-8"));//output
                new FileOutputStream(p2Output + "/indexed_forms.csv"), "UTF-8"));//output

        
        List<String> indexedNgrams = new ArrayList<>();
        
        //put the words and their indices in a hashmap for later O(1) lookup 
        LinkedHashMap<String, Integer> wordsMap = new LinkedHashMap();
        for (int i = 0; i < wordsList.size(); i++) {
            wordsMap.put(wordsList.get(i), i);
        }

        //order of the n-grams
        int n = -1;

        System.out.println("Indexing n-grams...");
        int notFound = 0;
        String bigrEtFreq = "";
        int loopIndex = 0;

        while ((bigrEtFreq = toBeIndexedNgrams.readLine()) != null) {

            if (loopIndex % 1000000 == 0) {
                System.out.println(loopIndex);
//                long stopTime = System.currentTimeMillis();
//                long elapsedTime = stopTime - startTime;
//                System.out.println(elapsedTime);
//                System.out.println("------------------");
            }

            String[] terms = bigrEtFreq.split("[ ,]"); //split around space and comma (for csv files)

            /* 
             only in the first iteration (loopIndex==1), check the length of bigrEtFreq and extract
             n -the size (order) of the ngrams.
             */
            if (loopIndex == 0) {
                n = terms.length;
            }

            //if if wordsMap consists of w1 w2 freq(of pair), subtract n by one
            //because terms[length-1] is the frequency
            if (wFreq) {
                n = n - 1;
            }

            //do all words of this bigram exist in the filtered word representations?
            boolean areAllWordsThere = true;
            for (int k = 0; k < n; k++) {
                boolean tmp = wordsMap.containsKey(terms[k]);
                areAllWordsThere = tmp && areAllWordsThere;
            }

            //if all words exist in the 
            if (areAllWordsThere) {

                String indices = "";
                String forms = "";
                for (int i = 0; i < n; i++) {
                    indices.concat(Integer.toString(wordsMap.get(terms[i])));
                    forms.concat(terms[i]);
                    if (i < n - 1) {
                        indices.concat(",");
                        forms.concat(",");
                    }
                }
                //if wFreq retrieve the frequency and add it to indices and forms
                if (wFreq) {
                    indices.concat(terms[terms.length]);
                    forms.concat(terms[terms.length]);
                }
                indexed_bigrs.write(indices + "\n");
                indexed_bigrs_forms.write(forms + "\n");

                indexedNgrams.add(indices);
                
            } else if (!areAllWordsThere) {

                notFound++;

                if (paddy) {
                    String indices = "";
                    String forms = "";
                    for (int i = 0; i < n; i++) {
                        indices.concat("1");
                        forms.concat("unk");
                        if (i < n - 1) {
                            indices.concat(",");
                            forms.concat(",");
                        }
                    }
                    if (wFreq) {
                        indices.concat("1");
                        forms.concat("1");
                    }
                    indexed_bigrs.write(indices + "\n");
                    indexed_bigrs_forms.write(forms + "\n");
                    indexedNgrams.add(indices);
                }
            }

            loopIndex++;
        }//end while

        indexed_bigrs.flush();
        indexed_bigrs.close();

        indexed_bigrs_forms.flush();
        indexed_bigrs_forms.close();

        return indexedNgrams;
        
        
    }
    
    
    /**
     * Overloaded version of main for when p2Output is not provided (output is not 
     * required to be written in files). 
     */
    public List main(List<String> wordsList, boolean paddy, boolean wFreq, String p2ToBIndexed) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        BufferedReader toBeIndexedNgrams = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(p2ToBIndexed)));//input

        
        List<String> indexedNgrams = new ArrayList<>();
        
        //put the words and their indices in a hashmap for later O(1) lookup 
        LinkedHashMap<String, Integer> wordsMap = new LinkedHashMap();
        for (int i = 0; i < wordsList.size(); i++) {
            wordsMap.put(wordsList.get(i), i);
        }

        //order of the n-grams
        int n = -1;

        System.out.println("Indexing n-grams...");
        int notFound = 0;
        String bigrEtFreq = "";
        int loopIndex = 0;

        while ((bigrEtFreq = toBeIndexedNgrams.readLine()) != null) {

            if (loopIndex % 1000000 == 0) {
                System.out.println(loopIndex);
//                long stopTime = System.currentTimeMillis();
//                long elapsedTime = stopTime - startTime;
//                System.out.println(elapsedTime);
//                System.out.println("------------------");
            }

            String[] terms = bigrEtFreq.split("[ ,]"); //split around space and comma (for csv files)

            /* 
             only in the first iteration (loopIndex==1), check the length of bigrEtFreq and extract
             n -the size (order) of the ngrams.
             */
            if (loopIndex == 0) {
                n = terms.length;
            }

            //if if wordsMap consists of w1 w2 freq(of pair), subtract n by one
            //because terms[length-1] is the frequency
            if (wFreq) {
                n = n - 1;
            }

            //do all words of this bigram exist in the filtered word representations?
            boolean areAllWordsThere = true;
            for (int k = 0; k < n; k++) {
                boolean tmp = wordsMap.containsKey(terms[k]);
                areAllWordsThere = tmp && areAllWordsThere;
            }

            //if all words exist in the 
            if (areAllWordsThere) {

                String indices = "";
                String forms = "";
                for (int i = 0; i < n; i++) {
                    indices.concat(Integer.toString(wordsMap.get(terms[i])));
                    forms.concat(terms[i]);
                    if (i < n - 1) {
                        indices.concat(",");
                        forms.concat(",");
                    }
                }
                //if wFreq retrieve the frequency and add it to indices and forms
                if (wFreq) {
                    indices.concat(terms[terms.length]);
                    forms.concat(terms[terms.length]);
                }

                indexedNgrams.add(indices);
                
            } else if (!areAllWordsThere) {

                notFound++;

                if (paddy) {
                    String indices = "";
                    String forms = "";
                    for (int i = 0; i < n; i++) {
                        indices.concat("1");
                        forms.concat("unk");
                        if (i < n - 1) {
                            indices.concat(",");
                            forms.concat(",");
                        }
                    }
                    if (wFreq) {
                        indices.concat("1");
                        forms.concat("1");
                    }
                    indexedNgrams.add(indices);
                }
            }

            loopIndex++;
        }//end while

        return indexedNgrams;
        
        
    }

    
    
}
