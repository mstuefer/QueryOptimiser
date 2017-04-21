package io.github.mstuefer;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryOptimiser is a little (very basic) Java-App which allows
 * to test/compare different algorithms for ordering selection
 * operators.
 * */
class Main {

    public static void main(String[] args) {
        String dataType = "";
        DataReader dataReader = new DataReader("./data/"+dataType+"/table.csv");

        QueryReader queryReader = new QueryReader("./data/"+dataType+"/queries.txt");

        TableHistogram tableHistogram = new TableHistogramBuilder()
                .addHistogram("custkey")
                .addHistogram("linenumber")
                .addHistogram("orderKey")
                .addHistogram("ordtotalprice")
                .addHistogram("quantity")
                .addHistogram("revenue")
                .addHistogram("size")
                .addHistogram("suppkey")
                .addHistogram("supplycost")
                .build(dataType);

        List<Double> optCosts = new ArrayList<>();
        List<Double> mdpCosts = new ArrayList<>();

        for (Query query : queryReader.getQueries()) {
            Plan optimalPlan = new QueryOptimiser(new OptimalQueryOptimiserStrategy(dataReader)).optimise(query);
            Plan midpointPlan = new QueryOptimiser(new MidpointQueryOptimiserStrategy(tableHistogram, dataReader)).optimise(query);

            // Print selectivities on stdout for manually analysing queries
            System.out.println(query);
            for (SelectionOperator selectionOperator: query.getSelectionOperators()) {
                double exactSelectivity = dataReader.getExactSelectivity(selectionOperator.getKey(), selectionOperator.getOperator(), selectionOperator.getValue());
                double[] intervalSelectivity = new IntervalSelectivityCalculator(tableHistogram, dataReader).getIntervalSelectivity(selectionOperator);

                System.out.printf("|");
                for (int i = 0; i < 100; i++) {
                    if(i > (intervalSelectivity[0] * 100) && i < (intervalSelectivity[1] * 100))
                        System.out.printf(".");
                    else
                        System.out.printf(" ");
                }
                System.out.printf("|\t %s with exact selectivity of (%.5f) ", selectionOperator.getKey(), exactSelectivity);
                System.out.printf("and intervals selectivity of (%.5f, %.5f)", intervalSelectivity[0], intervalSelectivity[1]);
                System.out.println();
            }

            double optimalCost = new CostCalculator(optimalPlan, dataReader).getCost();
            optCosts.add(optimalCost);

            double midpointCost = new CostCalculator(midpointPlan, dataReader).getCost();
            mdpCosts.add(midpointCost);

            System.out.println("cost(optimal plan): "+optimalCost+", cost(midpoint plan): "+midpointCost);
            System.out.println();
        }

        double sumOptCosts = optCosts.stream().mapToDouble(Double::doubleValue).sum();
        double sumMdpCosts = mdpCosts.stream().mapToDouble(Double::doubleValue).sum();

        System.out.println();
        System.out.println("Costs of all (optimal) plans :: "+sumOptCosts);
        System.out.println("Costs of all (midpoint) plans :: "+sumMdpCosts);
        System.out.println();

        System.out.println("avg cost per (optimal) plan :: "+(sumOptCosts/queryReader.getQueries().size()));
        System.out.println("avg cost per (midpoint) plan :: "+(sumMdpCosts/queryReader.getQueries().size()));
        System.out.println();

    }
}
