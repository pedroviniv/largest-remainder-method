/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sogo.commons.largest.remainder;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * class
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Number {

    private final Integer originalOrder;
    private BigDecimal value;
    private final BigDecimal weight;
    private final Boolean fixed;

    public static class Builder {

        private Integer originalOrder;
        private BigDecimal value;
        private BigDecimal weight;
        private Boolean fixed;

        private Builder() {
            this.fixed = false;
        }

        public Builder originalOrder(final Integer value) {
            this.originalOrder = value;
            return this;
        }

        public Builder value(final BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder weight(final BigDecimal value) {
            this.weight = value;
            return this;
        }

        public Builder isFixed(final Boolean isFixed) {
            this.fixed = isFixed;
            return this;
        }

        public Number build() {
            return new br.com.sogo.commons.largest.remainder.Number(originalOrder, value, weight, fixed);
        }
    }

    public static Number.Builder builder() {
        return new Number.Builder();
    }

    private Number(final Integer originalOrder, final BigDecimal value, final BigDecimal weight, final Boolean isFixed) {
        this.originalOrder = originalOrder;
        this.value = value;
        this.weight = weight;
        this.fixed = isFixed;
    }

    public Integer getOriginalOrder() {
        return originalOrder;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public Boolean isFixed() {
        return fixed;
    }

    /**
     * incrementa um determinado valor ao valor desta instancia de
     * {@link Number}.
     *
     * @param valueToBeIncremented
     */
    public void add(BigDecimal valueToBeIncremented) {
        this.value = this.value.add(valueToBeIncremented);
    }

    /**
     * compare two numbers by weight, if it's equal
     * then the value is compared.
     * 
     * @param a
     * @param b
     * @return 
     */
    private static int compareByWeightAndValue(Number a, Number b) {
        int weightCompareResult = a.weight.compareTo(b.weight);
        if (weightCompareResult == 0) {
            return a.value.compareTo(b.value);
        }
        return weightCompareResult;
    }

    /**
     * Recupera um comparator que compara um NumberItem com outro, levando em
     * consideraçao o peso (weight). Se desc = true, entao o comparator fara
     * ordenaçao de maneira decrescente. se desc = false, sera de maneira
     * ascendente.
     *
     * @param desc
     * @return
     */
    public static Comparator<Number> getComparatorByWeightAndValue(Boolean desc) {

        if (desc) {
            return (n1, n2) -> {
                return Number.compareByWeightAndValue(n2, n1);
            };
        }

        return (n1, n2) -> {
            return Number.compareByWeightAndValue(n1, n2);
        };
    }

    /**
     * Recupera um comparator que ordena pelo indice
     * {@link Number#originalOrder}.
     *
     * @return
     */
    public static Comparator<Number> getComparatorByOriginalOrder() {
        return (n1, n2) -> {
            return n1.originalOrder.compareTo(n2.originalOrder);
        };
    }

    @Override
    public String toString() {
        return "Number{" + "originalOrder=" + originalOrder + ", value=" + value + ", weight=" + weight + ", isFixed=" + fixed + '}';
    }
}
