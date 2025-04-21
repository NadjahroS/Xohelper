package com.xo.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GridGenerator {

    private int cellSize = 30;
    private int margin = 40;
    private final int width = 650;
    private final int height = 650;

    public GridGenerator() {}

    /**
     * Takes a empty grid and sets the graphics to red and thicker lines.
     * Takes a text file and scans it into a arraylist.
     * For each coordinate fills in a grid space with a red X at said coordinate.
     * Saves the new file
     * @param filename The filename to use as input for which grid coordinates ti fill
     * @throws IOException
     */
    public void fillGrid(String filename) throws IOException {
        BufferedImage grid = null;
        grid = ImageIO.read(new File("src/output/grid.png"));

        Graphics2D graphics = grid.createGraphics();
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(3));

        File text = new File("src/output/" + filename);
        Scanner scanner = new Scanner(text);
        ArrayList<String> coordinates = new ArrayList<>();

        while (scanner.hasNext()) {
            coordinates.add(scanner.nextLine());
        }

        // For each coordinate it corrects the x and y by subtracting one
        for (int i = 0; i < coordinates.size(); i++) {
            int cellX = margin + (coordinates.get(i).charAt(0)-'A') * cellSize;
            int cellY = margin + (Integer.parseInt(coordinates.get(i).substring(1))-1) * cellSize;

            graphics.drawLine(cellX, cellY, cellX + cellSize, cellY + cellSize);
            graphics.drawLine(cellX, cellY + cellSize, cellX + cellSize, cellY);
        }

        graphics.dispose();

        File file = new File("src/output/" + filename + ".png");
        ImageIO.write(grid,"png", file);
    }

    /**
     * Creates a buffered image and a graphics2D for said image.
     * Sets the background to cyan
     * In a for loop creates 21 horizontal and vertical lines mathematically with margins for the legends.
     * Also creates said legends along the grid in the for loop.
     * After disposing of the graphics, creates a png file.
     * @throws IOException
     */
    public void paintGrid() throws IOException {
        BufferedImage grid = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = grid.createGraphics();

        graphics.setColor(Color.lightGray);
        graphics.fillRect(0, 0, width, height);

        // Draws a 20x20 grid with legends
        graphics.setColor(Color.black);
        for (int i = 0; i <= 20; i++) {
            graphics.drawLine(margin, margin + (cellSize * i), width - 10, margin + (cellSize * i));
            graphics.drawLine(margin + (cellSize * i), margin, margin + (cellSize * i), height - 10);

            graphics.drawString(String.valueOf((char) ('A' + i)), margin + (cellSize * i) + 12, margin - 10);
            graphics.drawString(String.valueOf(i+1), margin - 15, margin + (cellSize * i) + 20);
        }

        graphics.dispose();

        File file = new File("src/output/grid.png");
        ImageIO.write(grid,"png", file);
    }
}
