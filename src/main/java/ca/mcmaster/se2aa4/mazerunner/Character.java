package ca.mcmaster.se2aa4.mazerunner;

public class Character {

    private int x, y;
    private char direction;

    public Character (int startX, int startY, char startDirection){
        this.x = startX;
        this.y = startY;
        this.direction = startDirection;
    }

    // depending on currently facing direction, determine the coordinates of the cell to the right of the character
    public int [] getRightCoords() {
        int [] coords = new int [2];
        if (this.direction == 'E'){
            coords[0] = this.x+1;
            coords[1] = this.y;
        }
        else if (this.direction == 'S'){
            coords[0] = this.x;
            coords[1] = this.y-1;
        }
        else if (this.direction == 'W') {
            coords[0] = this.x-1;
            coords[1] = this.y;
        }
        else {
            coords[0] = this.x;
            coords[1] = this.y+1;
        }
        return coords;
    }

    // depending on the currently facing direction, determine the coords of the cell directly in front of the character
    public int [] getForwardCoords() {
        int [] coords = new int [2];
        if (this.direction == 'E'){
            coords[0] = this.x;
            coords[1] = this.y+1;
        }
        else if (this.direction == 'S'){
            coords[0] = this.x+1;
            coords[1] = this.y;
        }
        else if (this.direction == 'W') {
            coords[0] = this.x;
            coords[1] = this.y-1;
        }
        else {
            coords[0] = this.x-1;
            coords[1] = this.y;
        }
        return coords;
    }


    // *** GETTERS & SETTERS ***

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public char getDirection(){
        return direction;
    }

    public void setDirection(char newDirection){
        this.direction = newDirection;
    }

    
    // *** MOVEMENT METHODS ***

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