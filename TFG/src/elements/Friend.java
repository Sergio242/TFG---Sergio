package elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Friend extends Element {

	public int vida;
	public int dano;
	public float velocidad;
	public GameScreen nivel;

	public Friend(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s);
		this.nivel = nivel;
		this.setEnabled(true);
	}

	public void act(float delta) {
		super.act(delta);
		if (this.getEnabled()) {
			if (this.vida <= 0) {

				this.setEnabled(false);
			}

			collide();

		}
	}

	public void dano(int dano) {
		this.vida -= dano;

	}

	public void collide() {
		if (this.overlaps(this.nivel.player)) {
			nivel.player.dano(this.dano);
		}

	}

}
