package proffa.dependency.injection;

// File: NotifierRegistry.java
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NotifierRegistry {

    private static final Map<String, Notifier> NOTIFIERS = new HashMap<>();

    static {
        initialize();
    }

    private static void initialize() {
        try {
            // 1) Ricaviamo il nome del package e la cartella corrispondente
            Class<?> thisClass = NotifierRegistry.class;
            String packageName = thisClass.getPackage().getName();               // es. "proffa.dependency.injection"
            String packagePath = packageName.replace('.', File.separatorChar);   // es. "proffa/dependency/injection"

            // 2) Cartella root del classpath (di solito .../target/classes)
            URL rootUrl = thisClass
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation();
            File rootDir = new File(rootUrl.toURI());

            // 3) Cartella che contiene le .class del package corrente
            File packageDir = new File(rootDir, packagePath);

            File[] files = packageDir.listFiles((dir, name) -> name.endsWith(".class"));
            if (files == null) {
                System.err.println("Nessun file .class trovato per il registry dei Notifier in " 
                        + packageDir.getAbsolutePath());
                return;
            }

            for (File file : files) {
                String fileName = file.getName();              // es. "ConsoleNotifier.class"
                String simpleName = fileName.substring(0, fileName.length() - ".class".length());
                String className = packageName + "." + simpleName;  // es. "proffa.dependency.injection.ConsoleNotifier"

                try {
                    Class<?> clazz = Class.forName(className);

                    // Deve implementare Notifier
                    if (!Notifier.class.isAssignableFrom(clazz)) {
                        continue;
                    }

                    // Evitiamo l'interfaccia Notifier stessa
                    if (clazz.equals(Notifier.class)) {
                        continue;
                    }

                    // Deve essere concreta
                    if (Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface()) {
                        continue;
                    }

                    // Costruttore vuoto
                    Constructor<?> ctor = clazz.getDeclaredConstructor();
                    ctor.setAccessible(true);
                    Object instance = ctor.newInstance();

                    Notifier notifier = (Notifier) instance;

                    // Chiave: nome classe senza "Notifier", tutto minuscolo
                    String key = simpleName.replace("Notifier", "").toLowerCase();

                    NOTIFIERS.put(key, notifier);

                } catch (ClassNotFoundException e) {
                    System.err.println("Classe non trovata (NotifierRegistry): " + className);
                } catch (NoSuchMethodException e) {
                    // Nessun costruttore vuoto → la saltiamo
                } catch (Exception e) {
                    System.err.println("Errore creando Notifier per " + className + ": " + e.getMessage());
                }
            }
        } catch (URISyntaxException e) {
            System.err.println("Errore nel calcolo del path del classpath: " + e.getMessage());
        }
    }

    public static Notifier getNotifier(String key) {
        return NOTIFIERS.get(key.toLowerCase());
    }

    public static Map<String, Notifier> getAllNotifiers() {
        return new HashMap<>(NOTIFIERS);
    }
}
