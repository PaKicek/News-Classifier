package org.pakicek.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Represents a news item with all relevant metadata
 */
public class NewsItem {
    private String website;
    private String title;
    private String author;
    private LocalDateTime publicationDate;
    private String link;
    private String pictureLink;
    private String category;
    private Topic topic;
    private int relevance;

    // Formatter for CSV date
    private static final DateTimeFormatter ISO_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Topic weight multipliers (1.0 = neutral, >1.0 = more relevant, <1.0 = less relevant)
    private static final Map<Topic, Double> TOPIC_WEIGHTS;

    static {
        // Using Map.ofEntries for more than 10 entries
        TOPIC_WEIGHTS = Map.ofEntries(
                Map.entry(Topic.ECONOMICS, 1.4),           // Economics - highest relevance
                Map.entry(Topic.SCIENCE_TECH, 1.3),        // Science and tech - high relevance
                Map.entry(Topic.HEALTH, 1.2),              // Health - increased relevance
                Map.entry(Topic.POLITICS, 1.1),            // Politics - slightly increased
                Map.entry(Topic.SOCIETY, 1.0),             // Society - neutral
                Map.entry(Topic.ACCIDENTS, 1.0),           // Accidents - neutral
                Map.entry(Topic.CULTURE, 0.9),             // Culture - slightly less relevant
                Map.entry(Topic.WEATHER, 0.9),             // Weather - slightly less relevant
                Map.entry(Topic.TRAVELS, 0.8),             // Travels - less relevant
                Map.entry(Topic.SPORT, 0.7),               // Sport - significantly less relevant
                Map.entry(Topic.UNKNOWN, 1.0)              // Unknown topic - neutral
        );
    }

    // Time constants
    private static final int VERY_RECENT_HOURS = 1;
    private static final int RECENT_HOURS = 6;
    private static final int DAY_OLD_HOURS = 24;
    private static final int FEW_DAYS_HOURS = 72;

    public NewsItem() {
        this.relevance = 3; // Default medium relevance
        this.topic = Topic.UNKNOWN;
        this.website = "Unknown";
    }

    // Getters and setters
    public String getWebsite() {
        return website != null ? website : "Unknown";
    }

    public void setWebsite(String website) {
        this.website = website != null ? website.trim() : "Unknown";
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public void setTitle(String title) {
        this.title = title != null ? title.trim() : "";
    }

    public String getAuthor() {
        return author != null ? author : "";
    }

    public void setAuthor(String author) {
        this.author = author != null ? author.trim() : "";
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLink() {
        return link != null ? link : "";
    }

    public void setLink(String link) {
        this.link = link != null ? link.trim() : "";
    }

    public String getPictureLink() {
        return pictureLink != null ? pictureLink : "";
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink != null ? pictureLink.trim() : "";
    }

    public String getCategory() {
        return category != null ? category : "";
    }

    public void setCategory(String category) {
        this.category = category != null ? category.trim() : "";
        if (!this.category.isEmpty()) {
            determineTopicFromCategory();
        }
    }

    public Topic getTopic() {
        return topic != null ? topic : Topic.UNKNOWN;
    }

    public int getRelevance() {
        return relevance;
    }

    /**
     * Get ISO formatted publication date for CSV
     * @return ISO date string or empty string
     */
    public String getIsoDate() {
        if (publicationDate == null) {
            return "";
        }
        return publicationDate.format(ISO_FORMATTER);
    }

    /**
     * Method to get data as string array for CSV
     */
    public String[] toCsvRow() {
        return new String[] {
                getWebsite(),
                escapeCsv(getTitle()),
                escapeCsv(getAuthor()),
                getIsoDate(),
                escapeCsv(getLink()),
                escapeCsv(getPictureLink()),
                escapeCsv(getCategory()),
                getTopic().getDisplayName(),
                String.valueOf(getRelevance())
        };
    }

    // CSV escaping
    private String escapeCsv(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // Determine topic from category
    private void determineTopicFromCategory() {
        this.topic = Topic.determineFromCategory(category);
    }

    /**
     * Calculate relevance based on publication date and topic importance
     * Relevance = time-based score * topic weight factor
     */
    public void calculateRelevanceByDate() {
        int timeBasedScore = calculateTimeBasedScore();

        // Apply topic weight to the time-based score
        this.relevance = applyTopicWeight(timeBasedScore);
    }

    /**
     * Calculate base relevance score based on publication date
     * @return score from 1 to 5
     */
    private int calculateTimeBasedScore() {
        if (publicationDate == null) {
            return 3; // Default score if no date
        }

        long hoursDiff = ChronoUnit.HOURS.between(publicationDate, LocalDateTime.now());

        // Determine relevance based on time difference
        if (hoursDiff <= VERY_RECENT_HOURS) {      // Up to 1 hour - very fresh
            return 5;
        } else if (hoursDiff <= RECENT_HOURS) {    // 1-6 hours
            return 4;
        } else if (hoursDiff <= DAY_OLD_HOURS) {   // 6-24 hours
            return 3;
        } else if (hoursDiff <= FEW_DAYS_HOURS) {  // 1-3 days
            return 2;
        } else {                                   // More than 3 days
            return 1;
        }
    }

    /**
     * Apply topic weight multiplier to the base relevance score
     * @param baseScore Base score (1-5) based on time
     * @return Weighted score (1-5) after applying topic multiplier
     */
    private int applyTopicWeight(int baseScore) {
        double weight = TOPIC_WEIGHTS.getOrDefault(this.topic, 1.0);
        double weightedScore = baseScore * weight;

        // Ensure the result is between 1 and 5
        int finalScore = (int) Math.round(weightedScore);
        return Math.max(1, Math.min(5, finalScore));
    }

    /**
     * Check if this news item has all required fields
     * @return true if has title and link
     */
    public boolean isValid() {
        return getTitle() != null && !getTitle().isEmpty() &&
                getLink() != null && !getLink().isEmpty();
    }

    @Override
    public String toString() {
        return String.format("NewsItem{website='%s', title='%s', topic='%s', relevance=%d}",
                getWebsite(),
                getTitle().length() > 30 ? getTitle().substring(0, 27) + "..." : getTitle(),
                getTopic().getDisplayName(),
                getRelevance());
    }
}