package com.mccomb.movieratings.webservice.model;

import lombok.experimental.Delegate;

public class MovieReviewEntity {
    @Delegate
    private MovieReview movieReview;
    
    public MovieReviewEntity() {
        this.movieReview = new MovieReview();
        movieReview.setMovie(new Movie());
        movieReview.setUser(new User());
    }
    
    public void setMovieId(String movieId) {
        movieReview.getMovie().setId(movieId);
    }
    
    public void setUserId(String userId) {
        movieReview.getUser().setId(userId);
    }
    
    public String getMovieId() {
        return movieReview.getMovie().getId();
    }
    
    public String getUserId() {
        return movieReview.getUser().getId();
    }
}
