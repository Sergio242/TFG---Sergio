package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import game.Parametros;
import managers.AudioManager;
import screens.GameScreen;
import ui.BarraVida;

public class Rata extends Enemy {
	private Animation<TextureRegion> rataRightIdle;
	private Animation<TextureRegion> rataLeftIdle;
	private Animation<TextureRegion> rataRightWalk;
	private Animation<TextureRegion> rataLeftWalk;
	private Animation<TextureRegion> rataRightDeath;
	private Animation<TextureRegion> rataLeftDeath;
	private Animation<TextureRegion> rataRightAttack;
	private Animation<TextureRegion> rataLeftAttack;
	public Animation<TextureRegion> rataRightHurt;
	public Animation<TextureRegion> rataLeftHurt;
	private Array<Solid> suelo;
	private int vidaMaxima = 1000;
	public int vidaActual = vidaMaxima;
	public Element pie;
	public Element solidG;
	private Player player;
	private boolean muerteIniciada = false;
	private int direccion;
	public boolean muerto = false;
	private float velocidad = 74;
	private float rangoVision;
	private float tiempoDesdeUltimoAtaque = 3f;
	private boolean collisionEnabled;
	private BarraVida barra;

	public Rata(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);
		this.nivel = nivel;
		rataRightIdle = loadFullAnimation("enemies/rataRightIdle.png", 1, 4, 0.25f, true);
		rataLeftIdle = loadFullAnimation("enemies/rataLeftIdle.png", 1, 4, 0.25f, true);
		rataRightWalk = loadFullAnimation("enemies/rataRightWalk.png", 1, 6, 0.15f, true);
		rataLeftWalk = loadFullAnimation("enemies/rataLeftWalk.png", 1, 6, 0.15f, true);
		rataRightAttack = loadFullAnimation("enemies/rataRightAttack.png", 1, 8, 0.08f, true);
		rataLeftAttack = loadFullAnimation("enemies/rataLeftAttack.png", 1, 8, 0.08f, true);
		rataRightDeath = loadFullAnimation("enemies/rataRightDeath.png", 1, 5, 0.14f, false);
		rataLeftDeath = loadFullAnimation("enemies/rataRightDeath.png", 1, 5, 0.14f, false);
		pie = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 4);
		pie.setPolygon(14, 26, 27, 18, 24);
		ajustarPie();
		suelo = new Array<Solid>();
		collisionEnabled = true;
		this.setPolygon(12, 14, 15, 25, 13);
		this.vida = 60;
		rangoVision = 130;
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

	public void colideGoblin() {
		if (nivel.player.overlaps(pie)) {
			if (isFacingRight()) {
				setAnimation(rataLeftAttack);
				if (velocity.x > 0) {
					direccion = 1;
				} else if (velocity.x < 0) {
					direccion = -1;
				} else if (velocity.y < 0) {
					direccion = -2;
				} else if (velocity.y > 0) {
					direccion = 2;
				}
			} else {
				setAnimation(rataRightAttack);
			}
		}
	}

	private void iniciarMovimientoAleatorio() {
		direccion = MathUtils.random(0, 1);
		if (!muerto) {
			switch (direccion) {
			case 0:
				setAnimation(rataLeftIdle);
				break;
			case 1:
				setAnimation(rataRightIdle);
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

		if (muerto && !muerteIniciada) {
			muerteIniciada = true;
			disableCollision();
			setTouchable(Touchable.disabled);
			if (isFacingRight()) {
				setAnimation(rataRightDeath);
			} else {
				setAnimation(rataLeftDeath);
			}

			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					remove();
					Parametros.puntuacion = Parametros.puntuacion + 10;
					AudioManager.playSound("audio/sounds/rataMuerte.mp3");
				}
			}, rataRightDeath.getAnimationDuration());
		}

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
						if (dirX > 0) {
							setAnimation(rataRightWalk);
						} else {
							setAnimation(rataLeftWalk);
						}
					} else {
						if (!muerto) {
							if (direccion == 0) {
								setAnimation(rataLeftIdle);
							} else {
								setAnimation(rataRightIdle);
							}
						}

					}
				} else {
					setAnimation(isFacingRight() ? rataRightIdle : rataLeftIdle);
				}
			} else {
				if (isFacingRight()) {
					setAnimation(rataRightAttack);
					if (nivel.player.getVelocity().x == 0) {
						direccion = 1;
					} else if (nivel.player.getVelocity().y == 0) {
						direccion = 2;
					}
				} else {
					setAnimation(rataLeftAttack);
					if (nivel.player.getVelocity().x < 0) {
						direccion = -1;
					} else if (nivel.player.getVelocity().y < 0) {
						direccion = -2;
					}
				}
				tiempoTranscurridoInmovilizado = 1f;
			}
		} else {
			if (muerto) {

				if (isFacingRight()) {
					setAnimation(rataRightDeath);
				} else {
					setAnimation(rataLeftDeath);
				}
				muerto = true;
			} else {
				this.setAnimation(rataLeftDeath);
				muerto = true;
			}
			velocity.x = 0;
			velocity.y = 0;
		}

		tiempoTranscurridoDesdeUltimoSonido += delta;
	}

	public boolean isFacingRight() {
		return (direccion == 1);
	}
}
