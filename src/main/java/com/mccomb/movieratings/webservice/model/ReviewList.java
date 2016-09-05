package com.mccomb.movieratings.webservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"count","reviews"})
public class ReviewList {
    public int getCount() {
        return reviews == null ? null : reviews.size(); 
    }
    private List<MovieReview> reviews;
}
