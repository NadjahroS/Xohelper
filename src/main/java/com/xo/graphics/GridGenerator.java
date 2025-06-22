package com.xo.graphics;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        grid = ImageIO.read(new File("src/output/grid.jpeg"));

        if (grid == null) {
            paintGrid();
            grid = ImageIO.read(new File("src/output/grid.jpeg"));
        }

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

        File file = new File("src/output/" + filename.replace(".txt", "") + ".jpeg");
        ImageIO.write(grid,"jpeg", file);
    }

    public void editMetadata(String file, String url, int pages) throws IOException {
        File image = new File(file);
        ImageMetadata metadata = Imaging.getMetadata(image);
        JpegImageMetadata jpegImageMetadata = (JpegImageMetadata) metadata;
        TiffOutputSet outputSet = null;

        if (metadata != null) {
            TiffImageMetadata exif = jpegImageMetadata.getExif();
            if (exif != null) {
                outputSet = exif.getOutputSet();
                List<TiffField> fields = exif.getAllFields();
                for (TiffField field : fields) {
                    System.out.println(field.getTagName() + ": " + field.getValueDescription());
                }
            } else {
                System.out.println("No exif");
            }
//        } else {
//            System.out.println("Metadata is null");
        }

        // Create empty exif metadataset if there's none
        if (outputSet == null) {
            outputSet = new TiffOutputSet();
        }

        // Root directory for basic fields like Software and Make
        TiffOutputDirectory rootDirectory = outputSet.getOrCreateRootDirectory();
        rootDirectory.removeField(TiffTagConstants.TIFF_TAG_SOFTWARE);
        rootDirectory.add(TiffTagConstants.TIFF_TAG_SOFTWARE, "XoHelper");

        rootDirectory.removeField(TiffTagConstants.TIFF_TAG_ARTIST);
        rootDirectory.add(TiffTagConstants.TIFF_TAG_ARTIST, "Nadjahro");

//        rootDirectory.removeField(TiffTagConstants.TIFF_TAG_IMAGE_DESCRIPTION);
//        rootDirectory.add(TiffTagConstants.TIFF_TAG_IMAGE_DESCRIPTION, url + ";" + pages + ";");

        // Exif directory for niche fields like user comment
        TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
        exifDirectory.removeField(ExifTagConstants.EXIF_TAG_USER_COMMENT);
        // Added ASCII to the beginning to prevent it being changed to XPComment randomly :/
        exifDirectory.add(ExifTagConstants.EXIF_TAG_USER_COMMENT, "ASCII\0\0\0" + url + ";" + pages);

        File tempFile = new File(image.getParent(), "temp_" + image.getName());
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        OutputStream outputStream = new BufferedOutputStream(fileOutputStream);

        new ExifRewriter().updateExifMetadataLossless(image, outputStream, outputSet);

        if (!image.delete() || !tempFile.renameTo(image)) {
            throw new IOException("Failed to overwrite original image with updated EXIF data.");
        }
    }

    /**
     * Creates directory if it doesn't exist
     * Creates a buffered image and a graphics2D for said image.
     * Sets the background to cyan
     * In a for loop creates 21 horizontal and vertical lines mathematically with margins for the legends.
     * Also creates said legends along the grid in the for loop.
     * After disposing of the graphics, creates a png file.
     * @throws IOException
     */
    public void paintGrid() throws IOException {
        Files.createDirectories(Paths.get("src/output"));

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

        File file = new File("src/output/grid.jpeg");
        ImageIO.write(grid,"jpeg", file);
    }
}
