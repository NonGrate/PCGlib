package kg.nongrate.pcglib;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kg.nongrate.pcglib.enums.State;

public class Generator {

    private static HashMap<String, Level> levels = new HashMap<>();
    private static ArrayList<Agent> currentAgents = new ArrayList<>();
    private static HashMap<Level, ArrayList<Agent>> agents = new HashMap<>();
    private static Level level;
    private static State STATE = State.READY;

    public static void eraseCurrentLevel(int width, int height) {
        level = new Level(width, height);
    }

    public static void removeCurrentAgents() {
        agents.put(level, new ArrayList<>());
    }

    public static void addLevel(String name, int width, int height) {
        Level tmpLevel = new Level(width, height);
        levels.put(name, tmpLevel);
        agents.put(tmpLevel, new ArrayList<>());
        level = tmpLevel;
    }

    public static String getLevelName() {
        if (!levels.containsValue(level)) {
            return null;
        }

        Set<Map.Entry<String, Level>> entrySet = levels.entrySet();
        for (Map.Entry entry : entrySet) {
            if (entry.getValue().equals(level)) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public static Level chooseLevel(String name) {
        Level tmp = levels.get(name);
        if (tmp != null) {
            level = levels.get(name);
        }

        return level;
    }

    public static void addAgent(Agent... agentArray) {
        Collections.addAll(agents.get(level), agentArray);
    }

    public static void addAgent(String levelName, Agent... agentArray) {
        Collections.addAll(agents.get(levels.get(levelName)), agentArray);
    }

    public static void processStep() {
        if (currentAgents == null) {
            currentAgents = agents.get(level);
        }
        currentAgents = (ArrayList<Agent>) Stream.of(currentAgents).peek(Agent::move).filter(agent -> agent.getTokens() > 0).collect(Collectors.toList());
        if (currentAgents.size() == 0) {
            STATE = State.FINISHED;
            currentAgents = null;
        } else {
            STATE = State.RUNNING;
        }
    }

    public static Level getLevel() {
        return level;
    }

    public static State getState() {
        return STATE;
    }

    public static void run() {
        currentAgents = agents.get(level);
        while (STATE != State.FINISHED) {
            processStep();
        }
    }

    // TODO decide how to run Generator from inside, not from outside
    // TODO can leave from outside, to control and process steps (process only few steps)
}
