package io.github.mstuefer;


import java.util.HashMap;

/**
 * QueryOptimiser is a little (very basic) Java-App which allows
 * to test/compare different algorithms for ordering selection
 * operators.
 * */
class Main {

    // java Main ./data/table.csv ./data/queries.txt
    public static void main(String[] args) {
        //dataReader = new DataReader(args[0]);
        DataReader dataReader = new DataReader("./data/table.csv");
        System.out.println(dataReader.getLineNumbers());

        //queryReader = new QueryReader(args[1]);
        QueryReader queryReader = new QueryReader("./data/queries.txt");
        System.out.println(queryReader.getQueries().get(0).getSelectionOperators().get(1));

        for (Query query : queryReader.getQueries()) {
            Plan optimalPlan = new QueryOptimiser(new OptimalQueryOptimiserStrategy(dataReader)).optimise(query);

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

            HashMap<String, HashMap<Integer, Integer>> allRequestedAttributeHistograms =
                    tableHistogram.getAllRequestedAttributeHistograms();

            System.out.println(allRequestedAttributeHistograms);
            System.out.println(tableHistogram.getAttributeHistogram("orderKey"));
            System.out.println(tableHistogram.getAttributeHistogram("supplycost"));

            System.out.println(optimalPlan);
            System.out.println("Cost of optimalPlan: "+new CostCalculator(optimalPlan, dataReader).getCost());

        }
    }
}
