package org.pakicek.model;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing news topics/categories
 */
public enum Topic {
    POLITICS("Politics",
            Arrays.asList("политика", "politic", "государство", "власть",
                    "правительство", "выборы", "парламент", "президент")),

    ECONOMICS("Economics",
            Arrays.asList("экономика", "econom", "бизнес", "финансы",
                    "рынок", "деньги", "инвестиции", "банк")),

    SOCIETY("Society",
            Arrays.asList("общество", "society", "социальн", "люди",
                    "семья", "образование", "дети", "пенсии")),

    ACCIDENTS("Accidents",
            Arrays.asList("происшествия", "accident", "чп", "авария",
                    "преступление", "пожар", "дтп", "катастрофа")),

    SPORT("Sport",
            Arrays.asList("спорт", "sport", "футбол", "хоккей",
                    "олимпиада", "соревнования", "чемпионат", "матч")),

    SCIENCE_TECH("Science and tech",
            Arrays.asList("наука", "science", "технологии", "tech",
                    "it", "космос", "инновации", "исследования")),

    CULTURE("Culture",
            Arrays.asList("культура", "culture", "искусство", "кино",
                    "музыка", "театр", "литература", "выставка")),

    HEALTH("Health",
            Arrays.asList("здоровье", "health", "медицина", "болезнь",
                    "лечение", "врачи", "больница", "вирус")),

    TRAVELS("Travels and tourism",
            Arrays.asList("туризм", "travel", "путешествия", "отдых",
                    "курорт", "отели", "отпуск", "экскурсия")),

    WEATHER("Weather",
            Arrays.asList("погода", "weather", "климат", "природа",
                    "экология", "катаклизм", "наводнение", "землетрясение")),

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