package Examen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SMpregunta extends Pregunta {
    private String[] opciones;
    private int respuestaCorrecta;

    public SMpregunta(String text, String[] opciones, int respuestaCorrecta, double peso) {
        super(text, peso);
        if (opciones == null || opciones.length < 2) {
            throw new IllegalArgumentException("Debe haber al menos 2 opciones y no pueden ser null.");
        }
        if (respuestaCorrecta < 0 || respuestaCorrecta >= opciones.length) {
            throw new IllegalArgumentException("Índice de respuesta correcta fuera de rango.");
        }
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public boolean buscar(boolean mostrarRespuesta) {
        Scanner in = null;
        try {
            in = new Scanner(System.in);
            System.out.println(getText());
            char opcionLetra = 'a';
            for (int i = 0; i < opciones.length; i++) {
                System.out.println(opcionLetra + ") " + opciones[i]);
                opcionLetra++;
            }
            if (mostrarRespuesta) {
                System.out.println("Respuesta correcta: " + (char)('a' + respuestaCorrecta));
            }
            System.out.print("Seleccione una opción (a-" + (char)('a' + opciones.length - 1) + "): ");
            String respuesta = in.nextLine().trim().toLowerCase();

            if (respuesta.length() != 1) {
                throw new InputMismatchException("La entrada debe ser una sola letra.");
            }

            char seleccion = respuesta.charAt(0);
            int indexSeleccion = seleccion - 'a';

            if (indexSeleccion < 0 || indexSeleccion >= opciones.length) {
                throw new InputMismatchException("La opción seleccionada no es válida.");
            }

            boolean esCorrecta = (indexSeleccion == respuestaCorrecta);
            registrar(respuesta, esCorrecta);
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
        } finally {
            // No cerrar Scanner(System.in) para evitar cerrar el flujo de entrada estándar.
            // if (in != null) {
            //     in.close();
            // }
        }
    }

    @Override
    public void registrar(String respuesta, Boolean correcta) {
        File arch = new File("examen.txt");

        try (FileWriter escritor = new FileWriter(arch, true)) {
            escritor.write("Pregunta: " + getText() + "\n");
            escritor.write("Respuesta correcta: " + opciones[respuestaCorrecta] + "\n");
            escritor.write("Respuesta introducida: " + opciones[(respuesta.charAt(0) - 'a')] + "\n");
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
        char opcionLetra = 'a';
        for (String opcion : opciones) {
            System.out.println(opcionLetra + ") " + opcion);
            opcionLetra++;
        }
        System.out.println("Respuesta correcta: " + (char)('a' + respuestaCorrecta));
    }

    @Override
    public String formatoArchivo() {
        StringBuilder sb = new StringBuilder();
        sb.append("1|SM|").append(text).append("|").append(peso).append("\n");
        sb.append("o|"); 
        for (int i = 0; i < opciones.length; i++) {
            sb.append(opciones[i]);
            if (i < opciones.length - 1) {
                sb.append("|");
            }
        }
        sb.append("\n");
        sb.append("r|").append((char)('a' + respuestaCorrecta)).append("\n");
        return sb.toString();
    }
}
