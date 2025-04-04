package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Strategy {
    private Maze maze;
    private Character character;
    private static final Logger logger = LogManager.getLogger(); // logger used to manage levels of output
    private int [] coords;
    private boolean isWall;
    private boolean inIntermediateState = false;

    public Strategy (Maze maze, Character character){
        this.character = character;
        this.maze = maze;
    }

    public char determineInstruction() {
        if (inIntermediateState) {
            inIntermediateState = false;
            return 'F';
        }
        // IMPLEMENTING RIGHT HAND EXPLORATION TECHNIQUE
        coords = character.getRightCoords();
        isWall = maze.isWall(coords[0], coords[1]);
        // 3 CASES  
        if (!isWall){  // NOT A WALL ON THE RIGHT
            // turns right and moves forwards
            // this case requires 2 moves, so we set the intermediate state to true
            inIntermediateState = true; // Set the intermediate state to true
            return 'R'; // Rotate right
        }
        else {
            coords = character.getForwardCoords();
            isWall = maze.isWall(coords[0], coords[1]);

            if (isWall){ // IS A WALL ON THE RIGHT, AND IN FRONT
                return 'L'; // Rotate left
            }
            else { // IS A WALL ON THE RIGHT, BUT NOT IN FRONT 
                return 'F'; // Move forward
            }
        }
    }

}