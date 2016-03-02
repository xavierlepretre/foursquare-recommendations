package com.foursquare.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class VenueIdTest
{
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception
    {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCanDeserialise() throws Exception
    {
        assertThat(mapper.readValue(getClass().getResourceAsStream("venue_id_1.json"), VenueId.class))
                .isEqualTo(VenueId.create("51eabef6498e10cf3aea7942"));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        assertThat(mapper.writeValueAsString(VenueId.create("51eabef6498e10cf3aea7942")))
                .isEqualTo("\"51eabef6498e10cf3aea7942\"");
    }
}