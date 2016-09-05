package com.mccomb.movieratings.webservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Movie {
	private String id;
	private String genre;
	private String name;
	private Double averageRating;
}
