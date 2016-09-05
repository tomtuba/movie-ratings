package com.mccomb.movieratings.dao;

import static java.util.Collections.singletonMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.mccomb.movieratings.webservice.model.Movie;
import com.mccomb.movieratings.webservice.model.MovieReview;
import com.mccomb.movieratings.webservice.model.MovieReviewEntity;
import com.mccomb.movieratings.webservice.model.User;
import com.mccomb.movieratings.webservice.model.UserReview;

@Component
public class MovieDAO {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Movie> getTopTenMoviesByRating() {
        return jdbcTemplate.query(
            " SELECT M.ID, M.NAME, M.GENRE,                              " +
            "     ROUND(AVG(CAST(R.RATING AS FLOAT)), 2) AVERAGE_RATING  " +
            "   FROM MOVIE M, MOVIE_REVIEW R                             " +
            "  WHERE M.ID = R.MOVIE_ID                                   " +
            "   GROUP BY M.ID                                            " +
            "   ORDER BY AVERAGE_RATING DESC                             " +
            "      LIMIT 10                                              ",

            new BeanPropertyRowMapper<>(Movie.class)
        );
    }

    public List<MovieReview> getReviewsForUser(String userId) {
        return jdbcTemplate.query(
            " SELECT M.ID MOVIE_ID, M.NAME MOVIE_NAME,  " +
            "     M.GENRE MOVIE_GENRE, R.RATING         " +
            "   FROM MOVIE M, MOVIE_REVIEW R            " + 
            "   WHERE M.ID = R.MOVIE_ID                 " + 
            "    AND R.USER_ID = :userId                " + 
            "    ORDER BY M.NAME                        ",
            
            singletonMap("userId", userId), 
            
            new BeanPropertyRowMapper<>(UserReview.class)
        ).stream().map(r -> r.getReview()).collect(Collectors.toList());
    }

    public List<Movie> getTopTwentyEveningMovies() {
        return jdbcTemplate.query(
            " SELECT M.ID, M.NAME, M.GENRE,                               " +
            "     ROUND(AVG(CAST(R.RATING AS FLOAT)), 2) AVERAGE_RATING   " +
            "   FROM MOVIE M, MOVIE_REVIEW R                              " +
            "   WHERE M.ID = R.MOVIE_ID                                   " +
            "    AND HOUR(R.TIME_WATCHED) >= 18                           " +
            "    GROUP BY M.ID                                            " +
            "    ORDER BY AVERAGE_RATING DESC                             " +
            "   LIMIT 20                                                  ",

            new BeanPropertyRowMapper<>(Movie.class)
        );
    }

    public Movie getAverageRatingForMovie(String movieId) {
        return jdbcTemplate.queryForObject(
            " SELECT M.ID, M.NAME, M.GENRE,                             " +
            "     ROUND(AVG(CAST(R.RATING AS FLOAT)), 2) AVERAGE_RATING " +
            "   FROM MOVIE M, MOVIE_REVIEW R                            " +
            "   WHERE M.ID = R.MOVIE_ID                                 " +
            "    AND M.ID = :movieId                                    " +
            "    GROUP BY M.ID                                          " +
            "    ORDER BY AVERAGE_RATING DESC                           ",

            singletonMap("movieId", movieId),

            new BeanPropertyRowMapper<>(Movie.class)
       );
    }
    
    public List<Movie> getTopFiveMoviesInGenre(String genre, String userId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("genre", genre);
        params.put("userId", userId);
        
        return jdbcTemplate.query(
                
            " SELECT M.ID, M.NAME, M.GENRE,                             " +
            "     ROUND(AVG(CAST(R.RATING AS FLOAT)), 2) AVERAGE_RATING " +
            "   FROM MOVIE M, MOVIE_REVIEW R, USER U, USER U2           " +
            "   WHERE M.ID = R.MOVIE_ID                                 " +
            "    AND M.GENRE = :genre                                   " +
            "    AND U.ID = R.USER_ID                                   " +
            "    AND U2.ID = :userId                                    " +
            "    AND NOT U2.ID = U.ID                                   " +
            "    AND U.DOB > U2.DOB - INTERVAL 5 YEAR                   " +
            "    AND U.DOB < U2.DOB + INTERVAL 5 YEAR                   " +
            "   GROUP BY M.ID                                           " +
            "    ORDER BY AVERAGE_RATING DESC                           " +
            "   LIMIT 5                                                 ",

            params,

            new BeanPropertyRowMapper<>(Movie.class)
        );
    }
    
    public void addMovie(Movie movie) {
        movie.setId(UUID.randomUUID().toString());
        jdbcTemplate.update(
                
            " INSERT INTO MOVIE         " +
            "    (ID, NAME, GENRE)      " +
            "      VALUES               " +
            "    (:id, :name, :genre)   ", 
            
            new BeanPropertySqlParameterSource(movie)
        );
    }
    
    public void addUser(User user) {
        user.setId(UUID.randomUUID().toString());
        jdbcTemplate.update(
                
            " INSERT INTO USER          " +
            "    (ID, NAME, DOB)        " +
            "      VALUES               " +
            "    (:id, :name, :dob)     ", 
            
            new BeanPropertySqlParameterSource(user)
        );
    }
    
    public void addReview(MovieReviewEntity entity) {
        
        jdbcTemplate.update(
                
            " INSERT INTO MOVIE_REVIEW          " +
            "    (MOVIE_ID, USER_ID, RATING)    " +
            "      VALUES                       " +
            "    (:movieId, :userId, :rating)   ", 
            
            new BeanPropertySqlParameterSource(entity)
        );
    }
}
