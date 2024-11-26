package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import game.Parametros;
import managers.AudioManager;
import screens.GameScreen;
import ui.BarraVida;

public class Player extends Element {

	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> espalda;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;

	private Animation<TextureRegion> frenteIdle;
	private Animation<TextureRegion> espaldaIdle;
	private Animation<TextureRegion> drchaIdle;
	private Animation<TextureRegion> izqdaIdle;

	public Element pies;
	public float velocidad;
	private float velocidadSalto;
	public boolean tocoSuelo;
	public boolean enEscalera;
	public Array<ShurikenMagico> cargador;
	private int numBalas = 50;
	private int balaActual = 0;
	private int direccion = 1;
	public float cadencia = 0.03f;
	private float tiempoDisparo = 0;
	public int velocidadShuriken = 10;
	private boolean sonidoCaminando = false;

	public Player(float x, float y, Stage s, GameScreen nivel, BarraVida barra) {
		super(x, y, s);
		this.velocidad = 75;
		this.velocidadSalto = 130;// 100

		// TODO Auto-generated constructor stub
		frente = loadFullAnimation("player/frontMainCharacter1.png", 1, 4, 0.2f, true);
		espalda = loadFullAnimation("player/backMainCharacter1.png", 1, 4, 0.2f, true);
		drcha = loadFullAnimation("player/rightMainCharacter1.png", 1, 4, 0.2f, true);
		izqda = loadFullAnimation("player/leftMainCharacter1.png", 1, 4, 0.2f, true);

		frenteIdle = loadFullAnimation("player/frontMainCharacterIdle1.png", 1, 4, 0.2f, true);
		espaldaIdle = loadFullAnimation("player/backMainCharacterIdle1.png", 1, 4, 0.2f, true);
		drchaIdle = loadFullAnimation("player/rightMainCharacterIdle1.png", 1, 4, 0.2f, true);
		izqdaIdle = loadFullAnimation("player/leftMainCharacterIdle1.png", 1, 4, 0.2f, true);

		this.setPolygon(8, 15, 19, 10, 8);

		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 10);
		pies.setRectangle();

		cargador = new Array<ShurikenMagico>();
		for (int i = 0; i < numBalas; i++) {
			this.cargador.add(new ShurikenMagico(0, 0, s, nivel, true));
		}

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if (tiempoDisparo >= 0) {
			this.tiempoDisparo -= delta;
		}

		controles();

		this.applyPhysics(delta);
		// colocarPies();

		animaciones();

	}

	private void controles() {
		boolean isMoving = (velocity.x != 0 || velocity.y != 0);
		String caminarSoundPath;

		switch (Parametros.nivel) {
		case 3:
			caminarSoundPath = "audio/sounds/caminarTierra.mp3";
			break;
		default:
			caminarSoundPath = "audio/sounds/caminar.mp3";
			break;
		}

		if (isMoving && !AudioManager.isSoundPlaying(caminarSoundPath)) {
			AudioManager.playSound(caminarSoundPath);
			sonidoCaminando = true;
		} else if (!isMoving && sonidoCaminando) {
			AudioManager.stopSpecificSound(caminarSoundPath);
			sonidoCaminando = false;
		}

		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			this.velocity.y = velocidad;
			this.setAnimation(frente);
		} else if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
			this.velocity.y = -velocidad;
			this.setAnimation(espalda);
		} else {
			this.velocity.y = 0;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			this.velocity.x = -velocidad;
			this.setAnimation(izqda);
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			this.velocity.x = velocidad;
			this.setAnimation(drcha);
		} else {
			this.velocity.x = 0;
		}
	}

	public void jump() {

		this.velocity.y = velocidadSalto;
		tocoSuelo = false;
	}

	public void colocarPies() {

		this.pies.setPosition(this.getX() + 3 * this.getWidth() / 8, this.getY() - this.getHeight() / 10);

	}

	public void disparar() {
		AudioManager.playSound("audio/sounds/lanzar.mp3");
		tiempoDisparo = cadencia;
		float bulletVelocityX = 0;
		float bulletVelocityY = 0;

		switch (direccion) {
		case 1:
			bulletVelocityX = velocidadShuriken;
			break;
		case -1:
			bulletVelocityX = -velocidadShuriken;
			break;
		case -2:
			bulletVelocityY = -velocidadShuriken;
			break;
		case 2:
			bulletVelocityY = +velocidadShuriken;
			break;
		}

		float bulletSpawnX = this.getX() + this.getWidth() / 3;
		float bulletSpawnY = this.getY() + this.getHeight() / 3;

		this.cargador.get(balaActual).disparar(bulletVelocityX, bulletVelocityY, bulletSpawnX, bulletSpawnY);
		this.balaActual = (this.balaActual + 1) % this.numBalas;
	}

	public void animaciones() {

		if (this.velocity.x > 0) {
			this.setAnimation(drcha);
			direccion = 1;
		} else if (this.velocity.x < 0) {
			this.setAnimation(izqda);
			direccion = -1;
		} else if (this.velocity.y < 0) {
			this.setAnimation(frente);
			direccion = -2;
		} else if (this.velocity.y > 0) {
			this.setAnimation(espalda);
			direccion = 2;
		}

		if (direccion == 1 && this.velocity.x == 0 && this.velocity.y == 0) {
			this.setAnimation(drchaIdle);
		} else if (direccion == -1 && this.velocity.x == 0 && this.velocity.y == 0) {
			this.setAnimation(izqdaIdle);
		} else if (direccion == -2 && this.velocity.x == 0 && this.velocity.y == 0) {
			this.setAnimation(frenteIdle);
		} else if (direccion == 2 && this.velocity.x == 0 && this.velocity.y == 0) {
			this.setAnimation(espaldaIdle);
		}

		if (Parametros.activarHabilidad == true) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {

				disparar();

			}
		}
	}

	public void dano(int dano) {

	}

}
