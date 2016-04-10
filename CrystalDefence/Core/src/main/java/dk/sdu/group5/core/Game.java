package dk.sdu.group5.core;

import com.badlogic.gdx.scenes.scene2d.Stage;


class Game extends com.badlogic.gdx.Game {

    private GameScreen gameScreen;
    private StartScreen startScreen;
    private Stage stage;
    
    @Override
    public void create() {
        gameScreen = new GameScreen();
        //something like this:
        startScreen = new StartScreen(() -> {
          //Gdx.app.exit();
            setScreen(gameScreen);
        });
        setScreen(startScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        startScreen.dispose();
        gameScreen.dispose();
    }

}
