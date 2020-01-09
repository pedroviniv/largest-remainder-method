package br.com.sogo.commons.largest.remainder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class LargestRemainder {

    /**
     * Recupera a parte "fracional" do number passado da casa decimal
     * "fromDecimalPlaces" em diante. Ex: Se o number passado for 68.78956, e
     * fromDecimalPlaces = 2, a parte fracional retornada sera da segunda casa
     * decimal em diante, 0.00956.
     *
     * @param number numero que tera sua parte fracional recuperada
     * @param fromDecimalPlaces numero de casas decimais de onde deve ser
     * extraido a parte fracional
     * @return
     */
    private BigDecimal getRemainder(BigDecimal number, final Integer fromDecimalPlaces) {
        return number.remainder(BigDecimal.valueOf(1 / Math.pow(10, fromDecimalPlaces)));
    }

    /**
     * recupera o menor valor de incremento possivel baseado na quantidade de
     * casas decimais. ex: se for duas casas decimais, o incremento sera de
     * 0.01, se for 3 casas decimais, sera 0.001, e assim por diante.
     *
     * @param decimalPlaces
     * @return
     */
    private BigDecimal getIncrementor(final Integer decimalPlaces, final Boolean negative) {
        return BigDecimal.ONE.divide(BigDecimal.valueOf(Math.pow(10, decimalPlaces))).multiply(BigDecimal.valueOf(negative ? 1 : -1));
    }

    /**
     * Devolve uma funçao callback que para cada indice (numero inteiro
     * apontando para um determinado elemento da lista),<br> uma lista de
     * porcentagens e o numero de casas decimais, cria uma instancia de
     * NumberItem com o valor<br>
     * sem a parte fracional (a partir do num de casas decimais informados), a
     * parte fracional<br> (tambem a partir do num de casas decimais
     * informadas).
     *
     * @param percentagens porcentagens a serem convertidas para NumberItem
     * @param decimalPlaces numero de casas decimais a ser utilizada para
     * extrair o valor sem a parte fracional e a parte fracional.
     *
     * @return
     */
    private IntFunction<Number> toNumberItem(final List<BigDecimal> percentagens, final Integer decimalPlaces, final List<Integer> fixedNumbers) {

        return (index) -> {
            BigDecimal current = percentagens.get(index);

            return Number.builder()
                    .originalOrder(index)
                    .value(current.setScale(decimalPlaces, RoundingMode.FLOOR))
                    .weight(getRemainder(current, decimalPlaces))
                    .isFixed(fixedNumbers.contains(index))
                    .build();
        };
    }

    /**
     * Apenas um atalho para {@link LargestRemainder#distributeRemainder(java.util.List, java.math.BigDecimal, java.lang.Integer)
     * }
     * que recebe, ao inves de instancias de {@link BigDecimal}, instancias de
     * Double e Integer.
     *
     * @param percentages
     * @param total
     * @param decimalPlaces
     * @return
     */
    public List<BigDecimal> distributeRemainder(List<Double> percentages, final Integer total, final Integer decimalPlaces, final List<Integer> fixedNumbers) throws IllegalArgumentException {

        final List<BigDecimal> bigPercentages = percentages.stream()
                .map(BigDecimal::valueOf)
                .collect(Collectors.toList());

        return this.distributeRemainder(bigPercentages, BigDecimal.valueOf(total), decimalPlaces, fixedNumbers);
    }

    /**
     * Apenas um atalho para {@link LargestRemainder#distributeRemainder(java.util.List, java.math.BigDecimal, java.lang.Integer)
     * }
     * que recebe, ao inves de instancias de {@link BigDecimal}, instancias de
     * Double.
     *
     * @param percentages
     * @param total
     * @param decimalPlaces
     * @return
     */
    public List<BigDecimal> distributeRemainder(List<Double> percentages, final Double total, final Integer decimalPlaces, final List<Integer> fixedNumbers) throws IllegalArgumentException {

        final List<BigDecimal> bigPercentages = percentages.stream()
                .map(BigDecimal::valueOf)
                .collect(Collectors.toList());

        return this.distributeRemainder(bigPercentages, BigDecimal.valueOf(total), decimalPlaces, fixedNumbers);
    }

    public List<BigDecimal> distributeRemainder(List<Double> percentages, final Integer total, final Integer decimalPlaces) throws IllegalArgumentException {
        return this.distributeRemainder(percentages, total, decimalPlaces, new ArrayList<>());
    }

    public List<BigDecimal> distributeRemainder(List<Double> percentages, final Double total, final Integer decimalPlaces) throws IllegalArgumentException {
        return this.distributeRemainder(percentages, total, decimalPlaces, new ArrayList<>());
    }

    public List<BigDecimal> distributeRemainder(List<BigDecimal> percentages, final BigDecimal total, final Integer decimalPlaces) throws IllegalArgumentException {
        return this.distributeRemainder(percentages, total, decimalPlaces, new ArrayList<>());
    }

    /**
     * Arredonda todos as porcentagens passadas de maneira que a soma delas
     * totalizem o valor "total" passado. A soluçao utilizada para distribuir a
     * parte arredondada foi a do Largest remainder method. Esta soluçao e
     * comentada na thread abaixo do stack overflow.
     *
     * https://stackoverflow.com/a/13483710
     *
     * @param percentages
     * @param total
     * @param decimalPlaces
     * @return
     */
    public List<BigDecimal> distributeRemainder(List<BigDecimal> percentages, final BigDecimal total, final Integer decimalPlaces, final List<Integer> fixedNumbers) throws IllegalArgumentException {

        if (decimalPlaces == null || decimalPlaces < 0) {
            throw new IllegalArgumentException("A casa decimal informada \"decimalPlaces\" nao deve ser nula e deve ser maior > -1. valor passado: " + decimalPlaces);
        }

        if (percentages == null || percentages.isEmpty()) {
            return new ArrayList<>();
        }

        /**
         * quantidade de porcentagens passadas na lista
         */
        final Integer percentageListSize = percentages.size();

        /**
         * Calculando a soma de todos os elementos arredondados
         */
        final BigDecimal sum = percentages.stream()
                .map(p -> p.setScale(decimalPlaces, RoundingMode.FLOOR))
                .reduce((prev, next) -> prev.add(next))
                .get();

        /**
         * Verificando se a array tera que ser ordenada de maneira decrescente
         * ou crescente, caso a soma atual seja maior do que o total desejado,
         * entao tera que ser ascendente, pois os valores serao decrementados do
         * menor para o maior ate que se iguale ao total desejado.
         */
        final Boolean desc = total.compareTo(sum) > 0;

        /**
         * Mapeando os numeros para a estrutura de dados NumberItem que possui,
         * alem do valor numerico arredondado, a parte fracional do numero a
         * partir da casa decimal informada no metodo. ex: 56.7899, se for
         * informado duas casas decimais, a parte fracional (weight) sera 0.0099
         */
        final List<Number> numbers = IntStream.range(0, percentageListSize)
                .mapToObj(toNumberItem(percentages, decimalPlaces, fixedNumbers))
                .sorted(Number.getComparatorByWeight(desc))
                .collect(Collectors.toList());

        /**
         * Recuperando o valor que sera incrementado ou decrementado dos valores
         * passados ate a soma deles seja igual ao total desejado.
         */
        final BigDecimal incrementor = getIncrementor(decimalPlaces, desc);

        /**
         * Calculando numero de incrementos/decrementos necessarios para que a
         * soma dos valores resultem no total desejado.
         */
        Integer iterationTimes = total.subtract(sum).abs()
                .multiply(BigDecimal.valueOf(Math.pow(10, decimalPlaces)))
                .intValue();

        /**
         * Iterando o numero calculado acima, alterando os valores numericos de
         * maneira que no final da iteraçao tenhamos certeza de que a soma de
         * todos os valores resultem no total desejado.
         */
        while (iterationTimes > 0) {

            for (Number currentNumber : numbers) {

                if (iterationTimes < 1) {
                    break;
                }

                if (currentNumber.isFixed()) {
                    continue;
                }

                currentNumber.add(incrementor);
                iterationTimes -= 1;
            }
        }

        /**
         * Ordenando os valores resultantes pela ordem original (ordem de
         * entrada no metodo) e devolvendo ao client.
         */
        return numbers.stream()
                .sorted(Number.getComparatorByOriginalOrder())
                .map(n -> n.getValue())
                .collect(Collectors.toList());
    }
}
