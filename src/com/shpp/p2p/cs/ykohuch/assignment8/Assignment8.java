package com.shpp.p2p.cs.ykohuch.assignment8;
import acm.graphics.GOval;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.util.ArrayList;


/**
 *This program visualizes the fall of snowflakes of various shapes and colors.
 Each snowflake has its own falling velocity and its sine wave oscillation amplitude
 */
public class Assignment8 extends WindowProgram {

    /*Width and height of application window in pixels*/
    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 500;

    /*diameter of snowflake*/
    private static final double DIAMETER = 50;

    /**@Warning
     * Number of snowflakes. If this value is changed, the size of the arrays speeds and trajectory must be resized
      */
    private static final double NUMBER_OF_SNOWFLAKES = 20;

    /*an array that stores the random generated numerical velocity values for each snowflake*/
    private double speeds[] = new double[20];

    /*an array that stores the random generated numerical x-axis velocity values for each snowflake*/
    private double trajectory[] = new double[20];

    /*minimum speed*/
    private static final double MIN_SPEED = 1;

    /*maximum speed*/
    private static final double MAX_SPEED = 20;

    /*oscillation amplitude on the x-axis*/
    private static final double AMPLITUDE = 10 + Math.random() * 20;

    /* The amount of time to pause between frames (12fps). */
    private static final double PAUSE_TIME = 1000.0 / 12;

    /*Start x and y axis coordinate*/
    private double x = 250;
    private double y = 250;


    public void run() {

        ArrayList<GOval> snowballs = createSnowballs(basicSnowflake(x, y, DIAMETER));

        while (true) {
            makeSnowfall(snowballs);
            pause(PAUSE_TIME);
        }
    }

    /*here creates Array List which contains the required number of ovals given by a constant*/
    private ArrayList<GOval> createSnowballs(GOval basicSnowflake) {
        ArrayList<GOval> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SNOWFLAKES; i++) {
            result.add(basicSnowflake(x, y, DIAMETER));
        }
        return result;
    }

    /*here creates a basic snowflake with a random color and size*/
    private GOval basicSnowflake(double x, double y, double diameter) {
        RandomGenerator rgen = RandomGenerator.getInstance();
        GOval circle = new GOval(Math.random() * getWidth(), APPLICATION_HEIGHT,
                rgen.nextDouble(diameter, diameter / 2), rgen.nextDouble(diameter, diameter / 2));
        circle.setFilled(true);
        circle.setFillColor(rgen.nextColor());
        add(circle);
        return circle;
    }

    /*the cycle generates randomly generated numerical values for the speed
     of incidence and the amplitude of the snowflake.
     These values are stored in separate arrays*/
    private void physicsOfSnowflakesMotion() {
        for (int i = 0; i < NUMBER_OF_SNOWFLAKES; i++) {
            speeds[i] = (MIN_SPEED + Math.random() * MAX_SPEED);

            /*in addition to the formation of a sinusoidal trajectory of fall,
             this formula allows realizing the effect of gusts of wind that blows the snowflakes left or right.
             For each snowflake the direction of movement is unique*/
            trajectory[i] = AMPLITUDE * Math.sin(Math.toDegrees(x));

                /*x and y axes of motion for each snowflake are assigned
                the corresponding iteration from the arrays above*/
            y = speeds[i];
            x = trajectory[i];
        }
    }

    /*a method in which snowflakes fall down
    * a loop passes through each element of the array list and make them move*/
    private void makeSnowfall(ArrayList<GOval> snowballs) {
        for (GOval snowflake : snowballs) {
            physicsOfSnowflakesMotion();
            snowflake.move(x, y);
            /*if the snowflake falls below the height of the screen,
             it is assigned a new location and changes color and size*/
            if (snowflake.getY() > getHeight()) {
                RandomGenerator rgen = RandomGenerator.getInstance();
                snowflake.setLocation(Math.random() * getWidth(), y);
                snowflake.setFillColor(rgen.nextColor());
                snowflake.setSize( rgen.nextDouble(DIAMETER, DIAMETER / 2),
                        rgen.nextDouble(DIAMETER, DIAMETER / 2));
            }
        }
    }
}
