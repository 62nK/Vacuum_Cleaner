// Course: CS4242
// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LCD extends Pane {

    // JavaFX objects
    private Rectangle display;
    private Text textBox;

    // Parameters
    private String message;
    private double performance;
    private double dirtRatio;

    // Constructors
    public LCD(){
        message = "no messages to display";
        spawnLCD();
    }

    // Setters
    public void setPerformance(double performance){
        this.performance = performance;

    }
    public void setDirtRatio(double dirtRatio){
        this.dirtRatio = dirtRatio;
    }
    public void updateMessage(){
        String newMessage = "Performance: "+String.format("%.1f", performance*100)+"%, Dirt%: "+String.format("%.1f", dirtRatio*100)+"%";
        textBox.setText(newMessage);
    }
    public void signalCompletion(){
        display.setFill(Color.RED);
    }

    // Service Methods
    private void spawnLCD(){
        display = new Rectangle(8,2, 168, 17.5);
        display.setFill(Color.GREEN);
        display.setStroke(Color.BLACK);
        textBox = new Text(12, 13, message);
        textBox.setStroke(Color.WHITE);
        textBox.setStrokeWidth(1);
        textBox.setFill(Color.WHITE);
        textBox.setFont(Font.font("Monaco", 8));
        updateMessage();
        getChildren().addAll(display, textBox);
    }
}
