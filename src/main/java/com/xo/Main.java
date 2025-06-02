package com.xo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.xo.graphics.GUI;
import com.xo.graphics.GridGenerator;
import com.xo.scrapers.RedditScraper;
import com.xo.scrapers.SteamScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    static GridGenerator gridGenerator = new GridGenerator();
    static RedditScraper redditScraper = new RedditScraper();
    static SteamScraper steamScraper = new SteamScraper();

    public static void main(String[] args) {
//        GUI gui = new GUI();

//        steamScraper.getHtml("https://steamcommunity.com/app/386180/eventcomments/598528021200225035_");
//        steamScraper.getHtml("https://steamcommunity.com/app/386180/eventcomments/599642060902915490?snr=2_9_100015_");
//        redditScraper.getHtml("https://www.reddit.com/r/Crossout/comments/1ivgcvy/find_a_treasure/");
        try {
            File file = new File("src/output/18-01-2025.jpeg");
            gridGenerator.readMetaData(file);
//            gridGenerator.paintGrid();
//            gridGenerator.fillGrid("18-01-2025.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}