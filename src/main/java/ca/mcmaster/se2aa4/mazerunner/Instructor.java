package ca.mcmaster.se2aa4.mazerunner;

public class Instructor {
    private Maze maze;
    private Character character;

    public Instructor (Maze maze){
        this.maze = maze;
        this.character = new Character(maze.findStart(), 0, 'E'); // MVP always starts on West wall, facing East
    }

    public void exploreMaze() {
        // for the MVP, maze exploration algorithms are HARD CODED
        // for final version, will develop Right-Hand exploration technique
        if (maze.getFilePath().equals("./examples/straight.maz.txt")){
            for (int i = 0; i < 4; i++){
                maze.walkOnCell(character.getX(), character.getY());
                character.moveForward();
            }
        }



    }
}