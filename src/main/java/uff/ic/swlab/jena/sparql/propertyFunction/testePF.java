package uff.ic.swlab.jena.sparql.propertyFunction;

import org.apache.jena.atlas.logging.Log;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.Table;
import org.apache.jena.sparql.algebra.TableFactory;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.apache.jena.sparql.algebra.op.OpFilter;
import org.apache.jena.sparql.algebra.op.OpJoin;
import org.apache.jena.sparql.algebra.op.OpTable;
import org.apache.jena.sparql.core.BasicPattern;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.ExecutionContext;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.iterator.QueryIterNullIterator;
import org.apache.jena.sparql.engine.main.QC;
import org.apache.jena.sparql.expr.E_Regex;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprVar;
import org.apache.jena.sparql.pfunction.PFuncListAndSimple;
import org.apache.jena.sparql.pfunction.PropFuncArg;
import org.apache.jena.sparql.util.NodeUtils;
import org.apache.jena.vocabulary.RDFS;

public class testePF extends PFuncListAndSimple {

    @Override
    public QueryIterator execEvaluated(Binding binding, PropFuncArg subject, Node predicate, Node object, ExecutionContext execCxt) {
        // (?s ?p ?o ?score) testePF "natural language sentence";

        // No real need to check the pattern arguments because
        // the replacement triple pattern and regex will cope
        // but we illustrate testing here.
        Node nodeVar = subject.getArg();
        String pattern = NodeUtils.stringLiteral(object);
        if (pattern == null) {
            Log.warn(this, "Pattern must be a plain literal or xsd:string: " + object.getArg());
            return QueryIterNullIterator.create(execCxt);
        }

        // Better
        // Build a SPARQL algebra expression
        Var var2 = createNewVar();                     // Hidden variable

        BasicPattern bp = new BasicPattern();
        Triple t = new Triple(nodeVar, RDFS.label.asNode(), var2);
        bp.add(t);
        OpBGP op = new OpBGP(bp);

        Expr regex = new E_Regex(new ExprVar(var2.getName()), pattern, "i");
        Op filter = OpFilter.filter(regex, op);

        // ---- Evaluation
        if (true) {
            // Use the reference query engine
            // Create a table for the input stream (so it uses working memory at this point,
            // which is why this is not the preferred way).
            // Then join to expression for this stage.
            Table table = TableFactory.create(input);
            Op op2 = OpJoin.create(OpTable.create(table), filter);
            return Algebra.exec(op2, execCxt.getDataset());
        }

        // Use the default, optimizing query engine.
        return QC.execute(filter, input, execCxt);
    }

    // Create a new, hidden, variable.
    private static Var createNewVar() {
        hiddenVariableCount++;
        String varName = "-search-" + hiddenVariableCount;
        return Var.alloc(varName);
    }

    // Build SPARQL syntax and compile it.
    // Not recommended.
    static int hiddenVariableCount = 0;

}
