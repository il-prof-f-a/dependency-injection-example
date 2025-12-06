package proffa.dependency.injection;

public class SubtractOperation extends BinaryOperation {

    public SubtractOperation(Notifier notifier) {
        super(notifier);
    }

    @Override
    protected double compute(double a, double b) {
        return a - b;
    }

    @Override
    protected String getSymbol() {
        return "-";
    }
}