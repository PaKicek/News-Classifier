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
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            request.setHeader("Accept", "application/xml, text/xml, */*;q=0.9");
            request.setHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            request.setHeader("Referer", "https://rg.ru/");
            request.setHeader("Connection", "keep-alive");

            return httpClient.execute(request, response -> {
                int statusCode = response.getCode();

                if (statusCode != 200) {
                    String errorMessage = "HTTP " + statusCode + " for RG";
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

            String author = getElementText(itemElement, "author");
            if (author.isEmpty()) {
                author = getElementTextWithNamespace(itemElement, "dc:creator");
                if (author.isEmpty()) {
                    author = "RG";
                }
            }
            newsItem.setAuthor(author);

            String pubDateStr = getElementText(itemElement, "pubDate");
            if (!pubDateStr.isEmpty()) {
                newsItem.setPublicationDate(parseDate(pubDateStr));
                newsItem.calculateRelevanceByDate();
            }

            newsItem.setLink(getElementText(itemElement, "link"));

            String category = getElementText(itemElement, "category");
            newsItem.setCategory(category);

            String pictureLink = extractRgImage(itemElement);
            newsItem.setPictureLink(pictureLink);

            return newsItem;

        } catch (Exception e) {
            System.err.println("Error parsing RG item: " + e.getMessage());
            return null;
        }
    }

    private String extractRgImage(Element itemElement) {
        String pictureLink = extractPictureLink(itemElement);
        if (pictureLink.isEmpty()) {
            pictureLink = extractRgMediaContent(itemElement);
        }
        if (pictureLink.isEmpty()) {
            pictureLink = extractImageFromDescription(itemElement);
        }
        if (pictureLink.isEmpty()) {
            pictureLink = extractRgNamespaceImages(itemElement);
        }
        return pictureLink;
    }

    private String extractRgMediaContent(Element itemElement) {
        org.w3c.dom.NodeList yandexFullText = itemElement.getElementsByTagName("yandex:full-text");
        for (int i = 0; i < yandexFullText.getLength(); i++) {
            org.w3c.dom.Node node = yandexFullText.item(i);
            String text = node.getTextContent();
            if (text != null && text.contains("<img")) {
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("<img[^>]+src=[\"']([^\"'>]+)[\"']").matcher(text);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        }
        return extractPictureLink(itemElement);
    }

    private String extractRgNamespaceImages(Element itemElement) {
        String[] possibleTags = {"enclosure", "media:thumbnail", "media:content", "yandex:logo", "image"};

        for (String tag : possibleTags) {
            org.w3c.dom.NodeList nodes = itemElement.getElementsByTagName(tag);
            for (int i = 0; i < nodes.getLength(); i++) {
                org.w3c.dom.Node node = nodes.item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element elem = (org.w3c.dom.Element) node;
                    String[] attributes = {"url", "src", "href", "file"};
                    for (String attr : attributes) {
                        String value = elem.getAttribute(attr);
                        if (!value.isEmpty() && (value.startsWith("http") || value.startsWith("//"))) {
                            if (value.matches(".*\\.(jpg|jpeg|png|gif|webp)(\\?.*)?$") || elem.getAttribute("type").startsWith("image/")) {
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