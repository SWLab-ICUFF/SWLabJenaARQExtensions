package uff.ic.swlab.jena.sparql.function;

import java.util.regex.Pattern;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;

public class isString extends FunctionBase1 {

    private static Pattern PATTERN = Pattern.compile(".*[À-ÿ].*");

    public isString() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v1) {
        if (v1.isString())
            try {
                Double.parseDouble(v1.getString());
                return NodeValue.makeBoolean(false);
            } catch (Throwable e) {
                try {
                    throw new Exception("Parse date: not implemented yet.");
                } catch (Throwable e1) {
                    if (Boolean.parseBoolean(v1.getString()))
                        return NodeValue.makeBoolean(false);
                    else
                        return NodeValue.makeBoolean(true);
                }
            }
        else
            return NodeValue.makeBoolean(false);
    }

}
