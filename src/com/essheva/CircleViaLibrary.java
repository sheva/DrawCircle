package com.essheva;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class CircleViaLibrary extends Pane {
    CircleViaLibrary(int x0, int y0, int R) {
        RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(1, Color.GREY));
        Circle circle = new Circle(x0, y0, R, gradient);
        getChildren().add(circle);
    }
}
