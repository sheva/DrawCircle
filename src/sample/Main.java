package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Axes axes = new Axes(800, 600,
                -40, 40, 1,
                -30, 30, 1
        );
        CircleSimple circleSimple = new CircleSimple(450, 350, 200);
        CircleCustom circleCustom = new CircleCustom(450, 350, 200, 10);
        circleCustom.drawCircle();

        StackPane root = new StackPane();
        root.getChildren().addAll(axes, circleSimple, circleCustom);
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
    }

    static class Axes extends Pane {
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        Axes(int width, int height, int xLower, int xUpper, int xTickUnit, int yLower, int yUpper, int yTickUnit) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLower, xUpper, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLower, yUpper, yTickUnit);
            yAxis.setSide(Side.LEFT);;
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1, yAxis.widthProperty()));

            getChildren().setAll(xAxis, yAxis);
        }
    }

    static class CircleSimple extends Pane {
        CircleSimple(int x0, int y0, int R) {
            RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.TRANSPARENT),
                    new Stop(1, Color.GREY));
            Circle circle = new Circle(x0, y0, R, gradient);
            getChildren().add(circle);
        }
    }
    static class CircleCustom extends Pane {
        private int centerX;
        private int centerY;
        private int radius;
        private int factor;
        private List<int[]> square_III = new ArrayList<>();
        private List<int[]> square_I = new ArrayList<>();
        private List<int[]> square_II = new ArrayList<>();
        private List<int[]> square_IV = new ArrayList<>();

        CircleCustom(int x0, int y0, int R, int factor) {
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
                    square_III.add(new int[]{i, floor});
                    square_I.add(new int[]  {2*centerX - i, 2*centerY - floor});
                    square_II.add(new int[] {i, 2*centerY - floor});
                    square_IV.add(new int[] {2*centerX - i, floor});
                    prev = floor;
                }
            }
        }

        private void drawCircle() {
            buildQuarterCircle(square_I);
            buildQuarterCircle(square_II);
            buildQuarterCircle(square_III);
            buildQuarterCircle(square_IV);
        }

        private void buildQuarterCircle(List<int[]> square) {
            for (int i = 0; i < square.size() - 1; i++) {
                Rectangle rectangle = new Rectangle(
                        Math.min(Math.abs(square.get(i)[0]), Math.abs(square.get(i+1)[0])),
                        Math.min(Math.abs(square.get(i)[1]), Math.abs(square.get(i+1)[1])),
                        Math.abs(square.get(i+1)[0] - square.get(i)[0]), Math.abs(square.get(i+1)[1] - square.get(i)[1]));
                rectangle.setFill(i % 2 == 0 ? Color.DARKCYAN : Color.CYAN);
                getChildren().add(rectangle);
            }
        }

        public List<int[]> getCoordinates() {
            return Stream.of(square_I, square_II, square_III, square_IV)
                    .flatMap(Collection::stream).collect(Collectors.toUnmodifiableList());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
