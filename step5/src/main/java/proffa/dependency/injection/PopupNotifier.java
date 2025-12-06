package proffa.dependency.injection;
import javax.swing.JOptionPane;

public class PopupNotifier implements Notifier {

    @Override
    public void notifyResult(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Risultato",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}