package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.news.GetNewsDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class NewsService {

    /**
     * Method for fetching top news from BBC
     *
     * @return List of GetNewsDto objects containing information about the news
     */
    public List<GetNewsDto> fetchNews() {
        RestTemplate restTemplate = new RestTemplate();
        String bbcNews = restTemplate.getForObject("https://www.bbc.com/news/topics/cq23pdgvyn7t", String.class);

        Document doc = Jsoup.parse(bbcNews);
        Elements newsItems = doc.select("div[data-testid=liverpool-card]");

        List<GetNewsDto> topNews = new ArrayList<>();

        if (!newsItems.isEmpty()) {
            for (int i = 0; i < Math.min(3, newsItems.size()); i++) {
                Element newsItem = newsItems.get(i);
                String title = newsItem.select("h2[data-testid=card-headline]").text();
                String description = newsItem.select("p[data-testid=card-description]").text();
                String link = "bbc.com" + newsItem.select("a[data-testid=internal-link]").attr("href");
                String[] imageSrcSet = newsItem.select("img").attr("srcset").split(",");
                String image = imageSrcSet[1].split(" ")[0];

                GetNewsDto news = new GetNewsDto(title, description, link, image);
                topNews.add(news);
            }
        }

        return topNews;
    }
}