package kg.nongrate.pcglib.defaults;

import kg.nongrate.pcglib.Generator;
import kg.nongrate.pcglib.enums.ActionEnum;
import kg.nongrate.pcglib.interfaces.Action;

/**
 * Default move interface implementations to ease use of library
 */
public final class ActionDefaults {

    private ActionDefaults() {}

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
    public static Action get(ActionEnum actionEnum) {
        switch (actionEnum) {
            case PLACE_TILE:
                return (agent) -> Generator.getLevel().set(agent.getPosition(), agent.getActionTile().getTileId());
            case PLACE_RECTANGLE_OUTLINE:
                return (agent) -> {
                    int startIndex, endIndex;
                    int agentActionSize = agent.getActionSize();
                    if (agentActionSize % 2 == 0) {
                        startIndex = -agentActionSize / 2;
                        endIndex = agentActionSize / 2 - 1;
                    } else {
                        startIndex = (int) -Math.ceil(agent.getActionSize() / 2);
                        endIndex = (int) Math.floor(agent.getActionSize() / 2);
                    }
                    for (int i = startIndex; i <= endIndex; i++) {
                        int agentX = agent.getPosition().getX();
                        int agentY = agent.getPosition().getY();
                        int tileId = agent.getActionTile().getTileId();

                        Generator.getLevel().set(agentX + i, agentY + startIndex, tileId);
                        Generator.getLevel().set(agentX + i, agentY + endIndex, tileId);
                        Generator.getLevel().set(agentX + endIndex, agentY + i, tileId);
                        Generator.getLevel().set(agentX + startIndex, agentY + i, tileId);
                    }
                };
            case PLACE_RECTANGLE:
                return (agent) -> {
                    for (int i = (int) -Math.floor(agent.getActionSize() / 2); i < (int) Math.ceil(agent.getActionSize() / 2); i++) {
                        //                    for (int i = -agent.getActionSize(); i < agent.getActionSize(); i++) {
                        for (int j = (int) -Math.floor(agent.getActionSize() / 2); j < (int) Math.ceil(agent.getActionSize() / 2); j++) {
                            //                        for (int j = -agent.getActionSize(); j < agent.getActionSize(); j++) {
                            Generator.getLevel().set(agent.getPosition().getX() + i, agent.getPosition().getY() + j, agent.getActionTile().getTileId());
                        }
                    }
                };
            case PLACE_CIRCLE_OUTLINE:
                return (agent) -> {
                    int agentX = agent.getPosition().getX();
                    int agentY = agent.getPosition().getY();
                    int tileId = agent.getActionTile().getTileId();
                    int actionSize = agent.getActionSize();
                    //                    for (int x = -agent.getActionSize(); x < agent.getActionSize(); x++) {
                    for (int x = (int) -Math.floor(actionSize / 2); x <= (int) Math.ceil(actionSize / 2); x++) {
                        //                        for (int y = -agent.getActionSize(); y < agent.getActionSize(); y++) {
                        for (int y = (int) -Math.floor(actionSize / 2); y <= (int) Math.ceil(actionSize / 2); y++) {
                            if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(actionSize / 2, 2) &&
                                    Math.pow(x, 2) + Math.pow(y, 2) > Math.pow((actionSize / 2) - 1, 2)) {
                                Generator.getLevel().set(agentX + x, agentY + y, tileId);
                            }
                        }
                    }
                };
            case PLACE_CIRCLE:
                return (agent) -> {
                    int agentX = agent.getPosition().getX();
                    int agentY = agent.getPosition().getY();
                    int tileId = agent.getActionTile().getTileId();
                    int actionSize = agent.getActionSize();
                    //                    for (int x = -agent.getActionSize(); x < agent.getActionSize(); x++) {
                    for (int x = (int) -Math.floor(actionSize / 2); x <= (int) Math.ceil(actionSize / 2); x++) {
                        //                        for (int y = -agent.getActionSize(); y < agent.getActionSize(); y++) {
                        for (int y = (int) -Math.floor(actionSize / 2); y <= (int) Math.ceil(actionSize / 2); y++) {
                            if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(actionSize / 2, 2)) {
                                Generator.getLevel().set(agentX + x, agentY + y, tileId);
                            }
                        }
                    }
                };
            case PLACE_HORIZONTAL_LINE:
                return (agent) -> {
                    for (int i = (int) -Math.floor(agent.getActionSize() / 2); i <= (int) Math.ceil(agent.getActionSize() / 2); i++) {
                        Generator.getLevel().set(agent.getPosition().getX() + i, agent.getPosition().getY(), agent.getActionTile().getTileId());
                    }
                };
            case PLACE_VERTICAL_LINE:
                return (agent) -> {
                    for (int i = (int) -Math.floor(agent.getActionSize() / 2); i <= (int) Math.ceil(agent.getActionSize() / 2); i++) {
                        Generator.getLevel().set(agent.getPosition().getX(), agent.getPosition().getY() + i, agent.getActionTile().getTileId());
                    }
                };
            case PLACE_CROSS:
                return (agent) -> {
                    for (int i = (int) -Math.floor(agent.getActionSize() / 2); i <= (int) Math.ceil(agent.getActionSize() / 2); i++) {
                        Generator.getLevel().set(agent.getPosition().getX() + i, agent.getPosition().getY(), agent.getActionTile().getTileId());
                        Generator.getLevel().set(agent.getPosition().getX(), agent.getPosition().getY() + i, agent.getActionTile().getTileId());
                    }
                };
            default:
                return (agent) -> System.out.println("ActionDefaults: Default case in switch. Returning empty lambda.");
        }
    }
}
