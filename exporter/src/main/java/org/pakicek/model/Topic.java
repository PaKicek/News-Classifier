package org.pakicek.model;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing news topics/categories
 */
public enum Topic {
    POLITICS("Politics",
            Arrays.asList("политика", "politics", "politic", "государство", "government",
                    "власть", "power", "правительство", "administration", "выборы",
                    "elections", "election", "парламент", "parliament", "президент",
                    "president", "senate", "congress", "diplomacy", "foreign policy")),

    ECONOMICS("Economics",
            Arrays.asList("экономика", "economics", "economy", "бизнес", "business",
                    "финансы", "finance", "рынок", "market", "деньги", "money",
                    "инвестиции", "investment", "банк", "bank", "currency",
                    "stock", "stock market", "trading", "inflation", "unemployment")),

    SOCIETY("Society",
            Arrays.asList("общество", "society", "социальн", "social", "люди",
                    "people", "семья", "family", "образование", "education",
                    "дети", "children", "пенсии", "pension", "community",
                    "demographics", "population", "welfare", "social issues")),

    ACCIDENTS("Accidents",
            Arrays.asList("происшествия", "accidents", "accident", "чп", "emergency",
                    "авария", "crash", "преступление", "crime", "criminal",
                    "пожар", "fire", "дтп", "traffic accident", "катастрофа",
                    "disaster", "catastrophe", "emergency", "rescue")),

    SPORT("Sport",
            Arrays.asList("спорт", "sports", "sport", "футбол", "football",
                    "хоккей", "hockey", "олимпиада", "olympics", "соревнования",
                    "competition", "чемпионат", "championship", "матч", "match",
                    "game", "basketball", "tennis", "athletics", "athlete")),

    SCIENCE_TECH("Science and tech",
            Arrays.asList("наука", "science", "scientific", "технологии", "technology",
                    "tech", "it", "information technology", "космос", "space",
                    "инновации", "innovation", "исследования", "research", "discovery",
                    "artificial intelligence", "ai", "machine learning", "robotics",
                    "computer", "software", "hardware", "internet", "digital")),

    CULTURE("Culture",
            Arrays.asList("культура", "culture", "cultural", "искусство", "art",
                    "кино", "cinema", "movie", "фильм", "film", "музыка", "music",
                    "театр", "theater", "theatre", "литература", "literature",
                    "выставка", "exhibition", "музей", "museum", "heritage")),

    HEALTH("Health",
            Arrays.asList("здоровье", "health", "healthcare", "медицина", "medicine",
                    "medical", "болезнь", "disease", "illness", "лечение", "treatment",
                    "врачи", "doctors", "physician", "больница", "hospital",
                    "клиника", "clinic", "вирус", "virus", "pandemic", "epidemic",
                    "vaccine", "vaccination", "pharmacy", "drug")),

    TRAVELS("Travels and tourism",
            Arrays.asList("туризм", "tourism", "travel", "travels", "путешествия",
                    "journey", "отдых", "vacation", "holiday", "курорт", "resort",
                    "отели", "hotels", "hotel", "отпуск", "leave", "экскурсия",
                    "excursion", "tour", "trip", "destination", "airline")),

    WEATHER("Weather",
            Arrays.asList("погода", "weather", "климат", "climate", "природа",
                    "nature", "экология", "ecology", "environment", "катаклизм",
                    "cataclysm", "наводнение", "flood", "flooding", "землетрясение",
                    "earthquake", "storm", "hurricane", "typhoon", "temperature",
                    "forecast", "global warming", "climate change")),

    UNKNOWN("Unknown", List.of());

    private final String displayName;
    private final List<String> keywords;

    Topic(String displayName, List<String> keywords) {
        this.displayName = displayName;
        this.keywords = keywords;
    }

    /**
     * Get display name for the topic
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Try to determine topic from category string
     */
    public static Topic determineFromCategory(String category) {
        if (category == null || category.isEmpty()) {
            return UNKNOWN;
        }

        String lowerCategory = category.toLowerCase();

        // Check each topic's keywords
        for (Topic topic : values()) {
            if (topic == UNKNOWN) continue;

            for (String keyword : topic.keywords) {
                if (lowerCategory.contains(keyword)) {
                    return topic;
                }
            }
        }
        return UNKNOWN;
    }
}