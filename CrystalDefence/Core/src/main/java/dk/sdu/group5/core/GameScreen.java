package dk.sdu.group5.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.Lookup;

import java.util.Collection;
import java.util.Objects;


class GameScreen implements Screen {
    private PauseScreen PS;
    private SpriteBatch batch;
    private BitmapFont font;
    private World world;
    private Collection<? extends IGameProcess> processes;


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        PS = new PauseScreen(this);



        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        processes = Lookup.getDefault().lookupAll(IGameProcess.class);
        world = new World();
        processes.forEach((p) -> p.start(world));
        world.getEntities().forEach(System.out::println);
        Gdx.input.setInputProcessor (new InputAdapter() {
            public boolean keyDown(int keycode) {
                if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)){
                    Game.getInstance().setScreen(PS);

                }

                    return true;
            }

            });

        }



     


    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // TODO: 03/03/16 add game rendering

        //update entities
        processes.forEach((p) -> p.update(world, delta));

        //render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        font.draw(batch, world.getEntities().toString(), 150, 220);
        world.getEntities().forEach(e -> {
            String texture = e.getTexture();
            if (texture != null && !Objects.equals(texture, "")) {
                batch.draw(new Texture(Gdx.files.classpath(texture)), e.getX(), e.getY());
                font.draw(batch,e.toString(),e.getX(),e.getY());
            }
        });
        batch.end();

        }


    /**
     *
     */

    /**
     * @param width  the width of the window
     * @param height the height of the window
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

           
    }
    
    

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
    




}
