package game;

public class Parametros {

//Screen
 //private static int anchoPantalla=1200;
 //private static int altoPantalla=900;
 
 private static int anchoPantalla=800;
 private static int altoPantalla=600;
 
 public static boolean debug=false;
 public static boolean balaPlayer;

 
 //Audio;
 public static float musicVolume=1;
 public static float soundVolume=1;
 
 
// public static float zoom=0.24f;
 public static float zoom=0.30f;
 
 //variables de juego
 
 public static int nivel=0;
 public static int tiempoTranscurrido=0;
 public static boolean llegoAlDestino = false;
 public static int gravedad=-130;
 public static float jugadorx=0;
 public static float jugadory=0;
 public static int vidaActual = 170;
 public static int maxVida=50;
 public static boolean activarHabilidad = false;
 public static boolean habilidadShurikenActiva = false;
 public static boolean habilidadCorrerActiva = false;
 public static int contadorRatasMuertas = 0;
 public static int puntuacion = 0;


 
 

 
 
 
 



public static int getAnchoPantalla() {
	return anchoPantalla;
}

public static void setAnchoPantalla(int anchoPantalla) {
	Parametros.anchoPantalla = anchoPantalla;
}

public static int getAltoPantalla() {
	return altoPantalla;
}

public static void setAltoPantalla(int altoPantalla) {
	Parametros.altoPantalla = altoPantalla;
}




 
}
