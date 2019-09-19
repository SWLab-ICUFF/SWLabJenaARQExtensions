package uff.ic.swlab.jena.fuseki.cmd;

import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AccumulatorFactory;
import org.apache.jena.sparql.expr.aggregate.AggCustom;
import org.apache.jena.sparql.expr.aggregate.AggregateRegistry;
import org.apache.jena.sparql.graph.NodeConst;
import uff.ic.swlab.jena.sparql.aggregate.AccTMinMax;
import uff.ic.swlab.jena.sparql.aggregate.MeanCoocurrFreq;

public class FusekiCmd {

    private static final AccumulatorFactory tMinMaxFactory = new AccumulatorFactory() {
        @Override
        public Accumulator createAccumulator(AggCustom agg, boolean distinct) {
            return new AccTMinMax(agg);
        }
    };

    private static final AccumulatorFactory tMinMaxFactory2 = new AccumulatorFactory() {
        @Override
        public Accumulator createAccumulator(AggCustom agg, boolean distinct) {
            return new MeanCoocurrFreq(agg);
        }
    };

    public static void main(String[] args) {
        String aggUri = "http://uff.ic.swlab.jena.sparql.aggregate/tMinMax";
        AggregateRegistry.register(aggUri, tMinMaxFactory, NodeConst.nodeMinusOne);
        AggregateRegistry.register(aggUri, tMinMaxFactory2, NodeConst.nodeMinusOne);

        org.apache.jena.fuseki.cmd.FusekiCmd.main(args);
    }
}
