// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

public class Actuator {
    // Parameters
    private Environment environment;

    // Constructors
    public Actuator(Environment environment) {
        this.environment = environment;
    }

    // Returns the true if the current location is cleaned successfully
    public void clean(Location location) {
        environment.clean(location);
    }

}
