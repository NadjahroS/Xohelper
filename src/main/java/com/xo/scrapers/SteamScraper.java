package com.xo.scrapers;

import com.xo.Utils;
import com.xo.graphics.GridGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class SteamScraper {

    static Utils utils = new Utils();
    static GridGenerator gridGenerator = new GridGenerator();

    private JTextArea progressText;
    private String filename;

    public SteamScraper(JTextArea progressText) {
        this.progressText = progressText;
    }

    public SteamScraper() {
    }

    public void getCoordinates(String url) {
        getCoordinates(url, 1);
    }

    /**
     * Web-scraper for comments from the Steam Crossout find the treasure contests.
     * After defining the starting variables it takes the HTML from the url.
     * First it checks the date to generate a filename with and takes the coordinates from the HTML.
     * Each loop it takes the coordinates from the subsequent pages.
     * Finally, it sorts the coordinates, writes them to a file, from which a jpeg is made and metadata gets added to.
     * @param url The Steam url to be scraped
     * @param start The starting point (1 if a new url or another number if you want to update)
     */
    public void getCoordinates(String url, int start) {
        org.jsoup.nodes.Document doc = null;
        BufferedWriter writer = null;

        int page = start;
        String baseUrl = url;

        // Using set instead of array for automatic duplicate removal
        Set<String> coordinates = new HashSet<>();

        try {
            while (true) {
//                if (progressText != null) {
//                    progressText.append("Checking page: " + page + "\n");
//                }
                System.out.println("Checking page: " + page);

                // Reset url and append next comment page
                if (page > 1) {
                    url = baseUrl;
                    url += ("?ctp=" + page);
                }

                doc = Jsoup.connect(url).userAgent("Mozilla").data("name", "jsoup").get();
//              System.out.println(doc);

                // Takes a date from the html, then the title, takes the useful part and converts it to LocalDate format.
                if (page == start) {
                    Element dateSpan = doc.selectFirst(".date");
                    if (dateSpan == null) {
                        progressText.append("Date not found");
                        return;
                    }
                    String dateTime = dateSpan.attr("title");
                    int dateIndex = dateTime.indexOf("@");
                    String date = dateTime.substring(0, dateIndex);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH);
                    String localDate = LocalDate.parse(date.trim(), formatter).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    // System.out.println(localDate);

                    filename = "steam-" + localDate;
                    File outputFile = new File("src/output/", ("steam-" + localDate + ".txt"));

                    // Checks if the file already exists before wiping it with the writer (mainly for updating)
                    if (Files.exists(Path.of("src/output/" + filename + ".txt"))) {
                        System.out.println("Found file: " + filename + ".txt");
                        BufferedReader reader = new BufferedReader(new FileReader(outputFile));
                        String data;
                        while ((data = reader.readLine()) != null ) {
                            coordinates.add(data);
//                            System.out.println(data);
                        }
                        reader.close();
                    }
                    writer = new BufferedWriter(new FileWriter(outputFile));
                }

                // Takes all comments from the html, stops the program if there are no comments left
                Elements comments = doc.select(".commentthread_comment_text");
                if (comments.isEmpty()) {
                    if (progressText != null) {
                        progressText.append("Page: " + page + " had no more comments" + "\n"  + "\n");
                    }
                    System.out.println("Page: " + page + " had no more comments");
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
//                 }
                page++;
                Thread.sleep(4000);
            }

            List<String> sortedCoordinates = new ArrayList<>(coordinates);
            sortedCoordinates.sort((a, b) -> {
                int letterCompare = Character.compare(a.charAt(0), b.charAt(0));
                if (letterCompare != 0) return letterCompare;
                // compare number part
                int aNum = Integer.parseInt(a.substring(1));
                int bNum = Integer.parseInt(b.substring(1));
                return Integer.compare(aNum, bNum);
            });

            for (String coord : sortedCoordinates) {
                writer.write(coord);
                writer.newLine();
            }
            writer.close();

            gridGenerator.fillGrid(filename + ".txt");
            gridGenerator.editMetadata("src/output/" + filename + ".jpeg", baseUrl, page-1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        } finally {
//            List<String> sortedCoordinates = new ArrayList<>(coordinates);
//            sortedCoordinates.sort((a, b) -> {
//                int letterCompare = Character.compare(a.charAt(0), b.charAt(0));
//                if (letterCompare != 0) return letterCompare;
//                // compare number part
//                int aNum = Integer.parseInt(a.substring(1));
//                int bNum = Integer.parseInt(b.substring(1));
//                return Integer.compare(aNum, bNum);
//            });
//
//            try {
//                if (writer != null) {
//                    for (String coord : sortedCoordinates) {
//                        writer.write(coord);
//                        writer.newLine();
//                    }
//                    writer.close();
//                }
//
//                gridGenerator.fillGrid(filename + ".txt");
//                gridGenerator.editMetadata("src/output/" + filename + ".jpeg", baseUrl, page-1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////          System.out.println(sortedCoordinates);
//        }
        // System.out.println(doc);
    }

    public String getFilename() {
        return filename;
    }
}
