package proffa.dependency.injection;

import java.util.HashMap;
import java.util.Map;

public class Main {
public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: java MainStep3 <notifica> <op> <a> <b>");
            return;
        }

        String notifierType = args[0];
        String opSymbol     = args[2];
        double a            = Double.parseDouble(args[1]);
        double b            = Double.parseDouble(args[3]);

        // Per ora lasciamo ancora la scelta Notifier con if/switch
        Notifier notifier = new ConsoleNotifier();  // semplificato

        Map<String, BinaryOperation> operations = new HashMap<>();
        operations.put("+", new SumOperation(notifier));
        operations.put("/", new DivideOperation(notifier));
        // aggiungere qui -, *, %, ^ ...

        BinaryOperation operation = operations.get(opSymbol);

        if (operation == null) {
            System.out.println("Operazione non supportata: " + opSymbol);
            return;
        }

        operation.execute(a, b); // TEMPLATE METHOD
    }
}