package datos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelo.Usuario;
import modelo.Vehiculo;


public class CargaUsuarios {

	private static final int CAMPOS_USUARIO = 3; // nombre, telefono y email
	private static final int POSICION_LICENCIA = 3; //indice de la posicion de la licencia e conducir
	private static final int POSICION_PRIMERA_PATENTE = 4;//indice de la posicion de la primer patente del vehiculo que le pertenece

	// recibe el mapa de vehiculos para poder buscar cada patente
	public static List<Usuario> cargar(String archivo, Map<String, Vehiculo> vehiculos) {
		
		List<Usuario> usuarios = new ArrayList<>();//crea una list para guardar los usuarios validos del archivo.txt
		Set<String> emails = new HashSet<>(); // para detectar emails repetidos ya que set solo es una coleccin de elementos unicos
		Set<String> patentesAsignadas = new HashSet<>(); // para detectar vehiculos con dos dueños

		for (Linea linea : LectorArchivo.leer(archivo)) {//con un for itch exploramos la list con todas las lineas del archivo 
			Usuario usuario = crear(archivo, linea, vehiculos, patentesAsignadas);//crea un objeto de tipo usuario pidiendole que lo haga el metodo crear que hace unas verificaciones antes de crearlo
		
			if (!emails.add(usuario.getEmail())) { // add devuelve false si ya estaba
				throw new ArchivoInvalidoException(archivo, linea.getNumero(),
						"email repetido " + usuario.getEmail());
			}
			usuarios.add(usuario);//con es un usuario valido lo añado ala list
		}
		return usuarios;//retorno la list de usuarios validos con conductores en alta y usuarios normales
	}
	
	

	private static Usuario crear(String archivo, Linea linea, Map<String, Vehiculo> vehiculos,
			Set<String> patentesAsignadas) {
		
		List<String> c = linea.getCampos();//creo una nueva list y le asigno la list que contiene diversos atrbutos o campos de una linea del archivo
		
		int numero = linea.getNumero();//asigno de que numero de linea del archivo es dichos datos

		if (c.size() < CAMPOS_USUARIO) {//me fijo si el dicha linea cuenta con la cantidad minima de datos para ser un usuario si no es asi lanso una exception
			throw new ArchivoInvalidoException(archivo, numero, "faltan datos (nombre, telefono y email)");
		}
		
		Usuario usuario = new Usuario(c.get(0), c.get(1), c.get(2));//si cumplio con los minimos datos creo un objeto de tipo usuario basico

		if (c.size() == CAMPOS_USUARIO) {
			return usuario; // no tiene licencia, es solo cliente
		}
		if (c.size() == POSICION_PRIMERA_PATENTE) {
			throw new ArchivoInvalidoException(archivo, numero, "tiene licencia pero ninguna patente");
		}

		// busca cada patente en el mapa de vehiculos
		List<Vehiculo> propios = new ArrayList<>();
		for (String patente : c.subList(POSICION_PRIMERA_PATENTE, c.size())) {//creo una sublist que contiene todas las patentes de los vehiculos que le pertenecen 
			Vehiculo vehiculo = vehiculos.get(patente);
			if (vehiculo == null) {
				throw new ArchivoInvalidoException(archivo, numero, "la patente " + patente + " no existe");
			}
			if (!patentesAsignadas.add(patente)) {
				throw new ArchivoInvalidoException(archivo, numero,
						"la patente " + patente + " ya pertenece a otro conductor");
			}
			propios.add(vehiculo);
		}

		// el alta de conductor pide un primer vehiculo, los demas se agregan despues
		usuario.altaConductor(c.get(POSICION_LICENCIA), propios.get(0));
		for (Vehiculo extra : propios.subList(1, propios.size())) {
			usuario.getConductor().agregarVehiculo(extra);
		}
		return usuario;//retorno un usuario con dado de alta como conductor
	}
}