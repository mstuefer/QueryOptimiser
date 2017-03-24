package io.github.mstuefer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * TableHistogram reads, for each requested attribute, via the TableHistogramBuilder,
 * the corresponding histogram from each csv-file of name 'data/<attributeName>.histogram'.
 *
 * csv-file format: interval,width,frequency
 *
 * Instead of calculating the exact histograms/statistics out of the data/table, they
 * are read from files, in order to be able to simulate 'dirty' statistics.
 * */
public class TableHistogram {

    private static final int INTERVAL_START = 0;
    private static final int INTERVAL_END = 1;
    private static final int FREQUENCY = 2;

    private LinkedHashMap<String, LinkedHashMap<Integer, Integer[]>> allRequestedAttributeHistograms;

    TableHistogram(TableHistogramBuilder builder) {
        allRequestedAttributeHistograms = new LinkedHashMap<>();
        for (String attributeName: builder.files) {
            allRequestedAttributeHistograms.put(attributeName, readHistogram(attributeName));
        }
    }

    public LinkedHashMap<Integer, Integer[]> getAttributeHistogram(String attributeName) {
        return allRequestedAttributeHistograms.get(attributeName);
    }

    // <interval_start [interval_end, frequency]>
    private LinkedHashMap<Integer, Integer[]> readHistogram(String attributeName) {
        LinkedHashMap<Integer, Integer[]> histogram = new LinkedHashMap<>();
        try {
            BufferedReader rd = new BufferedReader( new FileReader("data/bigmod/"+attributeName+".txt"));
            String line;

            while ((line = rd.readLine()) != null){
                String[] bin = line.split(",");
                Integer[] intervalEndAndFrequency = {Integer.parseInt(bin[INTERVAL_END]), Integer.parseInt(bin[FREQUENCY])};
                histogram.put(Integer.parseInt(bin[INTERVAL_START]), intervalEndAndFrequency);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return histogram;
    }

}
