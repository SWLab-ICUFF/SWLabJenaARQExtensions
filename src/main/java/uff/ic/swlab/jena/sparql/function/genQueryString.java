/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.function;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import org.apache.jena.sparql.util.FmtUtils;

/**
 *
 * @author angelo
 */
public class genQueryString extends FunctionBase2 {

    public genQueryString() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue kws, NodeValue comment) {
        if (!kws.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(kws.asNode()));
        if (!comment.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(comment.asNode()));

        //colocar as strings em lowercase e separar por espaçamento simples
        String[] comment_vector = comment.getString().replaceAll(" +", " ").replace(".","").replace(":","").toLowerCase().split(" ");
        String[] kws_vector = kws.getString().replaceAll(" +", " ").replace(".","").replace(":","").toLowerCase().split(" ");
 
        for (int i = 0; i < kws_vector.length; i++)
            for (String element_comment : comment_vector)
                if (kws_vector[i].equals(element_comment)) {
                    kws_vector[i] = null;
                    break;
                }
        
        String result = Arrays.asList(kws_vector).stream().filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(" "));
        
 
        return NodeValue.makeString(result);

    }

}
