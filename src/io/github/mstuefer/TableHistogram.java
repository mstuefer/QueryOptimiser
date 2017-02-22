package io.github.mstuefer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * TableHistogram reads, for each requested attribute, via the TableHistogramBuilder,
 * the corresponding histogram from each csv-file of name 'data/<attributeName>.histogram'.
 *
 * csv-file format: interval,width,frequency
 *
 * Instead of calculating the exact histograms/statistics out of the data/table, they
 * are read from files, in order to be able to simulate 'dirty' statistics.
 * */
class TableHistogram {

    private HashMap<String, HashMap<Integer, Integer>> allRequestedAttributeHistograms;

    TableHistogram(TableHistogramBuilder builder) {
        allRequestedAttributeHistograms = new HashMap<>();
        for (String attributeName: builder.files) {
            allRequestedAttributeHistograms.put(attributeName, readHistogram(attributeName));
        }
    }

    HashMap<String, HashMap<Integer, Integer>> getAllRequestedAttributeHistograms() {
        return allRequestedAttributeHistograms;
    }

    HashMap<Integer, Integer> getAttributeHistogram(String attributeName) {
        return allRequestedAttributeHistograms.get(attributeName);
    }

    private HashMap<Integer, Integer> readHistogram(String attributeName) {
        HashMap<Integer, Integer> histogram = new HashMap<>();
        try {
            BufferedReader rd = new BufferedReader( new FileReader("data/"+attributeName+".histogram"));
            String line;

            while ((line = rd.readLine()) != null){
                String[] bin = line.split(",");
                histogram.put(Integer.parseInt(bin[0]),Integer.parseInt(bin[2]));
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return histogram;
    }

}
