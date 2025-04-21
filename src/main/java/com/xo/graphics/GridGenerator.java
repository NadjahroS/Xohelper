package com.xo.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GridGenerator {

    private int cellSize = 30;
    private int margin = 40;
    private final int width = 650;
    private final int height = 650;

    public GridGenerator() {}

    public void fillGrid() throws IOException {
        BufferedImage grid = null;
        grid = ImageIO.read(new File("src/output/grid.png"));

        Graphics2D graphics = grid.createGraphics();
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(3));

        graphics.drawLine(margin, margin, margin + cellSize, margin + cellSize);
        graphics.drawLine(margin, margin + cellSize, margin + cellSize, margin);

        graphics.dispose();

        File file = new File("src/output/grid_filled.png");
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
