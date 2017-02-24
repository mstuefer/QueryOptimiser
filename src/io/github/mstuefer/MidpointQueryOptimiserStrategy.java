package io.github.mstuefer;

import java.util.*;
import java.util.stream.Stream;

public class MidpointQueryOptimiserStrategy implements QueryOptimiserStrategy {

    private static final int LOWERBOUND = 0;
    private static final int UPPERBOUND = 1;

    private TableHistogram tableHistogram;
    private DataReader dataReader;

    private Plan midpointPlan;

    MidpointQueryOptimiserStrategy(TableHistogram tableHistogram, DataReader dataReader) {
        this.tableHistogram = tableHistogram;
        this.dataReader = dataReader;
        midpointPlan = new Plan();
    }

    @Override
    public Plan optimise(Query query) {
        List<SelectionOperator> selectionOperators = query.getSelectionOperators();

        Double[] midpoints = new Double[selectionOperators.size()];
        HashMap<SelectionOperator, Double> midpoints2 = new HashMap<>();

        for (int i = 0; i < selectionOperators.size(); i++) {
            double[] intervalSelectivity = new IntervalSelectivityCalculator(tableHistogram, dataReader)
                    .getIntervalSelectivity(selectionOperators.get(i));

            midpoints[i] = ((intervalSelectivity[UPPERBOUND] - intervalSelectivity[LOWERBOUND])/2) + intervalSelectivity[LOWERBOUND];
            midpoints2.put(selectionOperators.get(i), midpoints[i]);
        }


        Stream<Map.Entry<SelectionOperator, Double>> ascMidPoints =
                midpoints2.entrySet().stream().sorted(Map.Entry.comparingByValue());

        ascMidPoints.forEach(this::fillMidpointPlan);

        return midpointPlan;
    }

    private void fillMidpointPlan(Map.Entry<SelectionOperator, Double> entry) {
        midpointPlan.addSelectionOperator(entry.getKey(), entry.getValue());
    }

}
