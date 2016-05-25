package dk.sdu.group5.tests.integration;

import dk.sdu.group5.common.data.*;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.IGameProcess;
import org.junit.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.NoSuchElementException;

public class PlayerBarrierIntegrationCollisionTest {
    private Collection<? extends IGameProcess> processes;
    private World world;
    private float delta;

    public PlayerBarrierIntegrationCollisionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        processes = Lookup.getDefault().lookupAll(IGameProcess.class);
        world = new World(null);
        world.setGameKeys(new GameKeys());

        // Time per frame if running at 60 fps
        delta = 1f / 60f;
    }

    @After
    public void tearDown() {
        processes.forEach(p -> p.stop(world));
    }

    @Test(expected = NoSuchElementException.class)
    public void testBarrierCreatedUpCollision() {
        Entity obstacle = new Entity();
        obstacle.setCollider(new SquareCollider(false, new AABB(0, 0, 2, 2)));
        obstacle.addProperty("collidable");
        world.addEntity(obstacle);

        world.getGameKeys().getPlayerPlaceBarrier().setState(KeyState.PRESSED);
        world.getGameKeys().getPlayerMovementUp().setState(KeyState.HELD);

        processes.forEach(process -> process.start(world));
        processes.forEach(process -> process.update(world, delta));

        Entity barrier = world.getEntities().stream()
                .filter(e -> e.getType() == EntityType.BARRIER).findFirst().get();
    }

    @ServiceProvider(service = ICollisionDetectorService.class)
    public static class StubCollisionPositiveDetector implements ICollisionDetectorService{

        @Override
        public boolean collides(Entity ent1, Entity ent2) {
            return true;
        }
    }

}
