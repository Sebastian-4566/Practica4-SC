package practica4;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ConsolaVista {
    private Scanner scanner;

    public ConsolaVista() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE GESTION DE INGRESOS ===");
        System.out.println("1. Procesar archivo y extraer datos");
        System.out.println("2. Mostrar listado de aportadores y validaciones");
        System.out.println("3. Mostrar total de ingresos válidos");
        System.out.println("4. Mostrar resumen de errores");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void imprimirListadoAportadores(ArrayList<Aportador> lista) {
        System.out.println("\n----------------------------------------------------------------------------------------------------");
        System.out.printf("%-4s | %-12s | %-28s | %-10s | %-32s%n",
                "N°", "CÉDULA", "CORREO ELECTRÓNICO", "MONTO", "ESTADO DE VALIDACIÓN");
        System.out.println("----------------------------------------------------------------------------------------------------");

        int num = 1;
        for (Aportador a : lista) {
            String montoFormateado = String.format(Locale.US, "$%.2f", a.getMonto());
            System.out.printf("%-4d | %-12s | %-28s | %-10s | %-32s%n",
                    num++,
                    a.getCedula(),
                    a.getCorreo(),
                    montoFormateado,
                    a.obtenerDetalleErrores());
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void imprimirTotalIngresos(double total, int validos, int totales) {
        System.out.println("\n=== REPORTE DE INGRESOS VÁLIDOS ===");
        System.out.println("Registros totales procesados: " + totales);
        System.out.println("Registros completamente válidos: " + validos);
        System.out.printf(Locale.US, "Total de ingresos válidos: $%.2f%n", total);
    }

    public void imprimirResumenErrores(int errCedula, int errCorreo, int errMonto, int totalConError) {
        System.out.println("\n=== RESUMEN DE ERRORES DETECTADOS ===");
        System.out.println("Registros con al menos un error: " + totalConError);
        System.out.println("- Cédulas con error (no tienen 10 dígitos): " + errCedula);
        System.out.println("- Correos electrónicos con estructura inválida: " + errCorreo);
        System.out.println("- Montos con error (no positivos / <= 0): " + errMonto);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("\n[INFO] " + mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("\n[ERROR] " + error);
    }
}
