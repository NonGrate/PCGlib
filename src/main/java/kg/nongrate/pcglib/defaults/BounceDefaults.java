package kg.nongrate.pcglib.defaults;

import java.util.Random;

import kg.nongrate.pcglib.enums.BounceStyleEnum;
import kg.nongrate.pcglib.interfaces.BounceStyle;
import kg.nongrate.pcglib.objects.Point;

/**
 * Default move interface implementations to ease use of library
 */
public final class BounceDefaults {

    private BounceDefaults() {}

    /*
     *     – place a specified tile at position
 *     – place a rectangular outline of specified tiles and size around position
 *     – place a filled rectangle of specified tiles and size around position
 *     – place a circle of specified tiles and size around position
 *     – place a platform/line of specified tiles and size at position
 *     – place a cross of specified tiles and size at position
 *     – place or remove specified tiles in a specified area according to the rules of Conway’s “Game of Life”.

        PLACE_RECTANGLE_OUTLINE,
        PLACE_RECTANGLE,
        PLACE_CIRCLE,
        PLACE_LINE,
        PLACE_CROSS,
     */

    // TODO check all actions
    public static BounceStyle get(BounceStyleEnum bounceStyleEnum) {
        switch (bounceStyleEnum) {
            case RESPAWN_AT_LOCATION:
                return (agent) -> {
                    Random random = new Random();
                    int radius = agent.getSpawnRadius();
                    Point respawn = agent.getBounceRespawnLocation();
                    agent.setPosition(respawn.getX() + random.nextInt(radius) - radius / 2, respawn.getY() + random.nextInt(radius) - radius / 2);
                };
            case RESPAWN:
                return (agent) -> {
                    Random random = new Random();
                    int radius = agent.getSpawnRadius();
                    Point start = agent.getStartPosition();
                    agent.setPosition(start.getX() + random.nextInt(radius) - radius / 2, start.getY() + random.nextInt(radius) - radius / 2);
                };
            case BOUNCE_BACK:
                return (agent) -> {
                    int x = agent.getPosition().getX();
                    int y = agent.getPosition().getY();
                    agent.setPosition(x < 0 ? -x : x, y < 0 ? -y : y);
                };
            default:
                return (agent) -> System.out.println("ActionDefaults: Default case in switch. Returning empty lambda.");
        }
    }
}
