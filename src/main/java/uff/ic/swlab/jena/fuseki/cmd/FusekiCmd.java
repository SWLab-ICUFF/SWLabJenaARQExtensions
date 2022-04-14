package uff.ic.swlab.jena.fuseki.cmd;

import org.apache.jena.query.ARQ;
import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AccumulatorFactory;
import org.apache.jena.sparql.expr.aggregate.AggCustom;
import org.apache.jena.sparql.expr.aggregate.AggregateRegistry;
import org.apache.jena.sparql.graph.NodeConst;
import org.apache.jena.sparql.pfunction.PropertyFunction;
import org.apache.jena.sparql.pfunction.PropertyFunctionFactory;
import org.apache.jena.sparql.pfunction.PropertyFunctionRegistry;
import uff.ic.swlab.jena.sparql.aggregate.AccTMinMax;
import uff.ic.swlab.jena.sparql.aggregate.KwFreqScore;
import uff.ic.swlab.jena.sparql.aggregate.MinimumCommonString;
import uff.ic.swlab.jena.sparql.propertyFunction.testePF;

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

    private static final AccumulatorFactory minimumCommonString = new AccumulatorFactory() {
        @Override
        public Accumulator createAccumulator(AggCustom agg, boolean distinct) {
            return new MinimumCommonString(agg);
        }
    };

    private static final PropertyFunctionFactory testePF = new PropertyFunctionFactory() {
        @Override
        public PropertyFunction create(final String uri) {
            return new testePF();
        }
    };

    public static void main(String[] args) {
        String aggUri1 = "http://uff.ic.swlab.jena.sparql.aggregate/tMinMax";
        String aggUri2 = "http://uff.ic.swlab.jena.sparql.aggregate/kwFreqScore";
        String aggUri3 = "http://uff.ic.swlab.jena.sparql.aggregate/minimumCommonString";
        AggregateRegistry.register(aggUri1, tMinMaxFactory, NodeConst.nodeMinusOne);
        AggregateRegistry.register(aggUri2, kwFreqScoreFactory, NodeConst.nodeMinusOne);
        AggregateRegistry.register(aggUri3, minimumCommonString, NodeConst.nodeMinusOne);

        String pfUri1 = "http://uff.ic.swlab.jena.sparql.propertyFunction/testePF";
        PropertyFunctionRegistry reg = PropertyFunctionRegistry.chooseRegistry(ARQ.getContext());
        PropertyFunctionRegistry.set(ARQ.getContext(), reg);
        reg.put(pfUri1, testePF);
        //PropertyFunctionRegistry.set(ARQ.getContext(), reg);

        org.apache.jena.fuseki.cmd.FusekiCmd.main(args);
    }
}
