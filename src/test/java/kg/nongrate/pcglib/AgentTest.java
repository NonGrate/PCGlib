package kg.nongrate.pcglib;

import com.annimon.stream.Stream;

import org.junit.Test;

import kg.nongrate.pcglib.Tiles.BGTile;
import kg.nongrate.pcglib.Tiles.BrickTile;
import kg.nongrate.pcglib.enums.ActionEnum;
import kg.nongrate.pcglib.enums.BounceStyleEnum;
import kg.nongrate.pcglib.enums.MoveStyleEnum;
import kg.nongrate.pcglib.enums.TriggerCheckEnum;
import kg.nongrate.pcglib.objects.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test behavior of all agent builder methods
 */
public class AgentTest {

    private static final Point AGENT_STARTING_POINT = new Point(2, 2);

    private static Agent.Builder getCoreAgentBuilder() {
        return Agent.builder().setSpawnTime(4).setPeriod(1).setTokens(80).setStartPosition(AGENT_STARTING_POINT).setSpawnRadius(4).setMoveStep(1)
                .setActionSize(4);
    }

    private static Agent getDefaultAgent() {
        return getCoreAgentBuilder().setTriggerCheck(TriggerCheckEnum.ALWAYS).setMoveStyle(MoveStyleEnum.RANDOM).setAction(ActionEnum.PLACE_TILE)
                .setTriggerCheckTile(new BGTile()).setActionTile(new BrickTile()).setBounceStyle(BounceStyleEnum.RESPAWN_AT_LOCATION)
                .setBounceRespawnLocation(new Point(5, 5)).build();
    }

    static TriggerCheckEnum[] triggerCheckEnums = TriggerCheckEnum.values();
    static MoveStyleEnum[] moveStyleEnums = MoveStyleEnum.values();
    static ActionEnum[] actionEnums = ActionEnum.values();
    static BounceStyleEnum[] bounceStyleEnums = BounceStyleEnum.values();

    static Agent[] triggerCasesAgents = new Agent[triggerCheckEnums.length + 1];
    static Agent[] moveStyleAgents = new Agent[moveStyleEnums.length + 1];
    static Agent[] bounceStyleAgents = new Agent[bounceStyleEnums.length + 1];
    static Agent[] actionAgents = new Agent[actionEnums.length + 1];

    static {
        Generator.addLevel("StartLevel", 5, 5);
        prepareTriggerCases();
        prepareMoveCases();
        prepareActionCases();
        prepareBounceCases();
    }

    @Test
    public void testGetSpawnTime() throws Exception {
        assertNotEquals(-1, getDefaultAgent().getSpawnTime());
    }

    @Test
    public void testIncrementLife() throws Exception {
        Agent agent = getDefaultAgent();
        assertEquals(0, agent.getLifeTime());
        agent.incrementLife();
        assertEquals(1, agent.getLifeTime());
    }

    @Test
    public void testGetPeriod() throws Exception {
        assertNotEquals(-1, getDefaultAgent().getPeriod());
    }

    @Test
    public void testGetTokens() throws Exception {
        assertNotEquals(-1, getDefaultAgent().getTokens());
    }

    @Test
    public void testGetStartPosition() throws Exception {
        assertEquals(AGENT_STARTING_POINT, getDefaultAgent().getStartPosition());
    }

    @Test
    public void testGetPosition() throws Exception {
        Agent agent = getDefaultAgent();
        agent.setPosition(1, 1);
        assertEquals(new Point(1, 1), agent.getPosition());
    }

    @Test
    public void testGetProbability() throws Exception {
        for (Agent agent : triggerCasesAgents) {
            if (!agent.isTriggerCheckCustom() && agent.getTriggerCheckEnum() == TriggerCheckEnum.PROBABILITY) {
                assertNotEquals(-1, agent.getProbability());
            } else {
                assertEquals(-1, (int) agent.getProbability());
            }
        }
    }

    @Test
    public void testGetSpawnRadius() throws Exception {
        assertNotEquals(-1, getDefaultAgent().getSpawnRadius());
    }

    @Test
    public void testGetMoveStyle() throws Exception {
        Agent agent = getDefaultAgent();
        if (agent.isMoveStyleCustom()) {
            assertNotNull(agent.getMoveStyle());
            assertNull(agent.getMoveStyleEnum());
        } else {
            assertNotNull(agent.getMoveStyleEnum());
            assertNull(agent.getMoveStyle());
        }
    }

    @Test
    public void testGetMoveStep() throws Exception {
        assertNotEquals(-1, getDefaultAgent().getMoveStep());
    }

    @Test
    public void testGetTriggerCheck() throws Exception {
        for (Agent agent : triggerCasesAgents) {
            if (agent.isTriggerCheckCustom()) {
                assertNotNull(agent.getTriggerCheck());
                assertNull(agent.getTriggerCheckEnum());
            } else {
                assertNull(agent.getTriggerCheck());
                assertNotNull(agent.getTriggerCheckEnum());
            }
        }
    }

    @Test
    public void testGetTriggerTile() throws Exception {
        for (Agent agent : triggerCasesAgents) {
            if (!agent.isTriggerCheckCustom() && agent.getTriggerCheckEnum() != TriggerCheckEnum.ALWAYS) {
                assertNotNull(agent.getTriggerTile());
            } else {
                assertNull(agent.getTriggerTile());
            }
        }
    }

    @Test
    public void testGetAction() throws Exception {
        for (Agent agent : actionAgents) {
            if (agent.isActionCustom()) {
                assertNotNull(agent.getAction());
                assertNull(agent.getActionEnum());
            } else {
                assertNull(agent.getAction());
                assertNotNull(agent.getActionEnum());
            }
        }
    }

    @Test
    public void testGetActionTile() throws Exception {
        assertNotNull(getDefaultAgent().getActionTile());
    }

    @Test
    public void testGetActionListener() throws Exception {
        for (Agent agent : triggerCasesAgents) {
            if (agent.isTriggerCheckCustom()) {
                assertNotNull(agent.getTriggerCheck());
                assertNull(agent.getTriggerCheckEnum());
            } else {
                assertNull(agent.getTriggerCheck());
                assertNotNull(agent.getTriggerCheckEnum());
            }
        }
    }

    @Test
    public void testGetBounceStyle() throws Exception {
        for (Agent agent : bounceStyleAgents) {
            if (agent.isBounceStyleCustom()) {
                assertNotNull(agent.getBounceStyle());
                assertNull(agent.getBounceStyleEnum());
            } else {
                assertNull(agent.getBounceStyle());
                assertNotNull(agent.getBounceStyleEnum());
            }
        }
    }

    @Test
    public void testIsMoveStyleCustom() throws Exception {
        for (Agent agent : moveStyleAgents) {
            if (agent.getMoveStyle() != null) {
                assertNull(agent.getMoveStyleEnum());
                assertTrue(agent.isMoveStyleCustom());
            } else {
                assertNotNull(agent.getMoveStyleEnum());
                assertFalse(agent.isMoveStyleCustom());
            }
            if (agent.getMoveStyleEnum() != null) {
                assertNull(agent.getMoveStyle());
                assertFalse(agent.isMoveStyleCustom());
            } else {
                assertNotNull(agent.getMoveStyle());
                assertTrue(agent.isMoveStyleCustom());
            }
        }
    }

    @Test
    public void testIsTriggerCheckCustom() throws Exception {
        for (Agent agent : triggerCasesAgents) {
            if (agent.getTriggerCheck() != null) {
                assertNull(agent.getTriggerCheckEnum());
                assertTrue(agent.isTriggerCheckCustom());
            } else {
                assertNotNull(agent.getTriggerCheckEnum());
                assertFalse(agent.isTriggerCheckCustom());
            }
            if (agent.getTriggerCheckEnum() != null) {
                assertNull(agent.getTriggerCheck());
                assertFalse(agent.isTriggerCheckCustom());
            } else {
                assertNotNull(agent.getTriggerCheck());
                assertTrue(agent.isTriggerCheckCustom());
            }
        }
    }

    @Test
    public void testIsActionCustom() throws Exception {
        for (Agent agent : actionAgents) {
            if (agent.getAction() != null) {
                assertNull(agent.getActionEnum());
                assertTrue(agent.isActionCustom());
            } else {
                assertNotNull(agent.getActionEnum());
                assertFalse(agent.isActionCustom());
            }
            if (agent.getActionEnum() != null) {
                assertNull(agent.getAction());
                assertFalse(agent.isActionCustom());
            } else {
                assertNotNull(agent.getAction());
                assertTrue(agent.isActionCustom());
            }
        }
    }

    @Test
    public void testIsBounceStyleCustom() throws Exception {
        for (Agent agent : bounceStyleAgents) {
            if (agent.getBounceStyle() != null) {
                assertNull(agent.getBounceStyleEnum());
                assertTrue(agent.isBounceStyleCustom());
            } else {
                assertNotNull(agent.getBounceStyleEnum());
                assertFalse(agent.isBounceStyleCustom());
            }
            if (agent.getBounceStyleEnum() != null) {
                assertNull(agent.getBounceStyle());
                assertFalse(agent.isBounceStyleCustom());
            } else {
                assertNotNull(agent.getBounceStyle());
                assertTrue(agent.isBounceStyleCustom());
            }
        }
    }

    @Test
    public void testMove() throws Exception {
        Stream.ofRange(0, 1).forEach((r) -> {
            for (Agent agent : moveStyleAgents) {
                agent.setPosition(AGENT_STARTING_POINT);
                Point previousPoint = agent.getPosition();
                agent.move();
                if (!agent.isMoveStyleCustom()) {
                    switch (agent.getMoveStyleEnum()) {
                        case RANDOM:
                            assertTrue(Math.abs(agent.getPosition().x - previousPoint.x) < 2);
                            assertTrue(Math.abs(agent.getPosition().y - previousPoint.y) < 2);
                            break;
                        case UP_RIGHT:
                            assertEquals(agent.getPosition().x, previousPoint.x + 1);
                            assertEquals(agent.getPosition().y, previousPoint.y - 1);
                            break;
                        case UP_LEFT:
                            assertEquals(agent.getPosition().x, previousPoint.x - 1);
                            assertEquals(agent.getPosition().y, previousPoint.y - 1);
                            break;
                        case UP:
                            assertEquals(agent.getPosition().x, previousPoint.x);
                            assertEquals(agent.getPosition().y, previousPoint.y - 1);
                            break;
                        case RIGHT:
                            assertEquals(agent.getPosition().x, previousPoint.x + 1);
                            assertEquals(agent.getPosition().y, previousPoint.y);
                            break;
                        case LEFT:
                            assertEquals(agent.getPosition().x, previousPoint.x - 1);
                            assertEquals(agent.getPosition().y, previousPoint.y);
                            break;
                        case DOWN_RIGHT:
                            assertEquals(agent.getPosition().x, previousPoint.x + 1);
                            assertEquals(agent.getPosition().y, previousPoint.y + 1);
                            break;
                        case DOWN_LEFT:
                            assertEquals(agent.getPosition().x, previousPoint.x - 1);
                            assertEquals(agent.getPosition().y, previousPoint.y + 1);
                            break;
                        case DOWN:
                            assertEquals(agent.getPosition().x, previousPoint.x);
                            assertEquals(agent.getPosition().y, previousPoint.y + 1);
                            break;
                    }
                }
            }
        });
    }

    @Test
    public void testCheckTrigger() throws Exception {

    }

    @Test
    public void testAct() throws Exception {
    }

    @Test
    public void testGetActionSize() throws Exception {
        for (Agent agent : actionAgents) {
            if (!agent.isActionCustom() && agent.getActionEnum() == ActionEnum.PLACE_TILE) {
                assertEquals(-1, agent.getActionSize());
            } else {
                assertNotEquals(-1, agent.getActionSize());
            }
        }
    }

    private static Agent.Builder getTriggerCasesAgentBuilder() {
        return getCoreAgentBuilder().setMoveStyle(MoveStyleEnum.RANDOM).setAction(ActionEnum.PLACE_TILE).setActionTile(new BrickTile())
                .setBounceStyle(BounceStyleEnum.RESPAWN_AT_LOCATION).setBounceRespawnLocation(new Point(5, 5));
    }

    private static Agent.Builder getMoveCasesAgentBuilder() {
        return getCoreAgentBuilder().setAction(ActionEnum.PLACE_TILE).setActionTile(new BrickTile()).setTriggerCheck(TriggerCheckEnum.ALWAYS)
                .setBounceStyle(BounceStyleEnum.RESPAWN_AT_LOCATION).setBounceRespawnLocation(new Point(5, 5));
    }

    private static Agent.Builder getBounceCasesAgentBuilder() {
        return getCoreAgentBuilder().setMoveStyle(MoveStyleEnum.RANDOM).setAction(ActionEnum.PLACE_TILE).setActionTile(new BrickTile())
                .setTriggerCheck(TriggerCheckEnum.ALWAYS);
    }

    private static Agent.Builder getActionCasesAgentBuilder() {
        return getCoreAgentBuilder().setMoveStyle(MoveStyleEnum.RANDOM).setActionTile(new BrickTile()).setTriggerCheck(TriggerCheckEnum.ALWAYS)
                .setBounceStyle(BounceStyleEnum.RESPAWN_AT_LOCATION).setBounceRespawnLocation(new Point(5, 5));
    }

    private static void prepareTriggerCases() {
        for (int i = 0; i < triggerCheckEnums.length; i++) {
            Agent.Builder tmpBuilder = getTriggerCasesAgentBuilder().setTriggerCheck(triggerCheckEnums[i]);

            // IMPORTANT ALWAYS case doesn't need tile to trigger. Tile can be null
            if (triggerCheckEnums[i] != TriggerCheckEnum.ALWAYS) {
                tmpBuilder.setTriggerCheckTile(new BGTile());
            }

            /* IMPORTANT PROBABILITY case must have probability variable initialized
             And TIME_INTERVAL case must have ActionPauseSteps variable initialized */
            if (triggerCheckEnums[i] == TriggerCheckEnum.PROBABILITY) {
                tmpBuilder.setProbability(.5);
            } else if (triggerCheckEnums[i] == TriggerCheckEnum.TIME_INTERVAL) {
                tmpBuilder.setActionPauseSteps(5);
            }
            triggerCasesAgents[i] = tmpBuilder.build();
        }
        triggerCasesAgents[triggerCheckEnums.length] = getTriggerCasesAgentBuilder().setTriggerCheck(agent -> false).build();
    }

    private static void prepareMoveCases() {
        for (int i = 0; i < moveStyleEnums.length; i++) {
            moveStyleAgents[i] = getMoveCasesAgentBuilder().setMoveStyle(moveStyleEnums[i]).build();
        }
        moveStyleAgents[moveStyleEnums.length] = getMoveCasesAgentBuilder().setMoveStyle(agent -> agent.setPosition(2, 2)).build();
    }

    private static void prepareActionCases() {
        for (int i = 0; i < actionEnums.length; i++) {
            actionAgents[i] = getActionCasesAgentBuilder().setAction(actionEnums[i]).build();
        }
        actionAgents[actionEnums.length] =
                getActionCasesAgentBuilder().setAction(agent -> Generator.getLevel().set(2, 2, agent.getActionTile().tileId)).build();
    }

    private static void prepareBounceCases() {
        for (int i = 0; i < bounceStyleEnums.length; i++) {
            Agent.Builder tmpBuilder = getBounceCasesAgentBuilder().setBounceStyle(bounceStyleEnums[i]);
            if (bounceStyleEnums[i] == BounceStyleEnum.RESPAWN_AT_LOCATION) {
                tmpBuilder.setBounceRespawnLocation(new Point(3, 3));
            }
            bounceStyleAgents[i] = tmpBuilder.build();
        }
        bounceStyleAgents[bounceStyleEnums.length] =
                getBounceCasesAgentBuilder().setBounceStyle(agent -> agent.setPosition(0, agent.getPosition().y)).build();
    }
}