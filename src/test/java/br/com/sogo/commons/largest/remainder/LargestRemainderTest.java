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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */

public class LargestRemainderTest {
    
    private final LargestRemainder largestRemainder;
    
    public LargestRemainderTest() {
        this.largestRemainder = new LargestRemainder();
    }
    
    private static BigDecimal big(Double number) {
        return BigDecimal.valueOf(number);
    }
    
    private static BigDecimal big(Integer number) {
        return BigDecimal.valueOf(number);
    }

    /**
     * teste dos numeros [54.567, 20.423, 25.01], cuja soma resulta em 100.
     * Este teste aborda o caso onde todos os numeros devem ser arredondados para
     * duas casas decimais sem que a soma de seus valores mude. Deve permanecer 100.
     */
    @Test
    public void toTwoDecimalCase() {
        
        List<Double> input = Arrays.asList(54.567, 20.423, 25.01);
        
        List<BigDecimal> result = this.largestRemainder.distributeRemainder(input, 100, 2, new ArrayList<>());
 
        assertEquals(Arrays.asList(big(54.57), big(20.42), big(25.01)), result);
    }

    /**
     * Teste dos numeros [12.45, 25.55, 12.00, 15.35, 24.65, 10.00], a soma resulta em 100,
     * cada um dos numeros serao convertidos para apenas uma casa decimal sem que a soma mude.
     */
    @Test
    public void toOneDecimalCase() {
        
        final List<Double> input = Arrays.asList(12.45, 25.55, 12.00, 15.35, 24.65, 10.00);
        
        final List<BigDecimal> result = largestRemainder.distributeRemainder(input, 100, 1, new ArrayList<>());
        
        assertEquals(Arrays.asList(big(12.5), big(25.6), big(12.0), big(15.3), big(24.6), big(10.0)), result);
    }
    
    /**
     * Teste dos numeros [12.45, 25.55, 12.00, 15.35, 24.65, 10.00], a soma resulta em 100,
     * cada um dos numeros serao convertidos para apenas uma casa decimal sem que a soma mude.
     */
    @Test
    public void toZeroDecimalCase() {
        
        final List<Double> input = Arrays.asList(12.45, 25.55, 12.00, 15.35, 24.65, 10.00);
        
        final List<BigDecimal> result = largestRemainder.distributeRemainder(input, 100, 0,  new ArrayList<>());
        
        assertEquals(Arrays.asList(big(12), big(26), big(12), big(15), big(25), big(10)), result);
    }
    
    /**
     * Aqui eh realiado um teste de numeros fixos.
     * Se temos um input de [38, 37, 10, 36] (soma=121)
     * e bloqueamos o numero 38 (posicao 0), so os numeros 37, 10 e 36 podem
     * ser modificados para que a soma corresponda ao esperado, 127.
     */
    @Test
    public void testFixedNumbersNoDecimalPlace() {
        
        // 127
        final List<Double> input = Arrays.asList(38D, 37D, 10D, 36D);
        final List<BigDecimal> expected = Arrays.asList(big(38), big(39), big(12), big(38));
        
        final List<BigDecimal> result = this.largestRemainder.distributeRemainder(input, 127D, 0, Arrays.asList(0));
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testFixedNumbersWithTwoDecimalPlaces() {
        
        // 120
        final List<Double> input = Arrays.asList(35.11, 37.90, 10.15, 36.50);
        final List<BigDecimal> expected = Arrays.asList(big(35.11), big(38.02), big(10.26), big(36.61));
        
        final List<BigDecimal> result = this.largestRemainder.distributeRemainder(input, 120D, 2, Arrays.asList(0));
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testEmptyValues() {
        
        final List<BigDecimal> result = this.largestRemainder.distributeRemainder(new ArrayList<>(), BigDecimal.valueOf(100), 1,  new ArrayList<>());
        assertEquals(result, new ArrayList<>());
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testTotal0() {
        
        this.largestRemainder.distributeRemainder(new ArrayList<>(), BigDecimal.ZERO, 0,  new ArrayList<>());
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testNegativeTotal() {
        
        List<BigDecimal> result = this.largestRemainder.distributeRemainder(new ArrayList<>(), big(-1), 0,  new ArrayList<>());
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testNegativeDecimalPlaces() {
        
        List<BigDecimal> result = this.largestRemainder.distributeRemainder(new ArrayList<>(), big(100), -1,  new ArrayList<>());
    }
    
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testNullDecimalPlaces() {
        
        List<BigDecimal> result = this.largestRemainder.distributeRemainder(new ArrayList<>(), big(100), null,  new ArrayList<>());
    }

    
}
