package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import screens.GameScreen;

public class WalkNpc3 extends Enemy {

	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> espalda;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;

	private Animation<TextureRegion> frenteIdle;
	private Animation<TextureRegion> espaldaIdle;
	private Animation<TextureRegion> drchaIdle;
	private Animation<TextureRegion> izqdaIdle;

	private Element pie;
	private Element cabeza;

	private float velX = 0;
	private float velY = 0;
	private float tiempoIdle = 0;
	private float idleDuracion = 2f;

	public WalkNpc3(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);

		drcha = loadFullAnimation("player/rightMainCharacter4.png", 1, 4, 0.2f, true);
		drchaIdle = loadFullAnimation("player/rightMainCharacterIdle4.png", 1, 4, 0.2f, true);

		this.setPolygon(8, 15, 32, 10, 8);
		System.out.println("Soy un NPC que camina hacia la derecha");

		this.vida = 10;
		this.velocidad = 30;

		setAnimation(frenteIdle);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		update(delta);
	}

	private void moverNpc(float delta) {
		velX = velocidad;
		velY = 0;
		setAnimation(drcha);

		moveBy(velX * delta, velY * delta);
		if (Math.random() < 0.005) {
			tiempoIdle = idleDuracion;
			setIdleAnimation();
		}
	}

	public void update(float delta) {

		if (tiempoIdle > 0) {
			tiempoIdle -= delta;
			setIdleAnimation();
			velX = 0;
			velY = 0;
		} else {
			moverNpc(delta);
		}
	}

	private void setIdleAnimation() {
		setAnimation(drchaIdle);
	}
}
