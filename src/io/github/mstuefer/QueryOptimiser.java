package io.github.mstuefer;

class QueryOptimiser {

    private QueryOptimiserStrategy strategy;

    QueryOptimiser(QueryOptimiserStrategy strategy) {
        this.strategy = strategy;
    }


    Plan optimise(Query query) {
        return this.strategy.optimise(query);
    }
}
