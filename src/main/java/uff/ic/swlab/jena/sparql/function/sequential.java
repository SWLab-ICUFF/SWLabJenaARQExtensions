package uff.ic.swlab.jena.sparql.function;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase0;

public class sequential extends FunctionBase0 {

    private AtomicLong counter = new AtomicLong(0);

    public sequential() {
        super();
    }

    @Override
    public NodeValue exec() {
        return NodeValue.makeInteger(counter.incrementAndGet());
    }

}
