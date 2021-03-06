package com.n2developers.moviecatalogservice.resources;

import com.n2developers.moviecatalogservice.models.CatalogItem;
import com.n2developers.moviecatalogservice.models.Movie;
import com.n2developers.moviecatalogservice.models.Rating;
import com.n2developers.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){


        //get all rated movie IDs
        UserRating rating=restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);

        return  rating.getUserRating().stream().map(rating1 -> {
            Movie movie= restTemplate.getForObject("http://movie-info-service/movies/"+rating1.getMovieId(), Movie.class);

//            Movie movie=webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/"+rating1.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();


            return new CatalogItem(movie.getName(),"test desc",rating1.getRating());

        }).collect(Collectors.toList());
        //For each movie ID, call movie info service and get details

        //put them all together



    }
}
