package com.xo;

import java.io.File;
import java.io.IOException;

import com.xo.graphics.GUI;
import com.xo.graphics.GridGenerator;
import com.xo.scrapers.RedditScraper;
import com.xo.scrapers.SteamScraper;

public class Main {

    static GridGenerator gridGenerator = new GridGenerator();
    static RedditScraper redditScraper = new RedditScraper();
    static SteamScraper steamScraper = new SteamScraper();

    public static void main(String[] args) {
        new GUI();

//        steamScraper.getCoordinates("https://steamcommunity.com/app/386180/eventcomments/7264963628761167032", 25);
//        steamScraper.getHtml("https://steamcommunity.com/app/386180/eventcomments/598528021200225035_");
//        steamScraper.getHtml("https://steamcommunity.com/app/386180/eventcomments/599642060902915490");
//        redditScraper.getHtml("https://www.reddit.com/r/Crossout/comments/1ivgcvy/find_a_treasure/");

//        try {
////            gridGenerator.editMetadata("src/output/steam-15-11-2024.jpeg", "test.com", 10);
////            gridGenerator.editMetadata("src/output/steam-18-01-2025.jpeg", "https://steamcommunity.com/app/386180/eventcomments/599642060902915490", 22);
////            gridGenerator.editMetadata("src/output/steam-30-05-2025.jpeg", "https://steamcommunity.com/app/386180/eventcomments/598528021200225035", 20);
//            gridGenerator.editMetadata("src/output/steam-17-03-2025.jpeg", "https://steamcommunity.com/app/386180/eventcomments/510701184375390509", 19);
////            gridGenerator.paintGrid();
////            gridGenerator.fillGrid("steam-15-11-2024.txt");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}