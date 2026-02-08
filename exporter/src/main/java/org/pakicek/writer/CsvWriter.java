package org.pakicek.writer;

import com.opencsv.CSVWriter;
import org.pakicek.model.NewsItem;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class CsvWriter {
    private static final String[] HEADERS = {
            "Website", "Title", "Author", "Publication Date", "Link", "Picture Link", "Category", "Topic", "Relevance"
    };

    public void writeToCsv(List<NewsItem> newsItems, String filename) {
        if (newsItems == null || newsItems.isEmpty()) {
            System.out.println("No data to write");
            return;
        }

        Collections.shuffle(newsItems);

        try (CSVWriter writer = new CSVWriter(
                new FileWriter(filename, StandardCharsets.UTF_8), ',',
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END
        )) {
            writer.writeNext(HEADERS);
            for (NewsItem item : newsItems) {
                writer.writeNext(item.toCsvRow());
            }
            System.out.println("Written " + newsItems.size() + " items to " + filename);
        } catch (IOException e) {
            System.err.println("Error while writing CSV: " + e.getMessage());
            throw new RuntimeException("Failed to write CSV", e);
        }
    }
}