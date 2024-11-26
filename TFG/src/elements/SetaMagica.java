package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class SetaMagica extends Friend {

	private Animation<TextureRegion> setaGirando;

	private int direccion;

	public SetaMagica(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		setaGirando = loadFullAnimation("player/setaMagicaR.png", 1, 8, 0.15f, true);

		this.setPolygon(16, 14, 13, -1, 1);
		this.vida = 10;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}

	public void update() {

	}

}
