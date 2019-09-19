package uff.ic.swlab.jena.sparql.aggregate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.jena.ext.com.google.common.util.concurrent.AtomicDouble;
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
        this.freqs = new HashMap<>();
        this.makeDistinct = false;
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
        String value = nv[1].asString().toLowerCase();
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
        return NodeValue.makeDouble(acumm / freqs.size());
    }

}
