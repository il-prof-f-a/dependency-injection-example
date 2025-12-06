package proffa.dependency.injection;

public class MultiplyOperation extends BinaryOperation {

    public MultiplyOperation(Notifier notifier) {
        super(notifier);
    }

    @Override
    protected double compute(double a, double b) {
        return a * b;
    }

    @Override
    protected String getSymbol() {
        return "*";
    }
}
