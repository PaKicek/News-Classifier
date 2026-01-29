package org.pakicek.config;

import java.util.*;

/**
 * Configuration for RSS feeds
 */
public class RssFeedConfig {
    private static final Map<String, List<String>> RSS_FEEDS = new HashMap<>();

    static {
        RSS_FEEDS.put("vedomosti", Arrays.asList(
                "https://www.vedomosti.ru/rss/rubric/business",
                "https://www.vedomosti.ru/rss/rubric/economics",
                "https://www.vedomosti.ru/rss/rubric/finance",
                "https://www.vedomosti.ru/rss/rubric/opinion",
                "https://www.vedomosti.ru/rss/rubric/politics",
                "https://www.vedomosti.ru/rss/rubric/technology",
                "https://www.vedomosti.ru/rss/rubric/realty",
                "https://www.vedomosti.ru/rss/rubric/auto",
                "https://www.vedomosti.ru/rss/rubric/management",
                "https://www.vedomosti.ru/rss/rubric/lifestyle"
        ));

        RSS_FEEDS.put("rt", Arrays.asList(
                "https://www.rt.com/rss/news/",
                "https://www.rt.com/rss/russia/",
                "https://www.rt.com/rss/business/",
                "https://www.rt.com/rss/op-ed/",
                "https://www.rt.com/rss/pop-culture/",
                "https://www.rt.com/rss-feed/"
        ));

        RSS_FEEDS.put("lenta", Arrays.asList(
                "https://lenta.ru/rss/news/russia",
                "https://lenta.ru/rss/articles",
                "https://lenta.ru/rss/articles/russia"
        ));

        RSS_FEEDS.put("gazeta", Collections.singletonList(
                "https://www.gazeta.ru/export/rss/first.xml"
        ));

        RSS_FEEDS.put("rg", Collections.singletonList(
                "https://rg.ru/xml/index.xml"
        ));

        RSS_FEEDS.put("kommersant", Collections.singletonList(
                "https://www.kommersant.ru/rss/news.xml"
        ));

        RSS_FEEDS.put("tass", Collections.singletonList(
                "https://tass.ru/rss/v2.xml"
        ));

        Map<String, List<String>> lowerCaseMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : RSS_FEEDS.entrySet()) {
            lowerCaseMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        RSS_FEEDS.clear();
        RSS_FEEDS.putAll(lowerCaseMap);
    }

    public static List<String> getFeedsForWebsite(String website) {
        return RSS_FEEDS.getOrDefault(website.toLowerCase(), Collections.emptyList());
    }

    public static Set<String> getAllWebsites() {
        return RSS_FEEDS.keySet();
    }

    public static int getTotalFeedCount() {
        return RSS_FEEDS.values().stream().mapToInt(List::size).sum();
    }
}