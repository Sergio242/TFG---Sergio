package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import game.Demo;
import managers.AudioManager;
import managers.ResourceManager;
import game.Parametros;

public class TitleScreen extends BScreen {
	private Texture backgroundTexture;
	private SpriteBatch batch;
	private Table tabla;
	private ShapeRenderer shapeRenderer;

	public TitleScreen(Demo game) {
		super(game);

		backgroundTexture = new Texture("maps/images/enodenTitleScreen.jpg");
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		tabla = new Table();
		tabla.setFillParent(true);

		this.uiStage.addActor(tabla);

		TextButton botonJugar = new TextButton("Jugar", ResourceManager.textButtonStyle);
		botonJugar.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			game.setScreen(new GameScreen(game));
			return false;
		});
		tabla.add(botonJugar).padBottom(10);
		tabla.row();

		TextButton botonControles = new TextButton("Controles", ResourceManager.textButtonStyle);
		botonControles.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			game.setScreen(new ControlsScreen(game));
			return false;
		});
		tabla.add(botonControles).padBottom(10);
		tabla.row();

		TextButton botonSalir = new TextButton("Salir", ResourceManager.textButtonStyle);
		botonSalir.addListener((Event e) -> {
			if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
				return false;
			this.dispose();
			Gdx.app.exit();
			return false;
		});
		tabla.add(botonSalir);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		uiStage.act();
		uiStage.draw();
	}
}
