package dk.sdu.group5.common.data;

import dk.sdu.group5.common.data.collision.ICollider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Entity {

    private final UUID ID = UUID.randomUUID();
    private float x, y;
    private Posf2d velocity;
    private int lives;

    private final Set<String> properties;
    private ICollider collider;
    private EntityType type;
    private String texture;

    public Entity() {
        velocity = new Posf2d(0f, 0f);
        properties = new HashSet<>();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Posf2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Posf2d velocity) {
        this.velocity = velocity;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addProperty(String property) throws Exception {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!properties.add(property)) {
            throw new Exception("A duplicate entry already exists matching the provided property!");
        }
    }

    public void removeProperty(String property) throws Exception {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!properties.remove(property)) {
            throw new Exception("No entry already exists matching the provided property!");
        }
    }

    public Set<String> getProperties() {
        return properties;
    }

    public ICollider getCollider() {
        return collider;
    }

    public void setCollider(ICollider collider) {
        this.collider = collider;
    }

    @Override
    public String toString() {
        return type +"{x:"+x+", y:"+y+", lives:"+lives+"}";
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
