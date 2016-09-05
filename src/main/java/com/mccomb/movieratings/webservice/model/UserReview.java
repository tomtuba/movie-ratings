package com.mccomb.movieratings.webservice.model;

import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class UserReview {
	@Delegate
	private final MovieReview review;
	
	public UserReview() {
		this.review = new MovieReview();
		this.review.setMovie(new Movie());
	}
	
	public void setMovieName(String movieName) {
		this.review.getMovie().setName(movieName);
	}
	
	public void setMovieGenre(String movieGenre) {
		this.review.getMovie().setGenre(movieGenre);
	}
	
	public void setMovieId(String movieId) {
		this.review.getMovie().setId(movieId);
	}

}
