package uff.ic.swlab.jena.fuseki.cmd;

import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AccumulatorFactory;
import org.apache.jena.sparql.expr.aggregate.AggCustom;
import org.apache.jena.sparql.expr.aggregate.AggregateRegistry;
import org.apache.jena.sparql.graph.NodeConst;
import uff.ic.swlab.jena.sparql.aggregate.AccTMinMax;
import uff.ic.swlab.jena.sparql.aggregate.KwFreqScore;
import uff.ic.swlab.jena.sparql.aggregate.kwNewSement;

public class FusekiCmd {

    private static final AccumulatorFactory tMinMaxFactory = new AccumulatorFactory() {
        @Override
        public Accumulator createAccumulator(AggCustom agg, boolean distinct) {
            return new AccTMinMax(agg);
        }
    };

    private static final AccumulatorFactory kwFreqScoreFactory = new AccumulatorFactory() {
        @Override
        public Accumulator createAccumulator(AggCustom agg, boolean distinct) {
            return new KwFreqScore(agg);
        }
    };
    
    private static final AccumulatorFactory kwNewSement = new AccumulatorFactory() {
        @Override
        public Accumulator createAccumulator(AggCustom agg, boolean distinct) {
            return new kwNewSement(agg);
        }
    };


    public static void main(String[] args) {
        String aggUri1 = "http://uff.ic.swlab.jena.sparql.aggregate/tMinMax";
        String aggUri2 = "http://uff.ic.swlab.jena.sparql.aggregate/kwFreqScore";
        String aggUri3 = "http://uff.ic.swlab.jena.sparql.aggregate/kwNewSement";
        AggregateRegistry.register(aggUri1, tMinMaxFactory, NodeConst.nodeMinusOne);
        AggregateRegistry.register(aggUri2, kwFreqScoreFactory, NodeConst.nodeMinusOne);
        AggregateRegistry.register(aggUri3, kwNewSement, NodeConst.nodeMinusOne);

        org.apache.jena.fuseki.cmd.FusekiCmd.main(args);
    }
}
