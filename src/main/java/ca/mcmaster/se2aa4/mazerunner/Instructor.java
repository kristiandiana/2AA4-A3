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

        maze.setStart(startRow, startCol); // special character updated in maze

        char startDirection;
        if (startWest) startDirection = 'E';
        else startDirection = 'W';

        this.character = new Character(startRow, startCol, startDirection); // MVP always starts on West wall, facing East
        this.canonical = new StringBuilder();

        // since the maze is undirected, the end row can be found by assuming we are starting from the opposite wall
        this.endRow = maze.findStart(!startWest);
        if (startWest) this.endCol = maze.getNumCols() - 1;
        else this.endCol = 0;
    }

    // applyInstruction used to validate inputted path step-by-step
    public void applyInstruction(char instruction){
        this.canonical.append(instruction);
        if (instruction == 'F'){
            character.moveForward();
                        maze.walkOnCell(character.getX(), character.getY());
        }
        else if (instruction == 'R'){
            character.rotateRight();
        }
        else if (instruction == 'L'){
            character.rotateLeft();
        }
        else if (instruction != ' '){
            logger.trace("Instruction, '" + instruction + "', is not valid. Only use F, R, or L.");
        }
    }

    public String getCanonical(){
        return this.canonical.toString();
    }

    public String getFactorized() {
        if (this.canonical.length() == 0) {
            return ""; // If canonical is empty, return an empty string
        }

        StringBuilder factorized = new StringBuilder();
        char currentChar = this.canonical.charAt(0);
        int count = 1;

        for (int i = 1; i < this.canonical.length(); i++) {
            char nextChar = this.canonical.charAt(i);

            if (nextChar == currentChar) {
                count++; // Increment count if the next character is the same
            } else {
                // Append the current character and its count (if count > 1)
                if (count > 1) {
                    factorized.append(count).append(currentChar).append(" ");
                } else {
                    factorized.append(currentChar).append(" ");
                }
                // Reset for the next character
                currentChar = nextChar;
                count = 1;
            }
        }

        // Append the last character and its count
        if (count > 1) {
            factorized.append(count).append(currentChar);
        } else {
            factorized.append(currentChar);
        }

        return factorized.toString().trim(); // Trim any trailing spaces
    }

    // checks if input path, with -p flag, is valid
    public boolean validatePath(String path){
        for (int i = 0; i < path.length(); i++){
            applyInstruction(path.charAt(i));
        }
        // if doesn't end up in the end cell, then return false
        if (character.getX() == this.endRow && character.getY() == this.endCol){
            return true;
        }
        else {
            return false;
        }
    }

    public void exploreMaze() {
        // IMPLEMENTING RIGHT HAND EXPLORATION TECHNIQUE
        int [] coords;
        boolean isWall;

        while (character.getX() != this.endRow || character.getY() != this.endCol){
            coords = character.getRightCoords();
            isWall = maze.isWall(coords[0], coords[1]);
            // 3 CASES  
            if (!isWall){  // NOT A WALL ON THE RIGHT
                // turns right and moves forwards
                character.rotateRight();
                canonical.append('R');
                character.moveForward();
                maze.walkOnCell(character.getX(), character.getY());
                canonical.append('F');
            }
            else {
                coords = character.getForwardCoords();
                isWall = maze.isWall(coords[0], coords[1]);
                if (isWall){ // IS A WALL ON THE RIGHT, AND IN FRONT
                    character.rotateLeft();
                    canonical.append('L');
                }
                else { // IS A WALL ON THE RIGHT, BUT NOT IN FRONT 
                    character.moveForward();
                    canonical.append('F');
                    maze.walkOnCell(character.getX(), character.getY());
                }
            }
        }

    }
}