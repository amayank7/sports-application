package com.sportsapp.newsservice.service;

import com.sportsapp.newsservice.repository.MatchRepository;
import com.sportsapp.newsservice.repository.NewsRepository;
import com.sportsapp.newsservice.repository.SportRepository;
import com.sportsapp.newsservice.repository.TourRepository;
import com.sportsapp.newsservice.dto.request.CreateNewsRequestDto;
import com.sportsapp.newsservice.entity.Match;
import com.sportsapp.newsservice.entity.News;
import com.sportsapp.newsservice.entity.Sport;
import com.sportsapp.newsservice.entity.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TourRepository tourRepository;

    @Autowired
    SportRepository sportRepository;

    public News createNews(CreateNewsRequestDto request){

        News news = News.builder().title(request.getTitle()).description(request.getDescription()).build();

        switch (request.getReferenceType()){
            case MATCH -> {
                Match match = matchRepository.findById(request.getReferenceId()).orElseThrow();
                updateMatch(news, match);
            }

            case TOUR -> {
                Tour tour = tourRepository.findById(request.getReferenceId()).orElseThrow();
                updateTour(news, tour);
            }

            case SPORT -> {
                Sport sport = sportRepository.findById(request.getReferenceId()).orElseThrow();
                updateSport(news, sport);
            }
        }

        return newsRepository.save(news);
    }

    private void updateMatch(News news, Match match) {
        news.setMatch(match);
        updateTour(news, match.getTour());
    }

    private void updateTour(News news, Tour tour) {
        news.setTour(tour);
        updateSport(news, tour.getSport());
    }

    private void updateSport(News news, Sport sport) {
        news.setSport(sport);
    }


    public List<News> getNewsByTourId(Long id) {
        return newsRepository.findByTourId(id);
    }

    public List<News> getNewsBySportId(Long id) {
        return newsRepository.findBySportId(id);
    }

    public List<News> getNewsByMatchId(Long id) {
        return newsRepository.findByMatchId(id);
    }
}
