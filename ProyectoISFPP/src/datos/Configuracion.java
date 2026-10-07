package datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Lee config.properties: los nombres de los archivos de datos y las dos esquinas
 * de la zona donde se generan las ubicaciones al azar
 */
public class Configuracion {

	public static final String ARCHIVO_POR_DEFECTO = "config.properties";

	private final Properties propiedades = new Properties();

	public Configuracion() {
		this(ARCHIVO_POR_DEFECTO);
	}

	public Configuracion(String archivo) {
		try (FileInputStream entrada = new FileInputStream(archivo)) {
			propiedades.load(entrada);
		} catch (IOException e) {
			throw new IllegalStateException("No se pudo leer la configuracion " + archivo);
		}
	}

	public String getArchivoUsuarios() {
		return texto("usuario");
	}

	public String getArchivoServicios() {
		return texto("servicio");
	}

	public String getArchivoVehiculos() {
		return texto("vehiculo");
	}

	public double getLatitud1() {
		return numero("latitud1");
	}

	public double getLongitud1() {
		return numero("longitud1");
	}

	public double getLatitud2() {
		return numero("latitud2");
	}

	public double getLongitud2() {
		return numero("longitud2");
	}

	// devuelve el valor de una propiedad o avisa cual falta
	private String texto(String clave) {
		String valor = propiedades.getProperty(clave);
		if (valor == null || valor.trim().isEmpty()) {
			throw new IllegalStateException("Falta la propiedad '" + clave + "' en la configuracion");
		}
		return valor.trim();
	}

	private double numero(String clave) {
		try {
			return Double.parseDouble(texto(clave));
		} catch (NumberFormatException e) {
			throw new IllegalStateException("La propiedad '" + clave + "' no es un numero");
		}
	}
}