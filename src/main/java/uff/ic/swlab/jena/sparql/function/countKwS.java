/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import org.apache.jena.sparql.util.FmtUtils;

/**
 *
 * @author angelo
 */
public class countKwS extends FunctionBase2 {
    
    public countKwS(){
        super();
    }

    @Override
    public NodeValue exec(NodeValue kws, NodeValue comment) {
        if (!kws.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(kws.asNode()));
        if (!comment.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(comment.asNode()));
        
        Integer count_repeat = 0;
        
      
        String comment_ = comment.getString().replaceAll(" +", " ");
        String kws_ = kws.getString().replaceAll(" +", " ");
        
       
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
        
        
        double cobertura = (double) count_repeat/kws_set.size();
       
        return NodeValue.makeDouble(cobertura);
        
        
    }
    
}
