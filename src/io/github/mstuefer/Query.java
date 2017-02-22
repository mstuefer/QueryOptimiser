package io.github.mstuefer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Query {

    private List<SelectionOperator> selectionOperators;

    Query() {
        selectionOperators = new ArrayList<>();
    }

    void addSelectionOperator(String operator) {
        List<String> items = Arrays.asList(operator.split(" "));

        selectionOperators.add(new SelectionOperator(items.get(0), items.get(1), Integer.parseInt(items.get(2))));
    }

    List<SelectionOperator> getSelectionOperators() {
        return selectionOperators;
    }

    @Override
    public String toString() {
        return selectionOperators.toString();
    }
}
