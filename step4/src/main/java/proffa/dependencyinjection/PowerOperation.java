package proffa.dependencyinjection;

public class PowerOperation extends BinaryOperation {

    public PowerOperation(Notifier notifier) {
        super(notifier);
    }

    @Override
    protected double compute(double a, double b) {
        return Math.pow(a, b);
    }

    @Override
    protected String getSymbol() {
        return "^";
    }
}