package uff.ic.swlab.jena.sparql.function;

import java.util.regex.Pattern;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.jena.vocabulary.RDF;

public class isContainerMembershipProperty extends FunctionBase1 {

    private static final String ns = RDF.uri;
    private static final Pattern PATTERN = Pattern.compile(ns + "\\_[0-9]+");

    public isContainerMembershipProperty() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v1) {
        if (!v1.isIRI())
            throw new ExprEvalException("Not a IRI: " + FmtUtils.stringForNode(v1.asNode()));
        return NodeValue.makeBoolean(PATTERN.matcher(v1.asNode().getURI()).matches());
    }

}
