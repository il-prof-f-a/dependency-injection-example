package proffa.dependency.injection;

public abstract class BinaryOperation {

    protected final Notifier notifier;

    public BinaryOperation(Notifier notifier) {
        this.notifier = notifier;
    }

    // TEMPLATE METHOD
    public final void execute(double a, double b) {
        if (!validate(a, b)) {
            onValidationError(a, b);
            return;
        }

        double result = compute(a, b);
        String message = formatMessage(a, b, result);
        notifier.notifyResult(message);
    }

    protected boolean validate(double a, double b) {
        return true;
    }

    protected void onValidationError(double a, double b) {
        notifier.notifyResult("Dati non validi per l'operazione " + getSymbol());
    }

    protected String formatMessage(double a, double b, double result) {
        return a + " " + getSymbol() + " " + b + " = " + result;
    }

    protected abstract double compute(double a, double b);

    protected abstract String getSymbol();
}
