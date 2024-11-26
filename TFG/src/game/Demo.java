package game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import managers.ResourceManager;
import screens.BScreen;
import screens.LoadScreen;
import ui.BarraVida;

public class Demo extends Game

{
	
	
	
	SpriteBatch batch;
	public BarraVida barraVida;
	public ResourceManager resourceManager;
	
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		InputMultiplexer im=new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		Texture[] barrasVaciasTextures = new Texture[] { new Texture("player/barra/barradevida15.png"),
				new Texture("player/barra/barradevida14.png"), new Texture("player/barra/barradevida13.png"),
				new Texture("player/barra/barradevida12.png"), new Texture("player/barra/barradevida11.png"),
				new Texture("player/barra/barradevida10.png"), new Texture("player/barra/barradevida9.png"),
				new Texture("player/barra/barradevida8.png"), new Texture("player/barra/barradevida7.png"),
				new Texture("player/barra/barradevida6.png"), new Texture("player/barra/barradevida5.png"),
				new Texture("player/barra/barradevida4.png"), new Texture("player/barra/barradevida3.png"),
				new Texture("player/barra/barradevida2.png"), new Texture("player/barra/barradevida1.png") };

		float barWidth = 660;
		float barHeight = 180;

		barraVida = new BarraVida(barrasVaciasTextures, barWidth, barHeight);
		barraVida.setPosition(10, Gdx.graphics.getHeight() + 470 - barraVida.getHeight() - 10);
		
		
		setScreen(new LoadScreen(this));
		
		
		
		
		
		
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		this.getScreen().dispose();
	}
}