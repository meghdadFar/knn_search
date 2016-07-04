/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unige.cui.meghdad.knnsearch;

/**
 * ListEntry implements Comparable and is used to sort a list of doubles 
 * while keeping the track of their indices. 
 * 
 * @author Meghdad Farahmand<meghdad.farahmand@gmail.com>
 * @since 3.6.2016
 * 
 */
public class ListEntry implements Comparable<ListEntry>{

    private int index;
    private double value;
    
    
    public ListEntry(int index,double value){
        this.index = index;
        this.value = value;
        
    }
    
    @Override
    public int compareTo(ListEntry o) {
        
        //to sort in descending order
        return -1*Double.valueOf(this.getValue()).compareTo(o.getValue());
        //to sort in ascending order
        //return Duble.valueOf(this.value).compareTo(o.value);
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }
}
