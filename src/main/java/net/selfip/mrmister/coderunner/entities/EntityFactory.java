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
    }

    public static EntityFactory create(Bounds bounds) {
        return new EntityFactory(bounds);
    }

    public AbstractEntity createBed(Point2D position) {
        return new Bed(position, bounds);
    }

    public AbstractEntity createCoffee(Point2D position) {
        return new Coffee(position, bounds);
    }

    public AbstractEntity createBug(Point2D position) {
        return new Bug(position, bounds);
    }

    public Player createPlayer(I18n i18n, GameLoop gameLoop) throws IOException {
        return new Player(bounds, i18n, gameLoop);
    }
}
