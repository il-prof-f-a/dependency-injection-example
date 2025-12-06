package proffa.dependency.injection;

public class ModuloOperation extends BinaryOperation {

    public ModuloOperation(Notifier notifier) {
        super(notifier);
    }

    @Override
    protected double compute(double a, double b) {
        return a % b;
    }

    @Override
    protected String getSymbol() {
        return "%";
    }
}