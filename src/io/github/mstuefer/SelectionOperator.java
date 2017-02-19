package io.github.mstuefer;

public class SelectionOperator {

    private String Key;
    private String Operator;
    private int Value;

    SelectionOperator(String Key, String Operator, int Value) {
        this.Key = Key;
        this.Operator = Operator;
        this.Value = Value;
    }

    public String getKey() {
        return Key;
    }

    public String getOperator() {
        return Operator;
    }

    public int getValue() {
        return Value;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", Key, Operator, Value);
    }
}
