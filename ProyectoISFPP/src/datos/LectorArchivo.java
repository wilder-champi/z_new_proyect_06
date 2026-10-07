package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectorArchivo {

	public static List<Linea> leer(String nombreArchivo) {
		List<Linea> lineas = new ArrayList<>();//creamos una list para guardar los objetos linea o las lineas que se pudieron guardar
		try {
			Scanner lector = new Scanner(new File(nombreArchivo));//se crea un escanner para explorar y empesar a leer el archivo desde el inicio
			int numero = 0; //inicialicemos numeor que nos servira para ver en que linea del archivo vamos o cual estamos leyendo
			while (lector.hasNextLine()) {//este while sigue mientras haya otra linea para leer en el archivo
				numero++;//incremetamos el indice de en que linea estamos
				String texto = lector.nextLine().trim(); //lee la siguiente linea del archivo y elimina los espacios
				// ignora las lineas vacias y los comentarios
				if (!texto.isEmpty() && !texto.startsWith("#")) {
					//sie esta es una linea valida se almacena dentro de la list de manera guardando que linea del archivo es y su contenido almesenado de manera separada dentro de otra list
					lineas.add(new Linea(numero, separarCampos(texto)));
				}
			}
			lector.close();//una ves terminamos de leer el archivo cerramso el lector
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("no se encontro el archivo " + nombreArchivo);
		}
		return lineas;//devolvemos la list que contiene todas las lineas que se pudieron almacenar de manera correcta
	}

	
	
	
	private static List<String> separarCampos(String texto) {
		
		List<String> campos = new ArrayList<>();//crea una list para guardar lo valores de la linea
		for (String campo : texto.split(";")) {//con este for separa lso datos atravez de los ;
			campo = campo.trim();//elimina lso espacion de dicho campo
			if (!campo.isEmpty()) {//pregunta si dicho campo esta vacio y si no es asi los añade ala list
				campos.add(campo);
			}
		}
		return campos;
	}
	
	
	
	
}