package elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import managers.ResourceManager;

public class LLuvia implements Poolable {
	private Vector2 position;
	private Vector2 velocity;
	private float lifeTime;
	private Texture texture;

	public LLuvia() {
		position = new Vector2();
		velocity = new Vector2();
		texture = ResourceManager.getTexture("maps/images/lluvia.png");
	}

	public void init(float x, float y, float velocityY, float lifeTime) {
		this.position.set(x, y);
		this.velocity.set(0, velocityY);
		this.lifeTime = lifeTime;
	}

	public void update(float deltaTime) {
		position.mulAdd(velocity, deltaTime);
		lifeTime -= deltaTime;
	}

	public void draw(Batch batch) {
		batch.draw(texture, position.x, position.y);
	}

	public boolean isAlive() {
		return lifeTime > 0 && position.y > 0;
	}

	@Override
	public void reset() {
		position.set(0, 0);
		velocity.set(0, 0);
		lifeTime = 0;
	}
}
