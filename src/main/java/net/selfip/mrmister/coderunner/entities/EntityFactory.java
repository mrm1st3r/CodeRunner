package net.selfip.mrmister.coderunner.entities;

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

    private BufferedImage[] playerAnimation;
    private BufferedImage[] bugAnimation;
    private BufferedImage[] bedAnimation;
    private BufferedImage[] coffeeAnimation;

    private EntityFactory() {
        try {
            coffeeAnimation = Coffee.init();
            bugAnimation = Bug.init();
            bedAnimation = Bed.init();
            playerAnimation = Player.init();
        } catch (Exception e) {
            LOG.warn("Could not load entity resource", e);
        }
    }

    public static EntityFactory create() {
        return new EntityFactory();
    }

    public Entity createBed(Point2D position) {
        return new Bed(position, bedAnimation);
    }

    public Entity createCoffee(Point2D position) {
        return new Coffee(position, coffeeAnimation);
    }

    public Entity createBug(Point2D position) {
        return new Bug(position, bugAnimation);
    }

    public PlayableEntity createPlayer() throws IOException {
        return new Player(playerAnimation);
    }
}
