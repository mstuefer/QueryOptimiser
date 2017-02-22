package io.github.mstuefer;

class SelectionOperator {

    private String Key;
    private String Operator;
    private int Value;

    SelectionOperator(String Key, String Operator, int Value) {
        this.Key = Key;
        this.Operator = Operator;
        this.Value = Value;
    }

    String getKey() {
        return Key;
    }

    String getOperator() {
        return Operator;
    }

    int getValue() {
        return Value;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", Key, Operator, Value);
    }
}
