package org.pakicek;

import org.pakicek.model.NewsItem;
import org.pakicek.parser.*;
import org.pakicek.config.RssFeedConfig;
import org.pakicek.parser.site.*;
import org.pakicek.writer.CsvWriter;

import java.util.*;

/**
 * Main application class for parsing multiple RSS feeds
 */
public class Main {
    private static final Map<String, Object> PARSERS = new HashMap<>();

    static {
        PARSERS.put("vedomosti", new VedomostiRssParser());
        PARSERS.put("rt", new RtRssParser());
        PARSERS.put("lenta", new LentaRssParser());
        PARSERS.put("tass", new TassRssParser());
        PARSERS.put("kommersant", new KommersantRssParser());
        PARSERS.put("gazeta", new GazetaRssParser());
        PARSERS.put("rg", new RgRssParser());
    }

    public static void main(String[] args) {
        try {
            String website = args.length > 0 ? args[0].toLowerCase() : "all";
            String outputFilename = determineOutputFilename(args);

            System.out.println("Starting multi-RSS parser");
            System.out.println("Total RSS feeds configured: " + RssFeedConfig.getTotalFeedCount());
            System.out.println("Mode: " + (website.equals("all") ? "All websites" : website));
            System.out.println("Output file: " + outputFilename);

            List<NewsItem> allNewsItems = parseWebsites(website);

            if (allNewsItems.isEmpty()) {
                System.out.println("\nNo news items found.");
                return;
            }

            CsvWriter csvWriter = new CsvWriter();
            csvWriter.writeToCsv(allNewsItems, outputFilename);

            System.out.println("\nSuccessfully saved " + allNewsItems.size() + " items to: " + outputFilename);

        } catch (Exception e) {
            System.err.println("Critical error: " + e.getMessage());
        }
    }

    private static String determineOutputFilename(String[] args) {
        String filename;
        if (args.length > 1) {
            filename = args[1];
            filename = filename.toLowerCase().endsWith(".csv") ? filename : filename + ".csv";
        } else {
            filename = "all_news_" + java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv";
        }
        return new java.io.File("data", filename).getPath();
    }

    private static List<NewsItem> parseWebsites(String website) {
        List<NewsItem> allNewsItems = new ArrayList<>();

        if (website.equals("all")) {
            System.out.println("\nParsing all websites:");

            for (String siteName : RssFeedConfig.getAllWebsites()) {
                System.out.println("\n[" + siteName.toUpperCase() + "]");

                if (!PARSERS.containsKey(siteName)) {
                    System.err.println("No parser registered for: " + siteName);
                    System.err.println("Available parsers: " + String.join(", ", PARSERS.keySet()));
                    continue;
                }

                try {
                    Object parser = PARSERS.get(siteName);
                    List<NewsItem> items;

                    if (parser instanceof MultiFeedRssParser) {
                        // For multiple RSS feed sites
                        items = ((MultiFeedRssParser) parser).parseAllFeeds();
                    } else if (parser instanceof BaseRssParser singleParser) {
                        // For single RSS feed sites
                        System.out.print("Parsing main RSS feed...\n");
                        items = singleParser.parseRss();
                    } else {
                        System.err.println("Invalid parser type for " + siteName);
                        continue;
                    }

                    if (items != null && !items.isEmpty()) {
                        allNewsItems.addAll(items);
                    }

                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } else {
            // Parse single website
            System.out.println("\nParsing " + website + ":");

            if (!PARSERS.containsKey(website)) {
                System.err.println("No parser available for: " + website);
                return allNewsItems;
            }

            try {
                Object parser = PARSERS.get(website);
                List<NewsItem> items;

                if (parser instanceof MultiFeedRssParser) {
                    items = ((MultiFeedRssParser) parser).parseAllFeeds();
                } else if (parser instanceof BaseRssParser) {
                    items = ((BaseRssParser) parser).parseRss();
                    if (items != null && !items.isEmpty()) {
                        System.out.println("Found " + items.size() + " items");
                    }
                } else {
                    throw new Exception("Invalid parser type");
                }

                if (items != null) {
                    allNewsItems.addAll(items);
                }

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return allNewsItems;
    }
}