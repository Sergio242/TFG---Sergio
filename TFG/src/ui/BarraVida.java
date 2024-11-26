package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import managers.AudioManager;

public class BarraVida extends Actor {
	public Texture[] barrasVacias;
	public int textureIndex;
	public float vidaActual;
	public float vidaMaxima = 160;
	private int perdidasDeVida = 0;
	private float barWidth;
	private float barHeight;

	public BarraVida(Texture[] barrasVacias, float barWidth, float barHeight) {
		this.barrasVacias = barrasVacias;
		this.barWidth = barWidth;
		this.vidaActual = this.vidaMaxima;
		this.barHeight = barHeight;
		this.textureIndex = barrasVacias.length - 1;
		setSize(barWidth, barHeight);
	}

	public float perderVida(float cantidad) {
		vidaActual -= cantidad;
		if (vidaActual < 0) {
			vidaActual = 0;
		}

		System.out.println("vida:" + vidaActual + "cantidad:" + cantidad);

		if (cantidad >= 10) {
			perdidasDeVida++;
			setBarraVaciaTexture(perdidasDeVida);
		}
		return vidaActual;
	}

	public void reset() {
		vidaActual = vidaMaxima;
		perdidasDeVida = 0;
		textureIndex = barrasVacias.length - 1;
	}

	public float ganarVida(float cantidad) {
		vidaActual += cantidad;
		if (vidaActual > vidaMaxima) {
			vidaActual = vidaMaxima;
		}

		if (cantidad >= 10) {
			perdidasDeVida--;
			if (perdidasDeVida < 0) {
				perdidasDeVida = 0;
			}
			setBarraVaciaTexture(perdidasDeVida);
		}

		return vidaActual;
	}

	private void setBarraVaciaTexture(int perdidasDeVida) {
		int index = barrasVacias.length - perdidasDeVida;
		if (index >= 0 && index < barrasVacias.length) {
			textureIndex = index;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		float scaleX = vidaActual / vidaMaxima;
		batch.draw(barrasVacias[textureIndex], getX(), getY(), barWidth * scaleX, barHeight);
	}

}
