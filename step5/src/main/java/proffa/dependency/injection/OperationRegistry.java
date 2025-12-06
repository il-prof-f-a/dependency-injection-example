package proffa.dependency.injection;

// File: OperationRegistry.java
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OperationRegistry {

    private static final Map<String, Class<? extends BinaryOperation>> OPERATIONS = new HashMap<>();

    static {
        initialize();
    }

    @SuppressWarnings("unchecked")
    private static void initialize() {
        try {
            Class<?> thisClass = OperationRegistry.class;
            String packageName = thisClass.getPackage().getName();
            String packagePath = packageName.replace('.', File.separatorChar);

            URL rootUrl = thisClass
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation();
            File rootDir = new File(rootUrl.toURI());

            File packageDir = new File(rootDir, packagePath);

            File[] files = packageDir.listFiles((dir, name) -> name.endsWith(".class"));
            if (files == null) {
                System.err.println("Nessun file .class trovato per il registry delle operazioni in "
                        + packageDir.getAbsolutePath());
                return;
            }

            for (File file : files) {
                String fileName = file.getName();
                String simpleName = fileName.substring(0, fileName.length() - ".class".length());
                String className = packageName + "." + simpleName;

                try {
                    Class<?> clazz = Class.forName(className);

                    // Deve estendere BinaryOperation
                    if (!BinaryOperation.class.isAssignableFrom(clazz)) {
                        continue;
                    }

                    // Saltiamo la classe astratta BinaryOperation
                    if (clazz.equals(BinaryOperation.class)) {
                        continue;
                    }

                    // Deve essere concreta
                    if (Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface()) {
                        continue;
                    }

                    Class<? extends BinaryOperation> opClass =
                            (Class<? extends BinaryOperation>) clazz;

                    // Costruttore (Notifier) per istanza di prova
                    Constructor<? extends BinaryOperation> ctor =
                            opClass.getDeclaredConstructor(Notifier.class);
                    ctor.setAccessible(true);

                    // Istanza temporanea con un Notifier qualsiasi (console) per leggere il simbolo
                    BinaryOperation tempInstance =
                            ctor.newInstance(new PopupNotifier());

                    String symbol = tempInstance.getOperationSymbol();

                    OPERATIONS.put(symbol, opClass);

                } catch (ClassNotFoundException e) {
                    System.err.println("Classe non trovata (OperationRegistry): " + className);
                } catch (NoSuchMethodException e) {
                    // Non ha costruttore (Notifier) → la ignoriamo
                } catch (Exception e) {
                    System.err.println("Errore creando operazione per " + className + ": " + e.getMessage());
                }
            }
        } catch (URISyntaxException e) {
            System.err.println("Errore nel calcolo del path del classpath: " + e.getMessage());
        }
    }

    public static BinaryOperation createOperation(String symbol, Notifier notifier) {
        Class<? extends BinaryOperation> opClass = OPERATIONS.get(symbol);
        if (opClass == null) {
            return null;
        }
        try {
            Constructor<? extends BinaryOperation> ctor =
                    opClass.getDeclaredConstructor(Notifier.class);
            ctor.setAccessible(true);
            return ctor.newInstance(notifier);
        } catch (Exception e) {
            System.err.println("Errore istanziando operazione '" + symbol + "': " + e.getMessage());
            return null;
        }
    }

    public static Map<String, Class<? extends BinaryOperation>> getAllOperationClasses() {
        return new HashMap<>(OPERATIONS);
    }
}
