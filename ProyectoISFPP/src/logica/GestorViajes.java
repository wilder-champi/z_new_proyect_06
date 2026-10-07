package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import enums.CalificacionViaje;
import enums.CategoriaVehiculo;
import enums.EstadoConductor;
import enums.EstadoViaje;

import modelo.Conductor;
import modelo.Servicio;
import modelo.Ubicacion;
import modelo.Usuario;
import modelo.Vehiculo;
import modelo.Viaje;

public class GestorViajes {

	private List<Viaje> viajes;
	private List<Usuario> usuarios;

	public GestorViajes(List<Viaje> viajes, List<Usuario> usuarios) {

		if (viajes == null || usuarios == null) {
			throw new IllegalArgumentException("Las listas no pueden ser null");
		}
		this.viajes = viajes;
		this.usuarios = usuarios;
	}

	public Viaje solicitarViaje(Usuario cliente, Ubicacion origen, Ubicacion destino, Servicio servicio) {

		if (cliente == null || origen == null || destino == null || servicio == null) {
			throw new IllegalArgumentException("Cliente, origen, destino y servicio son obligatorios");
		}
		Viaje viaje = new Viaje(UUID.randomUUID(), origen, destino, servicio, cliente);
		viaje.solicitar(LocalDateTime.now());
		viajes.add(viaje);
		return viaje;
	}

	public List<Usuario> buscarConductoresDisponibles(Viaje viaje) {

		if (viaje == null) {
			throw new IllegalArgumentException("El viaje no puede ser null");
		}
		if (viaje.estadoActual() != EstadoViaje.SOLICITADO) {
			throw new IllegalStateException("El viaje debe estar SOLICITADO");
		}
		List<Usuario> disponibles = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			Conductor conductor = usuario.getConductor();
			if (conductor != null && conductor.getEstadoconductor() == EstadoConductor.DISPONIBLE
					&& conductor.getVehiculoActivo() != null && cumpleCondiciones(viaje, conductor)) {
				disponibles.add(usuario);
			}
		}
		return disponibles;
	}

	private boolean cumpleCondiciones(Viaje viaje, Conductor conductor) {

		Vehiculo vehiculo = conductor.getVehiculoActivo();
		Servicio servicio = viaje.getServicio();
		boolean tipoVehiculoCorrecto = vehiculo.getTipoVehiculo() == servicio.getTipoVehiculo();
		boolean tipoServicioCorrecto = vehiculo.getTipoServicios().contains(servicio.getTipoServicio());
		int categoriaVehiculo = vehiculo.getCategoriaVehiculo().getValor();
		int categoriaSolicitada = servicio.getCategoriaVehiculo().getValor();
		CategoriaVehiculo minimaAceptada = conductor.getCategoriaVehiculoActivo();

		if (minimaAceptada == null) {
			minimaAceptada = vehiculo.getCategoriaVehiculo();
		}
		int categoriaMinima = minimaAceptada.getValor();
		boolean categoriaCorrecta = categoriaSolicitada <= categoriaVehiculo && categoriaSolicitada >= categoriaMinima;
		return tipoVehiculoCorrecto && tipoServicioCorrecto && categoriaCorrecta;
	}

	public void aceptarViaje(Viaje viaje, Usuario usuarioConductor) {
		if (viaje == null || usuarioConductor == null) {
			throw new IllegalArgumentException("Viaje y conductor son obligatorios");
		}
		if (viaje.estadoActual() != EstadoViaje.SOLICITADO) {
			throw new IllegalStateException("Solo se puede aceptar un viaje SOLICITADO");
		}
		Conductor conductor = usuarioConductor.getConductor();
		if (conductor == null) {
			throw new IllegalArgumentException("El usuario no esta registrado como conductor");
		}
		if (conductor.getEstadoconductor() != EstadoConductor.DISPONIBLE) {
			throw new IllegalStateException("El conductor no esta DISPONIBLE");
		}
		if (conductor.getVehiculoActivo() == null) {
			throw new IllegalStateException("El conductor no tiene un vehiculo activo");
		}
		if (!cumpleCondiciones(viaje, conductor)) {
			throw new IllegalStateException("El conductor no cumple las condiciones del viaje");
		}
		viaje.setVehiculo(conductor.getVehiculoActivo());
		viaje.aceptar(LocalDateTime.now(), usuarioConductor);
		conductor.agregarViaje(viaje);
		conductor.setEstadoconductor(EstadoConductor.VIAJE_A_ORIGEN);
	}

	public void iniciarViaje(Viaje viaje) {

		if (viaje == null) {
			throw new IllegalArgumentException("El viaje no puede ser null");
		}
		if (viaje.estadoActual() != EstadoViaje.ACEPTADO) {
			throw new IllegalStateException("Solo se puede iniciar un viaje ACEPTADO");
		}
		if (viaje.getConductor() == null || viaje.getConductor().getConductor() == null) {
			throw new IllegalStateException("El viaje no tiene conductor asignado");
		}
		viaje.iniciar(LocalDateTime.now());
		viaje.getConductor().getConductor().setEstadoconductor(EstadoConductor.VIAJE_A_DESTINO);
	}

	public void finalizarViaje(Viaje viaje, CalificacionViaje calificacionConductor,
			CalificacionViaje calificacionCliente) {
		
		if (viaje == null) {
			throw new IllegalArgumentException("El viaje no puede ser null");
		}
		if (viaje.estadoActual() != EstadoViaje.INICIADO) {
			throw new IllegalStateException("Solo se puede finalizar un viaje INICIADO");
		}
		if (calificacionConductor == null || calificacionCliente == null) {
			throw new IllegalArgumentException("Las dos calificaciones son obligatorias");
		}
		if (calificacionConductor == CalificacionViaje.NO_CALIFICADO
				|| calificacionCliente == CalificacionViaje.NO_CALIFICADO) {
			throw new IllegalArgumentException("Un viaje finalizado debe tener ambas calificaciones");
		}
		if (viaje.getConductor() == null || viaje.getConductor().getConductor() == null) {
			throw new IllegalStateException("El viaje no tiene conductor asignado");
		}
		viaje.finalizar(LocalDateTime.now(), calificacionConductor, calificacionCliente);
		viaje.getConductor().getConductor().setEstadoconductor(EstadoConductor.DISPONIBLE);
	}

	public void cancelarViaje(Viaje viaje, Usuario usuario, String motivo) {
		
		if (viaje == null || usuario == null) {
			throw new IllegalArgumentException("Viaje y usuario son obligatorios");
		}
		if (motivo == null || motivo.isBlank()) {
			throw new IllegalArgumentException("Debe indicar el motivo de cancelacion");
		}
		EstadoViaje estado = viaje.estadoActual();
		if (estado == null) {
			throw new IllegalStateException("El viaje todavia no fue solicitado");
		}
		if (estado == EstadoViaje.FINALIZADO || estado == EstadoViaje.CANCELADO || estado == EstadoViaje.RECHAZADO) {
			throw new IllegalStateException("El viaje ya no se puede cancelar");
		}
		boolean esCliente = usuario.equals(viaje.getCliente());
		boolean esConductor = viaje.getConductor() != null && usuario.equals(viaje.getConductor());
		if (!esCliente && !esConductor) {
			throw new IllegalArgumentException("Solo el cliente o el conductor asignado pueden cancelar el viaje");
		}
		viaje.cancelar(LocalDateTime.now(), usuario, motivo);
		if (viaje.getConductor() != null && viaje.getConductor().getConductor() != null) {
			viaje.getConductor().getConductor().setEstadoconductor(EstadoConductor.DISPONIBLE);
		}
	}

	public void rechazarViaje(Viaje viaje) {

		if (viaje == null) {
			throw new IllegalArgumentException("El viaje no puede ser null");
		}
		if (viaje.estadoActual() != EstadoViaje.SOLICITADO) {
			throw new IllegalStateException("Solo se puede rechazar un viaje SOLICITADO");
		}
		viaje.rechazar(LocalDateTime.now());
	}
	public List<Viaje> getViajes() {
		return new ArrayList<>(viajes);
	}
}