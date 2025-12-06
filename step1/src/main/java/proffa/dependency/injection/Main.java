package proffa.dependency.injection;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java MainStep1 <op> <a> <b>");
            return;
        }

        String op = args[1];
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[2]);

        CalculatorService calc = new CalculatorService();

        try {
            double result = calc.calculate(op, a, b);
            System.out.println("Risultato: " + result);
        } catch (IllegalArgumentException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }
    }
}
