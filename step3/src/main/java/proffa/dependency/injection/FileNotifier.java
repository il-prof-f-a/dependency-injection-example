package proffa.dependency.injection;

import java.io.FileWriter;
import java.io.IOException;

public class FileNotifier implements Notifier {
    private String filePath;

    public FileNotifier(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void notifyResult(String message) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(message + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Errore scrittura file: " + e.getMessage());
        }
    }
}
