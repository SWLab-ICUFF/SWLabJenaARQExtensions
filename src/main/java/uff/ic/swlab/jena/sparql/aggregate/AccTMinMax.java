/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.swlab.jena.sparql.aggregate;

import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.ExprLib;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AggCustom;
import org.apache.jena.sparql.function.FunctionEnv;

public class AccTMinMax implements Accumulator {

    static boolean DEBUG = false;
    protected long errorCount = 0;
    private boolean makeDistinct = false;
    private AggCustom agg = null;
    private NodeValue[] minMaxSoFar = null;

    public AccTMinMax(AggCustom agg) {
        this.agg = agg;
        this.makeDistinct = false;
    }

    @Override
    public final void accumulate(Binding binding, FunctionEnv functionEnv) {
        try {
            NodeValue[] nv = {
                ExprLib.evalOrNull(agg.getExprList().get(0), binding, functionEnv),
                ExprLib.evalOrNull(agg.getExprList().get(1), binding, functionEnv),
                ExprLib.evalOrNull(agg.getExprList().get(2), binding, functionEnv)
            };
            if (nv != null) {
                try {
                    accumulate(nv, binding, functionEnv);
                    return;
                } catch (ExprEvalException ex) {
                }
                errorCount++;
            }
        } catch (ExprEvalException ex) {
            errorCount++;
        }
    }

    public void accumulate(NodeValue[] nv, Binding binding, FunctionEnv functionEnv) {
        if (minMaxSoFar == null) {
            minMaxSoFar = nv;
            return;
        }
        int x = NodeValue.compareAlways(nv[1], minMaxSoFar[1]);
        int y = NodeValue.compareAlways(nv[2], minMaxSoFar[2]);
        if (x < 0 && y >= 0 || x == 0 && y > 0)
            minMaxSoFar = nv;
    }

    @Override
    public NodeValue getValue() {
        if (errorCount == 0)
            return minMaxSoFar[0];
        return NodeValue.makeString("error");
    }

}
