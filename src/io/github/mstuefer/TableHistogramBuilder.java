package io.github.mstuefer;

import java.util.ArrayList;
import java.util.List;

/**
 * TableHistogramBuilder collects a set of attributes for which it builds/reads,
 * via the TableHistogram object, a list of given histograms, and returns the
 * TableHistogram.
 * */
class TableHistogramBuilder {

    List<String> files = new ArrayList<>();

    TableHistogramBuilder addHistogram(String attributeName) {
        files.add(attributeName);
        return this;
    }

    TableHistogram build() {
        return new TableHistogram(this);
    }

}
