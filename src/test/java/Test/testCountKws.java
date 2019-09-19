/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author angelo
 */
public class testCountKws {
    
    public static void main(String[] args) {
        
        String comment = "Mauritius volcanic Mauritius MS parliamentary democracy The Republic of Mauritius  IND federal republic the Republic of  United Nations Military Observer Group in  and Pakistan UNMOGIP";
        String kws = "mauritius india";
        
        Integer count_repeat = 0;
        
        String comment_ = comment.replaceAll(" +", " ");
        String kws_ = kws.replaceAll(" +", " ");
        
        String[] comment_vector = comment_.toLowerCase().split(" ");
        
        String[] kws_vector = kws_.toLowerCase().split(" ");
        
        
        Set<String> kws_set = new HashSet<>();
        kws_set.addAll(Arrays.asList(kws_vector));
        
       
        Set<String> comment_set = new HashSet<>();
        comment_set.addAll(Arrays.asList(comment_vector));
        
      
        for (String element_kws: kws_set){
            
            for (String element_comment: comment_set){
                
                if (element_kws.equals(element_comment))
                    
                    count_repeat++;
                
            }
            
            
        }
        
        double cobertura = (double) count_repeat/ kws_set.size();
        
        System.out.println(cobertura);
        
    }
    
}
