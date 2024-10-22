package Examen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class CrearBat {
    public static void crearArchivoBat() {
        String nombreJar = getNombreJar();
        String contenidoBat = "java -jar \"" + nombreJar + "\" --desde-bat";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("ejecutar.bat"))) {
            escritor.write(contenidoBat);
            System.out.println("Archivo .bat creado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getNombreJar() {
        try {
            return new File(CrearBat.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getName();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "archivo.jar"; // Valor por defecto en caso de error
        }
    }
}
