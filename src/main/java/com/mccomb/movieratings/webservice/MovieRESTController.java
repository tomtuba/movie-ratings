package com.mccomb.movieratings.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mccomb.movieratings.dao.MovieDAO;
import com.mccomb.movieratings.webservice.exception.MovieNotFoundException;
import com.mccomb.movieratings.webservice.model.Movie;
import com.mccomb.movieratings.webservice.model.ReviewList;

@RestController
public class MovieRESTController {
	@Autowired
	private MovieDAO movieDAO;

	@GetMapping(value = "/age")
	public List<Movie> getTopTenMovies() {
		return movieDAO.getTopTenMoviesByRating();
	}

	@GetMapping(value = "/user/{userId}")
	public ReviewList getRatingsForUser(@PathVariable("userId") String userId) {
	    ReviewList list = new ReviewList();
	    list.setReviews(movieDAO.getReviewsForUser(userId));
	    return list;
	}
	
	@GetMapping(value = "/state")
	public List<Movie> getTopTwentyEveningMovies() {
		return movieDAO.getTopTwentyEveningMovies();
	}
	
	@GetMapping(value = "/movie/{movieId}")
    public Movie getAverageRatingForMovie(@PathVariable("movieId") String movieId) {
        try {
            return movieDAO.getAverageRatingForMovie(movieId);
        } catch (DataAccessException e) {
            throw new MovieNotFoundException();
        }
    }
	
	@GetMapping(value = "/genre/{genre}/user/{userId}")
    public List<Movie> getTopFiveMoviesInGenre(@PathVariable("genre") String genre, @PathVariable("userId") String userId) {
        return movieDAO.getTopFiveMoviesInGenre(genre, userId);
    }

}
