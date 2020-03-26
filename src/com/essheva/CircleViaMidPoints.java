package com.essheva;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class CircleViaMidPoints extends Pane {
    
    private int centerX;
    private int centerY;
    private int radius;
    private Color color;
    private int factor;
    private List<int[]> octant_I = new ArrayList<>(), octant_II = new ArrayList<>(),
            octant_III = new ArrayList<>(), octant_IV = new ArrayList<>(),
            octant_V = new ArrayList<>(), octant_VI = new ArrayList<>(),
            octant_VII = new ArrayList<>(), octant_VIII = new ArrayList<>();

    public CircleViaMidPoints(int centerX, int centerY, int R) {
        this(centerX, centerY, R, 1, Color.GOLD);
    }

    public CircleViaMidPoints(int centerX, int centerY, int R, int factor, Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        radius = R;
        this.factor = factor;
        this.color = color;

        buildCoordinates();
    }

    private void buildCoordinates() {
        secondOctant();
        mirrorToOctants();
    }

    private void mirrorToOctants() {
        for (int[] c : octant_II) {
            octant_I.add(new int[]{centerX + (centerY - c[1]), centerY - (c[0]- centerX)});
            octant_III.add(new int[]{centerX - (c[0] - centerX), c[1]});
            octant_IV.add(new int[]{centerX - (centerY - c[1]), centerY + (centerX - c[0])});
            octant_V.add(new int[]{centerX - (centerY - c[1]), centerY + (c[0] - centerX)});
            octant_VI.add(new int[]{centerX - (c[0] - centerX), centerY + (centerY - c[1])});
            octant_VII.add(new int[]{c[0], centerY + (centerY - c[1])});
            octant_VIII.add(new int[]{centerY + (centerX - c[1]), centerY + (c[0]- centerX)});
        }
    }

    private void secondOctant() {
        int x = centerX, y = centerY - radius;
        octant_II.add(new int[]{x, y});
        int p = (int) (factor * factor + Math.pow(y - centerY + factor/2, 2) - radius * radius);
        while(x - centerX <= centerY - y) {
            if (p > 0) {
                p = p + 2*factor*(x - centerX + y - centerY) + 5*factor*factor;
                y += factor;
            } else {
                p = p + 2*factor*(x-centerX) + 3*factor*factor;
            }
            x += factor;
            octant_II.add(new int[]{x, y});
        }
    }

    public void drawCircle() {
        buildOctant(octant_I,1);
        buildOctant(octant_II, 2);
        buildOctant(octant_III, 3);
        buildOctant(octant_IV, 4);
        buildOctant(octant_V, 5);
        buildOctant(octant_VI, 6);
        buildOctant(octant_VII, 7);
        buildOctant(octant_VIII, 8);
    }

    private void buildOctant(List<int[]> coords, int octant) {
        for (int[] c : coords) {
            int x = 0, y = 0;
            switch (octant) {
                case 1: case 6: x = c[0] - factor; y = c[1] - factor; break;
                case 2: case 5: x = c[0]; y = c[1]; break;
                case 3: case 8: x = c[0]- factor; y = c[1]; break;
                case 4: case 7: x = c[0]; y = c[1]- factor; break;
            }
            Rectangle rectangle = new Rectangle(x, y, factor, factor);
            rectangle.setFill(color);
            getChildren().add(rectangle);
        }
    }
}
