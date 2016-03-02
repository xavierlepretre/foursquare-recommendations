package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class RecommendedVenue implements Parcelable
{
    private static final String KEY_VENUE = "venue";
    private static final String KEY_REFERRAL_ID = "referralId";

    @JsonProperty(KEY_VENUE)
    @NonNull abstract public Venue getVenue();

    @JsonProperty(KEY_REFERRAL_ID)
    @NonNull abstract public ReferralId getReferralId();

    @JsonCreator static RecommendedVenue create(
            @JsonProperty(KEY_VENUE) @NonNull Venue venue,
            @JsonProperty(KEY_REFERRAL_ID) @NonNull ReferralId referralId)
    {
        return new AutoParcel_RecommendedVenue(venue, referralId);
    }
}
