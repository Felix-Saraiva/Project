/*-----------------------------------------------------------------------------
 - Developed by Felix Saraiva                                              -
 - Last modified 3/17/19 5:24 AM                                              -                        -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.thunder.project.api;

import com.thunder.project.model.Locations;
import com.thunder.project.model.Places;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MonumentApi {

    //GET Monuments Request
    @GET("/%3fPlace.json?print=pretty")
    Call<Places> getPlace();
    //GET Countries List Request
    @GET("/%3fLocation.json?print=pretty")
    Call<Locations> getLocations();
    //Get a Country Request
    @GET("/Countries/{l}")
    Call<Places> getPlaceByLocation(@Path("l")String location);
    //Get a Monument Request
    @GET("/Monuments/{m}")
    Call<Places> getPlacesByName(@Path("m") String placeName);

}
