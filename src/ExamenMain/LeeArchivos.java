package Examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LeeArchivos {
    private String archivo_Preguntas;
    private int cant_Preguntas;

    public LeeArchivos() {
        archivo_Preguntas = "Preguntas.txt";
    }

    public LeeArchivos(String archivo) throws IOException {
        archivo_Preguntas = archivo;
    }

    public Exam crearExamen() throws IOException {
        FileReader fr = new FileReader(archivo_Preguntas);
        BufferedReader flujo = new BufferedReader(fr);
        String texto;
        cant_Preguntas = 0;

        // Primera pasada para contar preguntas
        while ((texto = flujo.readLine()) != null) {
            if (!texto.startsWith("r|") && !texto.startsWith("o|")) {
                cant_Preguntas++;
            }
        }
        flujo.close();

        FileReader fr2 = new FileReader(archivo_Preguntas);
        BufferedReader flujo2 = new BufferedReader(fr2);
        Exam examen = new Exam(cant_Preguntas);
        String pregunta = null;
        double peso = 0;
        int tipo_pregunta = 0;
        String[] opciones = null;
        String respuesta = null;

        while ((texto = flujo2.readLine()) != null) {
            if (!texto.startsWith("r|") && !texto.startsWith("o|")) {
                String[] partes = texto.split("\\|");
                tipo_pregunta = switch (partes[1].trim()) {
                    case "TF" -> 1;
                    case "RC" -> 2;
                    case "SM" -> 3;
                    default -> 0;
                };
                pregunta = partes[2].trim();
                peso = Double.parseDouble(partes[3].trim());
            } else if (texto.startsWith("o|")) {
                opciones = texto.substring(2).split("\\|");
                for (int i = 0; i < opciones.length; i++) {
                    opciones[i] = opciones[i].trim();
                }
            } else if (texto.startsWith("r|")) {
                respuesta = texto.substring(2).trim();
                if (pregunta != null && respuesta != null) {
                    switch (tipo_pregunta) {
                        case 1 -> examen.agregaPregunta(new TFpregunta(pregunta,
                                Boolean.parseBoolean(respuesta), peso));
                        case 2 -> examen.agregaPregunta(new RCpregunta(pregunta, respuesta, peso));
                        case 3 -> examen.agregaPregunta(new SMpregunta(pregunta, opciones,
                                respuesta.charAt(0) - 'a', peso));
                    }
                    pregunta = null;
                    opciones = null;
                    respuesta = null;
                }
            }
        }
        flujo2.close();
        return examen;
    }

    public void modificarPreguntas() throws IOException {
        Exam examen = crearExamen();
        Scanner scanner = new Scanner(System.in);
        examen.mostrarPreguntasYRespuestas();
        System.out.print("Ingrese el número de la pregunta a modificar: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice >= 0 && indice < examen.MAX_PREGUNTAS) {
            Pregunta nuevaPregunta = examen.crearNuevaPregunta();
            if (nuevaPregunta != null) {
                modificarArchivoPregunta(indice, nuevaPregunta);
            }
        } else {
            System.out.println("Índice de pregunta inválido.");
        }
    }

    private void modificarArchivoPregunta(int indice, Pregunta nuevaPregunta) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo_Preguntas));
        StringBuilder contenido = new StringBuilder();
        String linea;
        int contadorPreguntas = -1;
        boolean saltarLineas = false;

        while ((linea = reader.readLine()) != null) {
            if (!linea.startsWith("r|") && !linea.startsWith("o|")) {
                contadorPreguntas++;
            }

            if (contadorPreguntas == indice && !saltarLineas) {
                // Añadir la nueva pregunta
                contenido.append(nuevaPregunta.formatoArchivo());
                saltarLineas = true;
                // Saltar líneas de la pregunta actual
                continue;
            }

            if (saltarLineas) {
                if (linea.startsWith("r|") || linea.startsWith("o|")) {
                    continue;
                } else {
                    saltarLineas = false;
                }
            }

            contenido.append(linea).append("\n");
        }
        reader.close();

        FileWriter writer = new FileWriter(archivo_Preguntas);
        writer.write(contenido.toString());
        writer.close();
    }

    public void crearNuevoExamen() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del nuevo archivo de examen: ");
        String nombreArchivo = scanner.nextLine();
        archivo_Preguntas = nombreArchivo;
        File archivoNuevo = new File(nombreArchivo+".txt");
        if (archivoNuevo.exists()) {
            System.out.println("El archivo ya existe. Se sobrescribirá.");
        }
        FileWriter escritor = new FileWriter(archivoNuevo);
        boolean agregarMas = true;
        while (agregarMas) {
            Exam examenTemp = new Exam(1);
            Pregunta nuevaPregunta = examenTemp.crearNuevaPregunta();
            if (nuevaPregunta != null) {
                escritor.write(nuevaPregunta.formatoArchivo());
            }
            System.out.print("¿Desea agregar otra pregunta? (s/n): ");
            String opcion = scanner.nextLine().trim().toLowerCase();
            agregarMas = opcion.equals("s");
        }
        escritor.close();
    }
}
