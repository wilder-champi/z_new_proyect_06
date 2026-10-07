package modelo;

import enums.RolUsuario;

public class Usuario {

	private String nombre;
	private String telefono;
	private String email;
	
	private RolUsuario rolActivo;
	private Cliente cliente;
	private Conductor conductor;
	
	public Usuario(String nombre, String telefono, String email) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		
		this.cliente = new Cliente(); //el usuario ya es cliente por defecto
	    this.rolActivo = RolUsuario.CLIENTE;
	    this.conductor = null;//todavia no es conductor asta que se registre 
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RolUsuario getRolActivo() {
		return rolActivo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Conductor getConductor() {
		return conductor;
	}
	
	//metodo
	public void altaConductor(String licencia,Vehiculo vehiculo) {
		
		if (licencia == null || vehiculo == null) {
			throw new IllegalArgumentException("se debe registrar la licencia y vehiculo");
		}
		
		if(conductor != null) {
			throw new IllegalArgumentException("el usuario ya esta registrado como conductor");

		}
			// Crea el conductor con su licencia y primer vehículo,
			conductor = new Conductor(licencia, vehiculo);
	}
	
	//metodo
	public void cambiarRolActivo(RolUsuario rolNuevo) {
		if (rolNuevo == null)
			throw new IllegalArgumentException("debe ingresar un rol");
		
		if(rolNuevo == RolUsuario.CONDUCTOR && conductor == null ) {
			throw new IllegalArgumentException("el usuario todavia no esta registrado como conductor");
		}
		rolActivo = rolNuevo;
	}
	
}
