// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class VacuumCleaner extends Pane {

    // Parameters
    private Sensor sensor;
    private Actuator actuator;
    private Location currentPosition;
    private LCD lcd;

    // State variables
    private double orientation;
    private ArrayList<Integer> nextDirection_q;

    // Performance measurement
    private double successCount;
    private double movesCount;
    private boolean complete;

    // JavaFX
    private ImageView imageViewVacuumCleaner;
    private Image imageVacuumCleaner;

    // Constructors
    public VacuumCleaner(Environment environment, int state){
        sensor = new Sensor(environment);
        actuator = new Actuator(environment);
        currentPosition = new Location(state);
        successCount = 0;

        spawnVacuumCleaner();
    }
    public VacuumCleaner(Environment environment){
        sensor = new Sensor(environment);
        actuator = new Actuator(environment);
        currentPosition = new Location();
        nextDirection_q = new ArrayList<>();
        lcd = new LCD();
        calculateDirtRatio();
        spawnVacuumCleaner();
    }

    // Actions
    public void action(){
        calculateDirtRatio();
        if(!complete) {
            movesCount++;
            if (see() == ControlPanel.DIRT) {
                suck();
                successCount++;
            }
            lcd.setDirtRatio(calculateDirtRatio());
            lcd.setPerformance(successCount / movesCount);
            lcd.updateMessage();
            switch (AI.next(sensor.seeArea(), currentPosition, nextDirection_q, orientation)) {
                case ControlPanel.DIRECTION_NORTH:
                    moveNorth();
                    break;
                case ControlPanel.DIRECTION_SOUTH:
                    moveSouth();
                    break;
                case ControlPanel.DIRECTION_WEST:
                    moveWest();
                    break;
                case ControlPanel.DIRECTION_EAST:
                    moveEast();
                    break;
                default:
                    System.out.println("Action Error!");
            }
        }
        else {

        }
    }

    private int see(){
        return sensor.see(currentPosition);
    }
    private void suck(){
        actuator.clean(currentPosition);
    }

    // Movement
    private void moveEast(){
        currentPosition.moveEast();
        imageViewVacuumCleaner.setRotate(ControlPanel.ORIENTATION_EAST);
        imageViewVacuumCleaner.setX(currentPosition.getCoordX() * ControlPanel.WIDTH / ControlPanel.SIZEX);
    }
    private void moveWest(){
        currentPosition.moveWest();
        imageViewVacuumCleaner.setRotate(ControlPanel.ORIENTATION_WEST);
        imageViewVacuumCleaner.setX(currentPosition.getCoordX() * ControlPanel.WIDTH / ControlPanel.SIZEX);
    }
    private void moveNorth(){
        currentPosition.moveNorth();
        imageViewVacuumCleaner.setRotate(ControlPanel.ORIENTATION_NORTH);
        imageViewVacuumCleaner.setY(currentPosition.getCoordY() * ControlPanel.HEIGHT / ControlPanel.SIZEY);
    }
    private void moveSouth(){
        currentPosition.moveSouth();
        imageViewVacuumCleaner.setRotate(ControlPanel.ORIENTATION_SOUTH);
        imageViewVacuumCleaner.setY(currentPosition.getCoordY() * ControlPanel.HEIGHT / ControlPanel.SIZEY);
    }

    // Service Methods
    private void spawnVacuumCleaner(){
        try{
            imageVacuumCleaner = new Image(new FileInputStream("src/images/roomba.png"));}
        catch (FileNotFoundException e){}
        imageViewVacuumCleaner = new ImageView(imageVacuumCleaner);
        imageViewVacuumCleaner.setFitWidth(ControlPanel.WIDTH/ ControlPanel.SIZEX);
        imageViewVacuumCleaner.setX(currentPosition.getCoordX()* ControlPanel.WIDTH/ ControlPanel.SIZEX);
        imageViewVacuumCleaner.setY(currentPosition.getCoordY()* ControlPanel.HEIGHT/ ControlPanel.SIZEY);
        imageViewVacuumCleaner.setRotate(orientation);
        imageViewVacuumCleaner.setPreserveRatio(true);
        imageViewVacuumCleaner.setSmooth(true);
        imageViewVacuumCleaner.setCache(true);
        getChildren().add(imageViewVacuumCleaner);
    }
    public LCD getLcd(){
        return lcd;
    }
    private double calculateDirtRatio(){
        int count = 0;
        for(int i = 0; i< ControlPanel.SIZEY; i++)
            for (int j = 0; j< ControlPanel.SIZEX; j++)
                if(sensor.seeArea()[i][j]== ControlPanel.DIRT)
                    count++;
        if (count==0) {
            complete = true;
            lcd.signalCompletion();
        }
        else
            complete = false;
        return (double)count/ ControlPanel.SIZEX/ ControlPanel.SIZEY;
    }
}
