/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author angelo
 */
public class testCountKws {
    
    public static void main(String[] args) {
        
        String comment = "Mauritius volcanic Mauritius MS parliamentary democracy The Republic of Mauritius India IND federal republic the Republic of India United Nations Military Observer Group in India and Pakistan UNMOGIP";
        String kws = "mauritius india";
        
        Integer count_repeat = 0;
        
        String[] comment_vector = comment.toLowerCase().split(" ");
        
        String[] kws_vector = kws.toLowerCase().split(" ");
        
        for (int i = 0; i < kws_vector.length; i++){
            
            for (int j = 0; j < comment_vector.length; j++){
                
                if (kws_vector[i].equals(comment_vector[j])){
                    
                    count_repeat++;
                    break;
                    
                }
                
            }
        }
        
        System.out.println("Total: " + count_repeat);
    }
    
}
