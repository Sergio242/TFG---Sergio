package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;

public final class ResourceManager {
	private ResourceManager() {
	}

	public static AssetManager assets = new AssetManager();
	public static LabelStyle buttonStyle;
	public static TextButtonStyle textButtonStyle;
	private Array<Texture> conductorImages;

	public static void loadAllResources() {

		// mapas
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		assets.load("maps/mapa0.tmx", TiledMap.class);
		assets.load("maps/mapa1.tmx", TiledMap.class);
		assets.load("maps/mapa2.tmx", TiledMap.class);
		assets.load("maps/train-map-02.tmx", TiledMap.class);
		assets.load("maps/train-map-03.tmx", TiledMap.class);
		assets.load("maps/train-map-04.tmx", TiledMap.class);
		assets.load("maps/train-map-05.tmx", TiledMap.class);

		// elementos de mapa
		// assets.load("maps/Images/arbol.png", Texture.class);
		assets.load("maps/images/rectangulo.jpg", Texture.class);
		assets.load("maps/images/lluvia.png", Texture.class);
		// enemigos

		// jugador
		assets.load("player/backMainCharacter1.png", Texture.class);
		assets.load("player/backMainCharacterIdle1.png", Texture.class);
		assets.load("player/frontMainCharacter1.png", Texture.class);
		assets.load("player/frontMainCharacterIdle1.png", Texture.class);
		assets.load("player/leftMainCharacter1.png", Texture.class);
		assets.load("player/leftMainCharacterIdle1.png", Texture.class);
		assets.load("player/rightMainCharacter1.png", Texture.class);
		assets.load("player/rightMainCharacterIdle1.png", Texture.class);

		// npc walk 1
		assets.load("player/backMainCharacter2.png", Texture.class);
		assets.load("player/backMainCharacterIdle2.png", Texture.class);
		assets.load("player/frontMainCharacter2.png", Texture.class);
		assets.load("player/frontMainCharacterIdle2.png", Texture.class);
		assets.load("player/leftMainCharacter2.png", Texture.class);
		assets.load("player/leftMainCharacterIdle2.png", Texture.class);
		assets.load("player/rightMainCharacter2.png", Texture.class);
		assets.load("player/rightMainCharacterIdle2.png", Texture.class);

		// npc walk 2
		assets.load("player/backMainCharacter3.png", Texture.class);
		assets.load("player/backMainCharacterIdle3.png", Texture.class);
		assets.load("player/frontMainCharacter3.png", Texture.class);
		assets.load("player/frontMainCharacterIdle3.png", Texture.class);
		assets.load("player/leftMainCharacter3.png", Texture.class);
		assets.load("player/leftMainCharacterIdle3.png", Texture.class);
		assets.load("player/rightMainCharacter3.png", Texture.class);
		assets.load("player/rightMainCharacterIdle3.png", Texture.class);

		// npc walk 3
		assets.load("player/rightMainCharacter4.png", Texture.class);
		assets.load("player/rightMainCharacterIdle4.png", Texture.class);

		// npc walk 4
		assets.load("player/backMainCharacterW1.png", Texture.class);
		assets.load("player/backMainCharacterIdleW1.png", Texture.class);
		assets.load("player/frontMainCharacterW1.png", Texture.class);
		assets.load("player/frontMainCharacterIdleW1.png", Texture.class);
		assets.load("player/leftMainCharacterW1.png", Texture.class);
		assets.load("player/leftMainCharacterIdleW1.png", Texture.class);
		assets.load("player/rightMainCharacterW1.png", Texture.class);
		assets.load("player/rightMainCharacterIdleW1.png", Texture.class);

		// npc walk 5
		assets.load("player/backMainCharacterW2.png", Texture.class);
		assets.load("player/backMainCharacterIdleW2.png", Texture.class);
		assets.load("player/frontMainCharacterW2.png", Texture.class);
		assets.load("player/frontMainCharacterIdleW2.png", Texture.class);
		assets.load("player/leftMainCharacterW2.png", Texture.class);
		assets.load("player/leftMainCharacterIdleW2.png", Texture.class);
		assets.load("player/rightMainCharacterW2.png", Texture.class);
		assets.load("player/rightMainCharacterIdleW2.png", Texture.class);

		// npc mision
		// npc walk 2
		assets.load("player/backMainCharacter5.png", Texture.class);
		assets.load("player/backMainCharacterIdle5.png", Texture.class);
		assets.load("player/frontMainCharacter5.png", Texture.class);
		assets.load("player/frontMainCharacterIdle5.png", Texture.class);
		assets.load("player/leftMainCharacter5.png", Texture.class);
		assets.load("player/leftMainCharacterIdle5.png", Texture.class);
		assets.load("player/rightMainCharacter5.png", Texture.class);
		assets.load("player/rightMainCharacterIdle5.png", Texture.class);

		// seta magica
		assets.load("player/setaMagicaR.png", Texture.class);

		// pluma
		assets.load("player/pluma.png", Texture.class);

		// shuriken
		assets.load("player/shuriken.png", Texture.class);

		// manzana
		assets.load("player/manzanaM.png", Texture.class);

		// enemigos
		assets.load("enemies/rataLeftAttack.png", Texture.class);
		assets.load("enemies/rataRightAttack.png", Texture.class);
		assets.load("enemies/rataLeftDeath.png", Texture.class);
		assets.load("enemies/rataRightDeath.png", Texture.class);
		assets.load("enemies/rataLeftIdle.png", Texture.class);
		assets.load("enemies/rataRightIdle.png", Texture.class);
		assets.load("enemies/rataLeftWalk.png", Texture.class);
		assets.load("enemies/rataRightWalk.png", Texture.class);

		assets.load("maps/images/enoden.png", Texture.class);
		assets.load("maps/images/enoden-04-R-OD.png", Texture.class);
		assets.load("maps/images/enoden-05-R-OD.png", Texture.class);

		// Conductor
		assets.load("player/ConductorGame.png", Texture.class);
		assets.load("player/ConductorGameE.png", Texture.class);

		// Sensei
		assets.load("player/SenseiGame.png", Texture.class);
		assets.load("player/SenseiGameE.png", Texture.class);

		// Electricidad
		assets.load("player/electricidadOn.png", Texture.class);
		assets.load("player/electricidadOff.png", Texture.class);

		// pantallas
		assets.load("maps/images/gameOver.jpg", Texture.class);
		assets.load("maps/images/winner.jpg", Texture.class);
		assets.load("maps/images/enodenTitleScreen.jpg", Texture.class);
		assets.load("maps/images/flecha.png", Texture.class);
		assets.load("maps/images/controles.png", Texture.class);

		// Audio
		assets.load("audio/sounds/caminar.mp3", Sound.class);
		assets.load("audio/sounds/caminarTierra.mp3", Sound.class);
		assets.load("audio/sounds/puntuacion.mp3", Sound.class);
		assets.load("audio/sounds/tranviaLLegando.mp3", Sound.class);
		assets.load("audio/sounds/lanzar.mp3", Sound.class);
		assets.load("audio/sounds/rataMuerte.mp3", Sound.class);
		assets.load("audio/sounds/comer.mp3", Sound.class);
		assets.load("audio/sounds/velocidad.mp3", Sound.class);
		assets.load("audio/sounds/escribiendo.mp3", Sound.class);
		assets.load("audio/sounds/perderVida.mp3", Sound.class);
		assets.load("audio/sounds/coger.mp3", Sound.class);
		assets.load("audio/sounds/electricidad.mp3", Sound.class);
		assets.load("audio/music/lluvia.mp3", Music.class);
		assets.load("audio/music/naturaleza.mp3", Music.class);
		assets.load("audio/music/tranviaDentro.mp3", Music.class);
		assets.load("audio/music/pruebas.mp3", Music.class);
		assets.load("audio/music/soleado.mp3", Music.class);

		// UI
		assets.load("ui/rojo.jpg", Texture.class);
		assets.load("ui/morado.jpg", Texture.class);

		// a�adir m�s elementos

	}

	private void loadConductorImages() {
		conductorImages = new Array<>();
		for (int i = 1; i <= 35; i++) {
			conductorImages.add(new Texture(Gdx.files.internal("player/condui/ui-conductor" + i + ".png")));
		}
	}

	public static boolean update() {
		return assets.update();
	}

	public static void botones() {

		// estilo para botones
		FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("sans.ttf"));
		FreeTypeFontParameter ftfp = new FreeTypeFontParameter();

		ftfp.size = 36;
		ftfp.color = Color.WHITE;
		ftfp.borderColor = Color.BLACK;
		ftfp.borderWidth = 2;

		BitmapFont fuentePropia = ftfg.generateFont(ftfp);
		buttonStyle = new LabelStyle();
		buttonStyle.font = fuentePropia;
		textButtonStyle = new TextButtonStyle();
		Texture buttonText = ResourceManager.getTexture("maps/images/rectangulo.jpg");
		NinePatch buttonPatch = new NinePatch(buttonText);
		textButtonStyle.up = new NinePatchDrawable(buttonPatch);
		textButtonStyle.font = fuentePropia;

	}

	/*
	 * public static TextureAtlas getAtlas(String path){ return assets.get(path,
	 * TextureAtlas.class);
	 * 
	 * }
	 */

	public static Texture getTexture(String path) {
		return assets.get(path, Texture.class);
	}

	public static Music getMusic(String path) {
		return assets.get(path, Music.class);
	}

	public static Sound getSound(String path) {
		return assets.get(path, Sound.class);
	}

	public static TiledMap getMap(String path) {
		return assets.get(path, TiledMap.class);
	}

	public static void dispose() {
		assets.dispose();
	}
}
