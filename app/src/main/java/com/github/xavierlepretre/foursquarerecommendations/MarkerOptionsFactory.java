package com.github.xavierlepretre.foursquarerecommendations;

import android.support.annotation.NonNull;

import com.foursquare.model.Location;
import com.foursquare.model.RecommendedVenue;
import com.foursquare.model.RecommendedVenuesGroup;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MarkerOptionsFactory
{
    @NonNull public static List<MarkerOptions> create(@NonNull List<RecommendedVenuesGroup> groups)
    {
        List<MarkerOptions> list = new ArrayList<>();
        for (RecommendedVenuesGroup group: groups)
        {
            for (RecommendedVenue recommendedVenue : group.getItems())
            {
                list.add(create(
                        recommendedVenue.getVenue().getLocation(),
                        recommendedVenue.getVenue().getName()));
            }
        }
        return list;
    }

    @NonNull public static MarkerOptions create(@NonNull Location location, @NonNull String title)
    {
        return new MarkerOptions()
                .position(new LatLng(location.getLat(), location.getLon()))
                .title(title);
    }
}
