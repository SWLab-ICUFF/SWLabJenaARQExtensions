/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;

import java.util.regex.Pattern;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;
import org.apache.jena.sparql.util.FmtUtils;

/**
 *
 * @author angelo
 */
public class replaceControlCharacters extends FunctionBase1 {
    
    private final Pattern PATTERN = Pattern.compile("\\p{C}");
    
    public replaceControlCharacters(){
        super();
    }

    @Override
    public NodeValue exec(NodeValue nv) {
        if (!nv.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(nv.asNode()));
        
        if(PATTERN.matcher(nv.getString()).matches()){
            String nodeValueString = nv.getString().replaceAll(PATTERN.toString(), " ");
            return NodeValue.makeString(nodeValueString);
        }else{
            return NodeValue.makeString(nv.getString());
        }

    }
    
}
