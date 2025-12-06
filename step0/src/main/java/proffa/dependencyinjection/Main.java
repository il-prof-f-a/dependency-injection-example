package proffa.dependencyinjection;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java MainSpaghetti <a> <op> <b>");
            return;
        }

        String op = args[1];
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[2]);

        double result;

        if (op.equals("+")) {
            result = a + b;
        } else if (op.equals("-")) {
            result = a - b;
        } else if (op.equals("*")) {
            result = a * b;
        } else if (op.equals("/")) {
            if (b == 0) {
                System.out.println("Errore: divisione per zero!");
                return;
            }
            result = a / b;
        } else if (op.equals("%")) {
            result = a % b;
        } else if (op.equals("^")) {
            result = Math.pow(a, b);
        } else {
            System.out.println("Operazione non supportata");
            return;
        }

        System.out.println("Risultato: " + result);
    }
}
