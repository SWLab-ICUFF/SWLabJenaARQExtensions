/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;

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
        
        String[] comment_vector = comment.getString().toLowerCase().split(" ");
        
        String[] kws_vector = kws.getString().toLowerCase().split(" ");
        
        for (int i = 0; i < kws_vector.length; i++){
            
            for (int j = 0; j < comment_vector.length; j++){
                
                if (kws_vector[i].equals(comment_vector[j])){
                    
                    count_repeat++;
                    break;
                    
                }
                
            }
        }
        
        return NodeValue.makeInteger(count_repeat);
        
        
    }
    
}
