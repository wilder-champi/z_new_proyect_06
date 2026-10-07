package datos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import enums.CategoriaVehiculo;
import enums.TipoServicio;
import enums.TipoVehiculo;
import logica.GeneradorUbicacion;
import modelo.Vehiculo;


public class CargaVehiculos {

	private static final int CAMPOS_MINIMOS = 6; // El segundo tipo de servicio es opcional

	// Devuelve los vehiculos por patente, asi CargaUsuarios los puede buscar
	
	
	public static Map<String, Vehiculo> cargar(String archivo, GeneradorUbicacion generador) {
		Map<String, Vehiculo> vehiculos = new LinkedHashMap<>();
		for (Linea linea : LectorArchivo.leer(archivo)) {
			Vehiculo vehiculo = crear(archivo, linea, generador);
			if (vehiculos.containsKey(vehiculo.getPatente())) {//verifica que dicha patente no este cargada previamente en el mapa
				throw new ArchivoInvalidoException(archivo, linea.getNumero(),
						"patente repetida " + vehiculo.getPatente());
			}
			vehiculos.put(vehiculo.getPatente(), vehiculo);
		}
		return vehiculos;
	}

	
	private static Vehiculo crear(String archivo, Linea linea, GeneradorUbicacion generador) {
		List<String> c = linea.getCampos();
		if (c.size() < CAMPOS_MINIMOS) {
			throw new ArchivoInvalidoException(archivo, linea.getNumero(),
					"se esperaban al menos " + CAMPOS_MINIMOS + " campos y hay " + c.size());
		}
		try {
			
			Vehiculo vehiculo = new Vehiculo(c.get(0), c.get(1), Integer.parseInt(c.get(2)),
					TipoVehiculo.valueOf(c.get(3)), CategoriaVehiculo.valueOf(c.get(4)), TipoServicio.valueOf(c.get(5)),
					generador.generar());
			// Si trae un segundo tipo de servicio se agrega
			for (int i = CAMPOS_MINIMOS; i < c.size(); i++) {
				vehiculo.agregarTipoServicio(TipoServicio.valueOf(c.get(i)));
			}
			return vehiculo;
		} catch (IllegalArgumentException e) {
			throw new ArchivoInvalidoException(archivo, linea.getNumero(), "valor invalido (" + e.getMessage() + ")");
		}
	}
}