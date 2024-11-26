package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.Parametros;
import managers.AudioManager;

public class Train extends Element {
	private Animation<TextureRegion> cerrada;
	private Animation<TextureRegion> abierta;
	private Animation<TextureRegion> abiertaTodo;
	private boolean sonidoTrenEnMarcha = false;
	private static final float VELOCIDAD_MAXIMA = 500;
	private static final float VELOCIDAD_MINIMA = 0;
	public static final float DISTANCIA_MINIMA_PARA_FRENAR = 900;
	public float distanciaRestante;
	float destinoX = 674.3099f;
	float destinoY = 539.3494f;
	float velocidadTren;

	private boolean llegoAlDestino = false;
	private boolean animacionAbiertaReproducida = false;
	public float tiempoTranscurrido = 0;

	public Train(float x, float y, Stage s) {
		super(x, y, s);
		cerrada = loadFullAnimationTrain("maps/images/enoden.png", 1, 1, 1f, true);
		abierta = loadFullAnimationTrain("maps/images/enoden-04-R-OD.png", 1, 17, 0.05f, true);
		abiertaTodo = loadFullAnimationTrain("maps/images/enoden-05-R-OD.png", 1, 1, 1f, true);
		this.setRectangle();
		this.velocity.x = -VELOCIDAD_MAXIMA;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		tiempoTranscurrido += delta;

		distanciaRestante = distanciaHastaDestino(destinoX, destinoY);

		float direccion = Math.signum(destinoX - getX());

		float umbral = 12;

		if (Math.abs(distanciaRestante) <= umbral) {
			llegoAlDestino = true;
			this.velocity.x = 0;
		} else {

			if (Math.abs(distanciaRestante) > DISTANCIA_MINIMA_PARA_FRENAR) {
				velocidadTren = VELOCIDAD_MAXIMA;
			} else {
				velocidadTren = VELOCIDAD_MINIMA + (Math.abs(distanciaRestante) / DISTANCIA_MINIMA_PARA_FRENAR)
						* (VELOCIDAD_MAXIMA - VELOCIDAD_MINIMA);
			}

			this.velocity.x = velocidadTren * direccion;
		}

		animaciones();
		this.applyPhysicsTrain(delta);
	}

	public void animaciones() {
		System.out.println(tiempoTranscurrido);

		boolean trenMoviendo = !llegoAlDestino || tiempoTranscurrido < 21.7;
		String sonidoTrenPath = "audio/sounds/tranviaLLegando.mp3";

		if (trenMoviendo && !AudioManager.isSoundPlaying(sonidoTrenPath)) {
			AudioManager.playSound(sonidoTrenPath);
			sonidoTrenEnMarcha = true;
		} else if (!trenMoviendo && sonidoTrenEnMarcha) {
			AudioManager.stopSpecificSound(sonidoTrenPath);
			sonidoTrenEnMarcha = false;
		}

		if (llegoAlDestino && tiempoTranscurrido >= 21.7) {
			System.out.println(tiempoTranscurrido + " abierta");
			System.out.println("Esta abierta");
			this.setAnimation(abierta);
		} else {
			System.out.println(tiempoTranscurrido + " cerrada del todo");
			System.out.println("sigue cerrada");
			this.setAnimation(cerrada);
		}

		if (llegoAlDestino && tiempoTranscurrido >= 22) {
			System.out.println(tiempoTranscurrido + " abierta del todo");
			System.out.println("Esta abierta del todo");
			this.setAnimation(abiertaTodo);
		}
	}

	private float distanciaHastaDestino(float destinoX, float destinoY) {
		float dx = destinoX - this.getX();
		float dy = destinoY - this.getY();
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
}
