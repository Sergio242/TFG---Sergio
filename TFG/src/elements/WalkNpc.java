package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import screens.GameScreen;

public class WalkNpc extends Enemy {

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
	private boolean pisa, choca;

	private int direccion;

	public WalkNpc(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);

		frente = loadFullAnimation("player/frontMainCharacter2.png", 1, 4, 0.2f, true);
		espalda = loadFullAnimation("player/backMainCharacter2.png", 1, 4, 0.2f, true);
		drcha = loadFullAnimation("player/rightMainCharacter2.png", 1, 4, 0.2f, true);
		izqda = loadFullAnimation("player/leftMainCharacter2.png", 1, 4, 0.2f, true);

		frenteIdle = loadFullAnimation("player/frontMainCharacterIdle2.png", 1, 4, 0.2f, true);
		espaldaIdle = loadFullAnimation("player/backMainCharacterIdle2.png", 1, 4, 0.2f, true);
		drchaIdle = loadFullAnimation("player/rightMainCharacterIdle2.png", 1, 4, 0.2f, true);
		izqdaIdle = loadFullAnimation("player/leftMainCharacterIdle2.png", 1, 4, 0.2f, true);

		this.setPolygon(8, 15, 32, 10, 8);
		System.out.println("Soy un NPC que camina como si fuera un loco");

		this.vida = 10;
		this.direccion = -1;
		this.velocidad = 30;

		setAnimation(frenteIdle);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		update(delta);
	}

	private void moverNpc(float delta) {
		if (Math.random() < 0.01) {
			int direccionAleatoria = (int) (Math.random() * 4);
			switch (direccionAleatoria) {
			case 0:
				velX = 0;
				velY = velocidad;
				setAnimation(espalda);
				break;
			case 1:
				velX = 0;
				velY = -velocidad;
				setAnimation(frente);
				break;
			case 2:
				velX = -velocidad;
				velY = 0;
				setAnimation(izqda);
				break;
			case 3:
				velX = velocidad;
				velY = 0;
				setAnimation(drcha);
				break;
			}
		}

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
		if (velY > 0) {
			setAnimation(espaldaIdle);
		} else if (velY < 0) {
			setAnimation(frenteIdle);
		} else if (velX > 0) {
			setAnimation(drchaIdle);
		} else if (velX < 0) {
			setAnimation(izqdaIdle);
		}
	}

}
