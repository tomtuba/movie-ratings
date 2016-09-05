package com.mccomb.movieratings.webservice.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class MovieReview {
	private User user;
	private Movie movie;
	private int rating;
	private ZonedDateTime timeWatched;
}
