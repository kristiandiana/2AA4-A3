package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Instructor {
    
    private Maze maze;
    private Player player;
    private Strategy strategy;
    private Executor executor;

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

        maze.setStart(startRow, startCol); // special player updated in maze

        char startDirection;
        if (startWest) startDirection = 'E';
        else startDirection = 'W';

        this.player = new Player(startRow, startCol, startDirection); // MVP always starts on West wall, facing East
        this.canonical = new StringBuilder();

        // since the maze is undirected, the end row can be found by assuming we are starting from the opposite wall
        this.endRow = maze.findStart(!startWest);
        if (startWest) this.endCol = maze.getNumCols() - 1;
        else this.endCol = 0;

        this.strategy = new Strategy(maze, this.player);
        this.executor = new Executor(maze, this.player);

    }

    // applyInstruction used to validate inputted path step-by-step
    public void applyInstruction(char instruction){
        this.canonical.append(instruction);
        this.executor.executeInstruction(instruction);
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
                count++; // Increment count if the next player is the same
            } else {
                // Append the current player and its count (if count > 1)
                if (count > 1) {
                    factorized.append(count).append(currentChar);
                } else {
                    factorized.append(currentChar);
                }
                // Reset for the next player
                currentChar = nextChar;
                count = 1;
            }
        }

        // Append the last player and its count
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
        if (player.getX() == this.endRow && player.getY() == this.endCol){
            return true;
        }
        else {
            return false;
        }
    }

    public void exploreMaze() {
        // IMPLEMENTING RIGHT HAND EXPLORATION TECHNIQUE
        while (player.getX() != this.endRow || player.getY() != this.endCol){ // continue until player reaches end cell

            char instruction = this.strategy.determineInstruction(); // get the next instruction to execute
            applyInstruction(instruction); // apply the instruction to the player

        }

    }

    public String factorizedToCanonical(String input) {
        StringBuilder result = new StringBuilder();
        StringBuilder currentFactor = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                currentFactor.append(c);  // build up the number (handles multi-digit)
            } else if (c == 'F' || c == 'R' || c == 'L') {
                int repeat = 1;  // default
                if (currentFactor.length() > 0) {
                    repeat = Integer.parseInt(currentFactor.toString());
                    currentFactor.setLength(0);  // clear buffer
                }
                for (int i = 0; i < repeat; i++) {
                    result.append(c);
                }
            } else {
                throw new IllegalArgumentException("Invalid player in input: " + c);
            }
        }

        return result.toString();
    }

}