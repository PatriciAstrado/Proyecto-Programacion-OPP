package Examen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Exam {
    private Pregunta[] preguntas;
    private int contador;
    public int MAX_PREGUNTAS;

    public Exam(int MAX_PREGUNTAS_IN) {
        MAX_PREGUNTAS = MAX_PREGUNTAS_IN;
        preguntas = new Pregunta[MAX_PREGUNTAS];
        contador = 0;
    }

    public void agregaPregunta(Pregunta p) {
        try {
            if (p == null) {
                throw new IllegalArgumentException("La pregunta no puede ser null.");
            }

            if (contador < MAX_PREGUNTAS) {
                preguntas[contador++] = p;
            } else {
                throw new IllegalStateException("No se pueden agregar más preguntas al examen.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al agregar pregunta: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error al agregar pregunta: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado al agregar la pregunta: " + e.getMessage());
        }
    }

    public void darExam(boolean mostrarRespuestas, boolean permitirModificaciones) {
        if (contador == 0) {
            System.out.println("El examen no tiene preguntas.");
            return;
        }

        double puntajeTotal = 0;
        double puntajeObtenido = 0;

        for (int i = 0; i < contador; i++) {
            try {
                System.out.println("\nPregunta " + (i + 1) + ":");
                if (permitirModificaciones) {
                    modificarPregunta(i);
                }
                boolean correcta = preguntas[i].buscar(mostrarRespuestas);
                puntajeTotal += preguntas[i].getPeso();
                if (correcta) {
                    puntajeObtenido += preguntas[i].getPeso();
                }
            } catch (NullPointerException e) {
                System.out.println("Error: La pregunta " + (i + 1) + " no está inicializada.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado al procesar la pregunta " + (i + 1) + ": " +
                                   e.getMessage());
            }
        }

        try {
            if (puntajeTotal == 0) {
                throw new ArithmeticException("El puntaje total es cero, no se puede calcular el porcentaje.");
            }
            double porcentaje = (puntajeObtenido / puntajeTotal) * 100;
            registrar(puntajeObtenido, puntajeTotal);
            System.out.printf("\nResultado: %.2f%% (%f/%f)\n", porcentaje, puntajeObtenido, puntajeTotal);
        } catch (ArithmeticException e) {
            System.out.println("Error al calcular el porcentaje: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado al calcular el porcentaje: " + e.getMessage());
        }
    }

    private void modificarPregunta(int indice) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Desea modificar esta pregunta? (s/n): ");
        String opcion = scanner.nextLine().trim().toLowerCase();
        if (opcion.equals("s")) {
            Pregunta nuevaPregunta = crearNuevaPregunta();
            if (nuevaPregunta != null) {
                preguntas[indice] = nuevaPregunta;
            }
        }
    }

    Pregunta crearNuevaPregunta() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de pregunta:");
        System.out.println("1. Verdadero/Falso");
        System.out.println("2. Respuesta Corta");
        System.out.println("3. Selección Múltiple");
        System.out.print("Opción: ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese el texto de la pregunta: ");
        String texto = scanner.nextLine();
        System.out.print("Ingrese el peso de la pregunta: ");
        double peso = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        switch (tipo) {
        case 1:
            System.out.print("Ingrese la respuesta correcta (true/false): ");
            boolean respuestaTF = scanner.nextBoolean();
            scanner.nextLine();
            return new TFpregunta(texto, respuestaTF, peso);
        case 2:
            System.out.print("Ingrese la respuesta correcta: ");
            String respuestaRC = scanner.nextLine();
            return new RCpregunta(texto, respuestaRC, peso);
        case 3:
            System.out.print("Ingrese el número de opciones: ");
            int numOpciones = scanner.nextInt();
            scanner.nextLine();
            String[] opciones = new String[numOpciones];
            for (int i = 0; i < numOpciones; i++) {
                System.out.print("Opción " + (char)('a' + i) + ": ");
                opciones[i] = scanner.nextLine();
            }
            System.out.print("Ingrese la opción correcta (a-" + (char)('a' + numOpciones - 1) + "): ");
            char opcionCorrecta = scanner.nextLine().charAt(0);
            int indiceCorrecto = opcionCorrecta - 'a';
            return new SMpregunta(texto, opciones, indiceCorrecto, peso);
        default:
            System.out.println("Tipo de pregunta inválido. No se creó ninguna pregunta.");
            return null;
        }
    }

        public void mostrarPreguntasYRespuestas() {
        if (contador == 0) {
            System.out.println("El examen no tiene preguntas.");
            return;
        }

        for (int i = 0; i < contador; i++) {
            System.out.println("\nPregunta " + (i + 1) + ":");
            preguntas[i].mostrarPreguntaYRespuesta();
        }
    }

    private void registrar(double puntajeObtenido, double puntajeTotal) {
        File arch = new File("examen.txt");
        double nota = (puntajeObtenido / puntajeTotal) * 100;
        try (FileWriter escritor = new FileWriter(arch, true)) {
            escritor.write(String.format("\nPuntaje Total: (%.2f/%.2f)\n", puntajeObtenido, puntajeTotal));
            escritor.write(String.format("\n Nota: %.2f\n", nota));
            if (nota >= 40) {
                escritor.write("Felicidades pasaste");
            } else {
                escritor.write("No pasaste");
            }
        } catch (IOException e) {
            System.out.println("Un error ocurrió al escribir en el archivo.");
        }
    }
}
