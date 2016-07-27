package kg.nongrate.pcglib.enums;

/**
 * Enum describing Movement style of an agent
 *
 * Move style: the way the agent moves every step.
 *     – follow a line in a specified direction(of 8possible directions)with a specified step size.
 *     – take a step in a random direction
 */
public enum MoveStyleEnum {
    UP, DOWN, LEFT, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, RANDOM
}
