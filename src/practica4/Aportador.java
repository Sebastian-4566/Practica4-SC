package practica4;

public class Aportador {
    private String cedula;
    private String correo;
    private double monto;
    private String montoTexto;

    private boolean cedulaValida;
    private boolean correoValido;
    private boolean montoValido;

    public Aportador(String cedula, String correo, double monto, String montoTexto) {
        this.cedula = cedula;
        this.correo = correo;
        this.monto = monto;
        this.montoTexto = montoTexto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public double getMonto() {
        return monto;
    }

    public String getMontoTexto() {
        return montoTexto;
    }

    public boolean isCedulaValida() {
        return cedulaValida;
    }

    public void setCedulaValida(boolean cedulaValida) {
        this.cedulaValida = cedulaValida;
    }

    public boolean isCorreoValido() {
        return correoValido;
    }

    public void setCorreoValido(boolean correoValido) {
        this.correoValido = correoValido;
    }

    public boolean isMontoValido() {
        return montoValido;
    }

    public void setMontoValido(boolean montoValido) {
        this.montoValido = montoValido;
    }

    public boolean esRegistroValido() {
        return cedulaValida && correoValido && montoValido;
    }

    public String obtenerDetalleErrores() {
        if (esRegistroValido()) {
            return "VÁLIDO";
        }
        StringBuilder errores = new StringBuilder("INVÁLIDO (Error en: ");
        boolean primero = true;
        if (!cedulaValida) {
            errores.append("Cédula");
            primero = false;
        }
        if (!correoValido) {
            if (!primero) errores.append(", ");
            errores.append("Correo");
            primero = false;
        }
        if (!montoValido) {
            if (!primero) errores.append(", ");
            errores.append("Monto");
        }
        errores.append(")");
        return errores.toString();
    }
}