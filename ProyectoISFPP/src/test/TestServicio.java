package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import modelo.Servicio;
import enums.CategoriaVehiculo;
import enums.TipoServicio;
import enums.TipoVehiculo;

class TestServicio {

	@Test
	void calcularcosto() {
		Servicio s = new Servicio("Estandar", 2000, 200, 100,
				 TipoVehiculo.AUTO, CategoriaVehiculo.ESTANDAR,TipoServicio.PASAJEROS);
		
		//Como se calculo el costo 
		/*Tarifa base:2000
		 * PrecioKm:200
		 * PrecioMinuto:100
		 * km:10
		 * Minutos:30
		 * Calcu: 2000+(200 * 10)+(100 * 30) = 7000
		 * */
		
		assertEquals(7000, s.calcularCosto(10, 30), 0.001);
		
	}

}
