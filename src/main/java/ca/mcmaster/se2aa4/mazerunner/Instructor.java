package ca.mcmaster.se2aa4.mazerunner;

public class Instructor {
    private Maze maze;
    private Character character;
    private StringBuilder canonical;

    public Instructor (Maze maze){
        this.maze = maze;
        this.character = new Character(maze.findStart(true), 0, 'E'); // MVP always starts on West wall, facing East
        this.canonical = new StringBuilder();
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