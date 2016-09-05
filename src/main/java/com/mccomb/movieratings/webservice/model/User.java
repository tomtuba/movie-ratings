package com.mccomb.movieratings.webservice.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class User {
	private String id;
	private String name;
	private LocalDate dob;
}
