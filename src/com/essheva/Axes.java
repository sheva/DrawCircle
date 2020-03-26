package com.essheva;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

public class Axes extends Pane {
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    Axes(int width, int height, int xLower, int xUpper, int xTickUnit, int yLower, int yUpper, int yTickUnit) {
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        xAxis = new NumberAxis(xLower, xUpper, xTickUnit);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setPrefWidth(width);
        xAxis.setLayoutY(height/2);

        yAxis = new NumberAxis(yLower, yUpper, yTickUnit);
        yAxis.setSide(Side.LEFT);
        yAxis.setPrefHeight(height);
        yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));

        getChildren().setAll(xAxis, yAxis);
    }

    public NumberAxis getXAxis() {
        return xAxis;
    }

    public NumberAxis getYAxis() {
        return yAxis;
    }
}
