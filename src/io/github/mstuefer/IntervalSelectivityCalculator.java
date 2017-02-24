package io.github.mstuefer;

import java.util.HashMap;
import java.util.Map;

class IntervalSelectivityCalculator {

    private static final int LOWERBOUND_SELECTIVITY = 0;
    private static final int UPPERBOUND_SELECTIVITY = 1;
    private static final int KEY = 0;
    private static final int VALUE = 1;
    
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
        HashMap<Integer, Integer> attributeHistogram = tableHistogram.getAttributeHistogram(attributeName);

        switch (operator) {
            case "=":
                return getEqualoperatorIntervalSelectivity(attributeHistogram, value);
            case "<":
                return getLessthanOperatorIntervalSelectiviy(attributeHistogram, value, dataLength);
            case ">":
                return getMorethanOperatorIntervalSelectivity(attributeHistogram, value, dataLength);
        }

        return null;
    }

    private double[] getEqualoperatorIntervalSelectivity(HashMap<Integer, Integer> attributeHistogram, int value) {
        double[] intervalSelectivity = new double[2]; // LOWERBOUND_SELECTIVITY, UPPERBOUND_SELECTIVITY
        Integer[] lastIntervalFrequency = new Integer[] { null, null };

        for (Map.Entry<Integer, Integer> entry: attributeHistogram.entrySet()){
            if(lastIntervalFrequency[KEY] == null) {
                lastIntervalFrequency[KEY] = entry.getKey();
                lastIntervalFrequency[VALUE] = entry.getValue();
                continue;
            }

            if(entry.getKey() > value) {
                double dataLength = dataReader.getDatalength();
                intervalSelectivity[LOWERBOUND_SELECTIVITY] =
                        (lastIntervalFrequency[1] - attributeHistogram.get(lastIntervalFrequency[0]))/dataLength;
                intervalSelectivity[UPPERBOUND_SELECTIVITY] = (lastIntervalFrequency[1])/dataLength;
                break;
            }
            lastIntervalFrequency[KEY] = entry.getKey();
            lastIntervalFrequency[VALUE] += entry.getValue();
        }
        return intervalSelectivity;
    }

    private double[] getLessthanOperatorIntervalSelectiviy(HashMap<Integer, Integer> attributeHistogram, int value, double dataLength) {
        int currentCount = 0;
        double[] intervalSelectivity = new double[2]; // LOWERBOUND_SELECTIVITY, UPPERBOUND_SELECTIVITY
        for (Map.Entry<Integer, Integer> bin: attributeHistogram.entrySet()) {
            currentCount += bin.getValue();
            if(bin.getKey() > value) {
                intervalSelectivity[LOWERBOUND_SELECTIVITY] = 0.0;
                intervalSelectivity[UPPERBOUND_SELECTIVITY] = (currentCount - bin.getValue()) / dataLength;
                break;
            }
        }
        return intervalSelectivity;
    }

    private double[] getMorethanOperatorIntervalSelectivity(HashMap<Integer, Integer> attributeHistogram, int value, double dataLength) {
        int currentCount = 0;
        double[] intervalSelectivity = new double[2]; // LOWERBOUND_SELECTIVITY, UPPERBOUND_SELECTIVITY
        for (Map.Entry<Integer, Integer> entry: attributeHistogram.entrySet()) {
            if(entry.getKey() < value) {
                currentCount = entry.getValue();
                continue;
            }
            currentCount += entry.getValue();
        }

        intervalSelectivity[LOWERBOUND_SELECTIVITY] = 1 - (currentCount / dataLength);
        intervalSelectivity[UPPERBOUND_SELECTIVITY] = 1.0;
        return intervalSelectivity;
    }
}
