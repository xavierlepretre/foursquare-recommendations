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

    @NonNull abstract public VenueId getId();
    @NonNull abstract public String getName();

    @JsonCreator static Venue create(
            @JsonProperty(KEY_ID) @NonNull VenueId id,
            @JsonProperty(KEY_NAME) @NonNull String name)
    {
        return new AutoParcel_Venue(id, name);
    }
}
