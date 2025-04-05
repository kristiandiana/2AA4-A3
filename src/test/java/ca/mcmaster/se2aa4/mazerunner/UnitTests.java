package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    // TEST CASES FOR THE PLAYER CLASS
    @Test
    public void testMoveForwardFacingNorth() {
        Player player = new Player(2, 3, 'N');
        player.moveForward();
        assertEquals(1, player.getX());
        assertEquals(3, player.getY());
    }

    @Test
    public void testRotateRightFromEast() {
        Player player = new Player(0, 0, 'E');
        player.rotateRight();
        assertEquals('S', player.getDirection());
    }

    @Test
    public void testRotateLeftFromEast() {
        Player player = new Player(0, 0, 'E');
        player.rotateLeft();
        assertEquals('N', player.getDirection());
    }

    // TEST CASES FOR EXECUTOR CLASS (MAKES CALLS TO PLAYER CLASS)
    @Test
    public void testExecuteRightRotatesPlayer() {
        Maze maze = new Maze("./examples/small.maz.txt");
        Player player = new Player(0, 0, 'E');
        Executor executor = new Executor(maze, player);

        executor.executeInstruction('R');
        assertEquals('S', player.getDirection());
    }

    // TEST CASES FOR STRATEGY CLASS (MAKES CALLS TO PLAYER AND MAZE CLASS)
    @Test
    public void testRightTurnWithIntermediateForward() {
        Maze maze = new Maze("./examples/small.maz.txt"); // No wall on right → 'R' then 'F'
        Player player = new Player(7, 1, 'E');
        Strategy strategy = new Strategy(maze, player);

        assertEquals('R', strategy.determineInstruction()); // First call
        assertEquals('F', strategy.determineInstruction()); // Second call (intermediate state)
    }

    @Test
    public void testWallRightWallFrontReturnsL() {
        Maze maze = new Maze("./examples/small.maz.txt"); // Wall right, wall front
        Player player = new Player(1, 9, 'E');
        Strategy strategy = new Strategy(maze, player);

        assertEquals('L', strategy.determineInstruction());
    }

    @Test
    public void testWallRightNoWallFrontReturnsF() {
        Maze maze = new Maze("./examples/small.maz.txt"); // Wall right, no wall front
        Player player = new Player(1, 8, 'E');
        Strategy strategy = new Strategy(maze, player);

        assertEquals('F', strategy.determineInstruction());
    }

    // TEST CASES FOR THE MAZE CLASS
    @Test
    public void testFindStartFromWestWall(){

        Maze maze = new Maze("./examples/small.maz.txt");

        int startRow = maze.findStart(true); // west wall
        assertEquals(8, startRow);
    }

    @Test
    public void testFindStartFromEastWall() {
        Maze maze = new Maze("./examples/small.maz.txt");

        int startRow = maze.findStart(false); // east wall
        assertEquals(5, startRow);
    }

    // TEST CASES FOR THE INSTRUCTOR CLASS
    @Test
    public void testFactorizedToCanonicalComplexPattern() {
        Maze maze = new Maze("./examples/small.maz.txt");

        Instructor instructor = new Instructor(maze, true);
        String result = instructor.factorizedToCanonical("10F3R2L5F1R");
        assertEquals("FFFFFFFFFFRRRLLFFFFFR", result);
    }

}
