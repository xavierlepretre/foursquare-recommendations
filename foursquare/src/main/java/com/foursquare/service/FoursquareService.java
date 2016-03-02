package com.foursquare.service;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foursquare.Constants;
import com.foursquare.credentials.Keys;
import com.foursquare.model.ExploreResponse;
import com.foursquare.model.Location;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

public class FoursquareService
{
    @NonNull private final FoursquareServiceRetrofit serviceRetrofit;
    @NonNull private final Keys keys;
    @NonNull private final String version;

    public FoursquareService(
            @NonNull Keys keys,
            @NonNull String version)
    {
        this(ObjectMapperFactory.create(), keys, version);
    }

    public FoursquareService(
            @NonNull ObjectMapper objectMapper,
            @NonNull Keys keys,
            @NonNull String version)
    {
        this(new Retrofit.Builder()
                        .baseUrl(Constants.END_POINT)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build()
                        .create(FoursquareServiceRetrofit.class),
                keys,
                version);
    }

    public FoursquareService(
            @NonNull FoursquareServiceRetrofit serviceRetrofit,
            @NonNull Keys keys,
            @NonNull String version)
    {
        this.serviceRetrofit = serviceRetrofit;
        this.keys = keys;
        this.version = version;
    }

    @NonNull public Observable<ExploreResponse> exploreVenues(@NonNull Location location)
    {
        return serviceRetrofit.exploreVenues(
                keys.getClientId(),
                keys.getClientSecret(),
                version,
                location.getQueryForm());
    }

    @NonNull public Observable<ExploreResponse> exploreVenuesByNear(@NonNull String location)
    {
        return serviceRetrofit.exploreVenuesByNear(
                keys.getClientId(),
                keys.getClientSecret(),
                version,
                location);
    }
}
