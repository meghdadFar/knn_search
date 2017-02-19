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
