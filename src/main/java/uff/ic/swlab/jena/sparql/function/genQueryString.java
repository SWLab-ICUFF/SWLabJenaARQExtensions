/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;

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
public class genQueryString extends FunctionBase2 {
    
    public genQueryString(){
        super();
    }

    @Override
    public NodeValue exec(NodeValue kws, NodeValue comment) {
         if (!kws.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(kws.asNode()));
        if (!comment.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(comment.asNode()));
        
        //deixar as strings somente com um espaçamento simples
         String comment_ = comment.getString().replaceAll(" +", " ");
         String kws_ = kws.getString().replaceAll(" +", " ");
         
         //colocar as strings em lowercase e separar por espaçamento simples
         String[] comment_vector = comment_.toLowerCase().split(" ");
         String[] kws_vector = kws_.toLowerCase().split(" ");
         
         //criando conjunto de palavras chaves evitando conter palavras chaves repetidas
         Set<String> kws_set = new HashSet<>();
         kws_set.addAll(Arrays.asList(kws_vector));
         
         //o mesmo para o kwsComment...
         Set<String> comment_set = new HashSet<>();
         comment_set.addAll(Arrays.asList(comment_vector));
         
         Integer aux = 0;
         String new_kws = "";
         for(String element_kws: kws_set){
             aux = 0; 
             for(String element_comment: comment_set){
                 
                 if (element_kws.equals(element_comment))
                     break;
                 else
                     aux = aux + 1;
               
             }
             if (aux == comment_set.size())
                 new_kws = new_kws + " " + element_kws; 
           
         }
         
         return NodeValue.makeString(new_kws);
        
    }
    
    
    
}
