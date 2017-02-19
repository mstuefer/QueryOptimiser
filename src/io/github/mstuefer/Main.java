package io.github.mstuefer;

public class Main {

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

            System.out.println(optimalPlan);
        }
    }
}
