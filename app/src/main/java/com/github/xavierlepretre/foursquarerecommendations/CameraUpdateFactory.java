package com.github.xavierlepretre.foursquarerecommendations;

import android.support.annotation.NonNull;

import com.foursquare.model.SuggestedBounds;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class CameraUpdateFactory
{
    @NonNull public static CameraUpdate create(
            @NonNull SuggestedBounds suggestedBounds,
            int padding)
    {
        return com.google.android.gms.maps.CameraUpdateFactory
                .newLatLngBounds(
                        createBounds(suggestedBounds),
                        padding);
    }

    @NonNull public static LatLngBounds createBounds(@NonNull SuggestedBounds suggestedBounds)
    {
        return new LatLngBounds(
                new LatLng(
                        suggestedBounds.getSouthWest().getLat(),
                        suggestedBounds.getSouthWest().getLon()),
                new LatLng(
                        suggestedBounds.getNorthEast().getLat(),
                        suggestedBounds.getNorthEast().getLon()));
    }
}
