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
    public static final String KEY_SUGGESTED_BOUNDS = "suggestedBounds";
    public static final String KEY_GROUPS = "groups";

    @JsonProperty(KEY_SUGGESTED_BOUNDS)
    @NonNull abstract public SuggestedBounds getSuggestedBounds();

    @JsonProperty(KEY_GROUPS)
    @NonNull abstract public List<RecommendedVenuesGroup> getGroups();

    @JsonCreator
    @NonNull public static RecommendedVenuesResponse create(
            @JsonProperty(KEY_SUGGESTED_BOUNDS) @NonNull SuggestedBounds suggestedBounds,
            @JsonProperty(KEY_GROUPS) @NonNull List<RecommendedVenuesGroup> groups)
    {
        return new AutoParcel_RecommendedVenuesResponse(suggestedBounds, groups);
    }
}
