package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Executor {
    private Maze maze;
    private Character character;
    private static final Logger logger = LogManager.getLogger(); // logger used to manage levels of output

    public Executor (Maze maze, Character character){
        this.character = character;
        this.maze = maze;
    }

    public void executeInstruction (char instruction){
        if (instruction == 'F'){
            executeForward();
        }
        else if (instruction == 'R'){
            executeRight();
        }
        else if (instruction == 'L'){
            executeLeft();
        }
        else if (instruction == ' '){
            logger.trace("Instruction, '" + instruction + "', is not valid. Only use F, R, or L.");
        }
    }

    private void executeForward(){
        character.moveForward();
        maze.walkOnCell(character.getX(), character.getY());
    }

    private void executeRight(){
        character.rotateRight();
    }

    private void executeLeft(){
        character.rotateLeft();
    }
}