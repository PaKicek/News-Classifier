package org.pakicek.parser.site;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.pakicek.model.NewsItem;
import org.pakicek.parser.BaseRssParser;
import org.w3c.dom.Element;

/**
 * Parser for RG RSS feed
 * URL: <a href="https://rg.ru/xml/index.xml">...</a>
 */
public class RgRssParser extends BaseRssParser {
    private static final String RSS_URL = "https://rg.ru/xml/index.xml";
    private static final String WEBSITE_NAME = "RG";

    public RgRssParser() {
        super(RSS_URL, WEBSITE_NAME);
    }

    @Override
    protected String downloadRss() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(rssUrl);

            // RG.ru headers
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            request.setHeader("Accept", "application/xml, text/xml, */*;q=0.9");
            request.setHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            request.setHeader("Referer", "https://rg.ru/");
            request.setHeader("Connection", "keep-alive");

            return httpClient.execute(request, response -> {
                int statusCode = response.getCode();

                if (statusCode != 200) {
                    // RG might return 403/406 without proper headers
                    String errorMessage = "HTTP " + statusCode + " for Rossiyskaya Gazeta";

                    // Provide helpful suggestions based on status code
                    if (statusCode == 403) {
                        errorMessage += "(Access Forbidden). Try different User-Agent.";
                    } else if (statusCode == 406) {
                        errorMessage += "(Not Acceptable). Check Accept headers.";
                    }

                    try {
                        throw new Exception(errorMessage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                if (response.getEntity() == null) {
                    try {
                        throw new Exception("Empty response from RG");
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

            // RG.ru often doesn't include author in RSS
            String author = getElementText(itemElement, "author");
            if (author.isEmpty()) {
                author = getElementTextWithNamespace(itemElement, "dc:creator");
                if (author.isEmpty()) {
                    // Sometimes author is in description or not included
                    author = "RG";
                }
            }
            newsItem.setAuthor(author);

            // Publication date - RG uses standard pubDate
            String pubDateStr = getElementText(itemElement, "pubDate");
            if (!pubDateStr.isEmpty()) {
                newsItem.setPublicationDate(parseDate(pubDateStr));
                newsItem.calculateRelevanceByDate();
            }

            newsItem.setLink(getElementText(itemElement, "link"));

            // Category - RG uses <category> tag
            String category = getElementText(itemElement, "category");
            newsItem.setCategory(category);

            // Image extraction - RG uses various methods
            String pictureLink = extractRgImage(itemElement);
            newsItem.setPictureLink(pictureLink);

            return newsItem;

        } catch (Exception e) {
            System.err.println("Error parsing RG item: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extract image from RG.ru RSS
     * RG uses different methods: enclosure, media:content, or images in description
     */
    private String extractRgImage(Element itemElement) {
        // First try enclosure
        String pictureLink = extractPictureLink(itemElement);

        if (pictureLink.isEmpty()) {
            // Try media:content (RG sometimes uses this)
            pictureLink = extractRgMediaContent(itemElement);
        }

        if (pictureLink.isEmpty()) {
            // Try description (HTML with <img> tags)
            pictureLink = extractImageFromDescription(itemElement);
        }

        if (pictureLink.isEmpty()) {
            // RG sometimes stores image in yandex:full-text or other namespaces
            pictureLink = extractRgNamespaceImages(itemElement);
        }

        return pictureLink;
    }

    /**
     * Extract image from RG-specific media tags
     */
    private String extractRgMediaContent(Element itemElement) {
        // Check yandex:full-text namespace (used by some Russian sites)
        org.w3c.dom.NodeList yandexFullText = itemElement.getElementsByTagName("yandex:full-text");
        for (int i = 0; i < yandexFullText.getLength(); i++) {
            org.w3c.dom.Node node = yandexFullText.item(i);
            String text = node.getTextContent();
            if (text != null && text.contains("<img")) {
                // Extract first image from HTML
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(
                        "<img[^>]+src=[\"']([^\"'>]+)[\"']").matcher(text);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        }

        // Check media:content
        return extractPictureLink(itemElement);
    }

    /**
     * Extract images from RG-specific namespaces
     */
    private String extractRgNamespaceImages(Element itemElement) {
        // RG might use custom namespaces for images
        String[] possibleTags = {
                "enclosure",
                "media:thumbnail",
                "media:content",
                "yandex:logo",
                "image"
        };

        for (String tag : possibleTags) {
            org.w3c.dom.NodeList nodes = itemElement.getElementsByTagName(tag);
            for (int i = 0; i < nodes.getLength(); i++) {
                org.w3c.dom.Node node = nodes.item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element elem = (org.w3c.dom.Element) node;
                    // Check common attributes for image URLs
                    String[] attributes = {"url", "src", "href", "file"};
                    for (String attr : attributes) {
                        String value = elem.getAttribute(attr);
                        if (!value.isEmpty() && (value.startsWith("http") || value.startsWith("//"))) {
                            // Ensure it's an image
                            if (value.matches(".*\\.(jpg|jpeg|png|gif|webp)(\\?.*)?$") ||
                                    elem.getAttribute("type").startsWith("image/")) {
                                // Add protocol if needed
                                if (value.startsWith("//")) {
                                    value = "https:" + value;
                                }
                                return value;
                            }
                        }
                    }
                }
            }
        }
        return "";
    }
}