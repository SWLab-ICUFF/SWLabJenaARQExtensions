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

     @Override
     public NodeValue exec(NodeValue v) {
          if (!v.isIRI())
             throw new ExprEvalException("Not a IRI: " + FmtUtils.stringForNode(v.asNode()));
          String label = null;
          if(v.asNode().getURI().contains("/") && v.asNode().getURI().contains("instances")){
              
              String[] uri = v.asNode().getURI().split("instances");
              String[] instanceName = uri[uri.length - 1].split("/");
              if(instanceName.length == 2)
                  label = instanceName[instanceName.length - 1];
              else
                  label = String.join("", instanceName);
              
          }else if(v.asNode().getURI().contains("#")){
              String[] uri = v.asNode().getURI().split("#");
              label = uri[uri.length - 1];
          }
          if (label == null)
              throw new ExprEvalException("Error construct label: ");

          return NodeValue.makeString(label);



     }
    


    
}
