/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;

import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;
import org.apache.jena.sparql.util.FmtUtils;

/**
 *
 * @author angelo
 */
public class label extends FunctionBase1 {
    
    public label(){
        super();
    }

    @Override
    public NodeValue exec(NodeValue v) {
         if (!v.isIRI())
            throw new ExprEvalException("Not a IRI: " + FmtUtils.stringForNode(v.asNode()));
         String label = null;
         if(v.asNode().getURI().contains("/")){
             String[] uri = v.asNode().getURI().split("/");
             label = uri[uri.length - 1];
         }else if(v.asNode().getURI().contains("#")){
             String[] uri = v.asNode().getURI().split("#");
             label = uri[uri.length - 1];
         }
         if (label == null)
             throw new ExprEvalException("Error construct label: ");
         
         return NodeValue.makeString(label);
         
         
        
    }
    
    
}
