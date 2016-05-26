package dk.sdu.group5.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
/**
 * Created by Hivemaster on 08-04-2016.
 */
public class GameOverScreen implements Screen {
    private BitmapFont font;
    private Skin skin;
    private Stage stage;
    private Table table;

    private final Game game;

    public GameOverScreen(Game game){
        this.game = game;

        font = new BitmapFont();
        font.setColor(Color.RED);
        stage = new Stage();

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("assets/ui-gray.atlas"));

        skin = new Skin();
        skin.addRegions(textureAtlas);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(skin.getDrawable("button_01"), skin.getDrawable("button_01"), skin.getDrawable("button_01"), font);
        addButton("New Game", ()-> startgame(), style);
        addButton("Main Menu", ()-> mainmenu(), style);
        addButton("Exit game", ()-> Gdx.app.exit(), style);

    }

    @Override
    public void show() {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();

        Gdx.input.setInputProcessor(stage);
    }
    private void mainmenu(){
        StartScreen startScreen = new StartScreen(() -> {
            game.setScreen(new GameScreen(game));
        });
        game.setScreen(startScreen);


    }
    private void startgame(){
        GameScreen gameScreen = new GameScreen(game);
        game.setScreen(gameScreen);

    }

    private void addButton(String text, final Runnable onEnter, TextButton.TextButtonStyle style) {
        final TextButton button = new TextButton(text, style);
        button.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                onEnter.run();
            }
        });
        table.add(button);
        table.row();
    }

    @Override
    public void render(float delta) {
        //painting the screen white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
        skin.dispose();
    }

}
