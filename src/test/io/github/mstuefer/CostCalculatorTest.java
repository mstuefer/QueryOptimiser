package io.github.mstuefer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CostCalculatorTest {
    private Plan plan;
    private DataReader dataReader;
    private CostCalculator costCalculator;
    private SelectionOperator selectionOperator1;
    private SelectionOperator selectionOperator2;
    private SelectionOperator selectionOperator3;

    @BeforeEach
    void setUp() {
        plan = new Plan();
        selectionOperator1 = new SelectionOperator("key_a", "<", 50);
        selectionOperator2 = new SelectionOperator("key_b", ">", 100);
        selectionOperator3 = new SelectionOperator("key_c", "<", 10);
        plan.addSelectionOperator(selectionOperator1, 0.0);
        plan.addSelectionOperator(selectionOperator2, 0.0);

        dataReader = mock(DataReader.class);
        costCalculator = new CostCalculator(plan, dataReader);

        when(dataReader.getExactSelectivity(selectionOperator1.getKey(), selectionOperator1.getOperator(), selectionOperator1.getValue())).thenReturn(0.5);
        when(dataReader.getExactSelectivity(
                selectionOperator2.getKey(),
                selectionOperator2.getOperator(),
                selectionOperator2.getValue())).thenReturn(0.75);
        when(dataReader.getExactSelectivity(
                selectionOperator3.getKey(),
                selectionOperator3.getOperator(),
                selectionOperator3.getValue())).thenReturn(0.1);
    }

    @Test
    void testGetCostOnInitialPlan() {
        assertEquals(1.5, costCalculator.getCost(), "getCost on Plan with 2 SelectionOperators");
    }

    @Test
    void testGetCostOnExtendedPlan() {
        plan.addSelectionOperator(selectionOperator3, 0.0);
        assertEquals(1.875, costCalculator.getCost(), "getCost on Plan with 3 SelectionOperators");
    }
}
