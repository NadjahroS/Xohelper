package com.xo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.select.Elements;

public class Utils {

    public Utils() {}

    public ArrayList<String> getCoordinates(Elements comments) {
        ArrayList<String> coordinates = new ArrayList<>();

        /*
        * ### Explanation:
        - `(?:\s|,|\n|^)`: Matches a whitespace, comma, newline, or start of the line.
        - `(?<=...)`: Positive lookbehind to ensure the pattern matches only if preceded by specific characters.
        - `[^\S\n]`: Matches any non-whitespace character except newlines (equivalent to space).
        - `и?`: Optionally matches the "и" character.
        - `(?=...)`: Positive lookahead to ensure the pattern matches only if followed by specific characters.
        - `[a-zA-Z][0-9]{1,2}`: Matches a letter followed by one or two digits, capturing your coordinate pattern.
        - `\s|,|$|\n`: Matches whitespace, comma, end of line, or newline.
        - `и?`: Optionally matches the "и" character.
        */
        Pattern coordPattern = Pattern.compile("(?<=(?:\\s|,|\\n" + //
                "|^)и?|[^\\S\\n" + //
                "]|^)[a-zA-Z][0-9]{1,2}(?=и?|\\s|,|$|\\n" + //
                ")");

        for (int i = 0; i < comments.size(); i++) {
            String commentText = comments.get(i).text();
            Matcher coordMatcher = coordPattern.matcher(commentText);

            while (coordMatcher.find()) {
                String coordinate = coordMatcher.group().trim().toUpperCase();
                coordinates.add(coordinate);
                // System.out.println("Coordinate " + (i+1) + ": " + coordinate);
            }
            // System.out.println(i+1 + ": " +  comments.get(i).text());
        }
        // Matcher coordMatcher = coordPattern.matcher(commentText);

        return coordinates;
    }
}
