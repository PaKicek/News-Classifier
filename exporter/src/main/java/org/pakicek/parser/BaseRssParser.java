package org.pakicek.parser;

import org.pakicek.model.NewsItem;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Base class for RSS parsers
 */
public abstract class BaseRssParser {
    protected String rssUrl;
    protected String websiteName;

    // RSS date formats
    protected static final List<String> DATE_FORMATS = Arrays.asList(
            "EEE, dd MMM yyyy HH:mm:ss Z",
            "EEE, dd MMM yyyy HH:mm:ss zzz",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd HH:mm:ss",
            "dd MMM yyyy HH:mm:ss",
            "EEE MMM dd HH:mm:ss zzz yyyy"
    );

    public BaseRssParser(String rssUrl, String websiteName) {
        this.rssUrl = rssUrl;
        this.websiteName = websiteName;
    }

    /**
     * Main method to parse RSS feed
     */
    public List<NewsItem> parseRss() {
        List<NewsItem> newsItems = new ArrayList<>();

        try {
            String rssContent = downloadRss();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(rssContent)));

            NodeList itemNodes = doc.getElementsByTagName("item");
            System.out.println("Found " + itemNodes.getLength() + " items in RSS feed");

            for (int i = 0; i < itemNodes.getLength(); i++) {
                try {
                    Element itemElement = (Element) itemNodes.item(i);
                    NewsItem newsItem = parseNewsItem(itemElement);

                    if (newsItem != null && newsItem.isValid()) {
                        newsItems.add(newsItem);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing item " + i + ": " + e.getMessage());
                }
            }

            System.out.println("Successfully parsed " + newsItems.size() + " items");

        } catch (Exception e) {
            System.err.println("Error parsing RSS: " + e.getMessage());
            throw new RuntimeException("Failed to parse RSS", e);
        }

        return newsItems;
    }

    protected abstract String downloadRss() throws Exception;
    protected abstract NewsItem parseNewsItem(Element itemElement);

    protected String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent().trim();
        }
        return "";
    }

    protected String getElementTextWithNamespace(Element parent, String qualifiedName) {
        NodeList nodes = parent.getElementsByTagName(qualifiedName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent().trim();
        }

        String[] parts = qualifiedName.split(":");
        if (parts.length == 2) {
            nodes = parent.getElementsByTagName(parts[1]);
            if (nodes.getLength() > 0) {
                return nodes.item(0).getTextContent().trim();
            }
        }

        return "";
    }

    protected String extractPictureLink(Element itemElement) {
        NodeList enclosures = itemElement.getElementsByTagName("enclosure");
        for (int i = 0; i < enclosures.getLength(); i++) {
            Element enclosure = (Element) enclosures.item(i);
            String type = enclosure.getAttribute("type");
            if (type.startsWith("image/")) {
                return enclosure.getAttribute("url");
            }
        }

        NodeList mediaContents = itemElement.getElementsByTagName("media:content");
        for (int i = 0; i < mediaContents.getLength(); i++) {
            Node node = mediaContents.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element mediaContent = (Element) node;
                String type = mediaContent.getAttribute("type");
                if (type.startsWith("image/")) {
                    return mediaContent.getAttribute("url");
                }
            }
        }

        return "";
    }

    protected String extractImageFromDescription(Element itemElement) {
        String description = getElementText(itemElement, "description");
        if (!description.isEmpty()) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                    "<img[^>]+src=[\"']([^\"'>]+)[\"']",
                    java.util.regex.Pattern.CASE_INSENSITIVE
            );
            java.util.regex.Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "";
    }

    protected LocalDateTime parseDate(String dateStr) {
        dateStr = dateStr.trim();

        for (String format : DATE_FORMATS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.US);
                return LocalDateTime.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {}
        }

        try {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.RFC_1123_DATE_TIME);
        } catch (DateTimeParseException e) {
            return LocalDateTime.now();
        }
    }

    public String getWebsiteName() {
        return websiteName;
    }
}