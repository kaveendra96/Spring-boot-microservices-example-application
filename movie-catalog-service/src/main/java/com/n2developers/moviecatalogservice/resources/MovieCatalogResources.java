package com.n2developers.moviecatalogservice.resources;

import com.n2developers.moviecatalogservice.models.CatalogItem;
import com.n2developers.moviecatalogservice.models.Movie;
import com.n2developers.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        //get all rated movie IDs
        List<Rating> rating= Arrays.asList(
            new Rating("0001",3),
            new Rating("0002",4)
        );

        return  rating.stream().map(rating1 -> {
            Movie movie= restTemplate.getForObject("http://localhost:8082/movies/"+rating1.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(),"test desc",rating1.getRating());

        }).collect(Collectors.toList());
        //For each movie ID, call movie info service and get details

        //put them all together



    }
}
