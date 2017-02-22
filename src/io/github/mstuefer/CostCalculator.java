package io.github.mstuefer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CostCalculator calculates the real costs of a given plan on given data.
 *
 * To do so, the AVI (Attribute Value Independence) assumption is made,
 * the cardinality is assumed to be 1 and the cost per tuple of evaluating an
 * operator is also assumed to be 1. Therefore cardinality Ω and cost are
 * left out of the cost-formula within getCost(). (NB: Since so far, only the
 * ordering of selection operators is of interest within the present QueryOptimiser,
 * those assumptions can be made.)
 *
 * A plan is a permutation of n selection operators, each selection operator
 * has a selectivity and a cost, where the selectivity ∈ [0,1] and cost=1 as
 * already mentioned.
 *
 * General Cost Formula: Ω(Σ(Π(selectivity)cost))
 *  Ω: 1
 *  Σ: i=1..n
 *  Π: j=1..(i-1)
 *  selectivity: ∈ [0,1]
 *  cost: 1
 *
 * */
class CostCalculator {

    private Plan plan;
    private DataReader dataReader;

    CostCalculator(Plan plan, DataReader dataReader) {
        this.plan = plan;
        this.dataReader = dataReader;
    }

    double getCost() {
        double cost = 1;

        List<SelectionOperator> selectionOperators = new ArrayList<>(plan.getSelectionOperators().keySet());
        double[] selectivities = new double[selectionOperators.size()];

        for (int i = 0; i < selectionOperators.size() - 1; i++) {
            SelectionOperator so = selectionOperators.get(i);
            selectivities[i] = dataReader.getSelectivity(so.getKey(), so.getOperator(), so.getValue());
            cost += multiplySelectivities(selectivities, i);
        }
        return cost;
    }

    private double multiplySelectivities(double[] selectivities, int j) {
        return Arrays.stream(Arrays.copyOfRange(selectivities, 0, j+1)).reduce(1, (a, b) -> a * b);
    }

}
