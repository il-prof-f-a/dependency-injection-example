package proffa.dependency.injection;

public class ConsoleNotifier implements Notifier {

    @Override
    public void notifyResult(String message) {
        System.out.println("[RISULTATO] " + message);
    }
}