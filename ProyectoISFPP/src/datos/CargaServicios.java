package datos;

import java.util.ArrayList;
import java.util.List;

import enums.CategoriaVehiculo;
import enums.TipoServicio;
import enums.TipoVehiculo;
import modelo.Servicio;


public class CargaServicios {

	private static final int CAMPOS = 7;

	public static List<Servicio> cargar(String archivo) {
		
		List<Servicio> servicios = new ArrayList<>();
		for (Linea linea : LectorArchivo.leer(archivo)) {
			servicios.add(crear(archivo, linea));
		}
		return servicios;
	}
	
	private static Servicio crear(String archivo, Linea linea) {
		
		List<String> c = linea.getCampos();
		if (c.size() != CAMPOS) {
			throw new ArchivoInvalidoException(archivo, linea.getNumero(),
					"se esperaban " + CAMPOS + " campos y hay " + c.size());
		}
		try {
			return new Servicio(c.get(0), Double.parseDouble(c.get(1)), Double.parseDouble(c.get(2)),
					Double.parseDouble(c.get(3)), TipoVehiculo.valueOf(c.get(4)),
					CategoriaVehiculo.valueOf(c.get(5)), TipoServicio.valueOf(c.get(6)));
		} catch (IllegalArgumentException e) {
			throw new ArchivoInvalidoException(archivo, linea.getNumero(), "valor invalido" + e.getMessage()	);
		}
	}
	
}