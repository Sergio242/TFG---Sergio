package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import game.Demo;
import managers.ResourceManager;

public class ControlsScreen extends BScreen {
    private Texture controlsImage;
    private Texture backArrowTexture;
    private SpriteBatch batch;
    private Table tabla;

    public ControlsScreen(Demo game) {
        super(game);

        controlsImage = new Texture("maps/images/controles.png"); 
        backArrowTexture = new Texture("maps/images/flecha.png"); 
        batch = new SpriteBatch();
        tabla = new Table();
        tabla.setFillParent(true);

        this.uiStage.addActor(tabla);

        Image controlsGuide = new Image(controlsImage);
        tabla.add(controlsGuide).padBottom(20);
        tabla.row();

        TextButton backButton = new TextButton("← Volver", ResourceManager.textButtonStyle);
        backButton.addListener((Event e) -> {
            if (!(e instanceof InputEvent) || !((InputEvent) e).getType().equals(Type.touchDown))
                return false;
            this.dispose();
            game.setScreen(new TitleScreen(game));
            return false;
        });
        tabla.add(backButton).padTop(10);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float imageWidth = controlsImage.getWidth();
        float imageHeight = controlsImage.getHeight();

        float scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight) * 0.8f; 
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        batch.begin();
        batch.draw(controlsImage, (screenWidth - scaledWidth) / 2, (screenHeight - scaledHeight) / 2, scaledWidth, scaledHeight);
        batch.end();

        uiStage.act();
        uiStage.draw();
    }


    @Override
    public void dispose() {
        super.dispose();
        controlsImage.dispose();
        backArrowTexture.dispose();
        batch.dispose();
    }
}
