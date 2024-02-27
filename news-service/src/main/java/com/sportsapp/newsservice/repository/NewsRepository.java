package com.sportsapp.newsservice.repository;

import com.sportsapp.newsservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findBySportId(Long sportId);
    List<News> findByTourId(Long tourId);
    List<News> findByMatchId(Long matchId);
}
