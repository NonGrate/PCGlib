package kg.nongrate.pcglib;

import java.lang.reflect.InvocationTargetException;

import kg.nongrate.pcglib.defaults.ActionDefaults;
import kg.nongrate.pcglib.defaults.BounceDefaults;
import kg.nongrate.pcglib.defaults.MoveStyleDefaults;
import kg.nongrate.pcglib.defaults.TriggerCheckDefaults;
import kg.nongrate.pcglib.enums.ActionEnum;
import kg.nongrate.pcglib.enums.BounceStyleEnum;
import kg.nongrate.pcglib.enums.MoveStyleEnum;
import kg.nongrate.pcglib.enums.TriggerCheckEnum;
import kg.nongrate.pcglib.exceptions.AgentArgumentsException;
import kg.nongrate.pcglib.exceptions.DuplicateVariableInitializationException;
import kg.nongrate.pcglib.interfaces.Action;
import kg.nongrate.pcglib.interfaces.BounceStyle;
import kg.nongrate.pcglib.interfaces.MoveStyle;
import kg.nongrate.pcglib.interfaces.TriggerCheck;
import kg.nongrate.pcglib.objects.Point;
import kg.nongrate.pcglib.objects.Tile;

/**
 * Agent class is used in content generation as thing that appears at defined position
 * in the level and does some activity based on defined logic
 * <p>
 * It accepts these parameters:
 * Spawn time[0-200]: The step number on which this agent is put into the level.This is an interesting value as it allows the sequencing of agents,
 * but still allows for overlap.
 * Period[1-5]: An agent only performs movement if its lifetime in steps is divisible by the period.
 * Tokens[10-80]: The amount of resources available to the agent. One token roughly equals a change to one tile.
 * Position[Anywhere within the level]: The center of the spawning circle in which the agent spawns.
 * Spawn radius[0-60]: The radius of the spawning circle in which the agent spawns.
 */

public class Agent {

    // not visible
    private Agent() {}

    public static Builder cloneBuilder(Builder agent) {
        Builder builder = Agent.builder();
        builder.setSpawnTime(agent.getAgent().spawnTime);
        builder.setPeriod(agent.getAgent().period);
        builder.setTokens(agent.getAgent().tokens);
        builder.setStartPosition(agent.getAgent().startPosition);
        builder.setSpawnRadius(agent.getAgent().spawnRadius);

        if (agent.getAgent().moveStyleCustom) {
            builder.setMoveStyle(agent.getAgent().moveStyle);
        } else {
            builder.setMoveStyle(agent.getAgent().moveStyleEnum);
        }
        builder.setMoveStep(agent.getAgent().moveStep);

        if (agent.getAgent().triggerCheckCustom) {
            builder.setTriggerCheck(agent.getAgent().triggerCheck);
        } else {
            builder.setTriggerCheck(agent.getAgent().triggerCheckEnum);
        }

        if (agent.getAgent().triggerTile != null) {
            builder.setTriggerCheckTile(agent.getAgent().triggerTile.getClass());
        }

        builder.setProbability(agent.getAgent().probability);

        if (agent.getAgent().actionCustom) {
            builder.setAction(agent.getAgent().action);
        } else {
            builder.setAction(agent.getAgent().actionEnum);
        }

        if (agent.getAgent().actionTile != null) {
            builder.setActionTile(agent.getAgent().actionTile.getClass());
        }

        builder.setActionSize(agent.getAgent().actionSize);

        builder.setActionPauseSteps(agent.getAgent().actionPauseSteps);

        if (agent.getAgent().bounceStyleCustom) {
            builder.setBounceStyle(agent.getAgent().bounceStyle);
        } else {
            builder.setBounceStyle(agent.getAgent().bounceStyleEnum);
        }

        builder.setBounceRespawnLocation(agent.getAgent().bounceRespawnLocation);

        return builder;
    }

    private int spawnTime = -1;
    private int lifeTime = 0;
    private int period = -1;
    private int tokens = -1;
    private Point startPosition = null;
    private Point position = null;
    private int spawnRadius = -1;

    private MoveStyle moveStyle = null;
    private MoveStyleEnum moveStyleEnum = null;
    private boolean moveStyleCustom = false;
    private int moveStep = -1;

    private TriggerCheck triggerCheck = null;
    private TriggerCheckEnum triggerCheckEnum = null;
    private Tile triggerTile = null;
    private boolean triggerCheckCustom = false;
    private double probability = -1;

    private Action action = null;
    private ActionEnum actionEnum = null;
    private Tile actionTile = null;
    private boolean actionCustom = false;
    private int actionSize = -1;

    private int actionPauseSteps = -1;

    private BounceStyle bounceStyle = null;
    private BounceStyleEnum bounceStyleEnum = null;
    private boolean bounceStyleCustom = false;
    private Point bounceRespawnLocation = null;

    public int getSpawnTime() {
        return spawnTime;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void incrementLife() {
        lifeTime++;
    }

    public int getPeriod() {
        return period;
    }

    public int getTokens() {
        return tokens;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public Point getPosition() {
        return position;
    }

    public double getProbability() {
        return probability;
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public MoveStyle getMoveStyle() {
        return moveStyle;
    }

    public MoveStyleEnum getMoveStyleEnum() {
        return moveStyleEnum;
    }

    public boolean isMoveStyleCustom() {
        return moveStyleCustom;
    }

    public int getMoveStep() {
        return moveStep;
    }

    public TriggerCheck getTriggerCheck() {
        return triggerCheck;
    }

    public TriggerCheckEnum getTriggerCheckEnum() {
        return triggerCheckEnum;
    }

    public Tile getTriggerTile() {
        return triggerTile;
    }

    public boolean isTriggerCheckCustom() {
        return triggerCheckCustom;
    }

    public Action getAction() {
        return action;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public Tile getActionTile() {
        return actionTile;
    }

    public boolean isActionCustom() {
        return actionCustom;
    }

    public int getActionPauseSteps() {
        return actionPauseSteps;
    }

    public BounceStyle getBounceStyle() {
        return bounceStyle;
    }

    public BounceStyleEnum getBounceStyleEnum() {
        return bounceStyleEnum;
    }

    public boolean isBounceStyleCustom() {
        return bounceStyleCustom;
    }

    public Point getBounceRespawnLocation() {
        return bounceRespawnLocation;
    }

    public void move() {
        if (checkTrigger()) {
            act();
        }
        if (moveStyleCustom) {
            moveStyle.move(this);
        } else {
            MoveStyleDefaults.get(moveStyleEnum).move(this);
        }
        // TODO check if x and y are correspond tile matrix
        if (getPosition().getX() < 0 || getPosition().getY() < 0 || getPosition().getX() > Generator.getLevel().getTileMatrix().length ||
                getPosition().getY() > Generator.getLevel().getTileMatrix()[0].length) {
            if (bounceStyleCustom) {
                bounceStyle.bounce(this);
            } else {
                BounceDefaults.get(bounceStyleEnum).bounce(this);
            }
        }
        incrementLife();
    }

    public boolean checkTrigger() {
        if (triggerCheckCustom) {
            return triggerCheck.canTrigger(this);
        } else if (triggerCheckEnum == TriggerCheckEnum.PROBABILITY) {
            return Math.random() < probability;
        } else {
            return triggerCheckEnum == TriggerCheckEnum.TIME_INTERVAL || TriggerCheckDefaults.get(triggerCheckEnum).canTrigger(this);
        }
    }

    public void act() {
        if ((!triggerCheckCustom && triggerCheckEnum == TriggerCheckEnum.TIME_INTERVAL && lifeTime % actionPauseSteps == 0) ||
                (triggerCheckCustom || triggerCheckEnum != TriggerCheckEnum.TIME_INTERVAL)) {
            if (actionCustom) {
                action.action(this);
            } else if (actionEnum == ActionEnum.PLACE_CONWAYS) {
                int tilesCount = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (Generator.getLevel().getTileMatrix()[getPosition().getX() + i][getPosition().getY() + j] == actionTile.getTileId()) {
                            tilesCount++;
                        }
                    }
                }
                if (tilesCount > 3 || tilesCount < 2) {
                    Generator.getLevel().set(getPosition(), 0);
                } else {
                    Generator.getLevel().set(getPosition(), actionTile.getTileId());
                }
            } else {
                ActionDefaults.get(actionEnum).action(this);
            }
            tokens--;
        }
    }

    public int getActionSize() {
        return actionSize;
    }

    public void setBounceRespawnLocation(Point bounceRespawnLocation) {
        this.bounceRespawnLocation = bounceRespawnLocation;
    }

    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
        //        this.getPosition().setLocation(x, y);
    }

    public void setPosition(Point point) {
        this.position = point;
        //        this.getPosition().setLocation(point);
    }

    public static Builder builder() {
        return new Agent().new Builder();
    }

    public class Builder {

        // not visible
        private Builder() {}

        public Builder setSpawnTime(int time) {
            if (spawnTime != -1) {
                throw new DuplicateVariableInitializationException("setSpawnTime(int)");
            }
            spawnTime = time;
            return this;
        }

        public Builder setPeriod(int per) {
            if (period != -1) {
                throw new DuplicateVariableInitializationException("setPeriod(int)");
            }
            period = per;
            return this;
        }

        public Builder setTokens(int tok) {
            if (tokens != -1) {
                throw new DuplicateVariableInitializationException("setTokens(int)");
            }
            tokens = tok;
            return this;
        }

        public Builder setStartPosition(Point pos) {
            if (startPosition != null) {
                throw new DuplicateVariableInitializationException("setStartPosition(Point)");
            }
            startPosition = pos;
            position = pos;
            return this;
        }

        public Builder setStartPosition(int x, int y) {
            if (startPosition != null) {
                throw new DuplicateVariableInitializationException("setStartPosition(Point)");
            }
            startPosition = new Point(x, y);
            position = new Point(x, y);
            return this;
        }

        public Builder setSpawnRadius(int radius) {
            if (spawnRadius != -1) {
                throw new DuplicateVariableInitializationException("setSpawnRadius(int)");
            }
            spawnRadius = radius;
            return this;
        }

        public Builder setMoveStyle(MoveStyle style) {
            if (moveStyle != null) {
                throw new DuplicateVariableInitializationException("setMoveStyle(MoveStyle)");
            } else if (moveStyleEnum != null) {
                throw new DuplicateVariableInitializationException("setMoveStyleEnum(MoveStyleEnum)");
            }
            moveStyle = style;
            moveStep = -1;
            moveStyleCustom = true;
            return this;
        }

        public Builder setMoveStyle(MoveStyleEnum styleEnum) {
            if (moveStyleCustom || moveStyleEnum != null) {
                throw new DuplicateVariableInitializationException("setMoveStyle(MoveStyleEnum)");
            }
            moveStyleEnum = styleEnum;
            moveStyleCustom = false;
            return this;
        }

        public Builder setMoveStep(int step) {
            if (moveStep != -1) {
                throw new DuplicateVariableInitializationException("setMoveStep(int)");
            }
            moveStep = step;
            return this;
        }

        public Builder setTriggerCheck(TriggerCheck trigger) {
            if (triggerCheck != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheck)");
            } else if (triggerCheckEnum != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheckEnum)");
            }
            triggerCheck = trigger;
            triggerCheckCustom = true;
            return this;
        }

        public Builder setTriggerCheckTile(Class<? extends Tile> tile) {
            if (triggerTile != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheckTile(Tile)");
            }

            try {
                triggerTile = tile.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                triggerTile = null;
            } catch (IllegalAccessException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                triggerTile = null;
            } catch (InstantiationException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                triggerTile = null;
            } catch (InvocationTargetException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                triggerTile = null;
            }
            return this;
        }

        public Builder setTriggerCheck(TriggerCheck trigger, Class<? extends Tile> tile) {
            if (triggerTile != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheckTile(Tile)");
            }
            if (triggerCheck != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheck)");
            } else if (triggerCheckEnum != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheckEnum)");
            }
            setTriggerCheck(trigger);
            setTriggerCheckTile(tile);
            return this;
        }

        public Builder setTriggerCheck(TriggerCheckEnum triggerEnum) {
            if (triggerCheckEnum != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheckEnum)");
            } else if (triggerCheck != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheck)");
            }
            triggerCheckEnum = triggerEnum;
            triggerCheckCustom = false;
            return this;
        }

        public Builder setTriggerCheck(TriggerCheckEnum triggerEnum, Class<? extends Tile> tile) {
            if (triggerTile != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheckTile(Tile)");
            }
            if (triggerCheck != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheck)");
            } else if (triggerCheckEnum != null) {
                throw new DuplicateVariableInitializationException("setTriggerCheck(TriggerCheckEnum)");
            }
            setTriggerCheck(triggerEnum);
            setTriggerCheckTile(tile);
            return this;
        }

        public Builder setProbability(double prob) {
            if (triggerCheckEnum == TriggerCheckEnum.PROBABILITY && probability != -1) {
                throw new DuplicateVariableInitializationException("setProbability(double)");
            }
            probability = prob;
            return this;
        }

        public Builder setActionPauseSteps(int steps) {
            if (triggerCheckEnum == TriggerCheckEnum.TIME_INTERVAL && actionPauseSteps != -1) {
                throw new DuplicateVariableInitializationException("setActionPauseSteps(int)");
            }
            actionPauseSteps = steps;
            return this;
        }

        public Builder setAction(Action act) {
            if (action != null) {
                throw new DuplicateVariableInitializationException("setAction(Action)");
            } else if (actionEnum != null) {
                throw new DuplicateVariableInitializationException("setAction(ActionEnum)");
            }
            action = act;
            actionCustom = true;
            return this;
        }

        public Builder setActionTile(Class<? extends Tile> tile) {
            if (actionTile != null) {
                throw new DuplicateVariableInitializationException("setActionTile(Tile)");
            }

            try {
                actionTile = tile.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                actionTile = null;
            } catch (IllegalAccessException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                actionTile = null;
            } catch (InstantiationException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                actionTile = null;
            } catch (InvocationTargetException e) {
                System.out.println("Cannot instantiate Tile of class: " + tile);
                actionTile = null;
            }
            return this;
        }

        public Builder setAction(Action act, Class<? extends Tile> tile) {
            if (actionTile != null) {
                throw new DuplicateVariableInitializationException("setActionTile(Tile)");
            }
            if (action != null) {
                throw new DuplicateVariableInitializationException("setAction(Action)");
            } else if (actionEnum != null) {
                throw new DuplicateVariableInitializationException("setAction(ActionEnum)");
            }
            setAction(act);
            setActionTile(tile);
            return this;
        }

        public Builder setAction(ActionEnum actEnum) {
            if (!actionCustom && actionEnum != null) {
                throw new DuplicateVariableInitializationException("setAction(ActionEnum)");
            }
            actionEnum = actEnum;
            actionCustom = false;
            return this;
        }

        public Builder setAction(ActionEnum actEnum, Class<? extends Tile> tile) {
            if (actionTile != null) {
                throw new DuplicateVariableInitializationException("setActionTile(Tile)");
            }
            if (action != null) {
                throw new DuplicateVariableInitializationException("setAction(Action)");
            } else if (actionEnum != null) {
                throw new DuplicateVariableInitializationException("setAction(ActionEnum)");
            }
            setAction(actEnum);
            setActionTile(tile);
            return this;
        }

        /**
         * This method is used to set size of agent's action in several default behaviors
         *
         * @param size - size of agent's action in all (!) directions
         * @return builder
         */
        public Builder setActionSize(int size) {
            if (actionSize != -1) {
                throw new DuplicateVariableInitializationException("setActionSize(int)");
            }
            actionSize = size;
            return this;
        }

        public Builder setBounceStyle(BounceStyle bounce) {
            if (bounceStyle != null) {
                throw new DuplicateVariableInitializationException("setBounceStyle(BounceStyle)");
            } else if (bounceStyleEnum != null) {
                throw new DuplicateVariableInitializationException("setBounceStyle(BounceStyleEnum)");
            }
            bounceStyle = bounce;
            bounceStyleCustom = true;
            return this;
        }

        public Builder setBounceStyle(BounceStyleEnum bounceEnum) {
            if (bounceStyleEnum != null) {
                throw new DuplicateVariableInitializationException("setBounceStyle(BounceStyleEnum)");
            } else if (bounceStyle != null) {
                throw new DuplicateVariableInitializationException("setBounceStyle(BounceStyle)");
            }

            bounceStyleEnum = bounceEnum;
            bounceStyleCustom = false;
            return this;
        }

        public Builder setBounceRespawnLocation(Point respawnLocation) {
            if (bounceRespawnLocation != null) {
                throw new DuplicateVariableInitializationException("setBounceRespawnLocation(Point)");
            }
            bounceRespawnLocation = respawnLocation;
            return this;
        }

        // TODO create check for start point x, y method
        // TODO create check for bounce respawn point x, y method
        public Builder setBounceRespawnLocation(int x, int y) {
            if (bounceRespawnLocation != null) {
                throw new DuplicateVariableInitializationException("setBounceRespawnLocation(Point)");
            }
            bounceRespawnLocation = new Point(x, y);
            return this;
        }

        public Agent build() {
            // Validation of basic stats
            StringBuilder builder = new StringBuilder();
            if (spawnTime == -1) {
                builder.append(" - setSpawnTime()\n");
            }
            if (period == -1) {
                builder.append(" - setPeriod()\n");
            }
            if (tokens == -1) {
                builder.append(" - setTokens()\n");
            }
            if (startPosition == null) {
                builder.append(" - setStartPosition()\n");
            }
            if (spawnRadius == -1) {
                builder.append(" - setSpawnRadius()\n");
            }

            // Validation of movement
            if (moveStyleCustom && moveStyle == null) {
                builder.append(" - setMoveStyle(MoveStyle)\n");
            }
            if (!moveStyleCustom && moveStyleEnum == null) {
                builder.append(" - setMoveStyle(MoveStyleEnum)\n");
            }

            // Validation of trigger
            if (triggerCheckCustom && triggerCheck == null) {
                builder.append(" - setTriggerCheck(TriggerCheck)\n");
            }
            if (!triggerCheckCustom && triggerCheckEnum == null) {
                builder.append(" - setTriggerCheck(TriggerCheckEnum)\n");
            }

            if (!triggerCheckCustom && triggerCheckEnum == TriggerCheckEnum.PROBABILITY && probability < 0) {
                builder.append(" - setProbability()\n");
            }

            if (!triggerCheckCustom && triggerCheckEnum == TriggerCheckEnum.TIME_INTERVAL && actionPauseSteps < 0) {
                builder.append(" - setActionPauseSteps()\n");
            }

            if (!triggerCheckCustom && triggerCheckEnum != TriggerCheckEnum.ALWAYS && triggerTile == null) {
                builder.append(" - setTriggerCheckTile(Tile)\n");
            }

            // Validation of action
            if (actionCustom && action == null) {
                builder.append(" - setAction(Action)\n");
            }
            if (!actionCustom && actionEnum == null) {
                builder.append(" - setAction(ActionEnum)\n");
            }

            if (!actionCustom && actionEnum != ActionEnum.PLACE_TILE && actionEnum != ActionEnum.PLACE_CONWAYS && actionSize == -1) {
                builder.append(" - setActionSize(int)\n");
            }

            if (actionTile == null) {
                builder.append(" - setActionTile(Tile)\n");
            }

            // Validation of bounce
            if (bounceStyleCustom && bounceStyle == null) {
                builder.append(" - setBounceStyle(BounceStyle)\n");
            }
            if (!bounceStyleCustom && bounceStyleEnum == null) {
                builder.append(" - setBounceStyle(BounceStyleEnum)\n");
            }

            if (!bounceStyleCustom && bounceStyleEnum == BounceStyleEnum.RESPAWN_AT_LOCATION && bounceRespawnLocation == null) {
                builder.append(" - setBounceRespawnLocation(Point)\n");
            }

            if (!builder.toString().isEmpty()) {
                throw new AgentArgumentsException(builder.toString());
            }

            clean();

            return Agent.this;
        }

        // TODO check if there are more vars to clean
        private void clean() {
            if (!triggerCheckCustom && triggerCheckEnum != TriggerCheckEnum.TIME_INTERVAL) {
                actionPauseSteps = -1;
            }
            if (!triggerCheckCustom && triggerCheckEnum != TriggerCheckEnum.TIME_INTERVAL) {
                probability = -1;
            }
            if (!triggerCheckCustom && triggerCheckEnum == TriggerCheckEnum.ALWAYS) {
                triggerTile = null;
            }
            if (triggerCheckCustom) {
                triggerCheckEnum = null;
            } else {
                triggerCheck = null;
            }
            if (actionCustom) {
                actionEnum = null;
            } else {
                action = null;
            }
            if (moveStyleCustom) {
                moveStyleEnum = null;
                moveStep = -1;
            } else {
                moveStyle = null;
            }
            if (bounceStyleCustom) {
                bounceStyleEnum = null;
            } else {
                bounceStyle = null;
            }
            if (!bounceStyleCustom && bounceStyleEnum != BounceStyleEnum.RESPAWN_AT_LOCATION) {
                bounceRespawnLocation = null;
            }
            if (!actionCustom && actionEnum == ActionEnum.PLACE_TILE) {
                actionSize = -1;
            }
        }

        public Agent getAgent() {
            return Agent.this;
        }
    }
}
