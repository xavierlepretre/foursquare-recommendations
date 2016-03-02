package com.foursquare.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class Location
{
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LONGITUDE = "lng";

    @JsonProperty(KEY_ADDRESS)
    @Nullable abstract public String getAddress();

    @JsonProperty(KEY_LATITUDE)
    abstract public double getLat();

    @JsonProperty(KEY_LONGITUDE)
    abstract public double getLon();

    @JsonIgnore
    @NonNull public String getQueryForm()
    {
        return new StringBuilder()
                .append(getLat())
                .append(",")
                .append(getLon())
                .toString();
    }

    @JsonCreator
    @NonNull public static Location create(
            @JsonProperty(KEY_ADDRESS) @Nullable String address,
            @JsonProperty(KEY_LATITUDE) double lat,
            @JsonProperty(KEY_LONGITUDE) double lon)
    {
        return new AutoParcel_Location(address, lat, lon);
    }
}
