package com.xo.graphics;

import com.xo.scrapers.SteamScraper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    private JPanel main;
    private JTextField urlField;
    private JButton startButton;
    private JPanel image;
    private JLabel title;
    private JTextArea progressText;

    SteamScraper steamScraper = new SteamScraper();

    public GUI() {
        setContentPane(main);
        setTitle("Crossout Helper");
        setSize(800,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Check installed looks and feels
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
            System.out.println(look.getClassName());
        }

        // Set and update look and feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(main);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(urlField.getText());
            }
        });
    }
}
