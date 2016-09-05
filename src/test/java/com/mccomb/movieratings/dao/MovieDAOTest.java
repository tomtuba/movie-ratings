package com.mccomb.movieratings.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.mccomb.movieratings.config.TestConfiguration;
import com.mccomb.movieratings.webservice.model.Movie;
import com.mccomb.movieratings.webservice.model.MovieReview;
import com.mccomb.movieratings.webservice.model.MovieReviewEntity;
import com.mccomb.movieratings.webservice.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class MovieDAOTest {
    private static final String USER_ID = "USER1";
    private static final String MOVIE_ID = "MOVIE1";
    private static final String GENRE = "Comedy";
    @Autowired
    private MovieDAO movieDAO;

    @Test
    public void testGetTopTenMoviesByRating() {
        List<Movie> movies = movieDAO.getTopTenMoviesByRating();
        
        assertFalse(CollectionUtils.isEmpty(movies));
    }

    @Test
    public void testGetReviewsForUser() {
        List<MovieReview> reviews = movieDAO.getReviewsForUser(USER_ID);
        
        assertFalse(CollectionUtils.isEmpty(reviews));
    }

    @Test
    public void testGetTopTwentyEveningMovies() {
        List<Movie> movies = movieDAO.getTopTwentyEveningMovies();
        assertFalse(CollectionUtils.isEmpty(movies));
    }

    @Test
    public void testGetAverageRatingForMovie() {
        Movie movie = movieDAO.getAverageRatingForMovie(MOVIE_ID);
        
        assertNotNull(movie);
    }

    @Test
    public void testGetTopFiveMoviesInGenre() {
        List<Movie> movies = movieDAO.getTopFiveMoviesInGenre(GENRE, USER_ID);
        assertFalse(CollectionUtils.isEmpty(movies));
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void testAddDuplicateUserFail() {
        User user = new User();
        user.setName("Taylor");
        
        movieDAO.addUser(user);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void testAddDuplicateMovieFail() {
        Movie movie = new Movie();
        movie.setName("Brazil");
        
        movieDAO.addMovie(movie);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void testAddDuplicateReviewFail() {
        MovieReviewEntity entity = new MovieReviewEntity();
        entity.setMovieId(MOVIE_ID);
        entity.setUserId(USER_ID);
        entity.setRating(4);
        entity.setTimeWatched(ZonedDateTime.now());
        
        movieDAO.addReview(entity);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void testAddMissingMovieReviewFail() {
        MovieReviewEntity entity = new MovieReviewEntity();
        entity.setMovieId("bad-movie-id");
        entity.setUserId(USER_ID);
        entity.setRating(4);
        entity.setTimeWatched(ZonedDateTime.now());
        
        movieDAO.addReview(entity);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void testAddMissingUserReviewFail() {
        MovieReviewEntity entity = new MovieReviewEntity();
        entity.setMovieId(MOVIE_ID);
        entity.setUserId("bad-user-id");
        entity.setRating(4);
        entity.setTimeWatched(ZonedDateTime.now());
        
        movieDAO.addReview(entity);
    }


}
