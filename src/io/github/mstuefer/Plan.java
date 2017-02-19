package io.github.mstuefer;

import java.util.LinkedHashMap;
import java.util.Map;

public class Plan {

    private Map<SelectionOperator, Integer> selectionOperators;

    Plan() {
        selectionOperators = new LinkedHashMap<>();
    }

    void addSelectionOperator(SelectionOperator selectionOperator, int selectivity) {
        selectionOperators.put(selectionOperator, selectivity);
    }

    @Override
    public String toString() {
        return selectionOperators.toString();
    }

}
