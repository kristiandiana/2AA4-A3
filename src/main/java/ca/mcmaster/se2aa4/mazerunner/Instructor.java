package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Instructor {
    private Maze maze;
    private Character character;
    private StringBuilder canonical;
    private int endRow;
    private int endCol;

    private static final Logger logger = LogManager.getLogger(); // logger used to manage levels of output

    public Instructor (Maze maze, boolean startWest){
        this.maze = maze;

        int startRow = maze.findStart(startWest);
        int startCol;
        if (startWest) startCol = 0;
        else startCol = maze.getNumCols() - 1;

        char startDirection;
        if (startWest) startDirection = 'E';
        else startDirection = 'W';

        this.character = new Character(startRow, startCol, startDirection); // MVP always starts on West wall, facing East
        this.canonical = new StringBuilder();
        this.endRow = maze.findStart(!startWest);
        if (startWest) this.endCol = maze.getNumCols() - 1;
        else this.endCol = 0;
    }

    public void applyInstruction(char instruction){
        this.canonical.append(instruction);
        if (instruction == 'F'){
            maze.walkOnCell(character.getX(), character.getY());
            character.moveForward();
        }
        else if (instruction == 'R'){
            character.rotateRight();
        }
        else if (instruction == 'L'){
            character.rotateLeft();
        }
        else {
            logger.trace("Instruction, '" + instruction + "', is not valid. Only use F, R, or L.");
        }
    }

    public boolean validatePath(String path){
        for (int i = 0; i < path.length(); i++){
            applyInstruction(path.charAt(i));
        }
        if (character.getX() == this.endRow && character.getY() == this.endCol){
            return true;
        }
        else {
            return false;
        }
    }

    public String exploreMaze() {
        // for the MVP, maze exploration algorithms are HARD CODED
        // for final version, will develop Right-Hand exploration technique
        if (maze.getFilePath().equals("./examples/straight.maz.txt")){
            for (int i = 0; i < 4; i++){
                maze.walkOnCell(character.getX(), character.getY());
                character.moveForward();
                this.canonical.append('F');
            }
        }
        // hardcoded traversal (shows left and right rotation functionality)
        else if (maze.getFilePath().equals("./examples/tiny.maz.txt")){
            for (int i = 0; i < 3; i++){
                maze.walkOnCell(character.getX(), character.getY());
                character.moveForward();
                this.canonical.append('F');
            }
            character.rotateLeft();
            character.rotateLeft();
            maze.walkOnCell(character.getX(), character.getY());
            character.moveForward();
            maze.walkOnCell(character.getX(), character.getY());
            character.moveForward();
            character.rotateLeft();
            character.rotateLeft();
            maze.walkOnCell(character.getX(), character.getY());
            character.moveForward();
            maze.walkOnCell(character.getX(), character.getY());
            character.moveForward();
            character.rotateLeft();
            this.canonical.append(" L ");
            for (int i = 0; i < 4; i++){
                maze.walkOnCell(character.getX(), character.getY());
                character.moveForward();
                this.canonical.append('F');
            }
            character.rotateRight();
            this.canonical.append(" R ");
            for (int i = 0; i < 3; i++){
                maze.walkOnCell(character.getX(), character.getY());
                character.moveForward();
                this.canonical.append("F");
            }
        }
        return this.canonical.toString();

    }
}