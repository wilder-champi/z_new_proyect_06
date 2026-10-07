package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import enums.CalificacionViaje;
import enums.RolUsuario;
import enums.EstadoViaje;

public class Viaje {
	private UUID id;
	private String motivoCancelacion;

	private Ubicacion origen;
	private Ubicacion destino;
	private Servicio servicio;
	private ArrayList<RegistroViaje> registroViaje = new ArrayList<>();
	private Vehiculo vehiculo;
	private Usuario cliente;
	private Usuario conductor;

	private RolUsuario rolCancela;
	private CalificacionViaje calificacionConductor;
	private CalificacionViaje calificacionCliente;
	public Viaje(UUID id, Ubicacion origen, Ubicacion destino, Servicio servicio, Usuario cliente) {
		super();
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.servicio = servicio;
		this.cliente = cliente;
	}
	
	public void solicitar(LocalDateTime fechaHora) {
		
	}
	public void aceptar(LocalDateTime fechaHora,Usuario conductor) {
		
	}
	
	public void iniciar(LocalDateTime fechaHora) {
		
	}
	
	public void finalizar(LocalDateTime fechaHora,CalificacionViaje calificacionConductor,CalificacionViaje calificacionCliente) {
		
	}
	
	public void cancelar(LocalDateTime fechaHora, Usuario usuario, String motivo) {
		
	}
	
	public void rechazar(LocalDateTime fechaHora) {
		
	}
	public EstadoViaje estadoActual() {
		
		return null;
	}
	
	
	
	
	
	
	
	
	
}
