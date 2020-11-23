/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sogo.commons.largest.remainder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Loader {
    
    public static void main(String[] args) {
        
        LargestRemainder largestRemainder = new LargestRemainder();
        
        List<BigDecimal> result = largestRemainder.distributeRemainder(
                Arrays.asList(41.5D, 37.5D, 15.08D, 4.75D, 1.17D),
        100D, 0, new ArrayList<>());
        
        System.out.println(result);
        
        List<BigDecimal> result2 = largestRemainder.distributeRemainder(
                Arrays.asList(15.08D, 37.5D, 41.5D, 4.75D, 1.17D),
        100D, 0, new ArrayList<>());
        
        System.out.println(result2);
    }
}
