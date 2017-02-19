package io.github.mstuefer;

public interface QueryOptimiserStrategy {

    Plan optimise(Query query);

}
