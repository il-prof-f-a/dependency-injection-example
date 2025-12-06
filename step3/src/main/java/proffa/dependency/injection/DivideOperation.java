package proffa.dependency.injection;

public class DivideOperation extends BinaryOperation {
    public DivideOperation(Notifier notifier) {
        super(notifier);
    }

    @Override
    protected boolean validate(double a, double b) {
        return b != 0;
    }

    @Override
    protected void onValidationError(double a, double b) {
        notifier.notifyResult("Errore: divisione per zero (" + a + " / " + b + ")");
    }

    @Override
    protected double compute(double a, double b) {
        return a / b;
    }

    @Override
    protected String getSymbol() {
        return "/";
    }
}
