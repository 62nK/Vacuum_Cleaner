// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.Image ;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Environment extends Pane {

    // 0: clean, 1: dirt, 2: obstacle

    // Parameters
    private int sizeX, sizeY;
    private int[][] environment;    // 2D array

    // JavaFX Objects
    ArrayList<Rectangle> tiles;
    ArrayList<ImageView> tilesCondition;
    Image dirt;

    // Constructors
    public Environment(int sizeX, int sizeY, int state){
        this.sizeX = sizeX; // Set width of environment
        this.sizeY = sizeY; // Set height of environment
        environment = new int[sizeY][sizeX];    // Define environment
        Location location;
        switch (state){
            case 1:
            case 2:
                location = new Location(0,0);
                setDirt(location);
                location = new Location(1,0);
                setDirt(location);
                break;
            case 3:
            case 4:
                location = new Location(0,0);
                setDirt(location);
                location = new Location(1,0);
                clean(location);
                break;
            case 5:
            case 6:
                location = new Location(1,0);
                clean(location);
                location = new Location(0,0);
                setDirt(location);
                break;
            case 7:
            case 8:
                location = new Location(1,0);
                clean(location);
                location = new Location(0,0);
                clean(location);
                break;
        }

        spawnTiles();
        spawnDirt();

    } // Create environment
    public Environment (int sizeX, int sizeY){
        this.sizeX = sizeX; // Set width of environment
        this.sizeY = sizeY; // Set height of environment
        environment = new int[sizeY][sizeX];    // Define environment
        setRandEnvironment();   // spread dirt randomly

        // JavaFX
        spawnTiles();
//        spawnDirt();

    }
    // create random environment
    public void setRandEnvironment() {
        double dirtProbability = (double) ControlPanel.CORES/sizeX/sizeY;
        for (int i=0; i<sizeY; i++){
            for (int j=0; j<sizeX; j++){
                if(Math.random()<dirtProbability) {
                    setDirt(new Location(i, j));
                    // And spread out the dirt
                    for (int sub_i = i- ControlPanel.SPREAD_RADIUS; sub_i < i+ ControlPanel.SPREAD_RADIUS; sub_i++)
                        for (int sub_j = j- ControlPanel.SPREAD_RADIUS; sub_j < j+ ControlPanel.SPREAD_RADIUS; sub_j++)
                            if (new Location(sub_i,sub_j).isValid())
                                setDirt(new Location(sub_i, sub_j));
                }
            }
        }
    }

    // Modifier of the environment
    public void setDirt(Location location){
        environment[location.getCoordY()][location.getCoordX()] = ControlPanel.DIRT;
    }
    public void clean(Location location){
        environment[location.getCoordY()][location.getCoordX()] = ControlPanel.CLEAN;
    }
    public void setObstacle(Location location){
        environment[location.getCoordY()][location.getCoordX()] = ControlPanel.OBSTACLE;
    }

    // returns the state of the position (X,Y): CLEAN/DIRT/OBSTACLE
    public int getState(Location location){
        return environment[location.getCoordY()][location.getCoordX()];
    }
    public int[][] getEntireEnvironment(){
        return environment;
    }

    public void update(){
        getChildren().removeAll();
        spawnTiles();
        spawnDirt();
    }
    private void spawnTiles(){
        tiles = new ArrayList<>();
        for(int i=0; i<sizeY; i++)
            for(int j=0; j<sizeX; j++){
                tiles.add(new Rectangle(j* ControlPanel.WIDTH/sizeX,i* ControlPanel.HEIGHT/sizeY, ControlPanel.WIDTH/sizeX, ControlPanel.HEIGHT/sizeY));
                tiles.get(i*sizeY+j).setStroke(Color.LIGHTSKYBLUE);
                tiles.get(i*sizeY+j).setFill(Color.WHITE);
                if((i+j)%2==0)
                    tiles.get(i*sizeY+j).setFill(Color.LIGHTSKYBLUE);
            }
        getChildren().addAll(tiles);
    }
    private void spawnDirt(){
        tilesCondition = new ArrayList<>();
        try{dirt = new Image(new FileInputStream("src/images/dirt.png"));}
        catch (FileNotFoundException e){}
        for(int i=0; i<sizeY; i++)
            for(int j=0; j<sizeX; j++){
                if(environment[i][j]== ControlPanel.DIRT) {
                    ImageView dirtyTile = new ImageView(dirt);
                    dirtyTile.setPreserveRatio(true);
                    dirtyTile.setSmooth(true);
                    dirtyTile.setCache(true);
                    dirtyTile.setFitWidth(ControlPanel.WIDTH/ ControlPanel.SIZEX);
                    dirtyTile.setX((j) * ControlPanel.WIDTH / ControlPanel.SIZEX);
                    dirtyTile.setY((i) * ControlPanel.HEIGHT / ControlPanel.SIZEY);
                    tilesCondition.add(dirtyTile);
                }
            }
        getChildren().addAll(tilesCondition);
    }
}
