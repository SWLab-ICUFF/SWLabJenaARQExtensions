package uff.ic.swlab.jena.sparql.function;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class NewClass {

    public static void main(String[] args) {
        String[] list0 = {};

        ArrayList<String> new_list = new ArrayList<>();

        System.out.println("A" + new_list.stream().filter(StringUtils::isNoneBlank).collect(Collectors.joining(" ")) + "B");

        //System.out.println((Arrays.asList(new_list)).stream().filter(StringUtils::isNotBlank)
        //        .collect(Collectors.joining(" ")));
    }
}
