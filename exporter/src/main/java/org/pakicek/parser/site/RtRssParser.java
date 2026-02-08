package org.pakicek.parser.site;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.pakicek.model.NewsItem;
import org.pakicek.parser.MultiFeedRssParser;
import org.w3c.dom.Element;

public class RtRssParser extends MultiFeedRssParser {

    public RtRssParser() {
        super("RT");
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
                        throw new Exception("HTTP " + response.getCode() + " for " + rssUrl);
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
            newsItem.setWebsite(getWebsiteName());
            newsItem.setTitle(getElementText(itemElement, "title"));

            String author = getElementTextWithNamespace(itemElement, "dc:creator");
            if (author.isEmpty()) {
                author = getElementText(itemElement, "author");
            }
            newsItem.setAuthor(author);

            String pubDateStr = getElementText(itemElement, "pubDate");
            if (!pubDateStr.isEmpty()) {
                newsItem.setPublicationDate(parseDate(pubDateStr));
                newsItem.calculateRelevanceByDate();
            }

            newsItem.setLink(getElementText(itemElement, "link"));

            String category = getElementText(itemElement, "category");
            if (category.isEmpty()) {
                category = extractRtCategoryFromUrl();
            }
            newsItem.setCategory(category);

            String pictureLink = extractRtMediaContent(itemElement);
            if (pictureLink.isEmpty()) {
                pictureLink = extractPictureLink(itemElement);
            }
            if (pictureLink.isEmpty()) {
                pictureLink = extractImageFromDescription(itemElement);
            }
            newsItem.setPictureLink(pictureLink);

            return newsItem;

        } catch (Exception e) {
            System.err.println("Error parsing RT item: " + e.getMessage());
            return null;
        }
    }

    private String extractRtCategoryFromUrl() {
        if (rssUrl.contains("/rss/")) {
            String path = rssUrl.substring(rssUrl.indexOf("/rss/") + 5);
            if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
            return switch (path) {
                case "news" -> "Новости";
                case "russia" -> "Россия";
                case "business" -> "Бизнес";
                case "op-ed" -> "Мнения";
                case "pop-culture" -> "Поп-культура";
                default -> path;
            };
        }
        return "";
    }

    private String extractRtMediaContent(Element itemElement) {
        org.w3c.dom.NodeList mediaThumbnails = itemElement.getElementsByTagName("media:thumbnail");
        for (int i = 0; i < mediaThumbnails.getLength(); i++) {
            org.w3c.dom.Node node = mediaThumbnails.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element mediaElement = (org.w3c.dom.Element) node;
                String url = mediaElement.getAttribute("url");
                if (!url.isEmpty()) {
                    return url;
                }
            }
        }
        return "";
    }
}