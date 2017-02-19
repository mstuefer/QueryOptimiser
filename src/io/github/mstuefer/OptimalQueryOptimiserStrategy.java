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
        Map<SelectionOperator, Integer> selectivities = new HashMap<>();

        for (SelectionOperator selectionOperator: query.getSelectionOperators()) {
            int selectivity = dataReader.getSelectivity(selectionOperator.getKey(), selectionOperator.getOperator(), selectionOperator.getValue());
            selectivities.put(selectionOperator, selectivity);
        }

        for (Map.Entry<SelectionOperator, Integer> entry:  sortByValue(selectivities).entrySet()) {
            optimalPlan.addSelectionOperator(entry.getKey(), entry.getValue());
        }
        return optimalPlan;
    }


    private static Map<SelectionOperator, Integer> sortByValue(Map<SelectionOperator, Integer> unsortMap) {
        List<Map.Entry<SelectionOperator, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Comparator.comparing(o -> (o.getValue())));

        Map<SelectionOperator, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<SelectionOperator, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
