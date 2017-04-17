# Query Optimiser

A little basic Java-App which allows to test different algorithms at the task of: 
'Ordering Selection Operators under Conditions of Uncertainity' 

## Installation

Clone the repository:

```
    git clone https://github.com/mstuefer/QueryOptimiser.git
```

Compile the source files:

```
    cd QueryOptimiser
    mkdir out
    javac -d out -cp src/ src/io/github/mstuefer/*.java
```

Add the necessary data files, which contain the queries and the histograms:

```
    cd out/io/github/mstuefer/
    ln -s ../../../../data/
    cd -
```

**N.B.**: The provided data files are only toy examples, you should substitute them with
your own datafile, queriesfile and histogramfiles for serious testing.

## Usage

To simply test if everything works fine, launch the following:

```
    java -cp out io.github.mstuefer.Main
```

The output should look like:

```

Costs of all plans (opt) :: 3.0
Costs of all plans (mdp) :: 3.0

avg cost per plan optimised via opt :: 1.5
avg cost per plan optimised via mdp :: 1.5

```

Now create your own QueryOptimiserStrategy.java in src/io/github/mstuefer, implementing 
the QueryOptimiserStrategy interface. Then adapt the Main.java accordingly, to use your
strategy, and get the costs of it. Finally run the following to compare the costs of
your strategy with the ones of the optimal- and the ones of the midpoint-strategy.

```
    javac -d out -cp src/ src/io/github/mstuefer/*.java
    java -cp out io.github.mstuefer.Main
```

## Authors

* **Manuel Stuefer** 

## License

This project is licensed under the MIT License.