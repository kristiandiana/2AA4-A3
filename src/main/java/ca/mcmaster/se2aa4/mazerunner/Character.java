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

    public void moveForward() {
        this.y++;
    }

}