package uff.ic.swlab.jena.sparql.aggregate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.ExprLib;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AggCustom;
import org.apache.jena.sparql.function.FunctionEnv;

public class MeanCoocurrFreq implements Accumulator {

    static boolean DEBUG = false;
    protected long errorCount = 0;
    private boolean makeDistinct = false;
    private AggCustom agg = null;
    private Map<String, AtomicInteger> freqs = null;

    public MeanCoocurrFreq(AggCustom agg) {
        this.agg = agg;
        this.makeDistinct = false;
        this.freqs = new HashMap<>();
    }

    @Override
    public final void accumulate(Binding binding, FunctionEnv functionEnv) {
        try {
            NodeValue[] nv = {
                ExprLib.evalOrNull(agg.getExprList().get(0), binding, functionEnv),
                ExprLib.evalOrNull(agg.getExprList().get(1), binding, functionEnv)
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

    private void accumulate(NodeValue[] nv, Binding binding, FunctionEnv functionEnv) {
        Set<String> kws = new HashSet<>(Arrays.asList(nv[0].asString().trim().toLowerCase().replaceAll(" +", " ").split(" ")));
        Set<String> value = new TreeSet<>(Arrays.asList(nv[1].asString().trim().toLowerCase().replaceAll(" +", " ").split(" ")));
        for (String kw : kws)
            if (value.contains(kw))
                getFreq(kw).incrementAndGet();
    }

    private AtomicInteger getFreq(String kw) {
        AtomicInteger freq = freqs.get(kw);
        if (freq == null)
            freqs.put(kw, freq = new AtomicInteger(0));
        return freq;
    }

    @Override
    public NodeValue getValue() {
        double acumm = 0;
        for (AtomicInteger freq : freqs.values())
            acumm += freq.intValue();
        if (acumm != 0)
            return NodeValue.makeDouble(acumm / freqs.size());
        else
            return NodeValue.makeDouble(Double.NEGATIVE_INFINITY);
    }

}
