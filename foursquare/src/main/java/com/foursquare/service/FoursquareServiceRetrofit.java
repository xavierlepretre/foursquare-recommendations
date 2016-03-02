package com.foursquare.service;

import android.support.annotation.NonNull;

import com.foursquare.model.ExploreResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

interface FoursquareServiceRetrofit
{
    String PREFIX = "/v2";
    String KEY_CLIENT_ID = "client_id";
    String KEY_CLIENT_SECRET = "client_secret";
    String KEY_VERSION = "v";
    String KEY_LAT_LON = "ll";
    String KEY_NEAR = "near";

    @GET(PREFIX + "/venues/explore")
    Observable<ExploreResponse> exploreVenues(
            @NonNull @Query(KEY_CLIENT_ID) String clientId,
            @NonNull @Query(KEY_CLIENT_SECRET)String clientSecret,
            @NonNull @Query(KEY_VERSION) String version,
            @NonNull @Query(KEY_LAT_LON) String latLng);

    @GET(PREFIX + "/venues/explore")
    Observable<ExploreResponse> exploreVenuesByNear(
            @NonNull @Query(KEY_CLIENT_ID) String clientId,
            @NonNull @Query(KEY_CLIENT_SECRET)String clientSecret,
            @NonNull @Query(KEY_VERSION) String version,
            @NonNull @Query(KEY_NEAR) String near);
}
