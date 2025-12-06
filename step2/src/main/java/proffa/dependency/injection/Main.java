package proffa.dependency.injection;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: java MainStep2 <notifica> <op> <a> <b>");
            System.out.println("notifica: console | file");
            return;
        }

        String notifierType = args[0];
        String op = args[2];
        double a = Double.parseDouble(args[1]);
        double b = Double.parseDouble(args[3]);

        Notifier notifier;
        if (notifierType.equalsIgnoreCase("file")) {
            notifier = new FileNotifier("risultati.txt");
        } else {
            notifier = new ConsoleNotifier();
        }

        CalculatorService calc = new CalculatorService(notifier);
        calc.calculateAndNotify(op, a, b);
    }
}