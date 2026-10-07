package datos;

import java.util.List;
import java.util.Map;

import modelo.Servicio;
import modelo.Usuario;
import modelo.Vehiculo;

 // Agrupa todo lo que se carga de los archivos de datos
public class Datos {

	private final List<Servicio> servicios;
	private final Map<String, Vehiculo> vehiculos; // por patente
	private final List<Usuario> usuarios;

	public Datos(List<Servicio> servicios, Map<String, Vehiculo> vehiculos, List<Usuario> usuarios) {
		this.servicios = servicios;
		this.vehiculos = vehiculos;
		this.usuarios = usuarios;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public Map<String, Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
}