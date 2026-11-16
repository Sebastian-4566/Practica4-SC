package practica4;

public class Main {
    public static void main(String[] args) {
        ArchivoModelo modelo = new ArchivoModelo();
        ConsolaVista vista = new ConsolaVista();
        AportadorController controlador = new AportadorController(modelo, vista);

        controlador.iniciar();
    }
}
