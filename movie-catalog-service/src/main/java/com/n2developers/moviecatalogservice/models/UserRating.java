package com.n2developers.moviecatalogservice.models;

import java.util.List;

public class UserRating {
    private List<Rating> userRating;

    UserRating(){}


    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
