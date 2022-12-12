package com.nighthawk.spring_portfolio.mvc.lightboard;

import lombok.Data;

import java.util.Random;
import java.util.Scanner;

@Data // Annotations to simplify writing code (ie constructors, setters)
public class LightBoard {
    private Light[][] lights;

    /* Initialize LightBoard and Lights */
    public LightBoard(int numRows, int numCols, double percentLightsOff) {
        // initializes light board according to size
        initializeLightBoard(numRows, numCols, percentLightsOff);

    }

    public void initializeLightBoard(int numRows, int numCols, double percentLightsOff) {
        this.lights = new Light[numRows][numCols];

        // 2D array nested loops, used for initialization
        double numLightsOff = 0;
        double totalLights = lights.length * lights[0].length;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (percentLightsOff == 100) {
                    lights[row][col] = new Light(false);
                } else if (numLightsOff / totalLights < percentLightsOff / 100) {
                    Random rd = new Random();
                    lights[row][col] = new Light(rd.nextBoolean());
                    if (!lights[row][col].on) {
                        numLightsOff++;
                    }
                    System.out.println(lights[row][col].on);
                } // scuffed way to turn on/off portion of lights
                else {
                    lights[row][col] = new Light(true); // each cell needs to be constructed
                }
            }
        }
    }

    /* Output is intended for API key/values */
    public String toString() {
        String outString = "[";
        // 2D array nested loops, used for reference
        for (int row = 0; row < lights.length; row++) {
            for (int col = 0; col < lights[row].length; col++) {
                outString +=
                        // data
                        "{" +
                                "\"row\": " + row + "," +
                                "\"column\": " + col + "," +
                                "\"light\": " + lights[row][col] + // extract toString data
                                "},";
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString = outString.substring(0, outString.length() - 1) + "]";
        return outString;
    }

    /* Output is intended for Terminal, effects added to output */
    public String toTerminal() {
        String outString = "[";
        // 2D array nested loops, used for reference
        for (int row = 0; row < lights.length; row++) {
            for (int col = 0; col < lights[row].length; col++) {
                outString +=
                        // reset
                        "\033[m" +

                        // color
                                "\033[38;2;" +
                                lights[row][col].getRed() + ";" + // set color using getters
                                lights[row][col].getGreen() + ";" +
                                lights[row][col].getBlue() + ";" +
                                lights[row][col].getEffect() + "m" +
                                // data, extract custom getters
                                "{" +
                                "\"" + "RGB\": " + "\"" + lights[row][col].getRGB() + "\"" +
                                "," +
                                "\"" + "Effect\": " + "\"" + lights[row][col].getEffectTitle() + "\"" +
                                "\"" + "On\": " + "\"" + lights[row][col].isOn() + "\"" +
                                "}," +
                                // newline
                                "\n";
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString = outString.substring(0, outString.length() - 2) + "\033[m" + "]";
        return outString;
    }

    /* Output is intended for Terminal, draws color palette */
    public String toColorPalette() {
        // block sizes
        final int ROWS = 5;
        final int COLS = 5;

        // Build large string for entire color palette
        String outString = "";
        // find each row
        for (int row = 0; row < lights.length; row++) {
            // repeat each row for block size
            for (int i = 0; i < ROWS; i++) {
                // find each column
                for (int col = 0; col < lights[row].length; col++) {
                    // repeat each column for block size
                    for (int j = 0; j < COLS; j++) {
                        if (lights[row][col].on == false) {
                            continue;
                        }
                        // print single character, except at midpoint print color code
                        String c = (i == (int) (ROWS / 2) && j == (int) (COLS / 2))
                                ? lights[row][col].getRGB()
                                : (j == (int) (COLS / 2)) // nested ternary
                                        ? " ".repeat(lights[row][col].getRGB().length())
                                        : " ";

                        outString +=
                                // reset
                                "\033[m" +

                                // color
                                        "\033[38;2;" +
                                        lights[row][col].getRed() + ";" +
                                        lights[row][col].getGreen() + ";" +
                                        lights[row][col].getBlue() + ";" +
                                        "7m" +

                                        // color code or blank character
                                        c +

                                        // reset
                                        "\033[m";
                    }
                }
                outString += "\n";
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString += "\033[m";
        return outString;
    }

    static public void main(String[] args) {
        // create and display LightBoard
        Scanner userInputScanner = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter Rows: ");
        int numRows = userInputScanner.nextInt(); // Read user input

        System.out.println("Enter Columns: ");
        int numCols = userInputScanner.nextInt();

        System.out.println("What percent of lights do you want to have off? ");
        double percentLightsOff = userInputScanner.nextInt();

        LightBoard lightBoard = new LightBoard(numRows, numCols, percentLightsOff);
        System.out.println(lightBoard); // use toString() method
        System.out.println(lightBoard.toTerminal());
        System.out.println(lightBoard.toColorPalette());
    }
}
