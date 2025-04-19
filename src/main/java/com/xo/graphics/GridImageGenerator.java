package com.xo.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GridImageGenerator {
    public static void main(String[] args) {
        int cellSize = 40;
        int gridSize = 20;
        int margin = 40;

        int width = cellSize * gridSize + margin;
        int height = cellSize * gridSize + margin;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // White background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Grid lines
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= gridSize; i++) {
            // Horizontal lines
            g.drawLine(margin, margin + i * cellSize, margin + gridSize * cellSize, margin + i * cellSize);
            // Vertical lines
            g.drawLine(margin + i * cellSize, margin, margin + i * cellSize, margin + gridSize * cellSize);
        }

        // Labels
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Letters A to T at top
        for (int i = 0; i < gridSize; i++) {
            char letter = (char) ('A' + i);
            g.drawString(String.valueOf(letter), margin + i * cellSize + cellSize / 2 - 5, margin - 10);
        }

        // Numbers 1 to 20 at left
        for (int i = 0; i < gridSize; i++) {
            g.drawString(String.valueOf(i + 1), 10, margin + i * cellSize + cellSize / 2 + 5);
        }

        g.dispose();

        // Save to file
        try {
            ImageIO.write(image, "png", new File("grid_map.png"));
            System.out.println("Grid image created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
