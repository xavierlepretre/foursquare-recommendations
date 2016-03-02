package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class RecommendedVenuesResponse implements Parcelable
{
    public static final String KEY_GROUPS = "groups";

    @JsonProperty(KEY_GROUPS)
    @NonNull abstract List<RecommendedVenuesGroup> getGroups();

    @JsonCreator
    @NonNull static RecommendedVenuesResponse create(
            @JsonProperty(KEY_GROUPS) @NonNull List<RecommendedVenuesGroup> groups)
    {
        return new AutoParcel_RecommendedVenuesResponse(groups);
    }
}
