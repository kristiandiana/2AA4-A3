package ca.mcmaster.se2aa4.mazerunner;

public class Instructor {
    private Maze maze;
    private Character character;

    public Instructor (Maze maze){
        this.maze = maze;
        this.character = new Character(maze.findStart(), 0, 'E'); // MVP always starts on West wall, facing East
    }


    public void exploreMaze() {
        // implement search algorithm here
    }
}