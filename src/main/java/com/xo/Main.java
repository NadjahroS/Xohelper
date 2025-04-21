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

import com.xo.graphics.GridGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    static Utils utils = new Utils();
    static GridGenerator gridGenerator = new GridGenerator();

    public static void main(String[] args) {
        try {
//            gridGenerator.paintGrid();
            gridGenerator.fillGrid("17-03-2025.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        getHTML("https://steamcommunity.com/app/386180/eventcomments/510701184375390509?snr=2_9_100015_");
    }

    public static void getHTML(String url) {
        org.jsoup.nodes.Document doc = null;
        BufferedWriter writer = null;

        int page = 0;
        String baseUrl = url;

        // Using set instead of array for automatic duplicate removal
        Set<String> coordinates = new HashSet<>();

        try {
             while (true) {
            // Reset url and append next comment page
            if (page > 0) {
                url = baseUrl;
                url += ("&ctp=" + page);
            }

            doc = Jsoup.connect(url).userAgent("Mozilla").data("name", "jsoup").get();

            // Takes a date from the html, then the title, takes the useful part and converts it to LocalDate format.
            if (page == 0) {
                Element dateSpan = doc.selectFirst(".date");
                assert dateSpan != null;
                String dateTime = dateSpan.attr("title");
                int dateIndex = dateTime.indexOf("@");
                String date = dateTime.substring(0, dateIndex);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH);
                String localDate = LocalDate.parse(date.trim(), formatter).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                 File outputFile = new File("src/output", (localDate + ".txt"));
                 writer = new BufferedWriter(new FileWriter(outputFile));
                // System.out.println(localDate);
            }

            // Takes all comments from the html, stops the program if there are no comments left
            Elements comments = doc.select(".commentthread_comment_text");
            if (comments.size() == 0) {
                break;
            }
                coordinates.addAll(utils.getCoordinates(comments));
//                System.out.println(coordinates);

                // Recognizes any 4 character or longer word as a username
                // Pattern usernamePattern = Pattern.compile("[\\p{L}\\p{N}._#-]{4,}");
                // Matcher usernameMatcher = usernamePattern.matcher(commentText);

                // while (usernameMatcher.find()) {
                //     String username = usernameMatcher.group();
                //     // writer.write(username);
                //     // writer.newLine();
                //     System.out.println("Username " + (i+1) + ": " + username.trim().toUpperCase());
                // }
            page++;
            Thread.sleep(4000);
             }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            List<String> sortedCoordinates = new ArrayList<>(coordinates);
            sortedCoordinates.sort((a, b) -> {
                int letterCompare = Character.compare(a.charAt(0), b.charAt(0));
                if (letterCompare != 0) return letterCompare;
                // compare number part
                int aNum = Integer.parseInt(a.substring(1));
                int bNum = Integer.parseInt(b.substring(1));
                return Integer.compare(aNum, bNum);
            });

            try {
                if (writer != null) {
                    for (String coord : sortedCoordinates) {
                        writer.write(coord);
                        writer.newLine();
                    }
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(sortedCoordinates);
        }
        // System.out.println(doc);
    }
}