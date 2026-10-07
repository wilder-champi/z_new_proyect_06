package logica;

import java.util.Random;

import modelo.Ubicacion;

public class GeneradorUbicacion {
	private double latitudMinima;
	private double latitudMaxima;
	private double longitudMinima;
	private double longitudMaxima;
	private Random aleatorio;

	public GeneradorUbicacion(double latitud1, double longitud1, double latitud2, double longitud2) {
		this.latitudMinima = Math.min(latitud1, latitud2);
		this.latitudMaxima = Math.max(latitud1, latitud2);
		this.longitudMinima = Math.min(longitud1, longitud2);
		this.longitudMaxima = Math.max(longitud1, longitud2);
		this.aleatorio = new Random();
	}

	public GeneradorUbicacion(double latitud1, double longitud1, double latitud2, double longitud2, Random random) {
		if (random == null) {
			throw new IllegalArgumentException("El random no puede ser null");
		}
		this.latitudMinima = Math.min(latitud1, latitud2);
		this.latitudMaxima = Math.max(latitud1, latitud2);
		this.longitudMinima = Math.min(longitud1, longitud2);
		this.longitudMaxima = Math.max(longitud1, longitud2);
		this.aleatorio = random;
	}

	public Ubicacion generar() {
		double latitud = valorAleatorio(this.latitudMinima, this.latitudMaxima);
		double longitud = valorAleatorio(this.longitudMinima, this.longitudMaxima);
		Ubicacion nuevaUbicacion = new Ubicacion(latitud, longitud);
		return nuevaUbicacion;
	}

	public double valorAleatorio(double minimo, double maximo) {
		double ancho = maximo - minimo;
		double aleatorio = this.aleatorio.nextDouble();
		double resultado = minimo + (aleatorio * ancho);
		return resultado;
	}

}
