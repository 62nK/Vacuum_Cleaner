// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestVacuumCleaner extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Objects
        Environment environment;
        VacuumCleaner vacuumCleaner;
        StackPane stackPane;

        if (ControlPanel.INITIAL_STATE !=-1){ // Initialize the system to one of the 8 initial states
            environment = new Environment(ControlPanel.SIZEX, ControlPanel.SIZEY, ControlPanel.INITIAL_STATE);
            vacuumCleaner = new VacuumCleaner(environment, ControlPanel.INITIAL_STATE);
        }
        else { // Initialize the system to a random state
            environment = new Environment(ControlPanel.SIZEX, ControlPanel.SIZEY);
            vacuumCleaner = new VacuumCleaner(environment);
        }

        // Create a handler for animation
        EventHandler<ActionEvent> eventHandler = e -> {
            // #perturbance dirties the environment
            if((int)(Math.random()*101)< ControlPanel.PERTURBANCE_PROBABILITY){
                System.out.println("######################### Watch out! sandstorm #########################");
                environment.setRandEnvironment();
            }

            vacuumCleaner.action();    // Percept sequence
            environment.update();
        };

        // Create an animation for a running clock
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(ControlPanel.TIME_SPAN), eventHandler));
        animation.setCycleCount(ControlPanel.STEPS);
        animation.play(); // Start animation


        // Put the pane on the scene and the scene on the stage
        stackPane = new StackPane();
        stackPane.getChildren().addAll(environment, vacuumCleaner, vacuumCleaner.getLcd());
        Scene scene = new Scene(stackPane, ControlPanel.WIDTH, ControlPanel.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vacuum World by Andrea Pinardi");
        primaryStage.show();
    }
}
