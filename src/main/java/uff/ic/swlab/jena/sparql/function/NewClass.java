package uff.ic.swlab.jena.sparql.function;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class NewClass {

    public static void main(String[] args) {
        String[] list0 = {"a", null, "b", "c"};

        System.out.println((Arrays.asList(list0)).stream().filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(" ")));
    }
}
