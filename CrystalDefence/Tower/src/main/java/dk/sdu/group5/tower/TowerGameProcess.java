package dk.sdu.group5.tower;

import dk.sdu.group5.common.data.*;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

@ServiceProvider(service = IGameProcess.class)
public class TowerGameProcess implements IGameProcess {
    private Entity tower;

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        tower = new Entity();
        tower.setType(EntityType.TOWER);
        tower.setLives(3);
        tower.setX(350);
        tower.setY(280);
        tower.setTexture("gridPattern.png");
        tower.setCollider(new BoxCollider(false, new AABB(-16, -16, 16, 16)));
        try {
            tower.addProperty("tangible");
            tower.addProperty("static");
            tower.addProperty("damageable");
            world.addEntity(tower);
        } catch (Exception ex) {
            Logger.getLogger(TowerGameProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(World world, float delta) {
        // Render stuff
        // Collision stuff
    }

    @Override
    public void stop(World world) {
        try {
            world.removeEntity(tower);
        } catch (Exception ex) {
            Logger.getLogger(TowerGameProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void uninstall() {

    }

}
