package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class SuggestedBounds implements Parcelable
{
    public static final String KEY_NORTH_EAST = "ne";
    public static final String KEY_SOUT_WEST = "sw";

    @JsonProperty(KEY_NORTH_EAST)
    @NonNull abstract public Location getNorthEast();

    @JsonProperty(KEY_SOUT_WEST)
    @NonNull abstract public Location getSouthWest();

    @JsonCreator public static SuggestedBounds create(
            @JsonProperty(KEY_NORTH_EAST) @NonNull Location northEast,
            @JsonProperty(KEY_SOUT_WEST) @NonNull Location southWest)
    {
        return new AutoParcel_SuggestedBounds(northEast, southWest);
    }
}
