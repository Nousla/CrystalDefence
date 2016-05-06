package dk.sdu.group5.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.Posf2d;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.ICollider;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.List;
import java.util.stream.Collectors;

@ServiceProvider(service = IGameProcess.class)
public class CollisionGameProcess implements IGameProcess{

    @Override
    public void start(World world) {

    }

    @Override
    public void update(World world, float delta) {
        world.clearCollisions();

        List<Entity> collidableEnts = world.getEntities().stream()
                .filter(e -> e.getCollider() != null)
                .collect(Collectors.toList());

        List<Entity> dynamicEnts = collidableEnts.stream()
                .filter(e -> !e.getProperties().contains("static"))
                .collect(Collectors.toList());

        dynamicEnts.stream().forEach(de -> collidableEnts.stream().filter(ce -> de != ce
                && collides(de, ce)).forEach(ce -> {
            applyImpulse(de, ce);
            world.addCollision(de, ce);
            if (ce.is("static")) {
                world.addCollision(ce, de);
            }
        }));
    }

    @Override
    public void stop(World world) {

    }

    private void applyImpulse(Entity e1, Entity e2) {
        ICollider e1Collider = e1.getCollider();
        ICollider e2Collider = e2.getCollider();
        if (notNull(e1Collider, e2Collider) && notTrigger(e1Collider, e2Collider) && isCollidable(e1, e2)) {
            float xDepth, yDepth;

            xDepth = getXDepth(e1, e2);
            yDepth = getYDepth(e1, e2);

            if (e2.is("static")) {
                move(e1, xDepth, yDepth);
            } else if (e1.is("static")) {
                move(e2, xDepth, yDepth);
            } else {
                moveBoth(e1, e2, xDepth, yDepth);
            }
        }
    }

    private void moveBoth(Entity e1, Entity e2, float xDepth, float yDepth) {
        if (Math.abs(xDepth) < Math.abs(yDepth)) {
            e1.setX(e1.getX() + xDepth / 2f);
            e2.setX(e2.getX() - xDepth / 2f);
        } else {
            e1.setY(e1.getY() + yDepth / 2f);
            e2.setY(e2.getY() - yDepth / 2f);
        }
    }

    private void move(Entity e, float xDepth, float yDepth) {
        if (Math.abs(xDepth) < Math.abs(yDepth)) {
            e.setX(e.getX() + xDepth);
        } else {
            e.setY(e.getY() + yDepth);
        }
    }

    private float getYDepth(Entity e1, Entity e2) {
        float yDepth = 0f;
        if (e1.getY() < e2.getY()) {
            yDepth = yDepthMax(e1, e2);
        } else if (e1.getY() > e2.getY()) {
            yDepth = yDepthMin(e1, e2);
        }
        return yDepth;
    }

    private float getXDepth(Entity e1, Entity e2) {
        float xDepth = 0f;
        if (e1.getX() < e2.getX()) {
            xDepth = xDepthMax(e1, e2);
        } else if (e1.getX() > e2.getX()) {
            xDepth = xDepthMin(e1, e2);
        }
        return xDepth;
    }

    private boolean isCollidable(Entity e1, Entity e2) {
        return e1.is("collidable") && e2.is("collidable");
    }

    private boolean notTrigger(ICollider e1Collider, ICollider e2Collider) {
        return e1Collider.notTrigger() && e2Collider.notTrigger();
    }

    private boolean notNull(ICollider e1collider, ICollider e2Collider) {
        return e1collider != null && e2Collider != null;
    }

    private float yDepthMin(Entity e1, Entity e2) {
        return yMax(e2) - yMin(e1);
    }

    private float yDepthMax(Entity e1, Entity e2) {
        return yMin(e2) - yMax(e1);
    }

    private float xDepthMin(Entity e1, Entity e2) {
        return xMax(e2) - xMin(e1);
    }

    private float xDepthMax(Entity e1, Entity e2) {
        return xMin(e2) - xMax(e1);
    }

    private float yMin(Entity e) {
        return e.getY() + e.getBounds().getOriginY();
    }

    private float yMax(Entity e) {
        return e.getY() + e.getBounds().getOriginY() + e.getBounds().getHeight();
    }

    private float xMin(Entity e) {
        return e.getX() + e.getBounds().getOriginX();
    }

    private float xMax(Entity e) {
        return e.getX() + e.getBounds().getOriginX() + e.getBounds().getWidth();
    }

    private boolean collides(Entity e1, Entity e2) {
        return intersects(e1.getCollider(), e2.getCollider(), e1, e2);
    }

    private boolean intersects(ICollider col1, ICollider col2, Posf2d col1Pos, Posf2d col2Pos) {
        return !(col1.getBounds().getOriginX() + col1Pos.getX() > col2.getBounds().getOriginX() + col2.getBounds().getWidth() + col2Pos.getX()
                || col1.getBounds().getOriginX() + col1.getBounds().getWidth() + col1Pos.getX() < col2.getBounds().getOriginX() + col2Pos.getX()
                || col1.getBounds().getOriginY() + col1Pos.getY() > col2.getBounds().getOriginY() + col2.getBounds().getHeight() + col2Pos.getY()
                || col1.getBounds().getOriginY() + col1.getBounds().getHeight() + col1Pos.getY() < col2.getBounds().getOriginY() + col2Pos.getY());
    }

    private boolean intersects(ICollider col1, ICollider col2, Entity e1, Entity e2) {
        return intersects(col1, col2, new Posf2d(e1.getX(), e1.getY()), new Posf2d(e2.getX(), e2.getY()));
    }

}