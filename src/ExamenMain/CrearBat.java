package Examen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class CrearBat {
    public static void crearArchivoBat() {
        String var0 = getNombreJar();
        String var1 = "java -jar \"" + var0 + "\" --desde-bat";

        try {
            BufferedWriter var2 = new BufferedWriter(new FileWriter("ejecutar.bat"));

            try {
                var2.write(var1);
                System.out.println("Archivo .bat creado exitosamente.");
            } catch (Throwable var6) {
                try {
                    var2.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            var2.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }
    }

    private static String getNombreJar() {
        try {
            return (new File(CrearBat.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getName();
        } catch (URISyntaxException var1) {
            var1.printStackTrace();
            return "archivo.jar";
        }
    }
}
