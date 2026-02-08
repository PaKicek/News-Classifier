package org.pakicek.parser;

import org.pakicek.model.NewsItem;
import org.pakicek.config.RssFeedConfig;

import java.util.*;

public abstract class MultiFeedRssParser extends BaseRssParser {
    public MultiFeedRssParser(String websiteName) {
        super("", websiteName);
        this.websiteName = websiteName;
    }

    public List<NewsItem> parseAllFeeds() {
        List<NewsItem> allItems = new ArrayList<>();
        List<String> feeds = RssFeedConfig.getFeedsForWebsite(getWebsiteKey());

        if (feeds.isEmpty()) {
            System.err.println("Error: no RSS feeds configured for " + getWebsiteName());
            System.err.println("Check RssFeedConfig.java configuration");
            return allItems;
        }

        System.out.println("Parsing " + feeds.size() + " RSS feeds for " + getWebsiteName());

        for (int i = 0; i < feeds.size(); i++) {
            String feedUrl = feeds.get(i);
            try {
                System.out.print("[" + (i+1) + "/" + feeds.size() + "] " + getFeedName(feedUrl) + "...\n");
                this.rssUrl = feedUrl;
                List<NewsItem> items = parseRss();
                if (items != null && !items.isEmpty()) {
                    allItems.addAll(items);
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return allItems;
    }

    private String getWebsiteKey() {
        return getWebsiteName().toLowerCase().replace(".ru", "").replace(".", "");
    }

    private String getFeedName(String url) {
        if (url.contains("/rubric/")) {
            return url.substring(url.lastIndexOf("/rubric/") + 8);
        } else if (url.contains("/rss/")) {
            String part = url.substring(url.lastIndexOf("/rss/") + 5);
            return part.isEmpty() || part.equals("/") ? "main" : part.replace("/", "");
        } else if (url.contains("/")) {
            String part = url.substring(url.lastIndexOf("/") + 1);
            return part.isEmpty() ? "main" : part;
        }
        return "main";
    }

    @Override
    protected abstract String downloadRss() throws Exception;

    @Override
    protected abstract NewsItem parseNewsItem(org.w3c.dom.Element itemElement);
}