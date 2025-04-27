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
        setSize(720,720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
