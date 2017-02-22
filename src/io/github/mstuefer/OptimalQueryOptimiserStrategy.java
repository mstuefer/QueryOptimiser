package io.github.mstuefer;

import java.util.*;

final class OptimalQueryOptimiserStrategy implements QueryOptimiserStrategy {

    private DataReader dataReader;

    OptimalQueryOptimiserStrategy(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public Plan optimise(Query query) {
        Plan optimalPlan = new Plan();
        Map<SelectionOperator, Double> selectivities = new HashMap<>();

        for (SelectionOperator selectionOperator: query.getSelectionOperators()) {
            double selectivity = dataReader.getExactSelectivity(selectionOperator.getKey(), selectionOperator.getOperator(), selectionOperator.getValue());
            selectivities.put(selectionOperator, selectivity);
        }

        for (Map.Entry<SelectionOperator, Double> entry:  sortByValue(selectivities).entrySet()) {
            optimalPlan.addSelectionOperator(entry.getKey(), entry.getValue());
        }
        return optimalPlan;
    }


    private static Map<SelectionOperator, Double> sortByValue(Map<SelectionOperator, Double> unsortMap) {
        List<Map.Entry<SelectionOperator, Double>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Comparator.comparing(o -> (o.getValue())));

        Map<SelectionOperator, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<SelectionOperator, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
