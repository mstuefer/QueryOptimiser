package io.github.mstuefer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class IntervalSelectivityCalculatorTest {
    private TableHistogram tableHistogram;
    private DataReader dataReader;
    private SelectionOperator selectionOperator;
    private IntervalSelectivityCalculator intervalSelectivityCalculator;
    private LinkedHashMap<Integer, Integer[]> attributeHistogram;

    private final String KEY = "fakeAttribute";
    private final int DATALENGTH = 100;

    @BeforeEach
    void setUp() {
        tableHistogram = mock(TableHistogram.class);
        dataReader = mock(DataReader.class);

        attributeHistogram = new LinkedHashMap<>();
        attributeHistogram.put(0, new Integer[] {10, 5});
        attributeHistogram.put(11, new Integer[] {20, 10});
        attributeHistogram.put(21, new Integer[] {30, 15});
        attributeHistogram.put(31, new Integer[] {40, 10});
        attributeHistogram.put(41, new Integer[] {50, 5});
        attributeHistogram.put(51, new  Integer[] {60, 5});
        attributeHistogram.put(61, new Integer[] {70, 5});
        attributeHistogram.put(71, new Integer[] {80, 20});
        attributeHistogram.put(81, new Integer[] {90, 20});
        attributeHistogram.put(91, new Integer[] {100, 5});

        intervalSelectivityCalculator = new IntervalSelectivityCalculator(tableHistogram, dataReader);

        when(dataReader.getDatalength()).thenReturn(DATALENGTH);

        when(tableHistogram.getAttributeHistogram(KEY)).thenReturn(attributeHistogram);
    }

    @Test
    void testGetLessThanIntervalSelectivity() {
        selectionOperator = new SelectionOperator(KEY, "<", 35);
        assertArrayEquals(
                new double[]{0.3, 0.4},
                intervalSelectivityCalculator.getIntervalSelectivity(selectionOperator)
        );
    }

    @Test
    void teatGetGreaterThanIntervalSelectivity() {
        selectionOperator = new SelectionOperator(KEY, ">", 35);
        assertArrayEquals(
                new double[] {.6, .7},
                intervalSelectivityCalculator.getIntervalSelectivity(selectionOperator)
        );

    }

    @Test
    void testWrongOperatorOnGetIntervalSelectivity() {
        selectionOperator = new SelectionOperator(KEY, "?", 35);
        Throwable exception =
                assertThrows(
                        AssertionError.class,
                        () -> intervalSelectivityCalculator.getIntervalSelectivity(selectionOperator)
                );

        assertEquals("Given operator not supported", exception.getMessage());
    }

}