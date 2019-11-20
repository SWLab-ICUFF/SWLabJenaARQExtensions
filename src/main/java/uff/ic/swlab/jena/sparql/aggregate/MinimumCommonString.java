/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.aggregate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.ExprLib;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AggCustom;
import org.apache.jena.sparql.function.FunctionEnv;

/**
 *
 * @author angelo
 */
public class MinimumCommonString implements Accumulator {

    static boolean DEBUG = false;
    protected long errorCount = 0;
    private boolean makeDistinct = false;
    private AggCustom agg = null;

    private List<String> newKws;

    public MinimumCommonString(AggCustom agg) {
        this.agg = agg;
        this.makeDistinct = false;
        this.newKws = new ArrayList<>();
    }

    @Override
    public final void accumulate(Binding binding, FunctionEnv functionEnv) {
        try {
            NodeValue[] nv = {
                ExprLib.evalOrNull(agg.getExprList().get(0), binding, functionEnv)
            //ExprLib.evalOrNull(agg.getExprList().get(1), binding, functionEnv)
            };
            if (nv != null) {
                try {
                    constructKws(nv, binding, functionEnv);
                    return;
                } catch (ExprEvalException ex) {
                }
                errorCount++;
            }
        } catch (ExprEvalException ex) {
            errorCount++;
        }
    }

    private void constructKws(NodeValue[] nv, Binding binding, FunctionEnv functionEnv) {
        List<String> nodeValue = Arrays.asList(nv[0].asString().trim().toLowerCase().replaceAll(" +", " ").split(" "));
        if (newKws.size() == 0)
            newKws.addAll(nodeValue);
        else
            for (Iterator<String> iterator = newKws.iterator(); iterator.hasNext();) {
                String value = iterator.next();
                if (!nodeValue.contains(value))
                    iterator.remove();

            }
        //for (String new_kws : new_kws_vector)
        //    for (String kw : kws)
        //        if (!new_kws_vector.contains(kw))
        //            kws.remove(kw);

    }

    @Override
    public NodeValue getValue() {
        if (errorCount > 0)
            return NodeValue.makeString("error");
        else
            return NodeValue.makeString(newKws.stream().filter(StringUtils::isNoneBlank).collect(Collectors.joining(" ")));
    }

}
