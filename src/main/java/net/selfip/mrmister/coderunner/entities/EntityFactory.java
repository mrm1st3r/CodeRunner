package net.selfip.mrmister.coderunner.entities;

import net.selfip.mrmister.coderunner.game.Bounds;
import net.selfip.mrmister.coderunner.game.GameLoop;
import net.selfip.mrmister.coderunner.lang.I18n;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Factory for creating new entity objects.
 */
public class EntityFactory {

    private final Bounds bounds;

    private EntityFactory(Bounds bounds) {
        this.bounds = bounds;
        Coffee.init();
        Bug.init();
        Bed.init();
    }

    public static EntityFactory create(Bounds bounds) {
        return new EntityFactory(bounds);
    }

    public Entity createBed(Point2D position) {
        return new Bed(position, bounds);
    }

    public Entity createCoffee(Point2D position) {
        return new Coffee(position, bounds);
    }

    public Entity createBug(Point2D position) {
        return new Bug(position, bounds);
    }

    public PlayableEntity createPlayer(I18n i18n, GameLoop gameLoop) throws IOException {
        return new Player(bounds, i18n, gameLoop);
    }
}
