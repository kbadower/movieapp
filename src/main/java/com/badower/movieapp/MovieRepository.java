package com.badower.movieapp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("select m from Movie m where(:year is null or m.year = :year) and (:title is null or trim(:title) = '' or m.title = :title)")
    List<Movie> findByYearAndTitle(@Param("year") Integer year, @Param("title") String title);
}

