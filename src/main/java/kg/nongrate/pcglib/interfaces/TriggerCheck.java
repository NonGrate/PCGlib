package kg.nongrate.pcglib.interfaces;

import kg.nongrate.pcglib.Agent;

/**
 * Enum that describing conditions under which agent should trigger its action
 */
public interface TriggerCheck {
    boolean canTrigger(Agent agent);
}
