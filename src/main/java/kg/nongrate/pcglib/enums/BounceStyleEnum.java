package kg.nongrate.pcglib.enums;

/**
 * Enum that describes action called when actor hits bounds of the level
 *
 * Boundary movement: The way the agent handles hitting a boundary.
 *     – bounce away
 *     – go back to start position
 *     – go back to within a specified rectangular area around the start position
 */
public enum BounceStyleEnum {
    BOUNCE_BACK, RESPAWN, RESPAWN_AT_LOCATION
}
