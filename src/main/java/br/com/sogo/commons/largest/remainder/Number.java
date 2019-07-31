/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sogo.commons.largest.remainder;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Number {

    private final Integer originalOrder;
    private BigDecimal value;
    private final BigDecimal weight;

    public static class Builder {

        private Integer originalOrder;
        private BigDecimal value;
        private BigDecimal weight;

        private Builder() {
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

        public Number build() {
            return new br.com.sogo.commons.largest.remainder.Number(originalOrder, value, weight);
        }
    }

    public static Number.Builder builder() {
        return new Number.Builder();
    }

    private Number(final Integer originalOrder, final BigDecimal value, final BigDecimal weight) {
        this.originalOrder = originalOrder;
        this.value = value;
        this.weight = weight;
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
         * Recupera um comparator que compara um NumberItem com outro, levando
         * em consideraçao o peso (weight). Se desc = true, entao o comparator
         * fara ordenaçao de maneira decrescente. se desc = false, sera de
         * maneira ascendente.
         *
         * @param desc
         * @return
         */
        public static Comparator<Number> getComparatorByWeight(Boolean desc) {

            if (desc) {
                return (n1, n2) -> {
                    return n2.weight.compareTo(n1.weight);
                };
            }

            return (n1, n2) -> {
                return n1.weight.compareTo(n2.weight);
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
            return "Number{" + "originalOrder=" + originalOrder + ", value=" + value + ", weight=" + weight + '}';
        }
}
