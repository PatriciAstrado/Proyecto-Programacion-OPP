package Examen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RCpregunta extends Pregunta {
    private String respuestaCorrecta;

    public RCpregunta(String text, String respuestaCorrecta, double peso) {
        super(text, peso);
        if (respuestaCorrecta == null || respuestaCorrecta.trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta correcta no puede ser null o vacía.");
        }
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public boolean buscar(boolean mostrarRespuesta) {
        Scanner in = null;
        try {
            in = new Scanner(System.in);
            System.out.println(getText());
            if (mostrarRespuesta) {
                System.out.println("Respuesta correcta: " + respuestaCorrecta);
            }
            System.out.print("Respuesta: ");
            String respuestaUsuario = in.nextLine().trim();

            if (respuestaUsuario.isEmpty()) {
                throw new InputMismatchException("La respuesta no puede estar vacía.");
            }

            boolean esCorrecta = respuestaUsuario.equalsIgnoreCase(respuestaCorrecta);
            registrar(respuestaUsuario, esCorrecta);
            if (esCorrecta) {
                System.out.println("¡Correcto!");
            } else {
                System.out.println("Incorrecto.");
            }
            return esCorrecta;
        } catch (InputMismatchException e) {
            System.out.println("Error de entrada: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void registrar(String respuesta, Boolean correcta) {
        File arch = new File("examen.txt");

        try (FileWriter escritor = new FileWriter(arch, true)) {
            escritor.write("Pregunta: " + getText() + "\n");
            escritor.write("Respuesta correcta: " + respuestaCorrecta.toUpperCase() + "\n");
            escritor.write("Respuesta introducida: " + respuesta.toUpperCase() + "\n");
            escritor.write("Puntaje: " + peso + "\n");
            if (correcta) {
                escritor.write(":: CORRECTA ::\n\n");
            } else {
                escritor.write(":: INCORRECTA ::\n\n");
            }
        } catch (IOException e) {
            System.out.println("Un error ocurrió al escribir en el archivo.");
        }
    }

    @Override
    public void mostrarPreguntaYRespuesta() {
        System.out.println(getText());
        System.out.println("Respuesta correcta: " + respuestaCorrecta);
    }

    @Override
    public String formatoArchivo() {
        StringBuilder sb = new StringBuilder();
        sb.append("1|RC|").append(text).append("|").append(peso).append("\n");
        sb.append("r|").append(respuestaCorrecta).append("\n");
        return sb.toString();
    }
}
