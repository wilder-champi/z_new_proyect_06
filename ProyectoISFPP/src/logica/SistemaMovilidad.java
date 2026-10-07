package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datos.Datos;
import modelo.Servicio;
import modelo.Usuario;
import modelo.Vehiculo;
import modelo.Viaje;

public class SistemaMovilidad {

    private List<Usuario> usuarios;
    private List<Servicio> servicios;
    private Map<String, Vehiculo> vehiculos;
    private List<Viaje> viajes;

    private GestorViajes gestorViajes;

    public SistemaMovilidad(Datos datos) {

        if (datos == null) {
            throw new IllegalArgumentException(
                    "Los datos no pueden ser null");
        }

        this.usuarios = datos.getUsuarios();
        this.servicios = datos.getServicios();
        this.vehiculos = datos.getVehiculos();

        this.viajes = new ArrayList<>();

        this.gestorViajes = new GestorViajes(viajes, usuarios);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public Map<String, Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Viaje> getViajes() {
        return new ArrayList<>(viajes);
    }

    public GestorViajes getGestorViajes() {
        return gestorViajes;
    }
}