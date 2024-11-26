package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import managers.AudioManager;
import screens.GameScreen;
import ui.BarraVida;

public class Electricista extends Friend {
	private Animation<TextureRegion> frente;
	private Animation<TextureRegion> espalda;
	private Animation<TextureRegion> drcha;
	private Animation<TextureRegion> izqda;

	private Animation<TextureRegion> frenteIdle;
	private Animation<TextureRegion> espaldaIdle;
	private Animation<TextureRegion> drchaIdle;
	public Animation<TextureRegion> izqdaIdle;
	private Array<Solid> suelo;
	private int vidaMaxima = 1000;
	public int vidaActual = vidaMaxima;
	public Element pie;
	public Element solidG;
	private Player player;
	private boolean muerteIniciada = false;
	private int direccion;
	public boolean muerto = false;
	private float velocidad = 57;
	private float rangoVision;
	private float tiempoDesdeUltimoAtaque = 3f;
	private boolean collisionEnabled;
	private BarraVida barra;

	public Electricista(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		this.nivel = nivel;
		frente = loadFullAnimation("player/frontMainCharacter5.png", 1, 4, 0.2f, true);
		espalda = loadFullAnimation("player/backMainCharacter5.png", 1, 4, 0.2f, true);
		drcha = loadFullAnimation("player/rightMainCharacter5.png", 1, 4, 0.2f, true);
		izqda = loadFullAnimation("player/leftMainCharacter5.png", 1, 4, 0.2f, true);
		frenteIdle = loadFullAnimation("player/frontMainCharacterIdle5.png", 1, 4, 0.2f, true);
		espaldaIdle = loadFullAnimation("player/backMainCharacterIdle5.png", 1, 4, 0.2f, true);
		drchaIdle = loadFullAnimation("player/rightMainCharacterIdle5.png", 1, 4, 0.2f, true);
		izqdaIdle = loadFullAnimation("player/leftMainCharacterIdle5.png", 1, 4, 0.2f, true);
		pie = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pie.setPolygon(14, 26, 27, 18, 24);
		ajustarPie();
		suelo = new Array<Solid>();
		collisionEnabled = true;
		this.setPolygon(8, 15, 32, 10, 8);
		this.vida = 60;
		rangoVision = 50;
		iniciarMovimientoAleatorio();
	}

	public void enableCollision() {
		collisionEnabled = true;
		this.setPolygon(12, 14, 15, 25, 28);
	}

	public void disableCollision() {
		collisionEnabled = false;
		this.setPolygon(3, 0, 0, 0, 0);

	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setBarraVida(BarraVida barra) {
		this.barra = barra;
	}

	public void ajustarPie() {
		pie.setPosition(this.getX() + this.getWidth() * 0.00000001f, this.getY() - this.getHeight() / 24);
	}

	private void iniciarMovimientoAleatorio() {
		direccion = MathUtils.random(0, 1);
		if (!muerto) {
			switch (direccion) {
			case 0:
				setAnimation(izqdaIdle);
				break;
			case 1:
				setAnimation(drchaIdle);
				break;
			default:
				break;
			}
		}

	}

	private float tiempoInmovilizado = 0.5f;
	private float tiempoTranscurridoInmovilizado = 0f;
	float tiempoTranscurridoDesdeUltimoSonido = 0f;

	@Override
	public void act(float delta) {
		super.act(delta);
		ajustarPie();
		tiempoTranscurridoInmovilizado += delta;

		if (vidaActual > 0) {
			if (!nivel.player.overlaps(this)) {
				if (tiempoTranscurridoInmovilizado >= tiempoInmovilizado) {
					float dirX = nivel.player.getX() - getX();
					float dirY = nivel.player.getY() - getY();
					float distancia = (float) Math.sqrt(dirX * dirX + dirY * dirY);

					if (distancia != 0) {
						dirX /= distancia;
						dirY /= distancia;
					}

					if (distancia < rangoVision) {
						if (tiempoTranscurridoDesdeUltimoSonido >= 7f) {
							tiempoTranscurridoDesdeUltimoSonido = 0f;
						}

						moveBy(dirX * velocidad * delta, dirY * velocidad * delta);

						if (Math.abs(dirX) > Math.abs(dirY)) {

							if (dirX > 0) {
								setAnimation(drcha);
							} else {
								setAnimation(izqda);
							}
						} else {
							if (dirY > 0) {
								setAnimation(espalda);
							} else {
								setAnimation(frente);
							}
						}
					} else {
						if (!muerto) {
							switch (direccion) {
							case 0:
								setAnimation(izqdaIdle);
								break;
							case 1:
								setAnimation(drchaIdle);
								break;
							case 2:
								setAnimation(espaldaIdle);
								break;
							case 3:
								setAnimation(frenteIdle);
								break;
							default:
								break;
							}
						}
					}
				} else {
					setAnimation(isFacingRight() ? drchaIdle : izqdaIdle);
				}
			} else {
				tiempoTranscurridoInmovilizado = 1f;
			}
		}
	}

	public boolean isFacingRight() {
		return (direccion == 1);
	}
}
