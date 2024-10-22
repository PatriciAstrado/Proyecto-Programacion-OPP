package Examen;
import java.util.Scanner;
import java.io.IOException;

public class Estudiante {
    public void darExamen() throws IOException {
        try{
        LeeArchivos ll;
        
        System.out.println("Desea introducir un examen o usar el dado por defecto? y/n");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();            
        if(id.equals("y")){
            System.out.println("introdusca el nombre del examen a evaluar: (.txt incluido)");
            id = scanner.nextLine();            
            ll = new LeeArchivos(id);    
        }else{
            ll = new LeeArchivos();
        }
        
        
        Exam examen = ll.crearExamen();
        examen.darExam(false, false);
        }catch(IOException e){
                        System.out.println("ERROR al cargar el archivo, por favor vuelva a intentarlo");
                    }
    }
}