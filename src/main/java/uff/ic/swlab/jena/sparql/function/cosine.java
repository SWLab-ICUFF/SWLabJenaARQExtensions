package uff.ic.swlab.jena.sparql.function;

import org.apache.commons.text.similarity.CosineDistance;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import org.apache.jena.sparql.util.FmtUtils;

public class cosine extends FunctionBase2 {

    public cosine() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v1, NodeValue v2) {
        if (!v1.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(v1.asNode()));
        if (!v2.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(v2.asNode()));
        return NodeValue.makeDouble(new CosineDistance().apply(v1.getString(), v2.getString()));
    }

}
