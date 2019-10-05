package uff.ic.swlab.jena.sparql.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class NewClass {

    public static void main(String[] args) {
        String[] list0 = {"a", null, "b", "c"};
        
        ArrayList<String> new_list = new ArrayList<>();
        new_list.add("a");
        new_list.add("b");
        
        System.out.println(new_list.stream().filter(StringUtils::isNoneBlank).collect(Collectors.joining(" ")));
        
        //System.out.println((Arrays.asList(new_list)).stream().filter(StringUtils::isNotBlank)
        //        .collect(Collectors.joining(" ")));
        
        
       
    }
}
