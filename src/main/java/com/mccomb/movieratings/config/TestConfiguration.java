package com.mccomb.movieratings.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages={"com.mccomb.movieratings.dao"})
@Import(DataSourceConfig.class)
public class TestConfiguration {
    
}
