package practica4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AportadorController {
    private ArchivoModelo modelo;
    private ConsolaVista vista;
    private ArrayList<Aportador> aportadores;
    private Scanner pausaScanner;

    private static final String REGEX_VALIDAR_CEDULA = "^\\d{10}$";
    private static final String REGEX_VALIDAR_CORREO = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    public AportadorController(ArchivoModelo modelo, ConsolaVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.aportadores = new ArrayList<>();
        this.pausaScanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    procesarArchivo();
                    pausar();
                    break;
                case 2:
                    mostrarListado();
                    pausar();
                    break;
                case 3:
                    mostrarTotalValidos();
                    pausar();
                    break;
                case 4:
                    mostrarResumenErrores();
                    pausar();
                    break;
                case 5:
                    vista.mostrarMensaje("Saliendo del sistema. ¡Hasta pronto!");
                    break;
                default:
                    vista.mostrarError("Opción no válida. Intente nuevamente.");
                    pausar();
            }
        } while (opcion != 5);
    }

    private void pausar() {
        System.out.print("\nPresione ENTER para volver al menú...");
        pausaScanner.nextLine();
    }

    private void procesarArchivo() {
        try {
            aportadores = modelo.leerYExtraerDatos("registro_ingresos.txt");
            validarAportadores();
            vista.mostrarMensaje("Archivo procesado exitosamente. Total de registros cargados: " + aportadores.size());
        } catch (IOException e) {
            vista.mostrarError("Error al leer el archivo: " + e.getMessage());
        }
    }

    private void validarAportadores() {
        for (Aportador a : aportadores) {
            boolean cedulaOk = a.getCedula() != null && a.getCedula().matches(REGEX_VALIDAR_CEDULA);
            boolean correoOk = a.getCorreo() != null && a.getCorreo().matches(REGEX_VALIDAR_CORREO);
            boolean montoOk = a.getMonto() > 0;

            a.setCedulaValida(cedulaOk);
            a.setCorreoValido(correoOk);
            a.setMontoValido(montoOk);
        }
    }

    private void mostrarListado() {
        if (aportadores.isEmpty()) {
            vista.mostrarError("No hay datos cargados. Ejecute primero la opción 1.");
            return;
        }
        vista.imprimirListadoAportadores(aportadores);
    }

    private void mostrarTotalValidos() {
        if (aportadores.isEmpty()) {
            vista.mostrarError("No hay datos cargados. Ejecute primero la opción 1.");
            return;
        }
        double total = 0.0;
        int validos = 0;
        for (Aportador a : aportadores) {
            if (a.esRegistroValido()) {
                total += a.getMonto();
                validos++;
            }
        }
        vista.imprimirTotalIngresos(total, validos, aportadores.size());
    }

    private void mostrarResumenErrores() {
        if (aportadores.isEmpty()) {
            vista.mostrarError("No hay datos cargados. Ejecute primero la opción 1.");
            return;
        }
        int errCedula = 0;
        int errCorreo = 0;
        int errMonto = 0;
        int registrosConError = 0;

        for (Aportador a : aportadores) {
            if (!a.esRegistroValido()) {
                registrosConError++;
            }
            if (!a.isCedulaValida()) {
                errCedula++;
            }
            if (!a.isCorreoValido()) {
                errCorreo++;
            }
            if (!a.isMontoValido()) {
                errMonto++;
            }
        }
        vista.imprimirResumenErrores(errCedula, errCorreo, errMonto, registrosConError);
    }
}
