package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SuggestedBoundsTest
{
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception
    {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    public void testCanDeserialise() throws Exception
    {
        SuggestedBounds created = mapper.readValue(getClass().getResourceAsStream("suggested_bounds_1.json"), SuggestedBounds.class);
        assertThat(created.getNorthEast()).isEqualTo(Location.create(null, 51.56975559565924, 0.014425992965698242));
        assertThat(created.getSouthWest()).isEqualTo(Location.create(null, 51.4325514045882, -0.3114430904388428));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        SuggestedBounds suggestedBounds = SuggestedBounds.create(
                Location.create("abc", 12, 34),
                Location.create("def", 56, 378));
        assertThat(mapper.writeValueAsString(suggestedBounds))
                .isEqualTo("{\"ne\":{\"address\":\"abc\",\"lat\":12.0,\"lng\":34.0}," +
                        "\"sw\":{\"address\":\"def\",\"lat\":56.0,\"lng\":378.0}}");
    }
}