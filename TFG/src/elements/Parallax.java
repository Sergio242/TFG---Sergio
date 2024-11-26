package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Parallax extends Element {
	private Animation<TextureRegion> cerrada;
	private static final float VELOCIDAD_MAXIMA = 300;
	private Element segundaImagen;
	private Element terceraImagen;

	public float tiempoTranscurrido = 0;

	public Parallax(float x, float y, Stage s) {
		super(x, y, s);
		cerrada = loadFullAnimationTrain("maps/images/Parallax-01.png", 1, 1, 1f, true);
		this.setRectangle();
		this.velocity.x = -VELOCIDAD_MAXIMA;

		segundaImagen = new Element(x + getWidth() - 1, y, s);
		segundaImagen.loadFullAnimationTrain("maps/images/Parallax-01.png", 1, 1, 1f, true);
		segundaImagen.setRectangle();
		segundaImagen.velocity.x = -VELOCIDAD_MAXIMA;

		terceraImagen = new Element(segundaImagen.getX() + segundaImagen.getWidth() - 1, y, s);
		terceraImagen.loadFullAnimationTrain("maps/images/Parallax-01.png", 1, 1, 1f, true);
		terceraImagen.setRectangle();
		terceraImagen.velocity.x = -VELOCIDAD_MAXIMA;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		segundaImagen.act(delta);
		terceraImagen.act(delta);

		tiempoTranscurrido += delta;
		this.velocity.x = -VELOCIDAD_MAXIMA;
		this.applyPhysicsTrain(delta);

		segundaImagen.velocity.x = -VELOCIDAD_MAXIMA;
		segundaImagen.applyPhysicsTrain(delta);

		terceraImagen.velocity.x = -VELOCIDAD_MAXIMA;
		terceraImagen.applyPhysicsTrain(delta);

		if (getX() + getWidth() < 0) {
			setX(terceraImagen.getX() + terceraImagen.getWidth() - 170);
		}

		if (segundaImagen.getX() + segundaImagen.getWidth() < 0) {
			segundaImagen.setX(getX() + getWidth() - 170);
		}

		if (terceraImagen.getX() + terceraImagen.getWidth() < 0) {
			terceraImagen.setX(segundaImagen.getX() + segundaImagen.getWidth() - 170);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		segundaImagen.draw(batch, parentAlpha);
		terceraImagen.draw(batch, parentAlpha);
	}
}
