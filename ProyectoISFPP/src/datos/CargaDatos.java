package datos;

import java.util.List;
import java.util.Map;

import logica.GeneradorUbicacion;
import modelo.Servicio;
import modelo.Usuario;
import modelo.Vehiculo;

/**
 * Coordina la carga: pide los nombres de archivo a Configuracion y llama a cada
 * carga en orden. Los vehiculos se cargan antes que los usuarios porque
 * los usuarios los buscan por patente
 */
public class CargaDatos {

	//Carga todo usando config.properties
	public static Datos cargar() {
		return cargar(new Configuracion());
	}

	public static Datos cargar(Configuracion config) {
		GeneradorUbicacion generador = new GeneradorUbicacion(config.getLatitud1(), config.getLongitud1(),
				config.getLatitud2(), config.getLongitud2());

		List<Servicio> servicios = CargaServicios.cargar(config.getArchivoServicios());
		Map<String, Vehiculo> vehiculos = CargaVehiculos.cargar(config.getArchivoVehiculos(), generador);
		List<Usuario> usuarios = CargaUsuarios.cargar(config.getArchivoUsuarios(), vehiculos);

		return new Datos(servicios, vehiculos, usuarios);
	}
}