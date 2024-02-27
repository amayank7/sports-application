package com.sportsapp.newsservice.controller;

import com.sportsapp.newsservice.dto.request.CreateNewsRequestDto;
import com.sportsapp.newsservice.entity.News;
import com.sportsapp.newsservice.entity.Sport;
import com.sportsapp.newsservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @PostMapping()
    public News createNews(@RequestBody CreateNewsRequestDto request){
        return newsService.createNews(request);
    }

    @GetMapping("tour")
    public List<News> getNewsByTourId(@RequestParam Long id){
        return newsService.getNewsByTourId(id);
    }

    @GetMapping("sport")
    public List<News> getNewsBySportId(@RequestParam Long id){
        return newsService.getNewsBySportId(id);
    }

    @GetMapping("match")
    public List<News> getNewsByMatchId(@RequestParam Long id){
        return newsService.getNewsByMatchId(id);
    }

}
