package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Enoden";
        config.width = Parametros.getAnchoPantalla();
        config.height = Parametros.getAltoPantalla();


        config.addIcon("icons/logo32.png", com.badlogic.gdx.Files.FileType.Internal);
      

        Game myGame = new Demo();
        new LwjglApplication(myGame, config);
    }
}
