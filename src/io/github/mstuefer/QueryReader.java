package io.github.mstuefer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class QueryReader {

    private List<Query> queries;

    QueryReader(String filename) {
        queries = new ArrayList<>();

        try {
            BufferedReader rd = new BufferedReader( new FileReader(filename));
            String query;

            while ((query = rd.readLine()) != null){
                String cleanQuery = query.replace(",", "").replace(";", "");
                List<String> selectionOperatorsInQuery = Arrays.asList(cleanQuery.split(" AND "));
                addQuery(selectionOperatorsInQuery);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private void addQuery(List<String> selectionOperators) {
        Query query = new Query();

        for (String selectionOperator: selectionOperators) {
            query.addSelectionOperator(selectionOperator);
        }

        queries.add(query);
    }

    List<Query> getQueries() {
        return queries;
    }
}
