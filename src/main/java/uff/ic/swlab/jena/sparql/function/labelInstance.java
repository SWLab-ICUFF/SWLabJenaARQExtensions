/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;


import org.apache.commons.lang3.StringUtils;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import org.apache.jena.sparql.util.FmtUtils;

/**
 *
 * @author angelo
 */
public class labelInstance extends FunctionBase2 {

    public labelInstance() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v1, NodeValue v2) {
        if (!v1.isIRI()) {
            throw new ExprEvalException("Not a IRI: " + FmtUtils.stringForNode(v1.asNode()));
        }
        if (!v2.isString()) {
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(v2.asNode()));
        }

        String label = null;
        String separatorInstance = v2.getString();
        String[] uri = v1.asNode().getURI().split(separatorInstance);
        String nameInstance = uri[uri.length - 1];
        String[] vectorLabel = nameInstance.split("/");
        
        label = StringUtils.join(vectorLabel, " ");
        
        if (label == null) {
            throw new ExprEvalException("Error construct label: ");
        }

        return NodeValue.makeString(label);

    }

}
