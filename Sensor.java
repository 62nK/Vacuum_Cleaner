// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

public class Sensor {

    // Parameters
    private Environment environment;

    // Constructors
    public Sensor (Environment environment){
        this.environment = environment;
    }

    // Returns the states of the current location: CLEAN/DIRT/OBSTACLE
    public int see(Location location){
        if (environment.getState(location)== ControlPanel.CLEAN)
            return ControlPanel.CLEAN;
        else if (environment.getState(location)== ControlPanel.DIRT)
            return ControlPanel.DIRT;
        return ControlPanel.OBSTACLE;
    }
    public int[][] seeArea(){
        return environment.getEntireEnvironment();
    }
}
