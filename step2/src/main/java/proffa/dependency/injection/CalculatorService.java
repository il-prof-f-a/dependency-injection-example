package proffa.dependency.injection;

public class CalculatorService {

    private Notifier notifier;

    public CalculatorService(Notifier notifier) {
        this.notifier = notifier;
    }

    public void calculateAndNotify(String op, double a, double b) {
        double result;

        switch (op) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> {
                if (b == 0) {
                    notifier.notifyResult("Errore: divisione per zero");
                    return;
                }
                result = a / b;
            }
            case "%" -> result = a % b;
            case "^" -> result = Math.pow(a, b);
            default -> {
                notifier.notifyResult("Operazione non supportata: " + op);
                return;
            }
        }

        notifier.notifyResult(a + " " + op + " " + b + " = " + result);
    }
}
