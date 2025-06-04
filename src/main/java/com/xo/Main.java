package com.xo;

import java.io.File;
import java.io.IOException;

import com.xo.graphics.GridGenerator;
import com.xo.scrapers.RedditScraper;
import com.xo.scrapers.SteamScraper;

public class Main {

    static GridGenerator gridGenerator = new GridGenerator();
    static RedditScraper redditScraper = new RedditScraper();
    static SteamScraper steamScraper = new SteamScraper();

    public static void main(String[] args) {
//        GUI gui = new GUI();

//        steamScraper.getHtml("https://steamcommunity.com/app/386180/eventcomments/598528021200225035_");
        steamScraper.getHtml("https://steamcommunity.com/app/386180/eventcomments/599642060902915490");
//        redditScraper.getHtml("https://www.reddit.com/r/Crossout/comments/1ivgcvy/find_a_treasure/");

        try {
            gridGenerator.editMetadata("src/output/steam-30-05-2025.jpeg", "", 10);
            gridGenerator.paintGrid();
            gridGenerator.fillGrid("steam-30-05-2025.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}