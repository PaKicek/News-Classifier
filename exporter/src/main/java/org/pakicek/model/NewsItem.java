package org.pakicek.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

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

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Topic weight multipliers (1.0 = neutral, >1.0 = more relevant, <1.0 = less relevant)
    private static final Map<Topic, Double> TOPIC_WEIGHTS;

    static {
        TOPIC_WEIGHTS = Map.ofEntries(
                Map.entry(Topic.ECONOMICS, 1.4),
                Map.entry(Topic.SCIENCE_TECH, 1.3),
                Map.entry(Topic.HEALTH, 1.2),
                Map.entry(Topic.POLITICS, 1.1),
                Map.entry(Topic.SOCIETY, 1.0),
                Map.entry(Topic.ACCIDENTS, 1.0),
                Map.entry(Topic.CULTURE, 0.9),
                Map.entry(Topic.WEATHER, 0.9),
                Map.entry(Topic.TRAVELS, 0.8),
                Map.entry(Topic.SPORT, 0.7),
                Map.entry(Topic.UNKNOWN, 1.0)
        );
    }

    private static final int VERY_RECENT_HOURS = 1;
    private static final int RECENT_HOURS = 6;
    private static final int DAY_OLD_HOURS = 24;
    private static final int FEW_DAYS_HOURS = 72;

    public NewsItem() {
        this.relevance = 3; // Default medium relevance
        this.topic = Topic.UNKNOWN;
        this.website = "Unknown";
    }

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

    public String getIsoDate() {
        if (publicationDate == null) {
            return "";
        }
        return publicationDate.format(ISO_FORMATTER);
    }

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

    private String escapeCsv(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private void determineTopicFromCategory() {
        this.topic = Topic.determineFromString(title);
        if (this.topic == Topic.UNKNOWN) {
            this.topic = Topic.determineFromString(category);
        }
    }

    public void calculateRelevanceByDate() {
        int timeBasedScore = calculateTimeBasedScore();
        this.relevance = applyTopicWeight(timeBasedScore);
    }

    private int calculateTimeBasedScore() {
        if (publicationDate == null) {
            return 3;
        }
        long hoursDiff = ChronoUnit.HOURS.between(publicationDate, LocalDateTime.now());
        if (hoursDiff <= VERY_RECENT_HOURS) {
            return 5;
        } else if (hoursDiff <= RECENT_HOURS) {
            return 4;
        } else if (hoursDiff <= DAY_OLD_HOURS) {
            return 3;
        } else if (hoursDiff <= FEW_DAYS_HOURS) {
            return 2;
        } else {
            return 1;
        }
    }

    private int applyTopicWeight(int baseScore) {
        double weight = TOPIC_WEIGHTS.getOrDefault(this.topic, 1.0);
        double weightedScore = baseScore * weight;
        int finalScore = (int) Math.round(weightedScore);
        return Math.max(1, Math.min(5, finalScore));
    }

    public boolean isValid() {
        return getTitle() != null && !getTitle().isEmpty() && getLink() != null && !getLink().isEmpty();
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