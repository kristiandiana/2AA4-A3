package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Executor {
    private Maze maze;
    private Player player;
    private static final Logger logger = LogManager.getLogger(); // logger used to manage levels of output

    public Executor (Maze maze, Player player){
        this.player = player;
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
        player.moveForward();
        maze.walkOnCell(player.getX(), player.getY());
    }

    private void executeRight(){
        player.rotateRight();
    }

    private void executeLeft(){
        player.rotateLeft();
    }
}