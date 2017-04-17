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
        DataReader dataReader = new DataReader("./data/table.csv");

        QueryReader queryReader = new QueryReader("./data/queries.txt");

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
                .build();

        List<Double> optCosts = new ArrayList<>();
        List<Double> mdpCosts = new ArrayList<>();

        for (Query query : queryReader.getQueries()) {
            Plan optimalPlan = new QueryOptimiser(new OptimalQueryOptimiserStrategy(dataReader)).optimise(query);
            Plan midpointPlan = new QueryOptimiser(new MidpointQueryOptimiserStrategy(tableHistogram, dataReader)).optimise(query);

            double optimalCost = new CostCalculator(optimalPlan, dataReader).getCost();
            optCosts.add(optimalCost);

            double midpointCost = new CostCalculator(midpointPlan, dataReader).getCost();
            mdpCosts.add(midpointCost);
        }

        double sumOptCosts = optCosts.stream().mapToDouble(Double::doubleValue).sum();
        double sumMdpCosts = mdpCosts.stream().mapToDouble(Double::doubleValue).sum();

        System.out.println();
        System.out.println("Costs of all plans (opt) :: "+sumOptCosts);
        System.out.println("Costs of all plans (mdp) :: "+sumMdpCosts);
        System.out.println();

        System.out.println("avg cost per plan optimised via opt :: "+(sumOptCosts/queryReader.getQueries().size()));
        System.out.println("avg cost per plan optimised via mdp :: "+(sumMdpCosts/queryReader.getQueries().size()));
        System.out.println();

    }
}
