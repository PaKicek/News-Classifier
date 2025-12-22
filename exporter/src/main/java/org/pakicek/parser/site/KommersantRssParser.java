package org.pakicek.parser.site;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.pakicek.model.NewsItem;
import org.pakicek.parser.BaseRssParser;
import org.w3c.dom.Element;

/**
 * Parser for Kommersant RSS feed
 */
public class KommersantRssParser extends BaseRssParser {
    private static final String RSS_URL = "https://www.kommersant.ru/rss/news.xml";
    private static final String WEBSITE_NAME = "Kommersant";

    public KommersantRssParser() {
        super(RSS_URL, WEBSITE_NAME);
    }

    @Override
    protected String downloadRss() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(rssUrl);
            request.setHeader("User-Agent", "Mozilla/5.0");
            request.setHeader("Accept", "application/xml, text/xml");

            return httpClient.execute(request, response -> {
                if (response.getCode() != 200) {
                    try {
                        throw new Exception("HTTP error: " + response.getCode());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                if (response.getEntity() == null) {
                    try {
                        throw new Exception("Empty response");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                return EntityUtils.toString(response.getEntity(), java.nio.charset.StandardCharsets.UTF_8);
            });
        }
    }

    @Override
    protected NewsItem parseNewsItem(Element itemElement) {
        NewsItem newsItem = new NewsItem();

        try {
            newsItem.setWebsite(websiteName);
            newsItem.setTitle(getElementText(itemElement, "title"));

            String author = getElementText(itemElement, "author");
            if (author.isEmpty()) {
                author = getElementTextWithNamespace(itemElement, "dc:creator");
            }
            newsItem.setAuthor(author);

            String pubDateStr = getElementText(itemElement, "pubDate");
            if (!pubDateStr.isEmpty()) {
                newsItem.setPublicationDate(parseDate(pubDateStr));
                newsItem.calculateRelevanceByDate();
            }

            newsItem.setLink(getElementText(itemElement, "link"));
            newsItem.setCategory(getElementText(itemElement, "category"));

            String pictureLink = extractPictureLink(itemElement);
            if (pictureLink.isEmpty()) {
                pictureLink = extractImageFromDescription(itemElement);
            }
            newsItem.setPictureLink(pictureLink);

            return newsItem;

        } catch (Exception e) {
            System.err.println("Error parsing item: " + e.getMessage());
            return null;
        }
    }
}