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
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ReadAndFilterWordRep reads word representations, filter them based
 * on some regex criteria and return a list of words and a list of their representations
 * with corresponding indices. 
 * 
 * Two overloaded rfwr methods implement this program. The first one genreates two 
 output files (words.txt and vectors.txt) and a List<List<String>> that consists of 
 * words and vectors. 
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 5.6.2016
 * 
 * 
 * 
 * 
 */
public class ReadAndFilterWordRepV2HashMap {

    
    /**
     * Method to create a vector of size "length" of random reals between -5 and 5.
     * 
     * 
     * @param length length of the vector of random numbers
     */
    
    public String randomVecGenerator(int length){
        
        StringBuilder v = new StringBuilder();
        Random r = new Random();
        for(int i=0;i<length;i++){
            double randomValue = (r.nextDouble() * 10.0d) - 5.0d;   
            v.append(randomValue);
            if(i < length-1){
                v.append(" ");
            }
        }
        return v.toString();
    }
    
    
    /**
     * Main method of ReadAndFilterWordRep. See the description of ReadAndFilterWordRep.
     * 
     * @param path2VecRep path 2 vector representations that has the 
     * following format: 
     * 
     * Malcolm -4.258007 -0.751280 3.748267 6.103424 0.509515 -4.308430...$
     * M.A. 2.822920 -7.469220 -0.241258 -5.380809 6.200501 4.150635...$
     * post-war -6.577691 6.759896 -2.152961 -1.298112 2.852708 0.576722...$
     * ...
     * 
     * 
     * @param genOutput whether or not create output file
     * @param p2output path to output file
     * @param length length of the vector representations
     * @return List<List<String>>: list_1: words list_2: vectors
     * @throws FileNotFoundException
     * @throws IOException 
     */
    
    public HashMap rfwr(String path2VecRep,int length,boolean genOutput,String p2output) throws FileNotFoundException, IOException {

        //String p2NCs2BeUsedForDic = path2Home+"/Resources/corpora/WIKI-2015/important/test_data/nc.gt5.txt";
        //String path2VecRep= path2Home+"/Resources/corpora/WIKI-2015/vectors_"+embeddings+"/vectors.WIKI15.lowerC.iter30.size50.BOW.txt";
        
        Writer w2vec_word_out = null;
        Writer w2vec_vec_out = null;
        
        if (genOutput) {
            //path to output
            String p2Filteredw2vecWords = p2output + "words";
            String p2Filteredw2vecVectors = p2output + "vectors";

            //writing out cleaned word2vec (words)
            w2vec_word_out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(p2Filteredw2vecWords), "UTF-8"));

            //writing out cleaned word2vec (vectors)
            w2vec_vec_out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(p2Filteredw2vecVectors), "UTF-8"));

        }

        /*
         Reading the representation File that has the following format:
        
         Malcolm -4.258007 -0.751280 3.748267 6.103424 0.509515 -4.308430...$
         M.A. 2.822920 -7.469220 -0.241258 -5.380809 6.200501 4.150635...$
         post-war -6.577691 6.759896 -2.152961 -1.298112 2.852708 0.576722...$
         */
        BufferedReader representationFile = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path2VecRep)));

        /*
        
         wrdsVectors Keys acontains the filtered w2vec words. 
          wrdsVectors Values vectors acontains the filtered w2vec vectors.
        
         Since we filter the vector representationFile here, chances are some of the words 
         that exist in later data (e.g. an evaluation set which did not follow the same
         filtering ctiteria) are not in the indexed words and vectors. 
        
         To avoid psuch problems, we set the first index of words to "unk" and vectors 
         to a dummy vector (average of 18000 first vectors). 
        
         */
        
        
        
        HashMap<String,String> wrdsVectors= new HashMap<>();
        

        //////words.add("unk");
        
        String unkVec = randomVecGenerator(length);
        
        //////vectors.add(unkVec);
        
        
        wrdsVectors.put("unk", unkVec);
        
        
        if (genOutput) {
            w2vec_word_out.write("unk\n");
            w2vec_vec_out.write(unkVec+"\n");
        }

        //return maps
        List<List<String>> returnLists = new ArrayList<>();

        //Malcolm -4.258007 -0.751280 3.748267 6.103424 0.509515 -4.308430
        //M.A. 2.822920 -7.469220 -0.241258 -5.380809 6.200501 4.150635
        //post-war -6.577691 6.759896 -2.152961 -1.298112 2.852708 0.576722
        Pattern w2vec = Pattern.compile("^([\\p{Alpha}-\\.]+)\\s(.+)$");

        //I assume there is no duplicates in the word2vec output:
        String l;
        while ((l = representationFile.readLine()) != null) {

            Matcher w2vecM = w2vec.matcher(l);

            if (w2vecM.find()) {
                
                wrdsVectors.put(w2vecM.group(1),w2vecM.group(2));
                if (genOutput) {
                    w2vec_word_out.write(w2vecM.group(1) + "\n");
                    w2vec_vec_out.write(w2vecM.group(2) + "\n");
                }
            }
        }
        if (genOutput) {
            w2vec_word_out.flush();
            w2vec_vec_out.flush();

            w2vec_word_out.close();
            w2vec_vec_out.close();
        }
        return wrdsVectors;
    }

    
    /*
     * Overloaded version of rfwr for when the output
     * file is not desired. 
    */
    public HashMap rfwr(String path2VecRep,int length) throws FileNotFoundException, IOException {

        //String p2NCs2BeUsedForDic = path2Home+"/Resources/corpora/WIKI-2015/important/test_data/nc.gt5.txt";
        //String path2VecRep= path2Home+"/Resources/corpora/WIKI-2015/vectors_"+embeddings+"/vectors.WIKI15.lowerC.iter30.size50.BOW.txt";

        

        /*
         Reading the representation File that has the following format:
        
         Malcolm -4.258007 -0.751280 3.748267 6.103424 0.509515 -4.308430...$
         M.A. 2.822920 -7.469220 -0.241258 -5.380809 6.200501 4.150635...$
         post-war -6.577691 6.759896 -2.152961 -1.298112 2.852708 0.576722...$
         */
        BufferedReader representationFile = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path2VecRep)));

        /*
        
         wrdsVectors Keys acontains the filtered w2vec words. 
         wrdsVectors Values vectors acontains the filtered w2vec vectors.
        
         Since we filter the vector representationFile here, chances are some of the words 
         that exist in later data (e.g. an evaluation set which did not follow the same
         filtering ctiteria) are not in the indexed words and vectors. 
        
         To avoid psuch problems, we set the first index of words to "unk" and vectors 
         to a dummy vector (average of 18000 first vectors). 
        
         */
        
        
        
        HashMap<String,String> wrdsVectors= new HashMap<>();
        

        //////words.add("unk");
        
        String unkVec = randomVecGenerator(length);
        
        //////vectors.add(unkVec);
        
        
        wrdsVectors.put("unk", unkVec);
        
        

        //return maps
        List<List<String>> returnLists = new ArrayList<>();

        //Malcolm -4.258007 -0.751280 3.748267 6.103424 0.509515 -4.308430
        //M.A. 2.822920 -7.469220 -0.241258 -5.380809 6.200501 4.150635
        //post-war -6.577691 6.759896 -2.152961 -1.298112 2.852708 0.576722
        Pattern w2vec = Pattern.compile("^([\\p{Alpha}-\\.]+)\\s(.+)$");

        //I assume there is no duplicates in the word2vec output:
        String l;
        while ((l = representationFile.readLine()) != null) {

            Matcher w2vecM = w2vec.matcher(l);

        }
        return wrdsVectors;
    }
    
    
    public static void main(String[] args) {
        
        
        
    }
    
}
