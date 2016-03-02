package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class VenueTest
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
        Venue created = mapper.readValue(getClass().getResourceAsStream("venue_1.json"), Venue.class);
        assertThat(created.getId()).isEqualTo(VenueId.create("51eabef6498e10cf3aea7942"));
        assertThat(created.getName()).isEqualTo("Brooklyn Bridge Park - Pier 2");
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        Venue venue = Venue.create(VenueId.create("abc"), "def");
        assertThat(mapper.writeValueAsString(venue))
                .isEqualTo("{\"id\":\"abc\",\"name\":\"def\"}");
    }
}