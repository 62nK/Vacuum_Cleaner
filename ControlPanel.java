// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/20/18
// Signature: Andrea Pinardi
// Score:

public interface ControlPanel {

    // Vacuum Cleaner
    /* AI algorithms:
       -1 = Random,
        0 = Recursive Proximity,
        1 = Recursive Proximity with Queue,
        2 = Recursive Weighted Proximity,
        3 = Minimum Distance
    */
    public static final int AI = 3;
    public static final int INITIAL_STATE = -1;  // 1 through 8, -1 to randomize
    public static final int STEPS = 100;   // Number of steps of the Vacuum Cleaner

    public static final double ORIENTATION_NORTH = 180.0;
    public static final double ORIENTATION_SOUTH = 0;
    public static final double ORIENTATION_WEST = 90.0;
    public static final double ORIENTATION_EAST = 270.0;

    public static final int DIRECTION_NORTH = 0;
    public static final int DIRECTION_SOUTH = 1;
    public static final int DIRECTION_WEST = 2;
    public static final int DIRECTION_EAST = 3;

    // Environment
    public static final int SIZEX = 10;  // Width
    public static final int SIZEY = 10;  // Height
    public static final int PERTURBANCE_PROBABILITY = 0;    // Probability to have random perturbation at any given step

    // JavaFX
    public static final int WIDTH = 700;
    public static final int HEIGHT =700;
    public static final double TIME_SPAN = 1000.0; // Milliseconds

    // Definitions
    public static final int CLEAN = 0;
    public static final int DIRT = 1;
    public static final int OBSTACLE = 2;

    // Dirt properties
    public static final int CORES = 100;
    public static final int SPREAD_RADIUS = 0;
}
