package Examen;

import java.io.IOException;
import java.util.Scanner;

public class Profesor {
    public void menuPrincipal() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenú del Profesor:");
            System.out.println("1. Tomar el examen (ver respuestas durante el examen)");
            System.out.println("2. Ver todas las preguntas y respuestas");
            System.out.println("3. Modificar preguntas del examen");
            System.out.println("4. Crear un nuevo examen apartir de documento deafult: Preguntas.txt");
            System.out.println("5. Cargar un examen apartir de un documento txt dado");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
            case 1:
                darExamenConRespuestas();
                break;
            case 2:
                verPreguntasYRespuestas();
                break;
            case 3:
                modificarPreguntas();
                break;
            case 4:
                crearNuevoExamen();
                break;
            case 5:
                System.out.println("Saliendo...");
                return;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private void darExamenConRespuestas() throws IOException {
        LeeArchivos ll = new LeeArchivos();
        Exam examen = ll.crearExamen();
        examen.darExam(true, false);
    }

    private void verPreguntasYRespuestas() throws IOException {
        LeeArchivos ll = new LeeArchivos();
        Exam examen = ll.crearExamen();
        examen.mostrarPreguntasYRespuestas();
    }

    private void modificarPreguntas() throws IOException {
        LeeArchivos ll = new LeeArchivos();
        ll.modificarPreguntas();
    }

    private void crearNuevoExamen() throws IOException {
        LeeArchivos ll = new LeeArchivos();
        ll.crearNuevoExamen();
    }
    
}
