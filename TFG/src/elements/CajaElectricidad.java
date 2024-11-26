package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class CajaElectricidad extends Friend {

	private Animation<TextureRegion> cajaOn;
	private Animation<TextureRegion> cajaOff;

	public CajaElectricidad(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		cajaOff = loadFullAnimation("player/electricidadOff.png", 1, 1, 0.15f, false);

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
