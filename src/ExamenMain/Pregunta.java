package Examen;

public abstract class Pregunta {
    protected double peso;
    protected String text;

    public Pregunta(String text, double peso) {
        this.text = text;
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getText() {
        return text;
    }

    public abstract boolean buscar(boolean mostrarRespuesta);

    public abstract void registrar(String respuesta, Boolean correcta);

    public abstract void mostrarPreguntaYRespuesta();

    public abstract String formatoArchivo();
}
