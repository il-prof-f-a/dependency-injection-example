package proffa.dependency.injection;
// File: MainReflection.java
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: java MainReflection <notifica> <op> <a> <b>");
            System.out.println("  <notifica>: uno tra i notifier trovati");
            System.out.println("  <op>: uno tra i simboli trovati");
            showAvailableNotifiersAndOperations();
            return;
        }

        String notifierKey = args[0];  // es: console, file, popup
        String opSymbol    = args[2];  // es: +, -, *, /, ^, %
        double a;
        double b;

        try {
            a = Double.parseDouble(args[1]);
            b = Double.parseDouble(args[3]);
        } catch (NumberFormatException ex) {
            System.out.println("Errore: <a> e <b> devono essere numeri.");
            return;
        }

        // 1) Prendo il Notifier dal registry
        Notifier notifier = NotifierRegistry.getNotifier(notifierKey);

        if (notifier == null) {
            System.out.println("Tipo di notifica sconosciuto: " + notifierKey);
            showAvailableNotifiersAndOperations();
            return;
        }

        // 2) Creo l'operazione giusta dal registry
        BinaryOperation operation = OperationRegistry.createOperation(opSymbol, notifier);

        if (operation == null) {
            System.out.println("Operazione non supportata: " + opSymbol);
            showAvailableNotifiersAndOperations();
            return;
        }

        // 3) Eseguo il template method
        operation.execute(a, b);
    }

    private static void showAvailableNotifiersAndOperations() {
        Map<String, Notifier> notifiers = NotifierRegistry.getAllNotifiers();
        Map<String, Class<? extends BinaryOperation>> ops = OperationRegistry.getAllOperationClasses();

        System.out.println("Notifier disponibili: " + notifiers.keySet());
        System.out.println("Operazioni disponibili: " + ops.keySet());
        System.out.println("Esempi:");
        System.out.println("  java MainReflection console 5 + 7");
        System.out.println("  java MainReflection file 3 * 4");
        System.out.println("  java MainReflection popup 10 / 2");
    }
}
