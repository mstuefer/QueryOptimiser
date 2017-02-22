package io.github.mstuefer;

interface QueryOptimiserStrategy {

    Plan optimise(Query query);

}
