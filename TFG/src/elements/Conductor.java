package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import managers.AudioManager;
import screens.GameScreen;

public class Conductor extends Npc {

	private Vector3 m3d;
	public Animation<TextureRegion> teclaE;
	public Animation<TextureRegion> sinTeclaE;
	public Player player;
	public Array<Texture> animationFrames;
	private float stateTime;
	public boolean isAnimating;
	private float frameDuration = 0.4f;
	private OrthographicCamera camara;
	private boolean soundPlayed;

	public Conductor(float x, float y, Stage s, GameScreen nivel) {
		super(x, y, s, nivel);

		sinTeclaE = loadFullAnimation("player/ConductorGame.png", 1, 6, 0.4f, true);
		teclaE = loadFullAnimationTrain("player/ConductorGameE.png", 1, 6, 0.4f, true);
		m3d = new Vector3();
		this.vida = 5;
		this.setRectangle(8, 4, 12, 24);
		pies = new Element(0, 0, s, this.getWidth() / 4, this.getHeight() / 10);
		pies.setRectangle(-30, 0, 30, 50);
		animationFrames = new Array<>();
		for (int i = 1; i <= 35; i++) {
			animationFrames.add(new Texture(Gdx.files.internal("player/condui/ui-conductor" + i + ".png")));
		}
		stateTime = 0f;
		isAnimating = false;
		soundPlayed = false;
		this.camara = nivel.camara;
	}

	public void act(float delta) {
		if (getEnabled()) {
			super.act(delta);
			this.applyPhysics(delta);
			colocarPies();

			if (isAnimating) {
				stateTime += delta;
				if (stateTime > frameDuration * animationFrames.size) {
					isAnimating = false;

				}
			}
		}
	}

	public void colocarPies() {
		this.pies.setPosition(this.getX() - 15, this.getY() - this.getHeight() / 10);
	}

	public void startAnimation() {
		if (!isAnimating) {
			isAnimating = true;
			stateTime = 0f;
			soundPlayed = false;
		}
	}

	public void update(float delta) {
		if (isAnimating) {
			stateTime += delta;
			if (stateTime > frameDuration * animationFrames.size) {
				isAnimating = false;

			}
		}
	}

	@Override
	public void drawDialog(Batch batch, float parentAlpha) {
		if (isAnimating) {
			int frameIndex = (int) (stateTime / frameDuration);

			if (frameIndex < animationFrames.size) {
				Texture frame = animationFrames.get(frameIndex);
				float frameX = camara.position.x - frame.getWidth() / 2;
				float frameY = camara.position.y - camara.viewportHeight / 2 - 80f;
				batch.draw(frame, frameX, frameY);

				if (!soundPlayed) {
					AudioManager.playSound("audio/sounds/escribiendo.mp3");
					soundPlayed = true;
				}

				if (frameIndex == animationFrames.size - 1) {
					AudioManager.stopSpecificSound("audio/sounds/escribiendo.mp3");
					soundPlayed = false;
				}
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public void dispose() {
		for (Texture texture : animationFrames) {
			texture.dispose();
		}
	}
}
