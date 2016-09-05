package com.mccomb.movieratings.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={"com.mccomb.movieratings"})
@EnableWebMvc
@Import(DataSourceConfig.class)
public class RatingsConfiguration {

}
