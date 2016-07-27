package kg.nongrate.pcglib.defaults;

import kg.nongrate.pcglib.Generator;
import kg.nongrate.pcglib.enums.TriggerCheckEnum;
import kg.nongrate.pcglib.interfaces.TriggerCheck;

/**
 * Default move interface implementations to ease use of library
 */
public final class TriggerCheckDefaults {

    private TriggerCheckDefaults() {}

    public static TriggerCheck get(TriggerCheckEnum triggerCheckEnum) {
        switch (triggerCheckEnum) {
            case ALWAYS:
                return (agent) -> true;
            case AT_AREA:
                return (agent) -> {
                    int x = agent.getPosition().getX();
                    int y = agent.getPosition().getY();
                    int[][] tileMatrix = Generator.getLevel().getTileMatrix();

                    for (int i = 0; i < 9; i++) {
                        try {
                            if (tileMatrix[x + (i % 3) - 1][y + (i / 3) - 1] != agent.getTriggerTile().getTileId()) {
                                return false;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {}
                    }
                    return true;
                };
            case AT_TILE:
                return (agent) -> Generator.getLevel().getTileMatrix()[agent.getPosition().getX()][agent.getPosition().getY()] == agent.getTriggerTile().getTileId();
            case TILE_TYPE_NOT_IN_AREA:
                return (agent) -> {
                    int x = agent.getPosition().getX();
                    int y = agent.getPosition().getY();
                    int[][] tileMatrix = Generator.getLevel().getTileMatrix();

                    for (int i = 0; i < 9; i++) {
                        try {
                            if (tileMatrix[x + (i % 3) - 1][y + (i / 3) - 1] == agent.getTriggerTile().getTileId()) {
                                return false;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {}
                    }
                    return true;
                };
            default: return (agent) -> {
                System.out.println("TriggerCheckDefaults: Default case in switch. Returning false.");
                return false;
            };
        }
    }
}
