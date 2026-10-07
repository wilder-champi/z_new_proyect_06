package modelo;

import java.time.LocalDateTime;
import java.util.List;
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
	private  List<RegistroViaje> registroViaje;
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
		this.registroViaje = new ArrayList<>();
	}
	
	public void solicitar(LocalDateTime fechaHora) {
		this.registroViaje.add(new RegistroViaje(fechaHora, EstadoViaje.SOLICITADO)); 
	}
	
	public void aceptar(LocalDateTime fechaHora,Usuario conductor) {
		this.conductor = conductor;
		this.registroViaje.add(new RegistroViaje(fechaHora, EstadoViaje.ACEPTADO));
	}
	
	public void iniciar(LocalDateTime fechaHora) {
		this.registroViaje.add(new RegistroViaje(fechaHora, EstadoViaje.INICIADO));
	}
	
	public void finalizar(LocalDateTime fechaHora,CalificacionViaje calificacionConductor,CalificacionViaje calificacionCliente) {
		this.calificacionConductor = calificacionConductor;
		this.calificacionCliente = calificacionCliente;
		this.registroViaje.add(new RegistroViaje(fechaHora, EstadoViaje.FINALIZADO));
	}
	
	public void cancelar(LocalDateTime fechaHora, Usuario usuario, String motivo) {
		//Veo de que rol es el usuario
		if(usuario.equals(cliente)) {
			this.rolCancela = RolUsuario.CLIENTE;
		}else if(usuario.equals(conductor)){
			this.rolCancela = RolUsuario.CONDUCTOR;
		}
		
		this.motivoCancelacion = motivo;
		this.registroViaje.add(new RegistroViaje(fechaHora, EstadoViaje.CANCELADO));
	}
	
	public void rechazar(LocalDateTime fechaHora) {
		this.registroViaje.add(new RegistroViaje(fechaHora, EstadoViaje.RECHAZADO));
	}
	
	public EstadoViaje estadoActual() {
		if(registroViaje.isEmpty()) {
			return null;
		}
		//Retorno el ultimo estado almacenado.
		return this.registroViaje.get(this.registroViaje.size()-1).getEstadoViaje();
	}

	//Getters y Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public Ubicacion getOrigen() {
		return origen;
	}

	public void setOrigen(Ubicacion origen) {
		this.origen = origen;
	}

	public Ubicacion getDestino() {
		return destino;
	}

	public void setDestino(Ubicacion destino) {
		this.destino = destino;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public List<RegistroViaje> getRegistroViaje() {
		return registroViaje;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getConductor() {
		return conductor;
	}

	public void setConductor(Usuario conductor) {
		this.conductor = conductor;
	}

	public RolUsuario getRolCancela() {
		return rolCancela;
	}

	public void setRolCancela(RolUsuario rolCancela) {
		this.rolCancela = rolCancela;
	}

	public CalificacionViaje getCalificacionConductor() {
		return calificacionConductor;
	}

	public void setCalificacionConductor(CalificacionViaje calificacionConductor) {
		this.calificacionConductor = calificacionConductor;
	}

	public CalificacionViaje getCalificacionCliente() {
		return calificacionCliente;
	}

	public void setCalificacionCliente(CalificacionViaje calificacionCliente) {
		this.calificacionCliente = calificacionCliente;
	}
	
}
