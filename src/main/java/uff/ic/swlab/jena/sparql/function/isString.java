package uff.ic.swlab.jena.sparql.function;

import java.util.regex.Pattern;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;
import org.apache.jena.sparql.util.FmtUtils;

public class isString extends FunctionBase1 {

    private static Pattern PATTERN = Pattern.compile(".*[À-ÿ].*");

    public isString() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v1) {
        if (!v1.isString())
            throw new ExprEvalException("Not a string literal: " + FmtUtils.stringForNode(v1.asNode()));

        return NodeValue.makeBoolean(PATTERN.matcher(v1.getString()).matches());
    }

}
