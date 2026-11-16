package practica4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArchivoModelo {

    private static final Pattern PATRON_CEDULA = Pattern.compile("(?i)(?:c[eé]dula|CI)\\s+(?:es\\s+)?(\\d+)");
    private static final Pattern PATRON_CORREO = Pattern.compile("(?i)correo\\s+(?:es\\s+)?([^\\s,]+)");
    private static final Pattern PATRON_MONTO = Pattern.compile("\\$\\s*(\\d+(?:\\.\\d+)?)|(\\d+(?:\\.\\d+)?)\\s*(?:d[oó]lares|USD|\\$)");

    public ArrayList<Aportador> leerYExtraerDatos(String rutaArchivo) throws IOException {
        ArrayList<Aportador> listaAportadores = new ArrayList<>();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists() || !archivo.canRead()) {
            throw new FileNotFoundException("No se encontró o no se puede leer el archivo: " + rutaArchivo);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            
            String linea;
            while ((linea = br.readLine()) != null) {
                Matcher mCedula = PATRON_CEDULA.matcher(linea);
                Matcher mCorreo = PATRON_CORREO.matcher(linea);
                Matcher mMonto = PATRON_MONTO.matcher(linea);

                if (mCedula.find() || mCorreo.find() || mMonto.find()) {
                    mCedula.reset();
                    mCorreo.reset();
                    mMonto.reset();

                    String cedula = mCedula.find() ? mCedula.group(1) : "NO_ENCONTRADA";
                    String correo = mCorreo.find() ? mCorreo.group(1) : "NO_ENCONTRADO";
                    
                    if (correo.endsWith(".")) {
                        correo = correo.substring(0, correo.length() - 1);
                    }

                    double monto = -1.0;
                    String montoTexto = "NO_ENCONTRADO";

                    if (mMonto.find()) {
                        String valorStr = (mMonto.group(1) != null) ? mMonto.group(1) : mMonto.group(2);
                        montoTexto = mMonto.group(0);
                        try {
                            monto = Double.parseDouble(valorStr);
                        } catch (NumberFormatException e) {
                            monto = -1.0;
                        }
                    }

                    listaAportadores.add(new Aportador(cedula, correo, monto, montoTexto));
                }
            }
        }
        return listaAportadores;
    }
}
