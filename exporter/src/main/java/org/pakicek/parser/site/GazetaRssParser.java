package org.pakicek.parser.site;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.pakicek.model.NewsItem;
import org.pakicek.parser.BaseRssParser;
import org.w3c.dom.Element;

/**
 * Parser for Gazeta.ru RSS feed
 * URL: <a href="https://www.gazeta.ru/export/rss/first.xml">...</a>
 */
public class GazetaRssParser extends BaseRssParser {
    private static final String RSS_URL = "https://www.gazeta.ru/export/rss/first.xml";
    private static final String WEBSITE_NAME = "Gazeta.ru";

    public GazetaRssParser() {
        super(RSS_URL, WEBSITE_NAME);
    }

    @Override
    protected String downloadRss() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(rssUrl);

            // Gazeta.ru requires proper headers
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (HTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            request.setHeader("Accept", "application/xml, text/xml, application/rss+xml, */*;q=0.9");
            request.setHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            request.setHeader("Connection", "keep-alive");

            return httpClient.execute(request, response -> {
                int statusCode = response.getCode();

                if (statusCode != 200) {
                    String errorMessage = "HTTP " + statusCode + " for " + rssUrl;

                    // Try to get error details
                    try {
                        if (response.getEntity() != null) {
                            String errorBody = EntityUtils.toString(response.getEntity());
                            if (errorBody != null && !errorBody.isEmpty()) {
                                errorMessage += "\nResponse preview: " +
                                        errorBody.substring(0, Math.min(200, errorBody.length()));
                            }
                        }
                    } catch (Exception ignored) {}

                    try {
                        throw new Exception(errorMessage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                if (response.getEntity() == null) {
                    try {
                        throw new Exception("Empty response from Gazeta.ru");
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
            newsItem.setWebsite(WEBSITE_NAME);
            newsItem.setTitle(getElementText(itemElement, "title"));

            // Gazeta.ru uses <author> tag
            String author = getElementText(itemElement, "author");
            if (author.isEmpty()) {
                author = getElementTextWithNamespace(itemElement, "dc:creator");
            }
            newsItem.setAuthor(author);

            // Publication date
            String pubDateStr = getElementText(itemElement, "pubDate");
            if (!pubDateStr.isEmpty()) {
                newsItem.setPublicationDate(parseDate(pubDateStr));
                newsItem.calculateRelevanceByDate();
            }

            newsItem.setLink(getElementText(itemElement, "link"));

            // Category - Gazeta.ru uses <category> tag
            String category = getElementText(itemElement, "category");
            newsItem.setCategory(category);

            // Image - Gazeta.ru uses enclosure with type="image/jpeg"
            String pictureLink = extractPictureLink(itemElement);
            if (pictureLink.isEmpty()) {
                // Try media:content (sometimes used)
                pictureLink = extractGazetaMediaContent(itemElement);
            }
            if (pictureLink.isEmpty()) {
                pictureLink = extractImageFromDescription(itemElement);
            }
            newsItem.setPictureLink(pictureLink);

            return newsItem;

        } catch (Exception e) {
            System.err.println("Error parsing Gazeta.ru item: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extract image from Gazeta.ru specific media tags
     */
    private String extractGazetaMediaContent(Element itemElement) {
        // Check for <media:content> tags
        org.w3c.dom.NodeList mediaContents = itemElement.getElementsByTagName("media:content");
        for (int i = 0; i < mediaContents.getLength(); i++) {
            org.w3c.dom.Node node = mediaContents.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element mediaElement = (org.w3c.dom.Element) node;
                String type = mediaElement.getAttribute("type");
                String url = mediaElement.getAttribute("url");

                if (type.startsWith("image/") && !url.isEmpty()) {
                    return url;
                }
            }
        }

        // Check for <enclosure> with image
        return extractPictureLink(itemElement);
    }
}