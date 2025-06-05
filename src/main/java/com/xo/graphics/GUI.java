package com.xo.graphics;

import com.xo.scrapers.SteamScraper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JPanel main;
    private JTextField urlField;
    private JButton startButton;
    private JPanel image;
    private JLabel title;
    private JTextArea progressText;
    private JComboBox<String> imageSelector;

    private final JLabel imageLabel = new JLabel();
    private final List<BufferedImage> images = new ArrayList<>();

    SteamScraper steamScraper = new SteamScraper(progressText);
    GridGenerator gridGenerator = new GridGenerator();

    public GUI() {
        setContentPane(main);
        setTitle("Crossout Helper");
        setSize(900, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set and update look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        // Set layout and add image label
        image.setLayout(new BorderLayout());
        image.add(imageLabel, BorderLayout.CENTER);

        loadImages();

        startButton.addActionListener(e -> {
            // Placeholder for scraper trigger
            System.out.println(urlField.getText());
//            progressText.append("\n");
            steamScraper.getHtml(urlField.getText());
            addImage(steamScraper.getFilename());
        });

        imageSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = imageSelector.getSelectedIndex();
                if (index >= 0 && index < images.size()) {
                    imageLabel.setIcon(new ImageIcon(images.get(index)));
                }
            }
        });

        setVisible(true);
    }

    /**
     * Searches the output map for images.
     * For each image found, it's added to the dropdown menu.
     * Afterward the first image is selected
     */
    public void loadImages() {
        Path outputPath = Paths.get("src/output");

        try {
            Files.createDirectories(outputPath);
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(outputPath, "*.jpeg");

            for (Path path : directoryStream) {
                BufferedImage image = ImageIO.read(path.toFile());
                if (image != null) {
                    images.add(image);
                    imageSelector.addItem(path.getFileName().toString());
                }
            }

            // Display first image if available
            if (!images.isEmpty()) {
                imageSelector.setSelectedIndex(0);
                imageLabel.setIcon(new ImageIcon(images.get(0)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the image from a filename and adds it to the list of images and selector menu
     * Sets the new image as selected in the dropdown menu and in the image space
     * @param filename The new single file to be added
     */
    public void addImage(String filename) {
        try {
            BufferedImage image = null;
            image = ImageIO.read(new File("src/output/" + filename + ".jpeg"));
            images.add(image);
            imageSelector.addItem(filename + ".jpeg");

            imageSelector.setSelectedItem(filename + ".jpeg");
            imageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public JTextArea getProgressText() {
        return progressText;
    }

    public void setProgressText(JTextArea progressText) {
        this.progressText = progressText;
    }
}
