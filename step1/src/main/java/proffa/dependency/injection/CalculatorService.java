package proffa.dependency.injection;

public class CalculatorService {

    public double calculate(String op, double a, double b) {
        switch (op) {
            case "+" -> { return a + b; }
            case "-" -> { return a - b; }
            case "*" -> { return a * b; }
            case "/" -> {
                if (b == 0) {
                    throw new IllegalArgumentException("Divisione per zero");
                }
                return a / b;
            }
            case "%" -> { return a % b; }
            case "^" -> { return Math.pow(a, b); }
            default -> throw new IllegalArgumentException("Operazione non supportata: " + op);
        }
    }
}