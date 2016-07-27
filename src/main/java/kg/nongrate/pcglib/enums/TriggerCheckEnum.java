package kg.nongrate.pcglib.enums;

/**
 * Created by arseniii on 10/19/15.
 * <p>
 * Trigger state: The condition for triggering an action, checked after each movement step.
 * – always
 * – when the agent hits a specified type of terrain.
 * – when a specified rectangular area is full of a specified tile type
 * – when a specified area does not contain a specified tile type
 * – with a specified probability
 * – with a specified time interval
 */
public enum TriggerCheckEnum {
    ALWAYS,
    AT_TILE,
    AT_AREA,
    TILE_TYPE_NOT_IN_AREA,
    PROBABILITY,
    TIME_INTERVAL
}
