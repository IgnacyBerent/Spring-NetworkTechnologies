package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.news.GetNewsDto;
import edu.lb.spring_networktechnologies.services.NewsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@PreAuthorize("isAuthenticated()")
@Tag(name = "News", description = "Endpoint for scraping news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Fetch news from the BBC website and return top 3 of them
     * @return List of GetNewsDto objects containing information about the news
     */
    @GetMapping("/fetch")
    @ApiResponse(responseCode = "200", description = "News Fetched")
    public ResponseEntity<List<GetNewsDto>> fetchNews() {
        return new ResponseEntity<>(newsService.fetchNews(), HttpStatus.OK);
    }
}