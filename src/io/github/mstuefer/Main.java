package io.github.mstuefer;

public class Main {

    private static DataReader dataReader;
    private static QueryReader queryReader;

    // java Main table.csv
    public static void main(String[] args) {
        //dataReader = new DataReader(args[0]);
        dataReader = new DataReader("./data/table.csv");
        System.out.println(dataReader.getLineNumbers());

        //queryReader = new QueryReader(args[1]);
        queryReader = new QueryReader("./data/queries.txt");
        System.out.println(queryReader.getQueries().get(0).getSelectionOperators().get(1));
    }
}
