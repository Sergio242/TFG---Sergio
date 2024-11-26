package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import game.Parametros;
import managers.AudioManager;
import screens.GameScreen;

public class ShurikenMagico extends Element {
	private GameScreen nivel;
	public int dano;
	public float velocidad;
	public float duracionBala;
	private float tiempoActiva;

	public ShurikenMagico(float x, float y, Stage s, GameScreen nivel, boolean balaPlayer) {
		super(x, y, s);
		this.nivel = nivel;
		this.loadFullAnimation("player/shuriken.png", 1, 1, 0.2f, true);
		this.setPolygon(16, 17, 17, 1, 1);
		this.setEnabled(false);
		this.duracionBala = 1.5f;
		this.velocidad = 20;
		this.dano = 10;
		Parametros.balaPlayer = balaPlayer;
	}

	public void act(float delta) {
		if (this.getEnabled()) {
			super.act(delta);
			this.applyPhysics(delta);
			if (this.tiempoActiva >= this.duracionBala) {
				this.setEnabled(false);
			} else {

				this.tiempoActiva += delta;
			}
			colide();

		}

	}

	private void colide() {

		if (Parametros.balaPlayer) {
			for (Enemy e : this.nivel.enemigos) {
				if (e.getEnabled() && this.overlaps(e)) {
					if (e instanceof Rata) {
						Rata rata = (Rata) e;

						rata.vidaActual -= 1000;
						if (rata.vidaActual <= 0) {

							rata.muerto = true;
							Parametros.contadorRatasMuertas++;
						}
						this.setEnabled(false);

					}

				}

			}
		}

		for (Solid s : this.nivel.suelo) {
			if (this.overlaps(s)) {
				this.setEnabled(false);

			}
		}
	}

	public void disparar(float dirX, float dirY, float x, float y) {
		this.setEnabled(true);
		this.tiempoActiva = 0;
		this.setPosition(x, y);
		this.velocity.x = dirX * velocidad;
		this.velocity.y = dirY * velocidad;

	}

}
