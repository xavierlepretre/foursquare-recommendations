package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class Venue implements Parcelable
{
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";

    @JsonProperty(KEY_ID)
    @NonNull abstract public VenueId getId();

    @JsonProperty(KEY_NAME)
    @NonNull abstract public String getName();

    @JsonProperty(KEY_LOCATION)
    @NonNull abstract public Location getLocation();

    @JsonCreator static Venue create(
            @JsonProperty(KEY_ID) @NonNull VenueId id,
            @JsonProperty(KEY_NAME) @NonNull String name,
            @JsonProperty(KEY_LOCATION) @NonNull Location location)
    {
        return new AutoParcel_Venue(id, name, location);
    }
}
