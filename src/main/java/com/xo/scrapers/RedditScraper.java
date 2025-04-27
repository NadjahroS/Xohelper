package com.xo.scrapers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xo.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RedditScraper {

    static Utils utils = new Utils();

    public RedditScraper() {}

    public void getHtml(String url) {
        JsonNode root = null;
        Document doc = null;
        Document comments = null;

        ObjectMapper mapper = new ObjectMapper();

        BufferedWriter writer = null;

        // Using set instead of array for automatic duplicate removal
        Set<String> coordinates = new HashSet<>();

        try {
//            root = mapper.readTree(new URL(url + ".json"));
////            System.out.println(json);
//
//            JsonNode commentsNode = root.get(1).get("data").get("children");
//
//            for (JsonNode commentWrapper : commentsNode) {
//                JsonNode comment = commentWrapper.get("data");
//                String author = comment.get("author").asText();
//                String body = comment.get("body").asText();
//
//                System.out.println("User: " + author);
//                System.out.println("Comment: " + body);
//                System.out.println("-----");
//            }

            doc = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla").data("name", "jsoup").get();
//            System.out.println(doc);



//            comments = Jsoup.connect("https://www.redditstatic.com/shreddit/en-US/shreddit-comment-tree-stats-exp-client-js-1858025a.js").ignoreContentType(true).userAgent("Mozilla").data("name", "jsoup").get();
//            System.out.println(comments);

            Elements scripts = doc.getElementsByTag("script");
            System.out.println(scripts);

            // Takes a date from the html, then the ts attribute, takes the useful part and converts it to LocalDate format.
            Element dateSpan = doc.selectFirst("faceplate-timeago");
            assert dateSpan != null;
            String dateTime = dateSpan.attr("ts");
            int dateIndex = dateTime.indexOf("T");
            String date = dateTime.substring(0, dateIndex);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            String localDate = LocalDate.parse(date.trim(), formatter).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            File outputFile = new File("src/output", ("reddit-" + localDate + ".txt"));
            writer = new BufferedWriter(new FileWriter(outputFile));


            // Recognizes any 4 character or longer word as a username
            // Pattern usernamePattern = Pattern.compile("[\\p{L}\\p{N}._#-]{4,}");
            // Matcher usernameMatcher = usernamePattern.matcher(commentText);

            // while (usernameMatcher.find()) {
            //     String username = usernameMatcher.group();
            //     // writer.write(username);
            //     // writer.newLine();
            //     System.out.println("Username " + (i+1) + ": " + username.trim().toUpperCase());
            // }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(sortedCoordinates);
        }
        // System.out.println(doc);
    }
}
