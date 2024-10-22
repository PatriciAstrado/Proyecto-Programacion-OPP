package Examen;

import java.io.IOException;
import java.util.Scanner;

public class ExamenMain {
    public static void main(String[] args) throws IOException {
               // Si el programa se ejecuta desde el .bat, continuar con la ejecución normal
        Scanner scanner = new Scanner(System.in);
        

    OUTER:
        while (true) {
            System.out.println("-----------------------");
            System.out.println("Seleccione su rol:");
            System.out.println("1. Profesor");
            System.out.println("2. Estudiante");
            System.out.println("3. Finalizar Programa");
            System.out.print("Ingrese el número correspondiente a su rol: ");
            int rol = scanner.nextInt();
            scanner.nextLine();
            switch (rol) {
            case 1:
                Profesor profesor = new Profesor();
                profesor.menuPrincipal();
                break;
            case 2:
                Estudiante estudiante = new Estudiante();
                estudiante.darExamen();
                break;
            case 3:
                System.out.println("Programa finalizado");
                break OUTER;
            default:
                System.out.println("Selección inválida.");
                break;
            }
        }
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione su rol:");
        System.out.println("1. Profesor");
        System.out.println("2. Estudiante");
        System.out.print("Ingrese el número correspondiente a su rol: ");
        int rol = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (rol == 1) {
            Profesor profesor = new Profesor();
            profesor.menuPrincipal();
        } else if (rol == 2) {
            Estudiante estudiante = new Estudiante();
            estudiante.darExamen();
        } else {
            System.out.println("Selección inválida. Por favor, reinicie el programa.");
        }

        String[] opciones1 = {"Mercurio", "Venus", "Tierra", "Marte"};
        // System.out.println(opciones1[1]);

        File arch = new File("examen.txt");
        arch.delete(); // aseguramos que el archivo este vacio via  borrandolo, si no solo escribe en las
           siguientes
                                   // lineas
        LeeArchivos ll = new LeeArchivos();
        Exam examen = ll.crearExamen();


        Exam examen = new Exam(10);

        // Agregar preguntas de Verdadero/Falso
        examen.agregaPregunta(new TFpregunta("La tierra es plana.", false, 1.0));
        examen.agregaPregunta(new TFpregunta("Java es un lenguaje de programación orientado a objetos.",
        true, 1.0)); examen.agregaPregunta(new TFpregunta("El sol gira alrededor de la tierra.",
           false, 1.0));

        // Agregar preguntas de Selección Múltiple
        String[] opciones1 = {"Mercurio", "Venus", "Tierra", "Marte"};
        examen.agregaPregunta(new SMpregunta("¿Cuál es el planeta más cercano al sol?", opciones1, 0, 2.0));

        String[] opciones2 = {"C++", "Java", "Python", "Ruby"};
        examen.agregaPregunta(new SMpregunta("¿Cuál de estos lenguajes no es interpretado?", opciones2,
           0, 2.0));

        String[] opciones3 = {"4", "5", "6", "7"};
        examen.agregaPregunta(new SMpregunta("¿Cuántos continentes hay en la tierra?", opciones3, 2, 2.0));

        // Agregar preguntas de Respuesta Corta
        examen.agregaPregunta(
          new RCpregunta("¿Cuál es el lenguaje de programación utilizado principalmente para Android?",
        "java", 2.0)); examen.agregaPregunta(new RCpregunta("¿Qué gas es necesario para la respiración
           humana?", "oxigeno", 2.0)); examen.agregaPregunta(new RCpregunta("Cuál es la capital de la Región de la RM?",
        "santiago", 2.0));

        // Dar el examen
        examen.darExam(); */
    }
}
