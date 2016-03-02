package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class RecommendedVenuesGroup implements Parcelable
{
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_ITEMS = "items";

    @JsonProperty(KEY_TYPE)
    @NonNull abstract public GroupType getType();

    @JsonProperty(KEY_NAME)
    @NonNull abstract public String getName();

    @JsonProperty(KEY_ITEMS)
    @NonNull abstract public List<RecommendedVenue> getItems();

    @JsonCreator
    @NonNull public static RecommendedVenuesGroup create(
            @JsonProperty(KEY_TYPE) @NonNull GroupType type,
            @JsonProperty(KEY_NAME) @NonNull String name,
            @JsonProperty(KEY_ITEMS) @NonNull List<RecommendedVenue> items)
    {
        return new AutoParcel_RecommendedVenuesGroup(type, name, items);
    }
}
