package io.github.mstuefer;

public class Main {

    private static DataReader dataReader;

    // java Main table.csv
    public static void main(String[] args) {
        //dataReader = new DataReader(args[0]);
        dataReader = new DataReader("./data/table.csv");

        System.out.println(dataReader.getLineNumbers());
    }
}
