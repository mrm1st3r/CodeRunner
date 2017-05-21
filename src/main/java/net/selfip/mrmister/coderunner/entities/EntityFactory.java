package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.game.Bounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Factory for creating new entity objects.
 */
public class EntityFactory {

    private static final Logger LOG = LoggerFactory.getLogger(EntityFactory.class);

    private final Bounds bounds;
    private BufferedImage[] playerAnimation;
    private BufferedImage[] bugAnimation;
    private BufferedImage[] bedAnimation;
    private BufferedImage[] coffeeAnimation;

    private EntityFactory(Bounds bounds) {
        this.bounds = bounds;
        try {
            coffeeAnimation = Coffee.init();
            bugAnimation = Bug.init();
            bedAnimation = Bed.init();
            playerAnimation = Player.init();
        } catch (Exception e) {
            LOG.warn("Could not load entity resource", e);
        }
    }

    public static EntityFactory create(Bounds bounds) {
        return new EntityFactory(bounds);
    }

    public Entity createBed(Point2D position) {
        return new Bed(position, bedAnimation, bounds);
    }

    public Entity createCoffee(Point2D position) {
        return new Coffee(position, coffeeAnimation, bounds);
    }

    public Entity createBug(Point2D position) {
        return new Bug(position, bugAnimation, bounds);
    }

    public PlayableEntity createPlayer() throws IOException {
        return new Player(bounds, playerAnimation);
    }
}
