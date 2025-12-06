package proffa.dependencyinjection;

public class SumOperation extends BinaryOperation {

    public SumOperation(Notifier notifier) {
        super(notifier);
    }

    @Override
    protected double compute(double a, double b) {
        return a + b;
    }

    @Override
    protected String getSymbol() {
        return "+";
    }
}