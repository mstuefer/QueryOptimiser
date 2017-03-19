package io.github.mstuefer;

import java.util.*;

class IntervalSelectivityCalculator {

    private static final int LOWERBOUND_SELECTIVITY = 0;
    private static final int UPPERBOUND_SELECTIVITY = 1;
    private static final int INTERVAL_END = 0;
    private static final int FREQUENCY = 1;
    
    private TableHistogram tableHistogram;
    private DataReader dataReader;

    IntervalSelectivityCalculator(TableHistogram tableHistogram, DataReader dataReader) {
        this.tableHistogram = tableHistogram;
        this.dataReader = dataReader;
    }

    double[] getIntervalSelectivity(SelectionOperator selectionOperator) {
        return getIntervalSelectivity(
                selectionOperator.getKey(),
                selectionOperator.getOperator(),
                selectionOperator.getValue()
        );
    }

    private double[] getIntervalSelectivity(String attributeName, String operator, int value) {
        double dataLength = dataReader.getDatalength(); // amount of tuples
        LinkedHashMap<Integer, Integer[]> attributeHistogram;
        attributeHistogram = operator.equals("<") ?
                tableHistogram.getAttributeHistogram(attributeName) :
                reverseOrder(tableHistogram.getAttributeHistogram(attributeName));

        return getOperatorIntervalSelectivity(attributeHistogram, value, dataLength);
    }


    private double[] getOperatorIntervalSelectivity(LinkedHashMap<Integer, Integer[]> attributeHistogram, int value, double dataLength) {
        int currentCount = 0;
        double[] intervalSelectivity = new double[2]; // LOWERBOUND_SELECTIVITY, UPPERBOUND_SELECTIVITY
        for (Map.Entry<Integer, Integer[]> bin: attributeHistogram.entrySet()) {
            currentCount += bin.getValue()[FREQUENCY];
            if(bin.getKey() <= value && bin.getValue()[INTERVAL_END] >= value) { // value in this bucket
                intervalSelectivity[LOWERBOUND_SELECTIVITY] = (currentCount - bin.getValue()[FREQUENCY]) / dataLength;
                if(bin.getKey() == value && bin.getValue()[INTERVAL_END] == value) // 0.3,0.3,20
                    intervalSelectivity[UPPERBOUND_SELECTIVITY] = intervalSelectivity[LOWERBOUND_SELECTIVITY];
                else
                    intervalSelectivity[UPPERBOUND_SELECTIVITY] = currentCount / dataLength;
                break;
            }
        }

        return intervalSelectivity;
    }

    private LinkedHashMap<Integer, Integer[]> reverseOrder(LinkedHashMap<Integer, Integer[]> linkedHashMap) {
        LinkedHashMap<Integer, Integer[]> reverseOrderedHashMap = new LinkedHashMap<>();
        ArrayList<Integer> keys = new ArrayList<>(linkedHashMap.keySet());

        for (int i = (linkedHashMap.size() - 1); i >= 0; i--) {
            reverseOrderedHashMap.put(keys.get(i), linkedHashMap.get(keys.get(i)));
        }
        return reverseOrderedHashMap;
    }
}
