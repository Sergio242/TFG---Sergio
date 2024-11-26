package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class Manzana extends Friend {

	private Animation<TextureRegion> setaGirando;

	private int direccion;

	public Manzana(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		setaGirando = loadFullAnimation("player/manzanaM.png", 1, 1, 0.15f, false);

		this.setPolygon(19, 17, 13, -1, 1);
		this.vida = 10;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}

	public void update() {

	}

}
