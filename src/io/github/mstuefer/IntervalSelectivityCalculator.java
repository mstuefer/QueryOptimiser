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

    double[] getIntervalSelectivity(String attributeName, int value) {

        double[] intervalSelectivity = new double[2];
        Integer[] lastIntervalFrequency = new Integer[] { null, null };
        HashMap<Integer, Integer> attributeHistogram = tableHistogram.getAttributeHistogram(attributeName);

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
}
