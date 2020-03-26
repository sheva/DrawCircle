package com.essheva;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CircleViaEquation extends Pane {

    private int centerX;
    private int centerY;
    private int radius;
    private int factor;
    private List<int[]> quadrant_I = new ArrayList<>();
    private List<int[]> quadrant_II = new ArrayList<>();
    private List<int[]> quadrant_III = new ArrayList<>();
    private List<int[]> quadrant_IV = new ArrayList<>();

    CircleViaEquation(int x0, int y0, int R, int factor) {
        centerX = x0;
        centerY = y0;
        radius  = R;
        this.factor = factor;

        buildCoordinates();
    }

    private void buildCoordinates() {
        Integer prev = null;
        for (int i = centerX - radius; i <= centerX ; i = i + factor) {
            double j = Math.sqrt(radius * radius - Math.pow(i - centerX, 2)) + centerY;
            int floor = (int)Math.floor(j/factor) * factor;
            if (prev == null || floor != prev) {
                quadrant_III.add(new int[]{i, floor});
                quadrant_I.add(new int[]  {2*centerX - i, 2*centerY - floor});
                quadrant_II.add(new int[] {i, 2*centerY - floor});
                quadrant_IV.add(new int[] {2*centerX - i, floor});
                prev = floor;
            }
        }
    }

    public void drawCircle() {
        buildQuarterCircle(quadrant_I);
        buildQuarterCircle(quadrant_II);
        buildQuarterCircle(quadrant_III);
        buildQuarterCircle(quadrant_IV);
    }

    private void buildQuarterCircle(List<int[]> coords) {
        for (int i = 0; i < coords.size() - 1; i++) {
            Rectangle rectangle = new Rectangle(
                    Math.min(Math.abs(coords.get(i)[0]), Math.abs(coords.get(i+1)[0])),
                    Math.min(Math.abs(coords.get(i)[1]), Math.abs(coords.get(i+1)[1])),
                    Math.abs(coords.get(i+1)[0] - coords.get(i)[0]), Math.abs(coords.get(i+1)[1] - coords.get(i)[1]));
            rectangle.setFill(i % 2 == 0 ? Color.DARKCYAN : Color.CYAN);
            getChildren().add(rectangle);
        }
    }

    public List<int[]> getCoordinates() {
        return Stream.of(quadrant_I, quadrant_II, quadrant_III, quadrant_IV)
                .flatMap(Collection::stream).collect(Collectors.toUnmodifiableList());
    }
}
