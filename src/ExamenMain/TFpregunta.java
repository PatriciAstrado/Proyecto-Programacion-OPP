package Examen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TFpregunta extends Pregunta {
    private boolean respuestaCorrecta;

    public TFpregunta(String text, boolean respuestaCorrecta, double peso) {
        super(text, peso);
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto de la pregunta no puede ser null o vacío.");
        }
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public boolean buscar(boolean mostrarRespuesta) {
        Scanner in = null;
        try {
            in = new Scanner(System.in);
            while (true) {
                System.out.println(getText() + " (V/F): ");
                if (mostrarRespuesta) {
                    System.out.println("Respuesta correcta: " + (respuestaCorrecta ? "V" : "F"));
                }
                String respuesta = in.nextLine().trim().toLowerCase();

                if (respuesta.equals("v") || respuesta.equals("f")) {
                    boolean usuarioResponde = respuesta.equals("v");
                    boolean esCorrecta = (usuarioResponde == respuestaCorrecta);
                    registrar(respuesta, esCorrecta);
                    if (esCorrecta) {
                        System.out.println("¡Correcto!");
                    } else {
                        System.out.println("Incorrecto.");
                    }
                    return esCorrecta;
                } else {
                    System.out.println("Entrada inválida. Por favor ingrese 'V' para Verdadero o 'F' para Falso.");
                }
            }
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
            escritor.write("Respuesta correcta: " + (respuestaCorrecta ? "V" : "F") + "\n");
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
        System.out.println("Respuesta correcta: " + (respuestaCorrecta ? "Verdadero" : "Falso"));
    }

    @Override
    public String formatoArchivo() {
        StringBuilder sb = new StringBuilder();
        sb.append("1|TF|").append(text).append("|").append(peso).append("\n");
        sb.append("r|").append(respuestaCorrecta).append("\n");
        return sb.toString();
    }
}
