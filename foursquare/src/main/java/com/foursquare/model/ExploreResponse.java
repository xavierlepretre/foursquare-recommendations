package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class ExploreResponse implements Parcelable
{
    public static final String KEY_META = "meta";
    public static final String KEY_RESPONSE = "response";

    @JsonProperty(KEY_META)
    @NonNull abstract ResponseMeta getMeta();

    @JsonProperty(KEY_RESPONSE)
    @NonNull abstract RecommendedVenuesResponse getResponse();

    @JsonCreator
    @NonNull static ExploreResponse create(
            @JsonProperty(KEY_META) @NonNull ResponseMeta meta,
            @JsonProperty(KEY_RESPONSE) @NonNull RecommendedVenuesResponse response)
    {
        return new AutoParcel_ExploreResponse(meta, response);
    }
}
