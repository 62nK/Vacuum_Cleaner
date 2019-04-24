// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

public class Location {
    private int coordX, coordY;

    // Constructors
    public Location(int coordX, int coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }
    public Location(int state){
        switch (state){
            case 1:
            case 3:
            case 5:
            case 7:
                coordX = 0;
                coordY = 0;
                break;
            case 2:
            case 4:
            case 6:
            case 8:
                coordX = 1;
                coordY = 0;
                break;
        }
    }
    public Location(){
        randLocation();
    }
    public void randLocation(){
        coordX = (int)(Math.random()* ControlPanel.SIZEX);
        coordY = (int)(Math.random()* ControlPanel.SIZEY);
    }

    // Getters
    public int getCoordX(){
        return coordX;
    }
    public int getCoordY() {
        return coordY;
    }
    public boolean isValid(){
        if(coordX<0 || coordX>= ControlPanel.SIZEX)
            return false;
        if(coordY<0 || coordY>= ControlPanel.SIZEY)
            return false;
        return true;
    }

    // Setters
    public void setCoord(int coordX, int coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }
    public void setCoordX(int coordX){
        this.coordX = coordX;
    }
    public void setCoordY(int coordY){
        this.coordY = coordY;
    }
    public void moveWest(){
        coordX--;
    }
    public void moveEast(){
        coordX++;
    }
    public void moveNorth(){
        coordY--;
    }
    public void moveSouth(){
        coordY++;
    }

    // Condition Check
    public boolean canMoveWest(){
        if (coordX>0){
            return true;    // movement to left is possible
        }
        else
            return false;  // movement to left is impossible
    }
    public boolean canMoveEast(){
        if (coordX< ControlPanel.SIZEX-1){
            return true;    // movement to right is possible
        }
        else
            return false;  // movement to right is impossible
    }
    public boolean canMoveNorth(){
        if (coordY>0){
            return true;    // movement upward is possible
        }
        else
            return false;  // movement upward is impossible
    }
    public boolean canMoveSouth(){
        if (coordY< ControlPanel.SIZEY-1){
            return true;    // movement downward is possible
        }
        else
            return false;  // movement to downward is impossible
    }
    public boolean canMove(int direction){
        if(direction==-1)
            return true;
        switch (direction){
            case ControlPanel.DIRECTION_NORTH:
                return canMoveNorth();
            case ControlPanel.DIRECTION_SOUTH:
                return canMoveSouth();
            case ControlPanel.DIRECTION_EAST:
                return canMoveEast();
            case ControlPanel.DIRECTION_WEST:
                return canMoveWest();
        }
        return false;
    }

    public static double getDistance(Location location1, Location location2){
        return Math.sqrt(Math.pow(location1.coordX-location2.coordX,2)+Math.pow(location1.getCoordY()-location2.getCoordY(),2));
    }
}
