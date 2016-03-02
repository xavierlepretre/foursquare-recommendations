package com.github.xavierlepretre.foursquarerecommendations;

import com.foursquare.model.Location;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MarkerOptionsFactoryTest
{
    @Test
    public void testCreateOne() throws Exception
    {
        MarkerOptions markerOptions = MarkerOptionsFactory.create(
                Location.create("add", 12, 34), "title");
        assertThat(markerOptions.getTitle()).isEqualTo("title");
    }
}