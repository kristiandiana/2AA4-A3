package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {

    private char [][] grid;
    private static final Logger logger = LogManager.getLogger();

    public Maze(String mazeFilePath) {
        readMaze(mazeFilePath);
    }

    private void readMaze(String mazeFilePath) {
        try {
            
            // first we need to calculate the number of rows and columns
            BufferedReader reader = new BufferedReader(new FileReader(mazeFilePath));
            String line;
            int rows = 0;
            int cols = 0;

            while ((line = reader.readLine()) != null) {
                rows++;
                cols = line.length();
            }

            grid = new char[rows][cols]; // initialize grid with appropriate sizes

            // create a new reader to now enter values into the allocated memory in grid
            reader.close();
            BufferedReader secondReader = new BufferedReader(new FileReader(mazeFilePath));

            for (int i = 0; i < rows; i++){
                line = secondReader.readLine();
                for (int j = 0; j < cols; j++){
                    // fill cell with character from file
                    if (line.charAt(j) == '#') {
                        grid[i][j] = '#';
                    }
                    else {
                        grid[i][j] = '0';
                    }
                }
            }
            logger.info("Finished reading maze from " + mazeFilePath);
        } 
        catch (Exception e) {
            logger.error("Error reading maze file: " + e.getMessage());
        }
    }

    public int getNumCols(){
        return this.grid[0].length;
    }

    public void displayMaze(){
        char cell;
        logger.info("Displaying maze");
        System.out.println("Current maze state:");
        StringBuilder line;
        for (int row = 0; row < grid.length; row++){
            line = new StringBuilder(); // used to build up the output for each row
            for (int col = 0; col < grid[0].length; col++){
                cell = grid[row][col];
                if (cell == '#'){
                    line.append("WALL ");
                }
                else if (cell == '&'){
                    line.append(" @@  ");
                }
                else if (cell == '0'){
                    line.append("     "); // prints the cell element since I want to update this for final version
                }
                else {
                    line.append(cell + "    ");
                }
            }
            System.out.println(line.toString()); // print the ENTIRE row
        }
    }

    public void setStart(int x, int y){ // sets the start cell to a special character
        this.grid[x][y] = '&';
    }

    public boolean isWall(int x, int y){ // boolean determines if a cell is a wall
        return this.grid[x][y] == '#';
    }

    public void walkOnCell(int x, int y){ // updates the counter of each cell based on number of times it was "walked on"
        int cell = (int)(grid[x][y])+1;
        grid[x][y] = (char)(cell);
    }

    public int findStart(boolean west) { // finds western wall starting row 
        // NOTE: this will be refactored in final version to account for East wall start                            
        int col;
        if (west) col = 0;
        else col = grid[0].length - 1;
        for (int row = 0; row < grid.length; row++){
            if (grid[row][col] == '0'){
                return row;
            }
        }
        return 0;
    }

}