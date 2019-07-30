package io.github.kieckegard.largest.remainder;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Loader {
    
    public static void main(String[] args) {
    
        
        new LargestRemainder()
                .distributeRemainder(
                
            Arrays.asList(
                        
                BigDecimal.valueOf(13.6263), 
                BigDecimal.valueOf(47.9896), 
                BigDecimal.valueOf(9.59600), 
                BigDecimal.valueOf(28.7880)
            ),
                BigDecimal.valueOf(100), 
                
                3
        )
        .forEach(System.out::println);
        
        
    }
}
