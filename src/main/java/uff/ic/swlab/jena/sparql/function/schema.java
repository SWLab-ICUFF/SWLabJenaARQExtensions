package uff.ic.swlab.jena.sparql.function;

import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class schema extends FunctionBase1 {

    public schema() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v1) {
        if (!v1.isIRI())
            throw new ExprEvalException("Not a IRI: " + FmtUtils.stringForNode(v1.asNode()));

        if (!v1.asNode().getURI().contains(RDF.getURI())
                && !v1.asNode().getURI().contains(RDFS.getURI())
                && !v1.asNode().getURI().contains(OWL.getURI())
                && !v1.asNode().getURI().equals(RDF.type.getURI()))
            return NodeValue.makeBoolean(false);
        else
            return NodeValue.makeBoolean(true);
    }
}
