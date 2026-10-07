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
		
		this.cliente = new Cliente(); //El usuario ya es cliente por defecto
	    this.rolActivo = RolUsuario.CLIENTE;
	    this.conductor = null;	//Todavia no es conductor hasta que se registre 
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
	
	//Metodo
	public void altaConductor(String licencia,Vehiculo vehiculo) {
		
		if (licencia == null || vehiculo == null) {
			throw new IllegalArgumentException("Se debe registrar la licencia y vehiculo");
		}
		
		if(conductor != null) {
			throw new IllegalArgumentException("El usuario ya esta registrado como conductor");

		}
			//Crea el conductor con su licencia y primer vehículo,
			conductor = new Conductor(licencia, vehiculo);
	}
	
	//Metodo
	public void cambiarRolActivo(RolUsuario rolNuevo) {
		if (rolNuevo == null)
			throw new IllegalArgumentException("Debe ingresar un rol");
		
		if(rolNuevo == RolUsuario.CONDUCTOR && conductor == null ) {
			throw new IllegalArgumentException("El usuario todavia no esta registrado como conductor");
		}
		rolActivo = rolNuevo;
	}
	
}
