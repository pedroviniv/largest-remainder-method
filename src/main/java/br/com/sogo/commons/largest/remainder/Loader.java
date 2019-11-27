/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sogo.commons.largest.remainder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Loader {
    
    public static void main(String[] args) {
        
        LargestRemainder largestRemainder = new LargestRemainder();
        
        List<BigDecimal> result = largestRemainder.distributeRemainder(Arrays.asList(35.10D, 37.90D, 10.15D, 36.50D), 120D, 2, Arrays.asList(0));
        
        System.out.println(result);
    }
}
