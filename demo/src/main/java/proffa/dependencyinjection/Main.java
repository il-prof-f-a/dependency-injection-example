package proffa.dependencyinjection;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: java MainFinal <notifica> <op> <a> <b>");
            System.out.println("  <notifica>: console | file | popup");
            System.out.println("  <op>: + | - | * | / | ^ | %");
            System.out.println("Esempi:");
            System.out.println("  java MainFinal console + 5 7");
            System.out.println("  java MainFinal file * 3 4");
            System.out.println("  java MainFinal popup / 10 2");
            return;
        }

        String notifierKey = args[0];  // console, file, popup
        String opSymbol    = args[2];  // + - * / ^ %
        double a;
        double b;

        try {
            a = Double.parseDouble(args[1]);
            b = Double.parseDouble(args[3]);
        } catch (NumberFormatException ex) {
            System.out.println("Errore: gli argomenti <a> e <b> devono essere numeri.");
            return;
        }

        // 1) Registry dei Notifier (tipo -> Notifier)
        Map<String, Notifier> notifiers = new HashMap<>();
        notifiers.put("console", new ConsoleNotifier());
        notifiers.put("file",    new FileNotifier("risultati.txt"));
        notifiers.put("popup",   new PopupNotifier());

        Notifier notifier = notifiers.get(notifierKey.toLowerCase());

        if (notifier == null) {
            System.out.println("Tipo di notifica sconosciuto: " + notifierKey);
            System.out.println("Uso 'console' come default.");
            notifier = new ConsoleNotifier();
        }

        // 2) Registry delle operazioni (simbolo -> BinaryOperation)
        Map<String, BinaryOperation> operations = new HashMap<>();
        operations.put("+", new SumOperation(notifier));
        operations.put("-", new SubtractOperation(notifier));
        operations.put("*", new MultiplyOperation(notifier));
        operations.put("/", new DivideOperation(notifier));
        operations.put("^", new PowerOperation(notifier));
        operations.put("%", new ModuloOperation(notifier));

        BinaryOperation operation = operations.get(opSymbol);

        if (operation == null) {
            System.out.println("Operazione non supportata: " + opSymbol);
            System.out.println("Operazioni supportate: " + operations.keySet());
            return;
        }

        // 3) Eseguo il Template Method
        operation.execute(a, b);
    }
}