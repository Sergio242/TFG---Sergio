package screens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;

import elements.CajaElectricidad;
import elements.Conductor;
import elements.Electricista;
import elements.Enemy;
import elements.Friend;
import elements.Npc;
import elements.Parallax;
import elements.Player;
import elements.PlumaMagica;
import elements.Rata;
import elements.Sensei;
import elements.SetaMagica;
import elements.Shuriken;
import elements.ShurikenMagico;
import elements.LLuvia;
import elements.Manzana;
import elements.Solid;
import elements.Train;
import elements.WalkNpc;
import elements.WalkNpc2;
import elements.WalkNpc3;
import elements.WalkNpc4;
import elements.WalkNpc5;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;
import ui.BarraVida;

public class GameScreen extends BScreen {

	Stage mainStage;

	public Array<Solid> suelo;
	public Array<Solid> entrarAlTren;
	public Array<Solid> activarPosteCol;
	public Array<Solid> escaleras;
	public Array<Train> tren;
	public Array<Parallax> parallax;
	public Array<Enemy> enemigos;
	public Array<Friend> amigos;
	public Array<Npc> npcs;
	public Array<Solid> checkPoints;
	public Array<Solid> muertes;
	public BarraVida barraVida;
	private long lastDamageTime = 0;
	float inicioX;
	float inicioY;
	private float tiempoMostrandoImagen = 0;
	private float tiempoMisionCompletada = 0;
	private boolean mostrarMisionCompletada = false;
	boolean mostrarImagen = false;
	boolean mostrarRatas = false;
	boolean sonidoReproducidoPoste = false;
	boolean sonidoReproducidoPosteDos = false;

	Texture misionRata;
	Texture misionBasura;
	Texture misionElectricista;
	Texture misionRataCompletada;
	Texture misionBasuraCompletada;
	Texture misionElectricistaCompletada;
	boolean misionBasuraCompletadaFlag = false;
	boolean misionElectricistaCompletadaFlag = false;
	boolean misionRataCompletadaFlag = false;
	Solid estacion;
	Solid entrarEstacion;
	Solid salirTren;
	Solid entrarCiudad;
	Solid electricidadPoste;
	Solid electricidadPosteDos;
	Solid end;

	Solid entrarTren;

	public OrthographicCamera camara;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private Pool<LLuvia> rainDropPool;
	private Array<LLuvia> activeRainDrops;
	private Random random;

	private int contadorBasura = 0;

	MapLayer superposicionLayer;
	MapLayer superposicionLayerPorche;
	MapLayer superposicionLayerSemaforo;
	MapLayer superposicionLayerCajaElectricidad;
	MapLayer superposicionLayerPosteUno;
	MapLayer superposicionLayerPosteDos;
	MapLayer superposicionLayerCables;
	MapLayer superposicionLayerValla3;
	MapLayer superposicionLayerTree;
	MapLayer superposicionLayerTree2;
	MapLayer superposicionLayerCasa;
	MapLayer superposicionLayerPorch;

	public Player player;
	public Conductor conductorT;
	public Sensei senseiT;
	public WalkNpc walkNpc1;
	public boolean activarPoste;
	public boolean activarPosteDos;
	private Label etiquetaPuntuacion;
	public boolean pasarAlsiguienteMapaUno = false;
	public boolean pasarAlsiguienteMapaDos = false;

	public GameScreen(Demo game) {
		super(game);
		Parametros.nivel = 3;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		mainStage = new Stage();

		switch (Parametros.nivel) {
		case 0:
			map = ResourceManager.getMap("maps/train-map-02.tmx");
			break;
		case 1:
			map = ResourceManager.getMap("maps/train-map-03.tmx");
			break;
		case 2:
			map = ResourceManager.getMap("maps/train-map-04.tmx");
			break;
		case 3:
			map = ResourceManager.getMap("maps/train-map-05.tmx");
			break;
		default:
			map = null;
			break;
		}

		AudioManager.stopMusic();

		switch (Parametros.nivel) {
		case 0:
			AudioManager.playMusic("audio/music/lluvia.mp3");
			break;
		case 1:
			AudioManager.playMusic("audio/music/tranviaDentro.mp3");
			break;
		case 2:
			AudioManager.playMusic("audio/music/soleado.mp3");
			break;
		case 3:
			AudioManager.playMusic("audio/music/naturaleza.mp3");
			break;
		default:
			break;
		}
		misionRata = new Texture("player/misionUiRat.png");
		misionRataCompletada = new Texture("player/misionUiRatComplete.png");
		misionBasura = new Texture("player/misionUiTrash.png");
		misionBasuraCompletada = new Texture("player/misionUiTrashComplete.png");
		misionElectricista = new Texture("player/misionUiElec.png");
		misionElectricistaCompletada = new Texture("player/misionUiElecComplete.png");

		renderer = new OrthogonalTiledMapRenderer(map, mainStage.getBatch());

		camara = (OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Parametros.getAnchoPantalla() * Parametros.zoom,
				Parametros.getAltoPantalla() * Parametros.zoom);
		renderer.setView(camara);

		camara.zoom = 2f;

		ArrayList<MapObject> elementos;
		elementos = getRectangleList("Solid");

		MapProperties props;
		Solid solido;
		suelo = new Array<Solid>();

		for (MapObject solid : elementos) {
			props = solid.getProperties();
			solido = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
			suelo.add(solido);
		}

		this.barraVida = game.barraVida;
		player = new Player(inicioX, inicioY, mainStage, this, barraVida);

		uiStage = new Stage();
		uiStage.addActor(barraVida);

		etiquetaPuntuacion = new Label("Puntuacion: " + Parametros.puntuacion, uiStyle);
		etiquetaPuntuacion.setPosition(30, 30);
		uiStage.addActor(etiquetaPuntuacion);

		elementos = getRectangleList("Start");
		props = elementos.get(0).getProperties();
		inicioX = (float) props.get("x");
		inicioY = (float) props.get("y");

		entrarAlTren = new Array<Solid>();

		elementos = getRectangleList("EntrarTren");

		for (MapObject solid : elementos) {
			if (!elementos.isEmpty()) {
				props = solid.getProperties();
				entrarTren = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"));
				entrarAlTren.add(entrarTren);
			}
		}

		elementos = getRectangleList("ElectricidadPoste");

		if (!elementos.isEmpty()) {
			props = elementos.get(0).getProperties();
			electricidadPoste = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
					(float) props.get("width"), (float) props.get("height"));
		}

		elementos = getRectangleList("ElectricidadPosteDos");

		if (!elementos.isEmpty()) {
			props = elementos.get(0).getProperties();
			electricidadPosteDos = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
					(float) props.get("width"), (float) props.get("height"));
		}

		elementos = getRectangleList("SalirTren");
		if (!elementos.isEmpty()) {
			props = elementos.get(0).getProperties();
			salirTren = new Solid((float) props.get("x"), (float) props.get("y"), mainStage, (float) props.get("width"),
					(float) props.get("height"));
		}

		elementos = getRectangleList("EntrarCiudad");
		if (!elementos.isEmpty()) {
			props = elementos.get(0).getProperties();
			entrarCiudad = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
					(float) props.get("width"), (float) props.get("height"));
		}

		enemigos = new Array<Enemy>();
		for (MapObject ene : getEnemyList()) {
			props = ene.getProperties();
			switch (props.get("enemigo").toString()) {

			case "walknpc":
				WalkNpc walkNpc1 = new WalkNpc((float) props.get("x"), (float) props.get("y"), mainStage, this);

				enemigos.add(walkNpc1);
				System.out.println(walkNpc1.getX() + " " + walkNpc1.getY());

				break;

			case "walknpc1":
				WalkNpc2 walkNpc2 = new WalkNpc2((float) props.get("x"), (float) props.get("y"), mainStage, this);

				enemigos.add(walkNpc2);
				System.out.println(walkNpc2.getX() + " " + walkNpc2.getY());

				break;

			case "walknpc2":
				WalkNpc3 walkNpc3 = new WalkNpc3((float) props.get("x"), (float) props.get("y"), mainStage, this);

				enemigos.add(walkNpc3);
				System.out.println(walkNpc3.getX() + " " + walkNpc3.getY());

				break;

			case "walknpc3":
				WalkNpc4 walkNpc4 = new WalkNpc4((float) props.get("x"), (float) props.get("y"), mainStage, this);

				enemigos.add(walkNpc4);
				System.out.println(walkNpc4.getX() + " " + walkNpc4.getY());

				break;

			case "walknpc4":
				WalkNpc5 walkNpc5 = new WalkNpc5((float) props.get("x"), (float) props.get("y"), mainStage, this);

				enemigos.add(walkNpc5);
				System.out.println(walkNpc5.getX() + " " + walkNpc5.getY());

				break;

			case "rata":
				Rata rata = new Rata((float) props.get("x"), (float) props.get("y"), mainStage, this);

				enemigos.add(rata);
				break;

			}

		}

		amigos = new Array<Friend>();
		for (MapObject ene : getFriendList()) {
			props = ene.getProperties();
			switch (props.get("Amigo").toString()) {
			case "setaG":
				SetaMagica setilla = new SetaMagica((float) props.get("x"), (float) props.get("y"), mainStage, this);
				amigos.add(setilla);
				break;

			case "plumaM":
				PlumaMagica pluma = new PlumaMagica((float) props.get("x"), (float) props.get("y"), mainStage, this);
				amigos.add(pluma);
				break;

			case "shurikenA":
				Shuriken shuriken = new Shuriken((float) props.get("x"), (float) props.get("y"), mainStage, this);
				amigos.add(shuriken);
				break;

			case "manzanaM":
				Manzana manzana = new Manzana((float) props.get("x"), (float) props.get("y"), mainStage, this);
				amigos.add(manzana);
				break;

			case "electricista":
				Electricista electricista = new Electricista((float) props.get("x"), (float) props.get("y"), mainStage,
						this);

				amigos.add(electricista);
				System.out.println(electricista.getX() + " " + electricista.getY());

				break;

			case "cajaon":
				CajaElectricidad cajaOn = new CajaElectricidad((float) props.get("x"), (float) props.get("y"),
						mainStage, this);

				amigos.add(cajaOn);
				System.out.println(cajaOn.getX() + " " + cajaOn.getY());

				break;
			default:
				break;

			}

		}

		parallax = new Array<Parallax>();
		Parallax parallaxBakcground;
		if (Parametros.nivel == 1) {
			for (int i = 0; i < 1; i++) {
				parallaxBakcground = new Parallax(-2000 + inicioX * i, inicioY, mainStage);
				parallax.add(parallaxBakcground);

			}
		}

		tren = new Array<Train>();
		Train train;
		player = new Player(inicioX, inicioY, mainStage, this, barraVida);
		if (Parametros.nivel == 0) {
			for (int i = 0; i < 1; i++) {
				train = new Train(8000 + inicioX * i, inicioY + 101, mainStage);
				tren.add(train);

			}
		}

		npcs = new Array<Npc>();
		for (MapObject ene : getNpcList()) {
			props = ene.getProperties();
			switch (props.get("npc").toString()) {
			case "conductor":
				conductorT = new Conductor((float) props.get("x"), (float) props.get("y"), mainStage, this);

				npcs.add(conductorT);
				System.out.println(conductorT.getX() + " " + conductorT.getY());

				break;
			case "sensei":
				senseiT = new Sensei((float) props.get("x"), (float) props.get("y"), mainStage, this);

				npcs.add(senseiT);
				System.out.println(senseiT.getX() + " " + senseiT.getY());

				break;

			}
			initializeRain();

		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camara.update();

		Batch batch = mainStage.getBatch();

		renderParallaxLayers(batch);

		renderer.setView(camara);
		renderer.render();

		mainStage.act();
		uiStage.act();
		colide();

		Parametros.jugadorx = player.getX();
		Parametros.jugadory = player.getY();

		System.out.println("y" + player.getY());
		System.out.println("x" + player.getX());

		if (player.getX() <= 244) {
			noCentrarCamaraIzq();
		} else if (player.getX() >= 1873) {
			noCentrarCamaraDrch();
		} else {
			centrarCamara();
		}

		if (Parametros.nivel == 1) {
			actualizarCamaraTren();
			if (player.getX() <= 471) {
				actualizarCamaraTrenIzq();
			} else if (player.getX() >= 991) {
				actualizarCamaraTrenDrc();
			}

		} else if (Parametros.nivel == 2) {
			if (player.getX() <= 244) {
				noCentrarCamaraIzq();
			} else if (player.getX() >= 1536) {
				noCentrarCamaraDrchTren2();
			} else {
				centrarCamara();
			}
		} else if (Parametros.nivel == 3) {
			if (player.getX() <= 250 && player.getY() <= 211) {
				noCentrarCamaraEsquinaIzqBot();
			} else if (player.getX() <= 250 && player.getY() >= 1247) {
				noCentrarCamaraEsquinaIzqUp();
			} else if (player.getX() >= 1173 && player.getY() <= 211) {
				noCentrarCamaraEsquinaDrchBot();
			} else if (player.getX() >= 1173 && player.getY() >= 1247) {
				noCentrarCamaraEsquinaDrchUp();
			} else if (player.getX() <= 250) {
				noCentrarCamaraIzqCity();
			} else if (player.getX() >= 1173) {
				noCentrarCamaraDrchCity();
			} else if (player.getY() >= 1247) {
				noCentrarCamaraUpCity();
			} else if (player.getY() <= 211) {
				noCentrarCamaraBotCity();
			}
		}

		renderer.setView(camara);
		actualizarInterfaz();

		renderer.render();

		mainStage.getBatch().begin();

		if (superposicionLayerValla3 != null && superposicionLayerValla3 instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerValla3);
		}
		superposicionLayerValla3 = map.getLayers().get("valla-03-S1");

		if (superposicionLayerPorche != null && superposicionLayerPorche instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerPorche);
		}
		superposicionLayerPorche = map.getLayers().get("antena-01-S0");

		if (superposicionLayerSemaforo != null && superposicionLayerSemaforo instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerSemaforo);
		}

		if (superposicionLayerCajaElectricidad != null
				&& superposicionLayerCajaElectricidad instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerCajaElectricidad);
		}

		if (superposicionLayerPosteUno != null && superposicionLayerPosteUno instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerPosteUno);
		}

		if (superposicionLayerPosteDos != null && superposicionLayerPosteDos instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerPosteDos);
		}

		if (activarPoste) {
			superposicionLayerPosteUno = map.getLayers().get("paloEOn");
			activarPoste = false;
		}

		else if (activarPosteDos) {
			superposicionLayerPosteDos = map.getLayers().get("paloEOn1");
		}

		for (Train train : tren) {
			train.draw(mainStage.getBatch(), 1);
			if (train.distanciaRestante <= 900) {
				superposicionLayerSemaforo = map.getLayers().get("semaforo-01-S0");
			}
		}

		for (Friend amigos : amigos) {
			if (amigos instanceof SetaMagica) {
				((SetaMagica) amigos).update();
			}
			amigos.draw(mainStage.getBatch(), 1);

			if (amigos instanceof PlumaMagica) {
				((PlumaMagica) amigos).update();
			}
			amigos.draw(mainStage.getBatch(), 1);

			if (amigos instanceof Electricista) {
				amigos.draw(mainStage.getBatch(), 1);
			}

			if (amigos instanceof CajaElectricidad) {
				amigos.draw(mainStage.getBatch(), 1);
			}

		}

		for (ShurikenMagico shuriken : player.cargador) {
			if (shuriken.getEnabled()) {
				shuriken.draw(mainStage.getBatch(), 1);
			}
		}

		for (Npc conductor : npcs) {
			if (conductor instanceof Conductor) {
				((Conductor) conductor).update(delta);
			}
			conductor.draw(mainStage.getBatch(), 1);

			if (conductor instanceof Sensei) {
				((Sensei) conductor).update(delta);
			}
			conductor.draw(mainStage.getBatch(), 1);

		}

		for (Enemy enemigos : enemigos) {
			if (enemigos instanceof WalkNpc) {
				enemigos.draw(mainStage.getBatch(), 1);
			}

			if (enemigos instanceof WalkNpc2) {
				enemigos.draw(mainStage.getBatch(), 1);
			}

			if (enemigos instanceof WalkNpc3) {
				enemigos.draw(mainStage.getBatch(), 1);
			}

			if (enemigos instanceof WalkNpc4) {
				enemigos.draw(mainStage.getBatch(), 1);
			}

			if (enemigos instanceof WalkNpc5) {
				enemigos.draw(mainStage.getBatch(), 1);
			}

			if (enemigos instanceof Rata) {
				enemigos.draw(mainStage.getBatch(), 1);
			}

		}

		player.draw(mainStage.getBatch(), 1);

		if (superposicionLayerCasa != null && superposicionLayerCasa instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerCasa);
		}
		superposicionLayerCasa = map.getLayers().get("casas");

		if (superposicionLayerPorch != null && superposicionLayerPorch instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerPorch);
		}
		superposicionLayerPorch = map.getLayers().get("porch");

		if (superposicionLayer != null && superposicionLayer instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayer);
		}
		superposicionLayer = map.getLayers().get("valla-01-S0");

		if (superposicionLayerCables != null && superposicionLayerCables instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerCables);
		}
		superposicionLayerCables = map.getLayers().get("cables-01-T1");

		if (superposicionLayerTree != null && superposicionLayerTree instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerTree);
		}
		superposicionLayerTree = map.getLayers().get("trees");

		if (superposicionLayerTree2 != null && superposicionLayerTree2 instanceof TiledMapTileLayer) {
			renderer.renderTileLayer((TiledMapTileLayer) superposicionLayerTree2);
		}
		superposicionLayerTree2 = map.getLayers().get("trees_02");

		for (Npc interactivos : npcs) {

			if (interactivos instanceof Sensei) {
				interactivos.drawDialog(mainStage.getBatch(), 0);
			}

			if (interactivos instanceof Conductor) {
				interactivos.drawDialog(mainStage.getBatch(), 0);
			}
		}

		renderRain(mainStage.getBatch(), delta);

		mainStage.getBatch().end();

		uiStage.getBatch().begin();

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float imageWidth = misionRata.getWidth();
		float imageHeight = misionRata.getHeight();
		boolean misionRataCompletadaFlag = false;

		if (Parametros.nivel == 1) {
			if (entrarTren == null) {
				if (Parametros.nivel == 1) {
					mostrarImagen = true;
				}
			} else {
				tiempoMostrandoImagen = 0;
			}

			if (mostrarImagen) {
				tiempoMostrandoImagen += Gdx.graphics.getDeltaTime();
				if (tiempoMostrandoImagen < 10) {
					uiStage.getBatch().draw(misionBasura, screenWidth - (imageWidth * 2),
							screenHeight - (imageHeight * 2) - 20, imageWidth * 2, imageHeight * 2);
				} else {
					mostrarImagen = false;
				}
			}

			if (contadorBasura == 10) {
				uiStage.getBatch().draw(misionBasuraCompletada, screenWidth - (imageWidth * 2),
						screenHeight - (imageHeight * 2) - 20, imageWidth * 2, imageHeight * 2);
				pasarAlsiguienteMapaUno = true;
			}
		}

		if (Parametros.nivel == 2) {
			if (salirTren == null) {
				if (Parametros.nivel == 2) {
					mostrarImagen = true;
				}
			} else {
				tiempoMostrandoImagen = 0;
			}

			if (mostrarImagen) {
				tiempoMostrandoImagen += Gdx.graphics.getDeltaTime();
				if (tiempoMostrandoImagen < 10) {
					uiStage.getBatch().draw(misionElectricista, screenWidth - (imageWidth * 2),
							screenHeight - (imageHeight * 2) - 20, imageWidth * 2, imageHeight * 2);
				} else {
					mostrarImagen = false;
				}
			}

			if (superposicionLayerPosteUno != null && superposicionLayerPosteDos != null) {

				uiStage.getBatch().draw(misionElectricistaCompletada, screenWidth - (imageWidth * 2),
						screenHeight - (imageHeight * 2) - 20, imageWidth * 2, imageHeight * 2);
				superposicionLayerCajaElectricidad = map.getLayers().get("electricidadOn");
				pasarAlsiguienteMapaDos = true;

			}
		}

		if (Parametros.nivel == 3) {
			if (salirTren == null) {
				if (Parametros.nivel == 3) {
					mostrarImagen = true;
				}
			} else {
				tiempoMostrandoImagen = 0;
			}

			if (mostrarImagen) {
				tiempoMostrandoImagen += Gdx.graphics.getDeltaTime();
				if (tiempoMostrandoImagen < 10) {
					uiStage.getBatch().draw(misionRata, screenWidth - (imageWidth * 2),
							screenHeight - (imageHeight * 2) - 20, imageWidth * 2, imageHeight * 2);
				} else {
					mostrarImagen = false;
				}
			}

			if (Parametros.contadorRatasMuertas == 26) {
				uiStage.getBatch().draw(misionRataCompletada, screenWidth - (imageWidth * 2),
						screenHeight - (imageHeight * 2) - 20, imageWidth * 2, imageHeight * 2);
			}

		}

		uiStage.getBatch().end();

		uiStage.draw();
		barraVida.vidaActual = barraVida.vidaMaxima;

	}

	private void renderParallaxLayers(Batch batch) {
		batch.begin();
		for (Parallax parallaxBack : parallax) {
			parallaxBack.draw(batch, 1);
		}
		batch.end();
	}

	private void initializeRain() {
		rainDropPool = new Pool<LLuvia>() {
			@Override
			protected LLuvia newObject() {
				return new LLuvia();
			}
		};
		activeRainDrops = new Array<LLuvia>();
		random = new Random();
	}

	private void renderRain(Batch batch, float delta) {

		if (Parametros.nivel == 0) {
			for (Iterator<LLuvia> it = activeRainDrops.iterator(); it.hasNext();) {
				LLuvia drop = it.next();
				drop.update(delta);
				if (!drop.isAlive()) {
					it.remove();
					rainDropPool.free(drop);
				}
			}

			for (int i = 0; i < 10; i++) {
				LLuvia drop = rainDropPool.obtain();
				float x = random.nextInt(Gdx.graphics.getWidth());
				float y = Gdx.graphics.getHeight();
				float velocityY = -200 - random.nextInt(100);
				float lifeTime = 2 + random.nextFloat() * 2;
				drop.init(x, y, velocityY, lifeTime);
				activeRainDrops.add(drop);
			}

			for (LLuvia drop : activeRainDrops) {
				drop.draw(batch);
			}
		}

	}

	public void cambioNivel(String atributo) {
		float vidaActual = barraVida.vidaActual;

		limpiarObjetosNivelActual();

		switch (atributo.toLowerCase()) {
		case "entrartren":

			Parametros.nivel = 1;
			cargarObjetosNivelActual();

			break;
		case "salirtren":
			Parametros.nivel = 2;
			cargarObjetosNivelActual();

			break;

		case "entrarciudad":
			Parametros.nivel = 3;
			cargarObjetosNivelActual();

			break;
		default:
			break;
		}

		game.setScreen(new GameScreen(game));
		barraVida.vidaActual = vidaActual;

	}

	public void actualizarCamaraTren() {

		this.camara.position.y = 127;
		this.camara.position.x = player.getX();
		camara.update();
	}

	public void actualizarCamaraTrenIzq() {
		this.camara.position.y = 127;
		this.camara.position.x = 471;
		camara.update();
	}

	public void actualizarCamaraTrenDrc() {
		this.camara.position.y = 127;
		this.camara.position.x = 991;
		camara.update();
	}

	private void limpiarObjetosNivelActual() {
		// suelo.clear();
		npcs.clear();
		enemigos.clear();
		estacion = null;
		salirTren = null;
		entrarTren = null;

	}

	private void cargarObjetosNivelActual() {
		ArrayList<MapObject> elementos;
		MapProperties props;

		switch (Parametros.nivel) {
		case 0: // Estacion

			elementos = getRectangleList("Solid");
			for (MapObject solid : elementos) {
				props = solid.getProperties();
				suelo.add(new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height")));
			}

			elementos = getRectangleList("Start");
			props = elementos.get(0).getProperties();
			player.setPosition((float) props.get("x"), (float) props.get("y"));

			for (MapObject solid : elementos) {
				if (!elementos.isEmpty()) {
					props = solid.getProperties();
					entrarTren = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
							(float) props.get("width"), (float) props.get("height"));
					entrarAlTren.add(entrarTren);
				}
			}

			break;
		case 1: // Tren

			elementos = getRectangleList("Solid");
			for (MapObject solid : elementos) {
				props = solid.getProperties();
				suelo.add(new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height")));
			}

			elementos = getRectangleList("Start");
			props = elementos.get(0).getProperties();
			player.setPosition((float) props.get("x"), (float) props.get("y"));

			elementos = getRectangleList("SalirTren");
			if (!elementos.isEmpty()) {
				props = elementos.get(0).getProperties();
				salirTren = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"));
			}

			break;

		case 2: // Tren 2

			elementos = getRectangleList("Solid");
			for (MapObject solid : elementos) {
				props = solid.getProperties();
				suelo.add(new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height")));
			}

			elementos = getRectangleList("Start");
			props = elementos.get(0).getProperties();
			player.setPosition((float) props.get("x"), (float) props.get("y"));

			elementos = getRectangleList("EntrarCiudad");
			if (!elementos.isEmpty()) {
				props = elementos.get(0).getProperties();
				entrarCiudad = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"));
			}

			elementos = getRectangleList("ElectricidadPoste");

			if (!elementos.isEmpty()) {
				props = elementos.get(0).getProperties();
				electricidadPoste = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"));
			}

			elementos = getRectangleList("ElectricidadPosteDos");

			if (!elementos.isEmpty()) {
				props = elementos.get(0).getProperties();
				electricidadPosteDos = new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height"));
			}

			break;

		case 3: // Tren 2

			elementos = getRectangleList("Solid");
			for (MapObject solid : elementos) {
				props = solid.getProperties();
				suelo.add(new Solid((float) props.get("x"), (float) props.get("y"), mainStage,
						(float) props.get("width"), (float) props.get("height")));
			}

			elementos = getRectangleList("Start");
			props = elementos.get(0).getProperties();
			player.setPosition((float) props.get("x"), (float) props.get("y"));

			break;

		default:
			break;
		}
	}

	public void colide() {

		for (Enemy enemigos : enemigos) {
			for (Friend amigos : amigos) {
				if (enemigos.overlaps(amigos)) {
					enemigos.preventOverlap(amigos);
				}
			}
		}

		for (Solid solid : suelo) {
			if (player.overlaps(solid)) {
				player.preventOverlap(solid);
			}
			if (player.pies.overlaps(solid)) {
				player.tocoSuelo = true;
			}
		}

		boolean chocar = false;

		for (Friend amigo : amigos) {

			if (chocar) {
				break;
			}

			if (player.overlaps(amigo)) {
				if (amigo instanceof SetaMagica) {
					AudioManager.playSound("audio/sounds/comer.mp3");
					SetaMagica setilla = (SetaMagica) amigo;
					player.preventOverlap(setilla);
					chocar = true;
					for (int i = 0; i < 1; i++) {

						if (barraVida.vidaMaxima >= 150) {
							barraVida.ganarVida(0);
							barraVida.vidaMaxima = barraVida.vidaActual;
						} else {
							barraVida.ganarVida(10);
							barraVida.vidaMaxima = barraVida.vidaActual + 10;
							System.out.println("vida ganada" + barraVida.vidaMaxima);
						}

					}
					setilla.setPolygon(3, 0, 0, 0, 0);
					amigo.remove();
					amigo.removeAnimation();
					break;
				}
				if (amigo instanceof Manzana) {
					Manzana manzana = (Manzana) amigo;
					player.preventOverlap(manzana);
					if (player.overlaps(manzana) && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
						AudioManager.playSound("audio/sounds/coger.mp3");
						manzana.setPolygon(3, 0, 0, 0, 0);
						manzana.remove();
						manzana.removeAnimation();
						contadorBasura++;
					}
				}

				if (amigo instanceof Electricista) {
					Electricista electricista = (Electricista) amigo;
					player.preventOverlap(electricista);
					for (Solid solid : suelo) {
						if (electricista.overlaps(solid)) {
							electricista.preventOverlap(solid);
						}
					}
					electricista.setAnimation(electricista.izqdaIdle);

				}

			}

		}

		for (Train train : tren) {
			if (train.tiempoTranscurrido >= 23) {
				for (Solid solid : entrarAlTren) {
					if (entrarTren != null && player.overlaps(solid)) {
						cambioNivel("entrartren");
						return;
					}
				}
			}
		}

		if (Parametros.contadorRatasMuertas == 26 && !misionRataCompletadaFlag) {
			AudioManager.playSound("audio/sounds/puntuacion.mp3");
			Parametros.puntuacion += 200;
			misionRataCompletadaFlag = true;
		}

		if (contadorBasura == 10 && !misionBasuraCompletadaFlag) {
			AudioManager.playSound("audio/sounds/puntuacion.mp3");
			Parametros.puntuacion += 50;
			misionBasuraCompletadaFlag = true;
		}

		if (superposicionLayerPosteUno != null && superposicionLayerPosteDos != null
				&& !misionElectricistaCompletadaFlag) {
			AudioManager.playSound("audio/sounds/puntuacion.mp3");
			Parametros.puntuacion += 50;
			misionElectricistaCompletadaFlag = true;
		}

		if (Parametros.contadorRatasMuertas == 26) {
			game.setScreen(new WinScreen(game));
			barraVida.reset();
			barraVida.vidaMaxima = 150;
			Parametros.vidaActual = 170;
			barraVida.textureIndex = barraVida.barrasVacias.length - 1;
			Parametros.nivel = 0;
			Parametros.puntuacion = 0;
			Parametros.contadorRatasMuertas = 0;
			AudioManager.stopMusic();

		}

		if (entrarTren != null && player.overlaps(entrarTren)) {
			cambioNivel("entrartren");

			return;
		}

		if (salirTren != null && player.overlaps(salirTren) && pasarAlsiguienteMapaUno) {
			cambioNivel("salirtren");
			return;
		}

		if (entrarCiudad != null && player.overlaps(entrarCiudad) && pasarAlsiguienteMapaDos) {
			cambioNivel("entrarciudad");
			return;
		}

		for (Friend amigos : amigos) {
			if (amigos instanceof Electricista) {
				Electricista elec = (Electricista) amigos;

				if (electricidadPoste != null && elec.overlaps(electricidadPoste) && !sonidoReproducidoPoste) {
					AudioManager.playSound("audio/sounds/electricidad.mp3");
					activarPoste = true;
					sonidoReproducidoPoste = true;
				} else if (electricidadPosteDos != null && elec.overlaps(electricidadPosteDos)
						&& !sonidoReproducidoPosteDos) {
					AudioManager.playSound("audio/sounds/electricidad.mp3");
					activarPosteDos = true;
					sonidoReproducidoPosteDos = true; 
				}
			}

			float velocidadOriginal = player.velocidad;

			if (amigos instanceof PlumaMagica) {
				if (player.overlaps(amigos) && !Parametros.habilidadCorrerActiva) {
					AudioManager.playSound("audio/sounds/velocidad.mp3");
					player.preventOverlap(amigos);
					Parametros.habilidadCorrerActiva = true;
					player.velocidad = 200;

					amigos.setPolygon(3, 0, 0, 0, 0);
					amigos.removeAnimation();

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							player.velocidad = velocidadOriginal;
							Parametros.habilidadCorrerActiva = false;
						}
					}, 5);
				}
			}

			if (amigos instanceof Shuriken) {
				if (player.overlaps(amigos) && !Parametros.habilidadShurikenActiva) {
					player.preventOverlap(amigos);
					Parametros.habilidadShurikenActiva = true;
					Parametros.activarHabilidad = true;
					amigos.setPolygon(3, 0, 0, 0, 0);
					amigos.removeAnimation();

					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							activarHabilidad();
							Parametros.habilidadShurikenActiva = false;
						}
					}, 10);
				}
			}

		}

		Iterator<Enemy> enemyIterator = enemigos.iterator();

		while (enemyIterator.hasNext()) {
			Enemy walknpc1 = enemyIterator.next();

			if (player.overlaps(walknpc1)) {
				player.preventOverlap(walknpc1);
			}

			for (Solid solid : suelo) {
				if (walknpc1.overlaps(solid)) {
					walknpc1.preventOverlap(solid);
				}
			}

			for (Train train : tren) {
				if (train.tiempoTranscurrido >= 23) {
					walknpc1.moveBy(600, 500);
					for (Solid solid : entrarAlTren) {
						if (walknpc1.overlaps(solid) && entrarTren != null) {
							enemyIterator.remove();
							break;
						}
					}
				}
			}

		}

		for (Enemy enemigos : enemigos) {
			if (enemigos instanceof Rata) {
				if (player.overlaps(enemigos)) {
					player.preventOverlap(enemigos);
					long currentTime = System.currentTimeMillis();
					if (currentTime - lastDamageTime >= 1000) {

						if (barraVida.vidaMaxima <= 0) {
							barraVida.vidaMaxima = Parametros.vidaActual;

						} else {

							if (barraVida.vidaMaxima > 10) {
								barraVida.vidaMaxima = barraVida.vidaActual - 10;
								AudioManager.playSound("audio/sounds/perderVida.mp3");
								barraVida.perderVida(10);
								System.out.println("vida perdida" + barraVida.vidaMaxima);

							} else {
								game.setScreen(new DeathScreen(game));
								barraVida.reset();
								barraVida.vidaMaxima = 150;
								Parametros.vidaActual = 170;
								barraVida.textureIndex = barraVida.barrasVacias.length - 1;
								Parametros.puntuacion = 0;
								AudioManager.stopMusic();
								AudioManager.stopSpecificSound("");
							}

						}
						lastDamageTime = currentTime;
					}

					for (Solid solid : suelo) {
						if (enemigos.overlaps(solid)) {
							enemigos.preventOverlap(solid);
						}
					}
				}
			}

		}

		Array<Enemy> copiaEnemigos = new Array<>(enemigos);
		for (Enemy enemigo : enemigos) {
			if (enemigo instanceof Rata) {
				for (Enemy otroEnemigo : copiaEnemigos) {
					if (otroEnemigo != enemigo && otroEnemigo instanceof Rata && enemigo.overlaps(otroEnemigo)) {
						enemigo.preventOverlap(otroEnemigo);
					}
				}
			}
		}

		for (Npc conductor : npcs) {

			if (conductor instanceof Conductor) {
			    if (player.overlaps(conductor)) {
			        player.preventOverlap(conductor);
			    }

			    if (Gdx.input.isKeyJustPressed(Input.Keys.E) && player.overlaps(conductor.pies)) {
			        // Comprobamos si ya está en una animación activa
			        if (!((Conductor) conductor).isAnimating) {
			            ((Conductor) conductor).startAnimation();
			            AudioManager.playSound("audio/sounds/puntuacion.mp3");
			            Parametros.puntuacion += 100;
			        }
			    }

			    if (conductorT.pies.overlaps(player)) {
			        conductor.setAnimation(conductorT.teclaE);
			    } else {
			        conductor.setAnimation(conductorT.sinTeclaE);
			    }
			}

			if (conductor instanceof Sensei) {
			    if (player.overlaps(conductor)) {
			        player.preventOverlap(conductor);
			    }

			    if (Gdx.input.isKeyJustPressed(Input.Keys.E) && player.overlaps(conductor.pies)) {
			        Sensei sensei = (Sensei) conductor;

			        // Comprobamos si ya está en una animación activa y si no se le ha dado puntuación
			        if (!sensei.isAnimating && !sensei.hasGivenPoints) {
			            sensei.startAnimation();
			            sensei.hasGivenPoints = true; // Marcar que ya se le dio puntos
			            AudioManager.playSound("audio/sounds/puntuacion.mp3");
			            Parametros.puntuacion += 100;
			        }
			    }

			    if (senseiT.pies.overlaps(player)) {
			        conductor.setAnimation(senseiT.teclaE);
			    } else {
			        conductor.setAnimation(senseiT.sinTeclaE);
			    }
			}


		}

		player.colocarPies();
	}

	public void centrarCamara() {
		this.camara.position.x = player.getX();
		this.camara.position.y = player.getY();

		camara.update();

	}

	public void noCentrarCamaraIzq() {
		this.camara.position.x = 250;
		this.camara.position.y = player.getY();
		camara.update();
	}

	public void noCentrarCamaraDrch() {
		this.camara.position.x = 1873;
		this.camara.position.y = player.getY();
		camara.update();
	}

	public void noCentrarCamaraDrchTren2() {
		this.camara.position.x = 1536;
		this.camara.position.y = player.getY();
		camara.update();
	}

	public void noCentrarCamaraIzqCity() {
		this.camara.position.x = 250;
		this.camara.position.y = player.getY();
		camara.update();
	}

	public void noCentrarCamaraDrchCity() {
		this.camara.position.x = 1173;
		this.camara.position.y = player.getY();
		camara.update();
	}

	public void noCentrarCamaraUpCity() {
		this.camara.position.x = player.getX();
		this.camara.position.y = 1247;
		camara.update();
	}

	public void noCentrarCamaraBotCity() {
		this.camara.position.x = player.getX();
		this.camara.position.y = 211;
		camara.update();
	}

	public void noCentrarCamaraEsquinaIzqBot() {
		this.camara.position.x = 250;
		this.camara.position.y = 211;
		camara.update();
	}

	public void noCentrarCamaraEsquinaIzqUp() {
		this.camara.position.x = 250;
		this.camara.position.y = 1247;
		camara.update();
	}

	public void noCentrarCamaraEsquinaDrchBot() {
		this.camara.position.x = 1173;
		this.camara.position.y = 211;
		camara.update();
	}

	public void noCentrarCamaraEsquinaDrchUp() {
		this.camara.position.x = 1173;
		this.camara.position.y = 1247;
		camara.update();
	}

	public void cambioNivel() {
		Parametros.nivel++;
		game.setScreen(new GameScreen(game));

	}

	public ArrayList<MapObject> getRectangleList(String propertyName) {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {
					list.add(obj);
				}

			}

		}

		return list;
	}

	public ArrayList<Polygon> getPolygonList(String propertyName) {

		Polygon poly;
		ArrayList<Polygon> list = new ArrayList<Polygon>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {

				if (!(obj instanceof PolygonMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {

					poly = ((PolygonMapObject) obj).getPolygon();
					list.add(poly);
				}

			}

		}

		return list;
	}

	public ArrayList<MapObject> getEnemyList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("enemigo")) {
					list.add(obj);

				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}

				}

			}

		}

		return list;
	}

	public ArrayList<MapObject> getFriendList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("Amigo")) {
					list.add(obj);

				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}

				}

			}

		}

		return list;
	}

	public ArrayList<MapObject> getNpcList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("npc")) {
					list.add(obj);

				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}

				}

			}

		}

		return list;
	}

	public void activarHabilidad() {
		Parametros.activarHabilidad = false;
	}

	private void actualizarInterfaz() {
		etiquetaPuntuacion.setText("Puntuacion: " + Parametros.puntuacion);
	}

}
