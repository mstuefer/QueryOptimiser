package io.github.mstuefer;

import java.util.LinkedHashMap;
import java.util.Map;

public class Plan {

    private Map<SelectionOperator, Double> selectionOperators;

    Plan() {
        selectionOperators = new LinkedHashMap<>();
    }

    void addSelectionOperator(SelectionOperator selectionOperator, Double selectivity) {
        selectionOperators.put(selectionOperator, selectivity);
    }

    Map<SelectionOperator, Double> getSelectionOperators() {
        return selectionOperators;
    }

    @Override
    public String toString() {
        return selectionOperators.toString();
    }

}
