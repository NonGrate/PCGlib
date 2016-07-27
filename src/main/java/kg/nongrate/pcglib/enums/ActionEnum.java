package kg.nongrate.pcglib.enums;

/**
 * This enum describes basic possibilities of behavior of the agent
 * <p>
 * Action type: The type of action performed if it is triggered.
 * – place a specified tile at position
 * – place a rectangular outline of specified tiles and size around position
 * – place a filled rectangle of specified tiles and size around position
 * – place a circle of specified tiles and size around position
 * – place a platform/line of specified tiles and size at position
 * – place a cross of specified tiles and size at position
 * – place or remove specified tiles in a specified area according to the rules of Conway’s “Game of Life”.
 */
public enum ActionEnum {
    PLACE_TILE,
    PLACE_RECTANGLE_OUTLINE,
    PLACE_RECTANGLE,
    PLACE_CIRCLE_OUTLINE,
    PLACE_CIRCLE,
    PLACE_HORIZONTAL_LINE,
    PLACE_VERTICAL_LINE,
    PLACE_CROSS,
    PLACE_CONWAYS
}
