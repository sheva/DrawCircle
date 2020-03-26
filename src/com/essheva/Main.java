package com.essheva;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Axes axes = new Axes(800, 600,
                -40, 40, 1,
                -30, 30, 1
        );

        int x0 = 450, y0 = 350, R = 250;
        CircleViaLibrary circleSimple = new CircleViaLibrary(x0, y0, R);
        CircleViaEquation circleViaEquation = new CircleViaEquation(x0, y0, R, 10);
        circleViaEquation.drawCircle();
        CircleViaMidPoints circleViaMidPoints = new CircleViaMidPoints(x0, y0, R, 3, Color.GOLD);
        circleViaMidPoints.drawCircle();

        StackPane root = new StackPane();
        root.getChildren().addAll(axes, circleSimple, circleViaEquation, circleViaMidPoints);
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
