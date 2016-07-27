package kg.nongrate.pcglib.defaults;

import kg.nongrate.pcglib.enums.MoveStyleEnum;
import kg.nongrate.pcglib.interfaces.MoveStyle;

/**
 * Default move interface implementations to ease use of library
 */
public final class MoveStyleDefaults {

    private MoveStyleDefaults() {}

    public static MoveStyle get(MoveStyleEnum moveStyleEnum) {
        // UP, DOWN, LEFT, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, RANDOM
        switch (moveStyleEnum) {
            case DOWN:
                return agent -> agent.setPosition(agent.getPosition().getX(), agent.getPosition().getY() + agent.getMoveStep());
            case DOWN_LEFT:
                return agent -> agent.setPosition(agent.getPosition().getX() - agent.getMoveStep(), agent.getPosition().getY() + agent.getMoveStep());
            case DOWN_RIGHT:
                return agent -> agent.setPosition(agent.getPosition().getX() + agent.getMoveStep(), agent.getPosition().getY() + agent.getMoveStep());
            case LEFT:
                return agent -> agent.setPosition(agent.getPosition().getX() - agent.getMoveStep(), agent.getPosition().getY());
            case RIGHT:
                return agent -> agent.setPosition(agent.getPosition().getX() + agent.getMoveStep(), agent.getPosition().getY());
            case UP:
                return agent -> agent.setPosition(agent.getPosition().getX(), agent.getPosition().getY() - agent.getMoveStep());
            case UP_LEFT:
                return agent -> agent.setPosition(agent.getPosition().getX() - agent.getMoveStep(), agent.getPosition().getY() - agent.getMoveStep());
            case UP_RIGHT:
                return agent -> agent.setPosition(agent.getPosition().getX() + agent.getMoveStep(), agent.getPosition().getY() - agent.getMoveStep());
            case RANDOM:
                int randx = (int) (Math.random() * 3);
                int randy = (int) (Math.random() * 3);
                return agent -> agent.setPosition(agent.getPosition().getX() + (randx < 1 ? -agent.getMoveStep() : randx > 2 ? agent.getMoveStep() : 0),
                        agent.getPosition().getY() + (randy < 1 ? -agent.getMoveStep() : randy > 2 ? agent.getMoveStep() : 0));
        }

        return (agent) -> System.out.println("ActionDefaults: Default case in switch. Returning empty lambda.");
    }
}
