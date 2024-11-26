package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;

public class DeathScreen extends BScreen {
    private Texture fondoTextura;
    private SpriteBatch batch;
    private Sprite fondoSprite;
    private Table tabla;
    private TextButton backButton;

    public DeathScreen(Demo game) {
        super(game);
        fondoTextura = new Texture("maps/images/gameOver.jpg");
        fondoSprite = new Sprite(fondoTextura);
        batch = new SpriteBatch();

        tabla = new Table();
        tabla.setFillParent(true);
        this.uiStage.addActor(tabla);

        backButton = new TextButton("Volver al Menú", ResourceManager.textButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game)); 
            }
        });

        tabla.add(backButton).padTop(20).expandX().center();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fondoSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.begin();
        fondoSprite.draw(batch);
        batch.end();

        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        fondoTextura.dispose();
        batch.dispose();
    }
}
