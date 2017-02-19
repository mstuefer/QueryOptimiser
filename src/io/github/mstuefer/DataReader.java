package io.github.mstuefer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

class DataReader {

    private Hashtable<String, List<Integer>> data;

    DataReader(String filename) {

        data = new Hashtable<>();

        try {
            BufferedReader rd = new BufferedReader( new FileReader(filename));
            String line;

            while ((line = rd.readLine()) != null){
                List<String> attributes = Arrays.asList(line.split(","));
                addTupleToData(attributes);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }

    String getLineNumbers() {
        return data.get("linenumber").toString();
    }

    private void addTupleToData(List<String> attributes) {
        addAttributeValueToData("orderKey", attributes.get(0));
        addAttributeValueToData("linenumber", attributes.get(1));
        addAttributeValueToData("custkey", attributes.get(3));
        addAttributeValueToData("suppkey", attributes.get(4));

        double quantity = Double.parseDouble(attributes.get(5).replace("\"",""));
        addAttributeValueToData("quantity", String.valueOf((int)quantity));

        double ordtotalprice = Double.parseDouble(attributes.get(6).replace("\"",""));
        addAttributeValueToData("ordtotalprice", String.valueOf((int)ordtotalprice));
        addAttributeValueToData("revenue", attributes.get(7));
        addAttributeValueToData("supplycost", attributes.get(8));
        addAttributeValueToData("size", attributes.get(14).replace("\"",""));
    }

    private void addAttributeValueToData(String key, String value) {
        if(data.containsKey(key))
            data.get(key).add(Integer.parseInt(value));
        else
            data.put(key, new ArrayList<Integer>() {{add(Integer.parseInt(value));}});
    }

}
