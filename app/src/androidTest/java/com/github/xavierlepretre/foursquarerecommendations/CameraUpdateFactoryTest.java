package com.github.xavierlepretre.foursquarerecommendations;

import com.foursquare.model.Location;
import com.foursquare.model.SuggestedBounds;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CameraUpdateFactoryTest
{
    @Test
    public void testCreateBounds() throws Exception
    {
        LatLngBounds bounds = CameraUpdateFactory.createBounds(
                SuggestedBounds.create(
                        Location.create(null, 34, 45),
                        Location.create(null, 12, 23)));
        assertThat(bounds.northeast.latitude).isEqualTo(34);
        assertThat(bounds.northeast.longitude).isEqualTo(45);
        assertThat(bounds.southwest.latitude).isEqualTo(12);
        assertThat(bounds.southwest.longitude).isEqualTo(23);
    }
}