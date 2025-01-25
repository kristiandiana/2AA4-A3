package ca.mcmaster.se2aa4.mazerunner;

public class Character {

    private int x, y;
    private char direction;

    public Character (int startX, int startY, char startDirection){
        this.x = startX;
        this.y = startY;
        this.direction = startDirection;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public char getDirection(){
        return direction;
    }

    public void rotateRight(){
        if (this.direction == 'E') this.direction = 'S';
        else if (this.direction == 'S') this.direction = 'W';
        else if (this.direction == 'W') this.direction = 'N';
        else this.direction = 'E';
    }
    public void rotateLeft(){
        if (this.direction == 'E') this.direction = 'N';
        else if (this.direction == 'N') this.direction = 'W';
        else if (this.direction == 'W') this.direction = 'S';
        else this.direction = 'E';
    }

    public void moveForward() {
        if (this.direction == 'E') this.y++;
        else if (this.direction == 'W') this.y--;
        else if (this.direction == 'N') this.x--;
        else this.x++;
    }

}